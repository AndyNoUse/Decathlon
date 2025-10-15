package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class Hep200M {

	private int score;
	private double A = 4.99087;
	private double B = 42.5;
	private double C = 1.81;
	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) throws InvalidResultException {

		// Acceptable values.
		if (runningTime < 20) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (runningTime > 100) {
			// get 1 point in 42.08sec
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");

		}
		score = calc.calculateTrack(A, B, C, runningTime);

		System.out.println("The result is " + score);
		return score;
	}

}
