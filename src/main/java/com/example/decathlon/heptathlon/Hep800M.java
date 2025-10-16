package com.example.decathlon.heptathlon;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;
import com.example.decathlon.common.*;

public class Hep800M {

	private int score;
	private double A = 0.11193;
	private double B = 254;
	private double C = 1.88;
	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) throws InvalidResultException {
		// Acceptable values.
		if (runningTime < 70) {
			System.out.println("Value too low");
			throw new InvalidResultException("Value too low");
		} else if (runningTime > 250) {
			// competitor gets 1 point in 250.79 sec
			System.out.println("Value too high");
			throw new InvalidResultException("Value too high");}
		score = calc.calculateTrack(A, B, C, runningTime);

		System.out.println("The result is " + score);
		return score;
	}

}
