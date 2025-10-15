package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class DecaLongJump {

	private int score;
	private double A = 0.14354;
	private double B = 220;
	private double C = 1.4;
	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on distance and height. Measured in centimetres.
	public int calculateResult(double distance) throws InvalidResultException {

		// Acceptable values.
		if (distance < 0) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (distance > 1000) {

			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}
		score = calc.calculateField(A, B, C, distance);
		System.out.println("The result is: " + score);
		return score;
	}

}
