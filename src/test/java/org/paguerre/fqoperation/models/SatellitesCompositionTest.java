package org.paguerre.fqoperation.models;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(JUnit4.class)
public class SatellitesCompositionTest {

	@Test
	public void accessModdifiers() {
		Satellite s1 = new Satellite("sato", new double[] { 1.0, 1.0 }, 10.0, new String[] { "este", "un" });
		Satellite s2 = new Satellite("skywalker", new double[] { 3.0, 3.0 }, 30.0,
				new String[] { "este", "", "secreto" });
		SatellitesComposition expected = new SatellitesComposition();
		expected.setSatellites(Stream.of(s1, s2).collect(Collectors.toSet()));
		SatellitesComposition actual = new SatellitesComposition();
		actual.setSatellites(Stream.of(s1, s2).collect(Collectors.toSet()));
		ReflectionAssert.assertReflectionEquals(expected, actual);
	}
}
