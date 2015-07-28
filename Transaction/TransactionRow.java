
public class TransactionRow {
	
	Range rangeObj;
	char statusCode;
	int transferCode;
	boolean isDeleted;
	
	void mergeRows(TransactionRow otherRow) {
		Range otherRange = otherRow.rangeObj;
		
		Range.Relation rangeType = rangeObj.classify(otherRange);
		//boolean sameCodes = (this.statusCode == otherRow.statusCode) && (this.transferCode == otherRow.transferCode);
		if (rangeType == Range.Relation.LESSOVERLAP) {				//&& sameCodes) {
			this.rangeObj.hi = otherRow.rangeObj.hi;
			deleteRow(otherRow);
		}
 		
		else if (rangeType == Range.Relation.MOREOVERLAP) {
			otherRow.rangeObj.lo = this.rangeObj.lo;
			deleteRow(this);
		}		
	}
	
	void deleteRow(TransactionRow row) {
		row.isDeleted = true;
	}
	
	void splitRow(TransactionRow otherRow) {
		
	}

}
