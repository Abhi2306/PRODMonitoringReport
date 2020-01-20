package com.anglo.base;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Date_Diff_Calculation {

	public static void main(String[] args) {
		
		String s = "2014-05-01";
		String e = "2014-06-10";
		LocalDate start = LocalDate.parse(s);
		LocalDate end = LocalDate.parse(e);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
		    totalDates.add(start);
		    start = start.plusDays(1);
		    System.out.println(start);
		}
	}
}
