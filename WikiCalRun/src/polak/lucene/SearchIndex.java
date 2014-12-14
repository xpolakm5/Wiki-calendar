//package polak.lucene;
//
//import java.io.File;
//
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TopScoreDocCollector;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//
//import polak.settings.Settings;
//
//public class SearchIndex {
//
//	
//	public static ScoreDoc[] SearchHitsString (String queryString) throws Exception {
//		
//	    // 0. Specify the analyzer for tokenizing text. The same analyzer should be used for indexing and searching
//	    StandardAnalyzer analyzer = new StandardAnalyzer();
//	    Directory index = FSDirectory.open(new File(Settings.defaultSelectedFolder + "\\luceneindex"));
//		
//		// the "title" arg specifies the default field to use when no field is explicitly specified in the query.
//	    Query q = new QueryParser("title", analyzer).parse(queryString);
//
//	    // 3. search
//	    int hitsPerPage = 10;
//	    IndexReader reader = DirectoryReader.open(index);
//	    IndexSearcher searcher = new IndexSearcher(reader);
//	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
//	    searcher.search(q, collector);
//	    
//	    reader.close();;
//	   ScoreDoc[] hits = collector.topDocs().scoreDocs;
//	    
//	    // 4. display results
//	    System.out.println("Found " + hits.length + " hits.");
//	    for(int i=0;i<hits.length;++i) {
//	      int docId = hits[i].doc;
//	      Document d = searcher.doc(docId);
//	      System.out.println((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
//	    }
//
//	    // reader can only be closed when there is no need to access the documents any more.
//		
//		return null;
//	}
//}
