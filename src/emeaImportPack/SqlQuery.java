package emeaImportPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlQuery {
	
	public void dbConnect() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection( GlobalConstants.serverName,
					GlobalConstants.userID, GlobalConstants.password);
			System.out.println("connected");
			Statement statement = conn.createStatement();
			String queryString = "SELECT  I.Identifier, MN_CP.value AS 'Close Price'," + 
			"MT_IID.Value AS 'Instrument ID', MT_SD.Value AS 'Security Description', " + 
			"MD_MD.Value AS 'Maturity Date'  "  +
 
						"FROM Instrument AS I " +
						"LEFT JOIN Marketdata_Numeric AS MN_CP ON (I.InstrumentID=MN_CP.InstrumentID AND MN_CP.Fieldname='Close Price')" +
						"LEFT JOIN Marketdata_Text AS MT_IID ON (I.InstrumentID=MT_IID.InstrumentID AND MT_IID.Fieldname='Instrument ID' AND MT_IID.Valuetimestamp=MN_CP.Valuetimestamp)" +
						"LEFT JOIN Marketdata_Text AS MT_SD ON (I.InstrumentID=MT_SD.InstrumentID AND MT_SD.Fieldname='Security Description' AND MT_SD.Valuetimestamp=MN_CP.Valuetimestamp) " +
						"LEFT JOIN Marketdata_Date AS MD_MD ON (I.InstrumentID=MD_MD.InstrumentID AND MD_MD.Fieldname='Maturity Date' AND MD_MD.Valuetimestamp=MN_CP.Valuetimestamp) " +
						"WHERE CAST(MN_CP.Valuetimestamp AS Date)='2017-02-15' AND MT_IID.Value='0#EURZ=R'" +

						"ORDER BY MD_MD.Value";
						
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
