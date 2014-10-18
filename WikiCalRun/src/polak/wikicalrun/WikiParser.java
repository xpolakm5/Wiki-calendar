package polak.wikicalrun;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
 
import org.codehaus.stax2.XMLInputFactory2;
import org.codehaus.stax2.XMLStreamReader2;

public class WikiParser {
	
	File sourceFilePath;
	
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

        InputStream xmlInputStream = new FileInputStream(xmlFileName);
        XMLInputFactory2 xmlInputFactory = (XMLInputFactory2)XMLInputFactory.newInstance();
        XMLStreamReader2 xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
        while(xmlStreamReader.hasNext()){
            int eventType = xmlStreamReader.next();
            switch (eventType) {
            case XMLEvent.START_ELEMENT:
            	
            	//System.out.print("#!!#"+xmlStreamReader.getNamespaceURI().toString()+"#!!#\n");
            	
               // System.out.println("<"+xmlStreamReader.getLocalName()+">");		//original was getName() - return with "{xmlns:namespace}"
                String actTag = xmlStreamReader.getLocalName();
                
                if(!actTag.equals("title")) {
                	//xmlStreamReader.next();
                	//xmlStreamReader.next();
                	//xmlStreamReader.skipElement();		//nemozem skipnut element v tomto pripade - preskocim vsetko vo vnutri <page>, cize aj title
                	//System.out.println("!!!!!!!! nie je title");
                }
                else {
                	//System.out.println("!!!!!!!! je title!!!!!");
                	xmlStreamReader.next();
                	System.out.println("Title: " + xmlStreamReader.getText());
                }
                
                break;
            case XMLEvent.CHARACTERS:
                //System.out.println(xmlStreamReader.getText());
                break;
            case XMLEvent.END_ELEMENT:
                //System.out.println("</"+xmlStreamReader.getLocalName()+">");
                break;
//            case XMLEvent.COMMENT:						//mine test
//                System.out.println("COMMENT </"+xmlStreamReader.getText().toString()+">");
//                break;
            default:
                //do nothing
                break;
            }
        }
         
    }
}
