package reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Report {
	String reportId;
	String filePath;
	File file;
	FileWriter writer;
	BufferedWriter buffer;
	
	
	public Report(String id,String directoryPath, String path) throws IOException {
		this.reportId = id;
		this.filePath = path;
		new File(directoryPath).mkdir();
		this.file = new File(this.filePath);
		

		
		this.writer = new FileWriter(this.file);
		this.buffer = new BufferedWriter(this.writer);
	}
	
	public void writeText(String text) throws IOException {
		this.buffer.write(text.replace(".", ","));
		this.buffer.flush();
	}

	public void writeLine(String line) throws IOException {
		this.writeText("\r\n" + line.replace(".", ","));
	}
	
	public void writeVector(String[] vector, String separator) throws IOException {
		this.buffer.write( vector[0] );
		for (int i = 1; i < vector.length; i++) {
			this.writeText(separator + vector[i]);
		}
	}
	
	public void writeVector(double[] vector, String separator, int endIndex) throws IOException {
		String text = Double.toString(vector[0]);
		this.writeText( text );
		if (vector.length > 1) {
  		for (int i = 1; i < endIndex; i++) {
  			try {
  				text = Double.toString(vector[i]);
  			} catch (Exception e) {
  				text = "0";
  			}
  			this.writeText( separator + text );
  		}
		}
	}
	
	public void writeVector(double[] vector, String separator) throws IOException {
		String text = Double.toString(vector[0]);
		this.writeText( text );
		if (vector.length > 1) {
  		for (int i = 1; i < vector.length; i++) {
  			try {
  				text = Double.toString(vector[i]);
  			} catch (Exception e) {
  				text = "0";
  			}
  			this.writeText( separator + text );
  		}
		}
	}
	
	public void close() throws IOException {
		this.buffer.flush();
		this.writer.close();
	}
}
