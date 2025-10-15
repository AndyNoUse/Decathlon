package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class DecaHighJump {

	private int score;
	private double A = 0.8465;
	private double B = 75;
	private double C = 1.42;
	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on distance and height. Measured in centimeters.
	public int calculateResult(double distance) throws InvalidResultException {

		// Acceptable values.
		if (distance < 0) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (distance > 300) {
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}

		score = calc.calculateField(A, B, C, distance);


		System.out.println("The result is: " + score);
		return score;
	}

}