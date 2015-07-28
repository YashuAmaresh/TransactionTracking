import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TransactionTable {
	ArrayList<TransactionRow> trTable= new ArrayList<TransactionRow>();
	
	void insertInTable() throws IOException{
		try(BufferedReader br = new BufferedReader(new FileReader("table.txt"))) {
		    for(String line; (line = br.readLine()) != null; ) {
		    	String columnArray[] = line.split(" ");
		        TransactionRow row = new TransactionRow();
		        row.rangeObj.lo = Integer.parseInt(columnArray[0]);
		        row.rangeObj.hi = Integer.parseInt(columnArray[1]);
		        row.statusCode = columnArray[2].charAt(0);
		        row.transferCode = Integer.parseInt(columnArray[3]);
		    }
		    
		}
	}
	
}
