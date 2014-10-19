package polak.wikicalrun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
 
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

public class WikiParser {
	
	File sourceFilePath;
	int numberOfElements = 0;
	int numberOfPeople = 0;
	
	public WikiParser(File sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
		System.out.println("ide vo wiki");
		try {
			execute(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
    private void execute(String xmlFileName) throws Exception {
        
    	System.out.println("filename: " + xmlFileName);
    	numberOfElements = 0;
    	numberOfPeople = 0;
    	
        InputStream xmlInputStream = new FileInputStream(xmlFileName);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
        XMLStreamReader2 xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
        while(xmlStreamReader.hasNext()){
            int eventType = xmlStreamReader.next();
            switch (eventType) {
            case XMLEvent.START_ELEMENT:

                isStartElement(xmlStreamReader);

                break;
//            case XMLEvent.CHARACTERS:
//            	String text = xmlStreamReader.getText();
//                System.out.println(text);
//                break;
//            case XMLEvent.END_ELEMENT:
//                System.out.println("</"+xmlStreamReader.getLocalName()+">");
//                break;
            default:
                break;
            }
            
        }
        System.out.println("Number of elements: " + numberOfElements);
        System.out.println("Number of people: " + numberOfPeople);
    }
    
    
    private void isStartElement(XMLStreamReader2 xmlStreamReader) throws Exception {
    	//System.out.print("#!!#"+xmlStreamReader.getNamespaceURI().toString()+"#!!#\n");
        // System.out.println("<"+xmlStreamReader.getLocalName()+">");				//original was getName() - return with "{xmlns:namespace}"
        
    	String currentStartTag = xmlStreamReader.getLocalName();
         
         if(currentStartTag.equals("title")) {
         	xmlStreamReader.next();
         	//System.out.println("Title: " + xmlStreamReader.getText());
         	numberOfElements++;
         } 
         else {

             if(currentStartTag.equals("text") && xmlStreamReader.hasNext()) {		//when start tag "text is found" (which contains all the needed data)...
             	//xmlStreamReader.next();												//... right here the text is read

             	while((xmlStreamReader.next()) == XMLEvent.CHARACTERS) {
             	
             	if(xmlStreamReader.hasText()) {										//sometimes there is no text in "text" field
             		String wholeText = xmlStreamReader.getText();					//before it was getText - problems with short output!; getElementText
             		
             		//System.out.println("Text: " + wholeText);
             		BufferedReader reader = new BufferedReader(new StringReader(wholeText));
             	    String line;
             	    
             		while ((line = reader.readLine()) != null) {
             			
             	        if(line.matches("^ *\\| *Meno *=.*")) {
             	        	System.out.println(line);
             	        	numberOfPeople++;
             	        }
             	        else if(line.matches("^ *\\| *Dátum narodenia *=.*")) {
             	        	System.out.println(line);
             	        }
             	    }
             	}
             	}
             }                    
         }
    }
}
