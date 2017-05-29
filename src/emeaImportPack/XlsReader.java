package emeaImportPack;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import reporting.MacroReport;


public class XlsReader {
	String filePath = "";
	String fileName = "";
	String fileSheet = "";
	int refLine = 1;
	String[] columnNames;
	Map<Integer, String[]> attributeValues;

	@SuppressWarnings("resource")
	public XlsReader(String path, String fileSheet, int refLine, boolean isImportRule)
			throws InvalidFormatException, IOException {
		this.filePath = path;
		this.refLine = refLine;
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		this.fileName = file.getName();
		this.fileSheet = fileSheet;
		XSSFWorkbook workBook = new XSSFWorkbook(inputFile);
		XSSFSheet sheet = workBook.getSheet(fileSheet);
		Row refRow = sheet.getRow(refLine);
		int nbColumns = refRow.getLastCellNum();
		int nbRows = sheet.getLastRowNum();
		this.columnNames = readRow(refRow, nbColumns, isImportRule);
		this.attributeValues = readSheet(sheet, refLine, isImportRule);
	}
	
	public static Map<Integer, String[]> readSheet(XSSFSheet sheet, int nbFirstRow,
			boolean isImportRule) throws IOException {
		Row refRow = sheet.getRow(nbFirstRow);
		int nbColumns = refRow.getLastCellNum();
		int nbRows = sheet.getLastRowNum();
		Map<Integer, String[]> rows = new HashMap<Integer, String[]>(nbColumns + 1);
		for (int i = 0; i < nbRows - nbFirstRow + 1; i++) {
			try {
				rows.put(i, readRow(sheet.getRow(i + nbFirstRow), nbColumns, isImportRule));
			} catch (Exception e) {
				MacroReport.writeLine("ErrorLog","ERROR : Cannot read row " + i + nbFirstRow + " in "+ sheet);
			}
		}
		return rows;
	}
	
	private static String[] readRow(Row xlsRow, int nbColumns, boolean isImportRule) {
		String[] row = new String[nbColumns];
		for (int i = 0; i < nbColumns; i++) {
			Cell cell = xlsRow.getCell(i + 1);
			if (cell != null) {
  			try {
  				switch (cell.getCellType()) {
  				case (Cell.CELL_TYPE_BLANK):
  					row[i] = "0";
  					break;
  				case (Cell.CELL_TYPE_BOOLEAN):
  					row[i] = String.valueOf(cell.getBooleanCellValue());
  					break;
  				case (Cell.CELL_TYPE_ERROR):
  					row[i] = "ERROR";
  					break;
  				case (Cell.CELL_TYPE_FORMULA):
  					try {
  						row[i] = String.valueOf(cell.getNumericCellValue());
  					} catch (Exception e1) {
  						try {
  							row[i] = getCorrectStringCell(cell);
    					} catch (Exception e2) {
    						MacroReport.writeLine("ErrorLog","XlsReader.readRow - Cannot load the formula cell " + cell.getCellFormula());
    					}
  					}
  					break;
  				case (Cell.CELL_TYPE_NUMERIC):
  					row[i] = String.valueOf(cell.getNumericCellValue());
  					break;
  				default:
  					row[i] = getCorrectStringCell(cell);
  					break;
  				}
  			} catch (Exception e) {
  				row[i] = "";
  				e.printStackTrace();
  			}
			}
		}
		return row;
	}
	
	public static String getCorrectStringCell(Cell cell) {
		String txt = cell.getStringCellValue();
		txt.replace("é", "e");
		txt.replace("è", "e");
		txt.replace("ê", "e");
		txt.replace("à", "a");
		return txt;
	}

}
