package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class Deca400M {

	private int score;
	private double A = 1.53775;
	private double B = 82;
	private double C = 1.81;


	CalcTrackAndField calc = new CalcTrackAndField();
	InputResult inputResult = new InputResult();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) throws InvalidResultException {
		// Acceptable values.
		if (runningTime < 20) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (runningTime > 100) {
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");
		}
		score = calc.calculateTrack(A, B, C, runningTime);
		System.out.println("The result is: " + score);
		return score;
	}

}
