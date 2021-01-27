package org.paguerre.fqoperation.models;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.unitils.reflectionassert.ReflectionAssert;

@RunWith(JUnit4.class)
public class TransporterTest {

	@Test
	public void accessModdifiers() {
		Transporter expected = new Transporter(new Position(new double[] {1.0, 1.0}), "este es");
		Transporter actual = new Transporter(new Position(new double[] {1.0, 1.0}), "este es");
		ReflectionAssert.assertReflectionEquals(expected, actual);
	}
}
