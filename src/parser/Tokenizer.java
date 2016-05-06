package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Tokenizer {
	public static void main(String[] args) {
		File filePath = new File("resources\\wikiFiles");
		
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
