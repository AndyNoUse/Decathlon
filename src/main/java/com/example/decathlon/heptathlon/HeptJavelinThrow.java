package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class HeptJavelinThrow {

	private int score;
	private double A = 15.9803;
	private double B = 3.8;
	private double C = 1.04;

	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on distance and height. Measured in metres.
	public int calculateResult(double distance) throws InvalidResultException {
		// Acceptable values.
		if (distance < 0) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");

		} else if (distance > 110) {

			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}
		score = calc.calculateField(A, B, C, distance);

		System.out.println("The result is: " + score);
		return score;
	}

}
