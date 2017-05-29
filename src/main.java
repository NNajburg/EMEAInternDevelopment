import java.io.IOException;

import emeaImportPack.SqlQuery;
import independantTests.TestCalcul;

public class main {

	public static void main(String[] args) throws IOException {
//		SqlQuery connServer = new SqlQuery();
//		connServer.dbConnect();
		TestCalcul.runAllTests();

	}

}
