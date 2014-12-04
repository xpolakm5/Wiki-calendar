package polak.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import polak.dataclasses.SearchData;
import polak.settings.Settings;

public class SearchParsed {

	private String txtDate;
	private String txtName;
	private String txtEvent;
	private File selectedFolder;
	private File selectedFilePeople;
	private File selectedFileEvents;

	public SearchParsed(String txtDate, String txtName, String txtEvent, File selectedFolder) {
		this.txtDate = txtDate;
		this.txtName = txtName;
		this.txtEvent = txtEvent;
		this.selectedFolder = selectedFolder;
		selectedFilePeople = new File(selectedFolder + Settings.nameOfPeopleFile);
		selectedFileEvents = new File(selectedFolder + Settings.nameOfEventsFile);
	}

	public List<SearchData> searchParsedIndexed(String queryString) throws Exception {

		if (selectedFilePeople.exists() && selectedFileEvents.exists()) {
			if (!txtDate.equals("")) {
				queryString = txtDate;						// searching by both dates (start and end) inside both files//TODO dorobit pre dates
			} else if (!txtName.equals("")) {
				return getQueryData(txtName, Settings.nameOfLuceneIndexPeople, "name");		// search by name in people folder
			} else if (!txtEvent.equals("")) {
				return getQueryData(txtEvent, Settings.nameOfLuceneIndexEvents, "name");		// serch by name in events folder
			}
		} else {
			System.out.println("Path " + selectedFolder.getAbsolutePath() + " doesn't contain required files.");
		}

		return null;
		
	}

	private List<SearchData> getQueryData(String queryString, String nameOfLuceneIndex, String queryParameter) throws Exception{

		// 0. Specify the analyzer for tokenizing text. The same analyzer should be used for indexing and searching
				StandardAnalyzer analyzer = new StandardAnalyzer();
				File luceneIndexDirectory = new File(selectedFolder + nameOfLuceneIndex);
				if (!luceneIndexDirectory.exists()) {
					System.out.println("File does not exist");
					analyzer.close();
					return null;
				}
				Directory index = FSDirectory.open(luceneIndexDirectory);

				// the "name" arg specifies the default field to use when no field is explicitly specified in the query.
				Query q = new QueryParser(queryParameter, analyzer).parse(queryString);//TODO zmena

				// 3. search
				int hitsPerPage = 50;
				IndexReader reader = DirectoryReader.open(index);
				IndexSearcher searcher = new IndexSearcher(reader);
				TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
				searcher.search(q, collector);

				ScoreDoc[] hits = collector.topDocs().scoreDocs;
				List<SearchData> foundData = new ArrayList<SearchData>();

				// 4. display results
				System.out.println("Found " + hits.length + " hits.");
				for (int i = 0; i < hits.length; ++i) {
					int docId = hits[i].doc;
					Document d = searcher.doc(docId);
					System.out.println((i + 1) + ". " + d.get("name") + "\t" + d.get("birthStartDate") + "\t" + d.get("deathEndDate"));
					foundData.add(new SearchData(d.get("name"), d.get("birthStartDate"), d.get("deathEndDate")));
				}
				reader.close();
		
		return foundData;
	}

	// /**
	// * Sarch is finding just output for the first inserted element. (Date, Name, Event in this order).
	// */
	// public List<String> searchParsed() {
	//
	//
	// if(selectedFilePeople.exists() && selectedFileEvents.exists()) {
	// if(!txtDate.equals("")) {
	// List<String> listOfPeople = findSubString(txtDate, selectedFilePeople);
	// List<String> listOfEvents = findSubString(txtDate, selectedFileEvents);
	// listOfPeople.addAll(listOfEvents);
	//
	// return listOfPeople;
	// }
	// else if(!txtName.equals("")) {
	// return findSubString(txtName, selectedFilePeople);
	// }
	// else if(!txtEvent.equals("")) {
	// return findSubString(txtEvent, selectedFileEvents);
	// }
	// }
	// else {
	// System.out.println("Path " + selectedFolder.getAbsolutePath() + " doesn't contain required files.");
	// }
	// return null;
	// }
	//
	// private List<String> findSubString(String substring, File sourceFile) {
	//
	// List<String> foundStrings = new ArrayList<String>();
	// BufferedReader in;
	// try {
	// in = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF-8"));
	//
	// while (in.ready()) {
	// String s = in.readLine();
	// if (s.contains(substring)) {
	// foundStrings.add(s);
	// }
	// }
	//
	// in.close();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return foundStrings;
	// }
}
