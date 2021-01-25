package org.paguerre.fqoperation.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.paguerre.fqoperation.models.Position;
import org.paguerre.fqoperation.models.Satellite;
import org.paguerre.fqoperation.models.SatellitesComposition;
import org.paguerre.fqoperation.models.Transporter;
import org.paguerre.fqoperation.services.TopSecretDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class TopSecretControllerTest {

	@MockBean
	@Qualifier("TopSecretSvc")
	TopSecretDelegate topSecretDelegate;

	@Autowired
	MockMvc mvc;

	@Autowired
	TopSecretController topSecretController;

	static MediaType mType;
	static ObjectMapper objMapper;
	static Satellite rb1;
	static Transporter t1;

	@BeforeAll
	public static void init() {
		mType = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
		objMapper = new ObjectMapper();
		rb1 = new Satellite("kenoby", new double[] { -500.0, -200.0 }, 100.0);
		t1 = new Transporter(new Position(new double[] { 7.0, 14.5 }), "este es un msg");
	}

	@Test
	public void processTopSecretOk() throws Exception {
		given(topSecretDelegate.find(ArgumentMatchers.any(SatellitesComposition.class))).willReturn(t1);
		SatellitesComposition sc = new SatellitesComposition();
		Set<Satellite> satellites = new HashSet<>();
		sc.setSatellites(satellites);
		satellites.add(rb1);
		mvc.perform(post("/v2/topsecret").accept(mType).contentType(mType).content(objMapper.writeValueAsString(sc)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.position.x", is(7.0)))
				.andExpect(jsonPath("$.position.y", is(14.5))).andExpect(jsonPath("$.message", is("este es un msg")));
	}

	@Test
	public void processTopSecretNotFound() throws Exception {
		given(topSecretDelegate.find(ArgumentMatchers.any(SatellitesComposition.class))).willReturn(null);
		SatellitesComposition sc = new SatellitesComposition();
		Set<Satellite> satellites = new HashSet<>();
		sc.setSatellites(satellites);
		satellites.add(rb1);
		mvc.perform(post("/v2/topsecret").accept(mType).contentType(mType).content(objMapper.writeValueAsString(sc)))
				.andExpect(status().isNotFound());
	}
}
