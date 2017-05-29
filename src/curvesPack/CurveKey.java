package curvesPack;

import conventions.BrokenPeriod;
import conventions.BusinessDay;
import conventions.Currency;
import conventions.CurveConvention;
import conventions.DayCount;
import conventions.RateConvention;
import conventions.Tenor;

public class CurveKey {

	public DayCount daycountDiscount;
	public BusinessDay businessConvention;
	public RateConvention rateConventionZC;
	public BrokenPeriod brokenConvention;
	public RateConvention rateConventionFWD;
	public DayCount daycountFWD;
	public DayCount dayCountFixed;
	public Currency currency;
	public Tenor tenorFixedLed;
	
	CurveKey(DayCount _daycountDiscount, BusinessDay _businessConvention, RateConvention _rateConventionZC,
			BrokenPeriod _brokenConvention, Currency _currency, RateConvention _rateConventionFWD, 
			DayCount _daycountFWD, Tenor _tenorFixedLed, DayCount _dayCountFixed){
		this.daycountDiscount = _daycountDiscount;
		this.businessConvention = _businessConvention;
		this.rateConventionZC = _rateConventionZC;
		this.brokenConvention = _brokenConvention;
		this.rateConventionFWD = _rateConventionFWD;
		this.daycountFWD = _daycountFWD;
		this.dayCountFixed = _dayCountFixed;
		this.currency = _currency;
		this.tenorFixedLed = _tenorFixedLed;
	}
	CurveKey(CurveConvention _otherCurveConvention) {
		this.daycountDiscount = _otherCurveConvention.getDaycountDiscount();
		this.businessConvention = _otherCurveConvention.getBusinessConvention();
		this.rateConventionZC = _otherCurveConvention.getRateConventionZC();
		this.brokenConvention = _otherCurveConvention.getBrokenConvention();
		this.rateConventionFWD = _otherCurveConvention.getRateConventionFWD();
		this.daycountFWD = _otherCurveConvention.getDaycountFWD();
		this.dayCountFixed = _otherCurveConvention.getDayCountFixed();
		this.currency = _otherCurveConvention.getCurrency();
		this.tenorFixedLed = _otherCurveConvention.getTenorFixedLed();
	}
	
}
