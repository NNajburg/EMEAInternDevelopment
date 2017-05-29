package conventions;

// These convention were found in the market date multicurve. there are specifique of Bloomberg
public enum CurveConvention {

	Forward3M_OISBased(DayCount.ActAct, BusinessDay.ModifiedFollowing, RateConvention.Actuarial, BrokenPeriod.Start,
			Currency.EUR, RateConvention.Actuarial, DayCount.Act360, Tenor.annual, DayCount.Act360),
	Forward6M_OISBased(Forward3M_OISBased),
	Forward1M_OISBased(Forward3M_OISBased),
	Forward12M_OISBased(Forward3M_OISBased),
	Forward6MBased3M(Forward3M_OISBased),
	Forward3MBased6M(Forward3M_OISBased),
	Forward1MBased3M(Forward3M_OISBased),
	Forward12MBased3M(Forward3M_OISBased);
	

	protected DayCount daycountDiscount;
	protected BusinessDay businessConvention;
	protected RateConvention rateConventionZC;
	protected BrokenPeriod brokenConvention;
	protected RateConvention rateConventionFWD;
	protected DayCount daycountFWD;
	protected DayCount dayCountFixed;
	protected Currency currency;
	protected Tenor tenorFixedLed;
	
	CurveConvention(DayCount _daycountDiscount, BusinessDay _businessConvention, RateConvention _rateConventionZC,
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
	CurveConvention(CurveConvention _otherCurveConvention) {
		this.daycountDiscount = _otherCurveConvention.daycountDiscount;
		this.businessConvention = _otherCurveConvention.businessConvention;
		this.rateConventionZC = _otherCurveConvention.rateConventionZC;
		this.brokenConvention = _otherCurveConvention.brokenConvention;
		this.rateConventionFWD = _otherCurveConvention.rateConventionFWD;
		this.daycountFWD = _otherCurveConvention.daycountFWD;
		this.dayCountFixed = _otherCurveConvention.dayCountFixed;
		this.currency = _otherCurveConvention.currency;
		this.tenorFixedLed = _otherCurveConvention.tenorFixedLed;
	}
	public BrokenPeriod getBrokenConvention() {
		return brokenConvention;
	}
	public void setBrokenConvention(BrokenPeriod brokenConvention) {
		this.brokenConvention = brokenConvention;
	}
	public DayCount getDaycountDiscount() {
		return daycountDiscount;
	}
	public BusinessDay getBusinessConvention() {
		return businessConvention;
	}
	public RateConvention getRateConventionZC() {
		return rateConventionZC;
	}
	public RateConvention getRateConventionFWD() {
		return rateConventionFWD;
	}
	public DayCount getDaycountFWD() {
		return daycountFWD;
	}
	public DayCount getDayCountFixed() {
		return dayCountFixed;
	}
	public Currency getCurrency() {
		return currency;
	}
	public Tenor getTenorFixedLed() {
		return tenorFixedLed;
	}
}
