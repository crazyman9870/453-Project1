package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Tokenizer {
	public static void main(String[] args) {
		
		StopWords stopWords = new StopWords();
		//stopWords.printStopWords();
		
		File filePath = new File("resources\\wikiFiles");
		TreeMap database = new TreeMap<String, HashSet<DocumentLink>>();
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
						System.out.println(nextLine);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(f.getAbsolutePath());
			System.out.println("STOP");
		}
	}
}
