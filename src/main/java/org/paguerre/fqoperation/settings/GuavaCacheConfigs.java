package org.paguerre.fqoperation.settings;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.paguerre.fqoperation.models.Satellite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

@Configuration
public class GuavaCacheConfigs {
	private final static Logger LOG = LoggerFactory.getLogger(GuavaCacheConfigs.class);

	@Autowired
	private Environment env;

	@Bean(name = "rebelSatellites")
	public LoadingCache<String, Set<Satellite>> rebelSatellitesCache() {
		return CacheBuilder.newBuilder().maximumSize(10000).recordStats().expireAfterWrite(4, TimeUnit.HOURS)
				.removalListener(new RemovalListener<String, Set<Satellite>>() {
					@Override
					public void onRemoval(RemovalNotification<String, Set<Satellite>> notification) {
						LOG.info("Planned removal of key={} from rebelSatellitesCache (Stored value={})",
								notification.getKey(), notification.getValue());
					}
				}).build(new CacheLoader<String, Set<Satellite>>() {
					@Override
					public Set<Satellite> load(String command) throws Exception {
						if (command.equals(env.getProperty("org.paguerre.fqoperation.resource"))) {
							// Expensive load is done here:
							String[] snames = (String[]) env
									.getProperty("org.paguerre.fqoperation.rebel.satellites.names", String[].class);
							String[] spositions = (String[]) env
									.getProperty("org.paguerre.fqoperation.rebel.satellites.positions", String[].class);
							Set<Satellite> rss = new HashSet<>();
							for (int i = 0; i < snames.length; i++) {
								if (StringUtils.isNotEmpty(snames[i])) {
									Satellite rs = new Satellite();
									rs.setName(snames[i]);
									rss.add(rs);
									if (StringUtils.isNotEmpty(spositions[i])) {
										String[] coords = StringUtils.split(spositions[i], ";");
										rs.setPosition(new double[] { Double.parseDouble(coords[0]),
												Double.parseDouble(coords[1]) });
									}
								}
							}
							return rss;
						}
						throw new IllegalArgumentException(
								"Invalid command for loading pre-existent rebel satellites/positions.");
					}
				});
	}
}
