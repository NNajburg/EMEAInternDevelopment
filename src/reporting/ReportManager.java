package reporting;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import emeaImportPack.GlobalConstants;
import reporting.MacroReport;

public class ReportManager {

	public String timestamp;
	public String inputPath = "";
	public String outputPath = "";
	private static ReportManager INSTANCE = null;
	
	// *************************MACRO CONSTRUCTOR*****************************


	public static ReportManager createInstance() {
		
		INSTANCE = new ReportManager();
		INSTANCE.setPath(GlobalConstants.inputPath,GlobalConstants.inputPath);
		return INSTANCE;
	}
	public static ReportManager getInstance() {
		if (ReportManager.class.isInstance(INSTANCE) == false) {
			createInstance();
		}
		return INSTANCE;
	}
	// ***************************************************************************

	
	
	public void setPath(String inputPath, String outputPath) {
		this.timestamp = "Date_" + LocalDate.now();
		this.outputPath = outputPath + this.timestamp + "/";
		this.inputPath = inputPath;
		File createFolder = new File(this.outputPath );
	}

	public static String getInputPath() {
		return ReportManager.getInstance().inputPath;
	}

	public static String getOutputPath() {
		return ReportManager.getInstance().outputPath;
	}
	public static void createLocalReport(String reportName) throws IOException {

		String columnTitles = ""; //set header here

		if (!reportName.contains("Log")) {
			MacroReport.createReport(reportName, getOutputPath());
			MacroReport.writeText(reportName, columnTitles);
		} else {
			MacroReport.createReportText(reportName, getOutputPath());
		}
	}

	public static void writeInLocalReport(String reportName, String text) throws IOException {
		try {
			MacroReport.writeText(reportName, text);
		} catch (Exception e) {
		}
	}

	public static void writeInLocalReport(String reportName, String text, double[] vector)
			throws IOException {
		if (reportName.length() > 1) {
			MacroReport.writeVector(reportName, text, vector, ";");
		}
	}

	public static void writeInLocalReport(String reportName, String text, String[] vector)
			throws IOException {
		if (reportName.length() > 1) {
			MacroReport.writeVector(reportName, text, vector, ";");
		}
	}

	public static void closeLocalReport(String reportName) throws IOException {
		MacroReport.close(reportName);
	}

}
