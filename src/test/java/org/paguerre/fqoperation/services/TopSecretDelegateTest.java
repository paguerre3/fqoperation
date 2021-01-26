package org.paguerre.fqoperation.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.paguerre.fqoperation.models.Satellite;
import org.paguerre.fqoperation.models.SatellitesComposition;
import org.paguerre.fqoperation.models.Transporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TopSecretDelegateTest {

	@Autowired
	@Qualifier("TopSecretSvc")
	TopSecretDelegate topSecretDelegate;

	@Test
	public void findBasic() {
		// satellite's names, positions, distances to space-craft and received
		// messages "must" be passed (fully populated objects are expected in this
		// case). This is the basic handler method where coordinates and messages are
		// resolved in parallel:
		Satellite rb1, rb2;
		rb1 = new Satellite("kenobi", new double[] { -500.0, -200.0 }, 100.0,
				new String[] { "este", "", "", "mensaje", "" });
		rb2 = new Satellite("skywalker", new double[] { 100.0, -100.0 }, 115.5,
				new String[] { "", "es", "un", "", "secreto" });
		Set<Satellite> satellites = new HashSet<>();
		satellites.add(rb1);
		satellites.add(rb2);
		Transporter t = topSecretDelegate.find(satellites);
		assertNotNull(t);
		assertNotNull(t.getPosition());
		assertEquals("este es un mensaje secreto", t.getMessage());
		double[] expectedCoordinate = new double[] { -216.0, -153.0 };
		assertEquals(expectedCoordinate[0], Math.floor(t.getPosition().getX()));
		assertEquals(expectedCoordinate[1], Math.floor(t.getPosition().getY()));
	}

	@Test
	public void findWithPreloadedCoordinates() {
		// "this" scenario assumes satellite's positions already saved in cache (common
		// case):
		SatellitesComposition sc = new SatellitesComposition();
		Set<Satellite> satellites = new HashSet<>();
		sc.setSatellites(satellites);
		satellites.add(new Satellite("kenobi", null, 100.0, new String[] { "este", "", "", "mensaje", "" }));
		satellites.add(new Satellite("skywalker", null, 115.5, new String[] { "", "es", "", "", "secreto" }));
		satellites.add(new Satellite("sato", null, 142.7, new String[] { "este", "", "un", "", "" }));
		Transporter t = topSecretDelegate.find(sc);
		assertNotNull(t);
		assertNotNull(t.getPosition());
		assertEquals("este es un mensaje secreto", t.getMessage());
		double[] expectedCoordinate = new double[] { -59.0, -70.0 };
		assertEquals(expectedCoordinate[0], Math.floor(t.getPosition().getX()));
		assertEquals(expectedCoordinate[1], Math.floor(t.getPosition().getY()));
	}

	@Test
	public void notFound() {
		SatellitesComposition sc = new SatellitesComposition();
		Set<Satellite> satellites = new HashSet<>();
		sc.setSatellites(satellites);
		satellites.add(new Satellite("kepler", null, 142.7, new String[] { "este", "", "un", "", "" }));
		assertNull(topSecretDelegate.find(sc));
		sc.setSatellites(null);
		assertNull(topSecretDelegate.find(sc));
		sc = null;
		assertNull(topSecretDelegate.find(sc));
	}
}
