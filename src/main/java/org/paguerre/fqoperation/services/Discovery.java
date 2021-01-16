package org.paguerre.fqoperation.services;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

@Service
public class Discovery {

	protected double[] getLocation(double[][] coordinates, double... distances) {
		return new NonLinearLeastSquaresSolver(new TrilaterationFunction(coordinates, distances),
				new LevenbergMarquardtOptimizer()).solve().getPoint().toArray();
	}
}
