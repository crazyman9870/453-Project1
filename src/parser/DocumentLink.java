package parser;

public class DocumentLink implements Comparable<DocumentLink>{
	
	private int keywordFrequency;
	private int wordCount;
	private int fileEntryNumber;
	
	public DocumentLink() {
		keywordFrequency = 1;
		wordCount = 1;
		fileEntryNumber = -1;
	}
	
	public DocumentLink(int wCount, int counter) {
		keywordFrequency = 1;
		wordCount = wCount;
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

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public int getFileEntryNumber() {
		return fileEntryNumber;
	}

	public void setFileEntryNumber(int fileEntryNumber) {
		this.fileEntryNumber = fileEntryNumber;
	}

	//Sorts by keyword frequency then by whichever document was added first
	@Override
	public int compareTo(DocumentLink arg0) {
		if((this.keywordFrequency - arg0.keywordFrequency) != 0)
			return this.keywordFrequency - arg0.keywordFrequency;
		else if((this.wordCount - arg0.wordCount) != 0)
			return this.wordCount - arg0.wordCount;
		else
			return -(this.fileEntryNumber - arg0.fileEntryNumber);
	}	
}
