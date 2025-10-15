package com.example.decathlon.deca;

import com.example.decathlon.common.*;

public class Deca100M {

	private int score;
	private double A = 25.4347;
	private double B = 18;
	private double C = 1.81;

	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) throws InvalidResultException {

		if (runningTime < 5) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (runningTime > 20) {
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}

		score = calc.calculateTrack(A, B, C, runningTime);

		System.out.println("The result is " + score);

		return score;
	}

}
