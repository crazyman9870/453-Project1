package parser;

import java.io.File;

public class Tokenizer {
	public static void main(String[] args) {
		File filePath = new File("resources\\wikiFiles");
		for(File f : filePath.listFiles()) {
			System.out.println(f.getAbsolutePath());
		}
	}
}
