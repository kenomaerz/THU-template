package modelCTSNOMED;

public class CTVo {

		private String term;
		private String termactive;
		private String offset;
		private String limit;

		// default Constructor
		public CTVo() {

		}

		public CTVo(String term, String termactive, String offset, String limit ) {
			// with all parameters
			
			this.setTerm(term);
			this.setTermactive(termactive);
			this.setOffset(offset);
			this.setLimit(limit);
			
		}
		
	
		// Get-Term
		public String getTerm() {
			return term;
		}

		// Set-Term
		public void setTerm(String term) {
			this.term = term;
		}

		// Get-Termactive
		public String getTermactive() {
			return termactive;
		}

		// Set-Termactive
		public void setTermactive(String termactive) {
			this.termactive = termactive;
		}

		// Get-Offset
		public String getOffset() {
			return offset;
		}

		// Set-Offset
		public void setOffset(String offset) {
			this.offset = offset;
		}

		// Get-Limit
		public String getLimit() {
			return limit;
		}

		// Set-Limit
		public void setLimit(String limit) {
			this.limit = limit;
		}

		
	}