package org.paguerre.fqoperation.models;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(JUnit4.class)
public class SatelliteTest {

	@Test
	public void accessModdifiers() {
		Satellite expected = new Satellite("sato", new double[] { 1.0, 1.0 }, 10.0, new String[] { "este", "un" });
		Satellite actual = new Satellite("sato", new double[] { 1.0, 1.0 }, 10.0, new String[] { "este", "un" });
		ReflectionAssert.assertReflectionEquals(expected, actual);
		expected = new Satellite("sato", new double[] { 1.0, 1.0 }, 10.0);
		actual = new Satellite("sato", new double[] { 1.0, 1.0 }, 10.0);
		ReflectionAssert.assertReflectionEquals(expected, actual);
		expected = new Satellite();
		actual = new Satellite();
		ReflectionAssert.assertReflectionEquals(expected, actual);
	}

	@Test
	public void equalsSemantic() {
		Satellite expected = new Satellite("sato", new double[] { 1.0, 1.0 }, 10.0, new String[] { "este", "un" });
		Satellite actual = new Satellite("sato", new double[] { 2.0, 2.0 }, 20.0);
		assertEquals(expected, actual);
		assertTrue(expected.hashCode() == actual.hashCode());
	}
}
