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
	
	TransactionRow splitRow(TransactionRow otherRow) {
		
		Range otherRange = otherRow.rangeObj;
		Range.Relation rangeType = rangeObj.classify(otherRange);
		TransactionRow tr=new TransactionRow();
		if(rangeType == Range.Relation.SUBSET) {
			tr.statusCode=this.statusCode;
			tr.transferCode=this.transferCode;
			tr.rangeObj.lo=otherRow.rangeObj.hi+1;
			tr.rangeObj.hi=this.rangeObj.hi;
			this.rangeObj.hi=otherRow.rangeObj.lo-1;
		}
		
		if(rangeType == Range.Relation.SUPERSET) {
			tr.statusCode=otherRow.statusCode;
			tr.transferCode=otherRow.transferCode;
			tr.rangeObj.lo=this.rangeObj.hi+1;
			tr.rangeObj.hi=otherRow.rangeObj.hi;
			otherRow.rangeObj.hi=this.rangeObj.lo-1;
			swapRowAttributes(this, otherRow);
		}
		
		return tr;
	
	}
	
	void swapRowAttributes(TransactionRow row1, TransactionRow row2) {
		int tempStartVal;
		int tempEndVal;
		
		tempStartVal = row1.rangeObj.hi;
		row1.rangeObj.hi = row2.rangeObj.hi;
		row2.rangeObj.hi = tempStartVal;
		
		tempEndVal = row1.rangeObj.lo;
		row1.rangeObj.lo = row2.rangeObj.lo;
		row2.rangeObj.lo = tempEndVal;
	}
	
}
