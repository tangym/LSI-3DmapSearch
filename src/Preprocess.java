import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author TYM
 *  
 */

public class Preprocess {
	public static final int POI_NUM = 16486;
	public static final int TERM_THRESHOLD = 100;
	private static ArrayList<String> Lexicon = new ArrayList<>() ;
	
	public static void genLexicon() throws IOException {
		BufferedReader reader = new BufferedReader( 
				new InputStreamReader(new FileInputStream(
						new File(MainClass.POI_TITLE_FILE)), "utf8"));
//				new FileReader(new File(MainClass.POI_TITLE_FILE)));
		FileWriter writer = new FileWriter(new File(MainClass.LEXICON_FILE));
		FileWriter writer2 = new FileWriter(new File(MainClass.TERM_DOCUMENT_FILE));
		
		HashMap<String, Integer[]> lexicon = new HashMap<>();
		
		int lineNumber = 0;
		String line = reader.readLine();
		while (line != null) {
			String[] seg = line.split(new String(" "));
						
			for (int i=0; i<seg.length; i++) {
				if (lexicon.containsKey(seg[i])) {
					lexicon.get(seg[i])[lineNumber]++;
				}
				else {
					Integer[] count = new Integer[POI_NUM];
					for (int index=0; index<count.length; index++) {
						count[index] = 0;
					}
					lexicon.put(seg[i], count);
					lexicon.get(seg[i])[lineNumber]++;
				}
			}
			
			line = reader.readLine();
			lineNumber++;
		}
		
		Iterator iterator = lexicon.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer[]> entry = 
					(Map.Entry<String, Integer[]>)iterator.next();
			String key = entry.getKey();
			Integer[] value = entry.getValue();
			
			int sum = 0;
			for (int i=0; i<value.length; i++) {
				sum += value[i];
			}
			
			if (sum < TERM_THRESHOLD) {
				writer.append(key + " " + sum + MainClass.LINE_SEPARATOR);
				for (int i=0; i<value.length; i++) {
					writer2.append(value[i] + " ");
				}
				writer2.append(MainClass.LINE_SEPARATOR);
			}
		}
		
		reader.close();
		writer.close();
		writer2.close();
	}
	
	public static void genQueryVector() throws IOException {
		BufferedReader reader = new BufferedReader( 
				new InputStreamReader(new FileInputStream(
						new File(MainClass.SEARCH_LIST_FILE)), "utf8"));
		BufferedReader reader2 = new BufferedReader( 
				new InputStreamReader(new FileInputStream(
						new File(MainClass.LEXICON_FILE)), "utf8"));
		FileWriter writer = new FileWriter(new File(MainClass.QUERY_VECTOR_FILE));

		String line = reader2.readLine();
		while (line != null) {
			String[] seg = line.split(new String(" "));
			Lexicon.add(seg[0]);			
			line = reader2.readLine();
		}
		
		
		int[] queryVector = new int[Lexicon.size()];
		for (int i=0; i<queryVector.length; i++) {
			queryVector[i] = 0;
		}
		
		line = reader.readLine();
		while (line != null) {
			String[] seg = line.split(new String(" "));
						
			for (int i=0; i<seg.length; i++) {
				if (Lexicon.indexOf(seg[i])<0) {
					continue;
				}
				else {
					queryVector[Lexicon.indexOf(seg[i])]++;	
				}
			}
			
			for (int i=0; i<queryVector.length; i++) {
				writer.append(queryVector[i] + " ");
			}
			writer.append(MainClass.LINE_SEPARATOR);
			
			line = reader.readLine();
		}
		
		reader.close();
		reader2.close();
		writer.close();
	} 
}
