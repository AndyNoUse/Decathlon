package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class DecaDiscusThrow {

	private int score;
	private double A = 12.91;
	private double B = 4;
	private double C = 1.1;
	boolean active = true;
	CalcTrackAndField calc = new CalcTrackAndField();


	// Calculate the score based on distance and height. Measured in meters.
	public int calculateResult(double distance) throws InvalidResultException {


		// Acceptable values.
		if (distance < 0) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");

		} else if (distance > 85) {
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}

		score = calc.calculateField(A, B, C, distance);
		System.out.println("The result is: " + score);
		return score;
	}

}
