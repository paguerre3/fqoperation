package org.paguerre.fqoperation.models;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(JUnit4.class)
public class SpacecraftTest {

	@Test
	public void accessModdifiers() {
		Spacecraft expected = new Spacecraft(new double[] { 1.0, 1.0 }, new String[] { "este", "un" });
		Spacecraft actual = new Spacecraft(new double[] { 1.0, 1.0 }, new String[] { "este", "un" });
		ReflectionAssert.assertReflectionEquals(expected, actual);
		expected = new Spacecraft();
		actual = new Spacecraft();
		ReflectionAssert.assertReflectionEquals(expected, actual);
	}
}
