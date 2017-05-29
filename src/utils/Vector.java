package utils;

import java.time.LocalDate;
import java.util.ArrayList;

import conventions.BrokenPeriod;
import conventions.BusinessDay;

public class Vector {

	public static LocalDate[] FlowDates ( LocalDate _valuationDate, LocalDate _startDate, LocalDate _endDate,
			int _frequency, BrokenPeriod _brokenConvention, BusinessDay _businessConvention) {
		
		LocalDate StartFlow = maxDate(_startDate, _valuationDate);
		ArrayList<LocalDate> temporaryFlowDates = new ArrayList<LocalDate>();
		
		if (_brokenConvention.equals(BrokenPeriod.Start)) {//On calcule les éléments depuis la fin
			LocalDate temporaryDate = _endDate;
			temporaryFlowDates.add(_endDate);
			while (temporaryDate.minusMonths(_frequency).isAfter(StartFlow)) {
				temporaryDate = temporaryDate.minusMonths(_frequency);
				temporaryFlowDates.add(temporaryDate);
			}
			temporaryFlowDates.add(StartFlow);

		} else {//On calcule les éléments depuis le début
			LocalDate temporaryDate = _startDate;
			temporaryFlowDates.add(_startDate);
			while (temporaryDate.plusMonths(_frequency).isBefore(_endDate)) {
				temporaryDate = temporaryDate.plusMonths(_frequency);
				temporaryFlowDates.add(temporaryDate);
			}
			temporaryFlowDates.add(_endDate);
		}
		//On utilise la liste pour remplir le tableau. 
		LocalDate[] finalFlowDates = new LocalDate[temporaryFlowDates.size()];
		if (_brokenConvention.equals(BrokenPeriod.Start)) {
			for(int i = finalFlowDates.length -1 ; i>=0; i--) {
				finalFlowDates[i] = Scalar.businessDay(temporaryFlowDates.get(temporaryFlowDates.size()-i-1),_businessConvention);
			}
		} else {
			for (int i = 0; i < finalFlowDates.length; i++) {
				finalFlowDates[i] = Scalar.businessDay(temporaryFlowDates.get(i),_businessConvention);
			}
		}
		return finalFlowDates;
	}

	private static LocalDate maxDate(LocalDate _date1, LocalDate _date2) { //Returns the latest date
		return _date1.isAfter(_date2) ? _date1 : _date2;
	}
}
