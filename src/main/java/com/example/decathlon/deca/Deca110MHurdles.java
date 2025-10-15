package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class Deca110MHurdles {

	private int score;
	private double A = 5.74352;
	private double B = 28.5;
	private double C = 1.92;
	boolean active = true;
	CalcTrackAndField calc = new CalcTrackAndField();
	InputResult inputResult = new InputResult();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) throws InvalidResultException {

		// Acceptable values.
		if (runningTime < 10) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (runningTime > 30) {
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}
		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is " + score);

		return score;
	}

}
