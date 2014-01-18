import java.io.IOException;

import Jama.Matrix;

/**
 * 
 */

/**
 * @author TYM
 *
 */
public class MainClass {
	public static final String IN_PATH = "dt/";
	public static final String POI_TITLE_FILE = IN_PATH + "poi_title.seg.txt";
	public static final String SEARCH_LIST_FILE = IN_PATH + "searchList.seg.txt";
	public static final String OUT_PATH = "out/";
	public static final String LEXICON_FILE = OUT_PATH + "term.txt";
	public static final String TERM_DOCUMENT_FILE = OUT_PATH + "term-document.txt";
	public static final String QUERY_VECTOR_FILE = OUT_PATH + "query_vector.txt";
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	public static void main(String[] args) {
		try {
//			Preprocess.genLexicon();
			Preprocess.genQueryVector();
		}
		catch (IOException ioe) {
			System.err.println(ioe);
			System.exit(1);
		}
	}	
	
}
