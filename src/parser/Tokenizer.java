package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

public class Tokenizer {
	
	public static void main(String[] args) {
		
		StopWords stopWords = new StopWords();
		//stopWords.printStopWords();
		PorterStemmer stemmer = new PorterStemmer();
		
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
				int wordCount = st.countTokens();
				while(st.hasMoreTokens()){
					nextLine = st.nextToken();
					if(nextLine.matches("[a-zA-Z'.]*") && !stopWords.contains(nextLine)) {
						/* String Formatting */
						nextLine = nextLine.toLowerCase();
						if(nextLine.contains(".") || nextLine.contains(","))
							nextLine = (String) nextLine.subSequence(0, nextLine.length()-1);
						if(nextLine.contains("'s"))
							nextLine = (String) nextLine.substring(0, nextLine.length()-2);
						if(nextLine.contains("'") && nextLine.length() > 1) {
//							System.out.println(nextLine);
							if(nextLine.charAt(0) == '\'')
								nextLine = (String) nextLine.substring(1, nextLine.length());
							if(nextLine.charAt(0) == '\'')
								nextLine = (String) nextLine.substring(1, nextLine.length());
							if(nextLine.charAt(nextLine.length()-1) == '\'')
								nextLine = (String) nextLine.substring(0, nextLine.length()-1);
//							System.out.println(nextLine);
						}
						//nextLine = stemmer.stem(nextLine);
						/* Check if the keyword already exists */
						if(database.containsKey(nextLine)) {
							if(database.get(nextLine).containsKey(f.getPath()))
								database.get(nextLine).get(f.getPath()).incrementKeywordFrequency();
							else {
								database.get(nextLine).put(f.getPath(), new DocumentLink(wordCount, counter));
							}
						}
						else {
							TreeMap<String, DocumentLink> newMap = new TreeMap<>();
							newMap.put(f.getPath(), new DocumentLink(wordCount, counter));
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
		counter -= 1;
		System.out.println("COUNTER = " + counter);
		System.out.println("DONE WITH THAT DATA");
		
		while(true) {
			System.out.println("HERE");
			Scanner query = new Scanner(System.in);
			ResultSet scores = new ResultSet(); 
			while(query.hasNext()) {
				System.out.println("STARTING WHILE");
				String word = query.next();
				if(word.equals("quit")) {
					query.close();
					return;
				}
				System.out.println(word);
				TreeMap<String, DocumentLink> docs = database.get(word);
				for(Entry<String, DocumentLink> entry : docs.entrySet()) {
					if(scores.containsKey(entry.getKey()))
						scores.updateScore(entry.getKey(), calculateScore(word, entry.getValue(), counter, docs.size()));
					else
						scores.addNode(entry.getKey(), calculateScore(word, entry.getValue(), counter, docs.size()));
				}
				System.out.println("OUT OF FOR");
				System.out.println(scores.getSize());
				
			}
			query.close();
			scores.printTopTenResults();
		}
	}
	
	private static double calculateScore(String word, DocumentLink doc, int docTotal, int keywordDocTotal) {
		double termFrequency = ((double)doc.getKeywordFrequency()/(double)doc.getWordCount());
		double inverseDocumentFrequency = (Math.log(((double) docTotal / (double) keywordDocTotal))/Math.log(2.0));
		double queryScore = (termFrequency * inverseDocumentFrequency);
		return queryScore;
	}
	
	
	
//	private double Score(String query, DocumentLink doc, int docTotal, int keywordDocTotal) {
//		StringTokenizer queryWords = new StringTokenizer(query.toString());
//		String word = "";
//		double queryScore = 0.0;
//		double termFrequency = 0.0;
//		double inverseDocumentFrequency = 0.0;
//		while(queryWords.hasMoreTokens()) {
//			word = queryWords.nextToken();
//			termFrequency = ((double)doc.getKeywordFrequency()/(double)doc.getWordCount());
//			inverseDocumentFrequency = (Math.log(((double) docTotal / (double) keywordDocTotal))/Math.log(2.0));
//			queryScore += (termFrequency * inverseDocumentFrequency);
//		}
//		return queryScore;
//	}
}
