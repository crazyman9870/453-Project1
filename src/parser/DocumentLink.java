package parser;

public class DocumentLink implements Comparable<DocumentLink>{
	
	private int keywordFrequency;
	private int fileEntryNumber;
	
	public DocumentLink() {
		keywordFrequency = 1;
		fileEntryNumber = -1;
	}
	
	public DocumentLink(int counter) {
		keywordFrequency = 1;
		fileEntryNumber = counter;
	}
	
	public int getKeywordFrequency() {
		return keywordFrequency;
	}

	public void setKeywordFrequency(int keywordFrequency) {
		this.keywordFrequency = keywordFrequency;
	}
	
	public void incrementKeywordFrequency() {
		this.keywordFrequency++;
	}

	//Sorts by keyword frequency then by whichever document was added first
	@Override
	public int compareTo(DocumentLink arg0) {
		if((this.keywordFrequency - arg0.keywordFrequency) != 0)
			return this.keywordFrequency - arg0.keywordFrequency;
		else
			return -(this.fileEntryNumber - arg0.fileEntryNumber);
	}	
}
