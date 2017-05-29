package reporting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;



public class MacroReport {
	Map<String, Report> list = new HashMap<String, Report>();

	// ***************************** MACRO CONSTRUCTOR********************************
	private static MacroReport INSTANCE = null;

	public static MacroReport createInstance() {
		INSTANCE = new MacroReport();
		return INSTANCE;
	}
	
	public static MacroReport getInstance() {
		if (MacroReport.class.isInstance(INSTANCE) == false) {
			createInstance();
		}
		return INSTANCE;
	}
	// ***************************************************************************

	// ***************************OBJECT CONSTRUCTOR******************************
	static public void createReport(String id, String path) throws IOException {
		MacroReport.getInstance().list.put(id, new Report(id, path, path + "//" + id + ".csv"));
	}
	
	static public void createReportText(String id, String path) throws IOException {
		MacroReport.getInstance().list.put(id, new Report(id, path, path + "//" +  id + ".txt"));
	}


	// *****************************************************************************
	public Report get(String id) {
		return this.list.get(id);
	}

	static public void writeText(String id, String text) throws IOException {
		MacroReport reportContainer = MacroReport.getInstance();
		if (!reportContainer.list.containsKey(id)) {
			ReportManager.createLocalReport(id);
		}
		MacroReport.getInstance().get(id).writeText(text);
	}

	static public void writeLine(String id, String line) throws IOException {
		MacroReport reportContainer = MacroReport.getInstance();
		if (!reportContainer.list.containsKey(id)) {
			ReportManager.createLocalReport(id);
		}
		MacroReport.getInstance().get(id).writeLine(line);
	}

	static public void writeVector(String id, String text, double[] vector, String separator)
			throws IOException {
		MacroReport reportContainer = MacroReport.getInstance();
		if (!reportContainer.list.containsKey(id)) {
			ReportManager.createLocalReport(id);
		}
		Report report = MacroReport.getInstance().get(id);
		report.writeText("\r\n" + text ); //+ ";"
		report.writeVector(vector, separator);
		report.buffer.flush();
	}
	
	static public void writeVector(String id, String text, double[] vector, String separator, int endIndex)
			throws IOException {
		MacroReport reportContainer = MacroReport.getInstance();
		if (!reportContainer.list.containsKey(id)) {
			ReportManager.createLocalReport(id);
		}
		Report report = MacroReport.getInstance().get(id);
		report.writeText("\r\n" + text + ";");
		report.writeVector(vector, separator, endIndex);
		report.buffer.flush();
	}

	static public void writeVector(String id, String text, String[] vector, String separator)
			throws IOException {
		Report report = MacroReport.getInstance().get(id);
		report.writeText("\r\n" + text + ";");
		report.writeVector(vector, separator);
	}
	
	static public void close(String id) throws IOException {
		MacroReport.getInstance().get(id).close();
	}

	public void closeAll() throws IOException {
		for (Entry<String, Report> entry : this.list.entrySet()) {
			entry.getValue().buffer.flush();
			entry.getValue().close();
		}
	}
}
