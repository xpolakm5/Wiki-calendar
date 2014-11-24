package polak.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import polak.settings.Settings;

public class CreateIndex {

	
	public CreateIndex(File selectedFolder) throws Exception {

		File selectedFilePeople =  new File(selectedFolder + Settings.nameOfPeopleFile);
		File selectedFileEvents =  new File(selectedFolder + Settings.nameOfEventsFile);
		
		StandardAnalyzer analyzer = new StandardAnalyzer();

	    /* Where to store index */
		
		File directoryOfIndex = new File(Settings.defaultSelectedFolder + Settings.nameOfLuceneIndex);
		
		deleteDirectory(directoryOfIndex);
		
	    Directory index = FSDirectory.open(directoryOfIndex);

	    IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);

	    IndexWriter w = new IndexWriter(index, config);
	    
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFilePeople), "UTF-8"));
			//TODO prerobit aj na 2. file

			while (in.ready()) {
				String oneLine = in.readLine();
				
				String[] splittedString = oneLine.split(";");
				
				if(splittedString.length == 2) {
					addDoc(w, splittedString[0], splittedString[1], "");
					//System.out.println("vypisanie: "  + oneLine);
				}
				else if(splittedString.length == 3) {
					addDoc(w, splittedString[0], splittedString[1], splittedString[2]);
					//System.out.println("vypisanie: "  + oneLine);
				}
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    w.close();
	}
	
	private static void addDoc(IndexWriter w, String name, String birthDate, String deathDate) {
		Document doc = new Document();
		doc.add(new TextField("name", name, Field.Store.YES));
		doc.add(new StringField("birthDate", birthDate, Field.Store.YES)); 				// use a string field for isbn because we don't want it tokenized
		doc.add(new StringField("deathDate", deathDate, Field.Store.YES));
		
		try {
			w.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
}
