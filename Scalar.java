package utils;

import java.time.*;
import java.time.temporal.ChronoUnit;

import conventions.BusinessDay;
import conventions.DayCount;
import conventions.RateConvention;

public class Scalar {

	public static double yearFrac(LocalDate _date1, LocalDate _date2, DayCount _convention) {
		
		switch(_convention) {
		case Act360 :
			return  ChronoUnit.DAYS.between(_date2,_date1) / 360.0;
		case Act365 :
			return ChronoUnit.DAYS.between(_date2,_date1) / 365.0;
		case Act366 :
			return ChronoUnit.DAYS.between(_date2,_date1) / 366.0;
		case ActAct :
			if (_date1.getYear() == _date2.getYear()) {
				return ChronoUnit.DAYS.between(_date2,_date1)/daysInYear(_date1);
			} else {
				return daysToEnd(_date1)/daysInYear(_date1) +
						daysFromStart(_date2)/daysInYear(_date2) +
						_date2.getYear() - _date1.getYear() - 1 ;  // Formule du fichier Multicurve
			}
		case trente360 :
			return (Math.max(30 - _date1.getDayOfMonth(), 0) + 
					Math.min(30, _date2.getDayOfMonth()) +
					360 * (_date2.getYear() - _date1.getYear()) +
					30 * (_date2.getMonthValue() - _date1.getMonthValue() - 1 )) / 360;
			default :
				return 1.0; //Note that this should never ever happen !
		}

	}

	public static LocalDate businessDay(LocalDate _date, BusinessDay _convention) {
		switch (_convention) {
		case Following :
			if (_date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				return _date.plusDays(1);
			} else if  (_date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
				return _date.plusDays(2);
			} else {
				return _date;
			}
		case Preceding :
			if (_date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				return _date.minusDays(2);
			} else if  (_date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
				return _date.minusDays(1);
			} else {
				return _date;
			}
		case ModifiedFollowing :
			if (_date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
				if (_date.plusDays(1).getMonth().equals(_date.getMonth())) { //On ne change pas de mois
					return _date.plusDays(1);
				} else {
					return _date.minusDays(2); // On change de mois
				}
			} else if (_date.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
				if (_date.plusDays(2).getMonth().equals(_date.getMonth())) { //On ne change pas de mois
					return _date.plusDays(2);
				} else {
					return _date.minusDays(1); // On change de mois
				}
			}
		default :
			return _date; //Note that this should never ever happen !
		}
		
	}
	
	private static double daysInYear(LocalDate _date1) {
		return _date1.isLeapYear() ? 366.0 : 365.0;
	} 
	
	private static double daysToEnd(LocalDate _date) {
		return ChronoUnit.DAYS.between(_date,_date.withMonth(12).withDayOfMonth(31));
	}
	
	private static double daysFromStart(LocalDate _date) {
		return ChronoUnit.DAYS.between(_date.withMonth(1).withDayOfMonth(1),_date);
	}
	
	public static double ZC_DF(LocalDate _valuationDate, LocalDate _dateZC, double _ZC, 
			 RateConvention _rateConvention, DayCount _dayCount){
		
		if(_rateConvention==RateConvention.Linear){
			return (1/(1+_ZC*yearFrac( _valuationDate, _dateZC, _dayCount)));
		}
		else if (_rateConvention==RateConvention.Actuarial){
			return (1/(1+Math.pow(_ZC, yearFrac( _valuationDate, _dateZC, _dayCount)))); 
		}
		else {
			return (Math.exp(-_ZC*yearFrac( _valuationDate, _dateZC, _dayCount)));
		}
	}
	
	public static double DF_ZC(LocalDate _valuationDate, LocalDate _dateDF, double _DF,
			RateConvention _rateConvention, DayCount _dayCount){
		
		if(_rateConvention==RateConvention.Linear){
			return ((1/yearFrac(_valuationDate, _dateDF, _dayCount))*(1/_DF-1));
		}
		else if (_rateConvention==RateConvention.Actuarial){
			return (Math.pow(_DF,(-1/yearFrac(_valuationDate, _dateDF, _dayCount)))-1); 
		}
		else {
			return (-Math.log(_DF)/yearFrac(_valuationDate, _dateDF, _dayCount));
		}
	}
	
	public static double linearInterpolation(LocalDate _date1, LocalDate _date2, double _rate1,
			double _rate2, LocalDate _interpolationDate){
		return (_rate1+(ChronoUnit.DAYS.between(_interpolationDate,_date1))*(_rate2 - _rate1)/(ChronoUnit.DAYS.between(_date2,_date1)));
	}
	
	public static double linearInterpolation(LocalDate _interpolationDate, LocalDate[] _dateVector,
			double[] _rateVector){
		
		if (_interpolationDate.isBefore(_dateVector[0])){
			return (_rateVector[0]);
		}
		else if (_interpolationDate.isAfter(_dateVector[_dateVector.length-1])){
			return (_rateVector[_rateVector.length-1]);
		}
		else {
			int N=0;
			while (_dateVector[N].isBefore(_interpolationDate) && N < _dateVector.length){
				N=N+1;
			}
			return linearInterpolation(_dateVector[N-1], _dateVector[N], _rateVector[N-1], _rateVector[N], _interpolationDate);
		}
	}
}
