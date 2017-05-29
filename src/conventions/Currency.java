package conventions;

public enum Currency {
USD(0),
EUR(1),
JPY(2),
GBP(3),
CHF(4);
	
protected int liquidityIndice;

	Currency(int _liquidity) {
		this.liquidityIndice = _liquidity;
	}

	//Returns the most liquid currency
	public Currency getMostLiquid(Currency _curr1, Currency _curr2) {
		return (Math.min(_curr1.liquidityIndice, _curr2.liquidityIndice) == _curr1.liquidityIndice) ? _curr1 : _curr2;
	}
}
