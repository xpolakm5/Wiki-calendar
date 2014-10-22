package polak.wikicalrun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
 
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

public class WikiParser {
	
	File sourceFilePath;
	int numberOfElements = 0;
	int numberOfPeople = 0;
	FileSave fileSave;
	String lastTitle = "";
	
	public WikiParser(File sourceFilePath) {
		this.sourceFilePath = sourceFilePath;
		
		fileSave = new FileSave(sourceFilePath.getParent() + "\\WikiCalOutput.txt");			//output is saved where input is located
		
		try {
			execute(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
    private void execute(String xmlFileName) throws Exception {
        
    	System.out.println("filename: " + xmlFileName);
    	numberOfElements = 0;
    	numberOfPeople = 0;
    	long startTime = System.currentTimeMillis();
    	
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
        
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time of generating output [ms]: " + totalTime);
        
        fileSave.stopWritingToFile();
    }
    
    /**
     * Called when while parsing XML file start element is found
     * @param xmlStreamReader
     * @throws Exception
     */
    private void isStartElement(XMLStreamReader2 xmlStreamReader) throws Exception {
    	//System.out.print("#!!#"+xmlStreamReader.getNamespaceURI().toString()+"#!!#\n");
        // System.out.println("<"+xmlStreamReader.getLocalName()+">");					//original was getName() - return with "{xmlns:namespace}"
        
    	String currentStartTag = xmlStreamReader.getLocalName();
        
    	String foundName = null;
    	String foundBirthDate = null;
    	String foundDeathDate = null;
    	
         if(currentStartTag.equals("title")) {
         	xmlStreamReader.next();
         	lastTitle = xmlStreamReader.getText();
         	//System.out.println("Title: " + lastTitle);
         	numberOfElements++;
         } 
         else {

        	 /* Every person processing starts here (starts for every page) */
        	 
             if(currentStartTag.equals("text") && xmlStreamReader.hasNext()) {			//when start tag "text is found" (which contains all the needed data)...
             	//xmlStreamReader.next();												//... right here the text is read

            	String wholeText = ""; 
            	 
             	while((xmlStreamReader.next()) == XMLEvent.CHARACTERS) {				//.getText() return text in smaller parts, this reads it all
             	
	             	if(xmlStreamReader.hasText()) {										//sometimes there is no text in "text" field
	             		wholeText = wholeText + xmlStreamReader.getText();					//before it was getText - problems with short output!; getElementText
	             	}
             	}
             	
	             		//System.out.println("Text: " + wholeText);
	             		BufferedReader reader = new BufferedReader(new StringReader(wholeText));
	             	    String line;
	             	    Pattern pattern = Pattern.compile("= *(.*?)$");
	             	   
	             		while ((line = reader.readLine()) != null) {
	             	        if(line.matches("^ *\\| *[Mm]eno *=.*") || line.matches("^ *\\| *[Pp]lné [Mm]eno *=.*")) {	//if line have: beggining of line, char '|', "Meno", char '=' 
	             	        	//System.out.println(line);
	             	        	Matcher matcher = pattern.matcher(line);
	             	        	if (matcher.find())
	             	        	{
	             	        		String foundSubstring = matcher.group(1);
	             	        	    //System.out.println(matcher.group(1));
	             	        		if(!foundSubstring.equals("") && !foundSubstring.equals(" ") && foundName == null) {	//it have to be null so it doesn't owerwrite good name with character from book		
	             	        			if(foundSubstring.contains("{{PAGENAME}}")) {
	             	        				//fileSave.addLineToFile(lastTitle, true);
	             	        				foundName = lastTitle;
	             	        			} else {
	             	        				//fileSave.addLineToFile(foundSubstring, true);
	             	        				foundName = foundSubstring;
	             	        			}
	             	        		}
	             	        		else {
	             	        			//System.out.println(foundSubstring + " lasttitle: " + lastTitle);
	             	        		}
	             	        	}
	             	        	numberOfPeople++;
	             	        }//|Dátum a miesto narodenia 
	             	        else if(line.matches("^ *\\| *[Dd]átum narodenia *=.*") || line.matches("^ *\\| *[Nn]arodenie *=.*") || line.matches("^ *\\| *[Dd]átum a miesto narodenia  *=.*")) {

	             	        	Matcher matcher = pattern.matcher(line);
	             	        	if (matcher.find())
	             	        	{
	             	        		String birthDate = matcher.group(1);
	             	        		if(!birthDate.equals("") && !birthDate.equals(" ")) {
	             	        			foundBirthDate = birthDate;
	             	        			//parseDate(foundBirthDate);
	             	        		}
	             	        	   //fileSave.addLineToFile("##" + matcher.group(1), false);
	             	        	}
	             	        }
	             	        else if(line.matches("^ *\\| *[Dd]átum úmrtia *=.*") || line.matches("^ *\\| *[Úú]mrtie *=.*") || line.matches("^ *\\| *[Dd]átum a miesto úmrtia *=.*")) {
	             	        	Matcher matcher = pattern.matcher(line);
	             	        	if (matcher.find())
	             	        	{
	             	        		String deathDate = matcher.group(1);
	             	        		if(!deathDate.equals("") && !deathDate.equals(" ")) {
	             	        			//foundDeathDate = deathDate;
	             	        			foundDeathDate = parseDate(deathDate);
	             	        		}
	             	        	   //fileSave.addLineToFile("$$" + matcher.group(1), false);
	             	        	}
	             	        }
	             	    }
	             	}
             	}
             	
             	//ak sa naslo meno a aspon jeden datum, ulozi sa do suboru
             	
             	if(foundName != null) {												//if name is found
             		
             		if(foundBirthDate != null || foundDeathDate != null) {			//if there is any time
                 		fileSave.addLineToFile(foundName, true);
                 		
                 		if(foundBirthDate != null) {
                 			//fileSave.addLineToFile("##" + foundBirthDate, false);
                 		}
                 		if(foundDeathDate != null) {
                 			fileSave.addLineToFile("," + foundDeathDate, false);
                 		}
             		}
             	}
             	
             	//fileSave.addLineToFile("$$" + matcher.group(1), false); date of death
             }

    
    String parseDate(String sourceDate) {
    	
    	String returnDate = "";
    	
    	if((sourceDate.contains("dúv") || sourceDate.contains("duv")) && !sourceDate.contains("rokumrtia|mesiac")) {
    		//
     	    Pattern pattern = Pattern.compile("\\{\\{d[uú]v\\|(.*?)\\}\\}");		//{{dúv|(.*?)[}*]
    		// Pattern pattern = Pattern.compile("\\{\\{dúv|(.*?)[\\}*]");
     	   String foundSubstring = "";
     	    
        	Matcher matcher = pattern.matcher(sourceDate);
        	if (matcher.find())
        	{
        		foundSubstring = matcher.group(1);
        		
        		String[] tokens = foundSubstring.split("\\|");
//        		for(int i = 0; i < tokens.length; i++) {
//        			//System.out.println("Tokens: " + tokens[i]);
//        		}

        		if(tokens.length > 5) {
        			returnDate = tokens[5] + "." + tokens[4] + "." + tokens[3] + "," + tokens[2] + "." + tokens[1] + "." + tokens[0];
        		}
        		else {
        			return null;
        		}

        		System.out.println(returnDate + "      original: " + sourceDate);
        	}
        	
    		
    	}
    	
    	return returnDate;
    }
}
