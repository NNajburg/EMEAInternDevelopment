package containers;

import java.util.HashMap;
import java.util.Map;

import curvesPack.Curve;
import curvesPack.CurveKey;

public class CurvesContainer {
	Map<CurveKey, Curve> list = new HashMap<CurveKey, Curve>();
	
	private static CurvesContainer INSTANCE = null;
	
	public static CurvesContainer createInstance() {
		INSTANCE = new CurvesContainer();
		return INSTANCE;
	}

	public static CurvesContainer getInstance() {
		if (CurvesContainer.class.isInstance(INSTANCE) == false) {
			createInstance();
		}
		return INSTANCE;
	}
	
}
