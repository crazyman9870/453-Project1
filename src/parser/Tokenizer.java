package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
//						nextLine = nextLine.toLowerCase();
//						if(nextLine.contains(".") || nextLine.contains(","))
//							nextLine = (String) nextLine.subSequence(0, nextLine.length()-1);
//						if(nextLine.contains("'s"))
//							nextLine = (String) nextLine.substring(0, nextLine.length()-2);
//						if(nextLine.contains("'") && nextLine.length() > 1) {
////							System.out.println(nextLine);
//							if(nextLine.charAt(0) == '\'')
//								nextLine = (String) nextLine.substring(1, nextLine.length());
//							if(nextLine.charAt(0) == '\'')
//								nextLine = (String) nextLine.substring(1, nextLine.length());
//							if(nextLine.charAt(nextLine.length()-1) == '\'')
//								nextLine = (String) nextLine.substring(0, nextLine.length()-1);
////							System.out.println(nextLine);
//						}
						nextLine = stemmer.stem(nextLine.toLowerCase());
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
		
		Scanner scan = new Scanner(System.in);
		while(true) {
//			System.out.println("HERE");
			ResultSet scores = new ResultSet();
			StringTokenizer query = new StringTokenizer(scan.nextLine());//create a string
			while(query.hasMoreTokens()) {
//				System.out.println("STARTING WHILE");
				String word = stemmer.stem(query.nextToken());
				if(word.equals("quit")) {
					scan.close();
					return;
				}
				System.out.println(word);
				TreeMap<String, DocumentLink> docs = database.get(word);
				for(Entry<String, DocumentLink> entry : docs.entrySet()) {
					
					
					//Not Sure
					int maxFrequency = 0;
					BufferedReader buff;
					try {
						buff = new BufferedReader(new FileReader(entry.getKey()));
						StringBuffer strbuff = new StringBuffer();
						String testingStr = "";
						while ((testingStr = buff.readLine()) != null)
							strbuff.append(testingStr+"\n");
						buff.close();
						//save it to a bin tree.
						StringTokenizer token = new StringTokenizer(strbuff.toString());//create a string
						while(token.hasMoreTokens()){
							testingStr = token.nextToken();
							if(testingStr.matches("[a-zA-Z'.]*") && !stopWords.contains(testingStr)) {
								testingStr = stemmer.stem(testingStr.toLowerCase());
								int testingFrequency = database.get(testingStr).get(entry.getKey()).getKeywordFrequency();
								if(testingFrequency > maxFrequency)
									maxFrequency = testingFrequency;
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Not Sure			
					
					
					if(scores.containsKey(entry.getKey()))
						scores.updateScore(entry.getKey(), calculateScore(word, entry.getValue(), counter, docs.size(), maxFrequency));
					else
						scores.addNode(entry.getKey(), calculateScore(word, entry.getValue(), counter, docs.size(), maxFrequency));
				}
				//System.out.println("OUT OF FOR");
				System.out.println(scores.getSize());
				
			}
			scores.printTopTenResults();
		}
	}
	
	private static double calculateScore(String word, DocumentLink doc, int docTotal, int keywordDocTotal, int maxFrquency) {
//		double termFrequency = ((double)doc.getKeywordFrequency()/(double)doc.getWordCount());
//		double termFrequency = ((double)doc.getKeywordFrequency()/(double)maxFrquency);
		double inverseDocumentFrequency = (Math.log(((double) docTotal / (double) keywordDocTotal))/Math.log(2.0));
//		double queryScore = (termFrequency * inverseDocumentFrequency);
		double queryScore = ((double)doc.getKeywordFrequency() * inverseDocumentFrequency);
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
