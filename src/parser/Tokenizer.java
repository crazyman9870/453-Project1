package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Tokenizer {
	
	public static void main(String[] args) {
		
		StopWords stopWords = new StopWords();
		//stopWords.printStopWords();
		
		File filePath = new File("resources\\wikiFiles");
		TreeMap<String, TreeMap<String,DocumentLink>> database = new TreeMap<>();
		int counter = 1;
		for(File f : filePath.listFiles()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(f));
				StringBuffer str = new StringBuffer();
				String nextLine = "";
				while ((nextLine = in.readLine()) != null)
					str.append(nextLine+"\n");
				in.close();
				//save it to a bin tree.
				StringTokenizer st = new StringTokenizer(str.toString());//create a string
				while(st.hasMoreTokens()){
					nextLine = st.nextToken();
					if(nextLine.matches("[a-zA-Z'.]*") && !stopWords.contains(nextLine)) {
						/* String Formatting */
						nextLine = nextLine.toLowerCase();
						if(nextLine.contains("."))
							nextLine = (String) nextLine.subSequence(0, nextLine.length()-1);
						if(nextLine.contains("'s"))
							nextLine = (String) nextLine.substring(0, nextLine.length()-2);
						/* Check if the keyword already exists */
						if(database.containsKey(nextLine)) {
							if(database.get(nextLine).containsKey(f.getPath()))
								database.get(nextLine).get(f.getPath()).incrementKeywordFrequency();
							else {
								database.get(nextLine).put(f.getPath(), new DocumentLink(counter));
							}
						}
						else {
							TreeMap<String, DocumentLink> newMap = new TreeMap<>();
							newMap.put(f.getPath(), new DocumentLink(counter));
							database.put(nextLine, newMap);
						}
						//System.out.println(nextLine);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.out.println(f.getPath());
			counter++;
		}
		System.out.println("DONE");
	}
}
