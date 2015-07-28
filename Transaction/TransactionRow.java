import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


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
	
	void insertInOutputTable(TransactionRow tr)
	{
		for(int i=0 ;i<trTable.size()-1;i++)
		{
			for(int j= i + 1;j > 0 ;j--){
				checkRelation(trTable.get(i), trTable.get(j), i, j);
			}
		}
	}
	
	void checkRelation(TransactionRow row1, TransactionRow row2, int i, int j)
	{
		if (row1.statusCode == row2.statusCode && row1.transferCode == row2.transferCode) {
			if (!row1.rangeObj.isDisjoint(row2.rangeObj)) {
				row1.mergeRows(row2);
			}
		}
		
		if (row1.rangeObj.classify(row2.rangeObj) == Range.Relation.SAME) {
			row1.statusCode = row2.statusCode;
			row1.transferCode = row2.transferCode;
		}
		
		if (row1.statusCode != row2.statusCode || row1.transferCode != row2.transferCode) {
			if (!row1.rangeObj.isDisjoint(row2.rangeObj)) {
				TransactionRow newRow = row1.splitRow(row2);
				trTable.add(i + 1, row2);
				trTable.add(i + 2, newRow);				
			}
			
		}
	}
	
}
