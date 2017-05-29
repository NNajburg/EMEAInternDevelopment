package independantTests;

import java.time.LocalDate;

import conventions.BrokenPeriod;
import conventions.BusinessDay;
import utils.Vector;

public class TestCalcul {

	public static void runAllTests() {
		
		String result = "";
		result += testFlowDates() ? "" : "il y a une coquille dans la génération de l'échéancier";
		
		System.out.println(result);
	}
	public static Boolean testFlowDates(){
		
		LocalDate[] resultFlowDatesFunction = Vector.FlowDates(LocalDate.parse("2015-02-10"),
				LocalDate.parse("2015-02-10"), LocalDate.parse("2017-09-24"), 1, BrokenPeriod.Start,
				BusinessDay.Following);
		LocalDate[] resultExcel = {LocalDate.parse("2015-02-10"), LocalDate.parse("2015-02-24"), LocalDate.parse("2015-03-24"),
				LocalDate.parse("2015-04-24"), LocalDate.parse("2015-05-25"), LocalDate.parse("2015-06-24"),
				LocalDate.parse("2015-07-24"), LocalDate.parse("2015-08-24"), LocalDate.parse("2015-09-24"),
				LocalDate.parse("2015-10-26"), LocalDate.parse("2015-11-24"), LocalDate.parse("2015-12-24"),
				LocalDate.parse("2016-01-25"), LocalDate.parse("2016-02-24"), LocalDate.parse("2016-03-24"),
				LocalDate.parse("2016-04-25"), LocalDate.parse("2016-05-24"), LocalDate.parse("2016-06-24"),
				LocalDate.parse("2016-07-25"), LocalDate.parse("2016-08-24"), LocalDate.parse("2016-09-26"),
				LocalDate.parse("2016-10-24"), LocalDate.parse("2016-11-24"), LocalDate.parse("2016-12-26"),
				LocalDate.parse("2017-01-24"), LocalDate.parse("2017-02-24"), LocalDate.parse("2017-03-24"),
				LocalDate.parse("2017-04-24"), LocalDate.parse("2017-05-24"), LocalDate.parse("2017-06-26"),
				LocalDate.parse("2017-07-24"), LocalDate.parse("2017-08-24"), LocalDate.parse("2017-09-25")};
		
		Boolean isCorrect = true;
		for (int i = 0; i < resultFlowDatesFunction.length; i++) {
			isCorrect = isCorrect & (resultFlowDatesFunction[i].isEqual(resultExcel[i]));
		}
		
		return isCorrect;
	}
	
	
}
