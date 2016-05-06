package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeMap;

public class Tokenizer {
	public static void main(String[] args) {
		File filePath = new File("resources\\wikiFiles");
		TreeMap database = new TreeMap<String, HashSet>();
		for(File f : filePath.listFiles()) {
			try {
				Scanner scan = new Scanner(f);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println(f.getAbsolutePath());
		}
	}
}
