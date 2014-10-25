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
	//int numberOfElements = 0;
	//int numberOfPeople = 0;
	FileSave fileSave;
	String lastTitle = "";

	public WikiParser(File sourceFilePath) {
		this.sourceFilePath = sourceFilePath;

		fileSave = new FileSave(sourceFilePath.getParent() + "\\WikiCalOutput.txt");		// output is saved where input is located

		try {
			execute(sourceFilePath.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void execute(String xmlFileName) throws Exception {

		System.out.println("filename: " + xmlFileName);
		//numberOfElements = 0;
		//numberOfPeople = 0;
		long startTime = System.currentTimeMillis();

		InputStream xmlInputStream = new FileInputStream(xmlFileName);
		XMLInputFactory2 xmlInputFactory = (XMLInputFactory2) XMLInputFactory.newInstance();
		XMLStreamReader2 xmlStreamReader = (XMLStreamReader2) xmlInputFactory.createXMLStreamReader(xmlInputStream);
		while (xmlStreamReader.hasNext()) {
			int eventType = xmlStreamReader.next();
			switch (eventType) {
			case XMLEvent.START_ELEMENT:

				isStartElement(xmlStreamReader);

				break;
			default:
				break;
			}

		}
		//System.out.println("Number of elements: " + numberOfElements);
		//System.out.println("Number of people: " + numberOfPeople);

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Time of generating output [ms]: " + totalTime);

		fileSave.stopWritingToFile();
	}

	/**
	 * Called when while parsing XML file start element is found
	 * 
	 * @param xmlStreamReader
	 * @throws Exception
	 */
	private void isStartElement(XMLStreamReader2 xmlStreamReader) throws Exception {

		String currentStartTag = xmlStreamReader.getLocalName();

		String foundName = null;
		String foundBirthDate = null;
		String foundDeathDate = null;

		if (currentStartTag.equals("title")) {
			xmlStreamReader.next();
			lastTitle = xmlStreamReader.getText();
			//numberOfElements++;
		} else {

			/* Every person processing starts here (starts for every page) */

			if (currentStartTag.equals("text") && xmlStreamReader.hasNext()) {				// when start tag "text is found" (which contains all the needed data)...

				String wholeText = "";

				while ((xmlStreamReader.next()) == XMLEvent.CHARACTERS) {					// .getText() return text in smaller parts, this reads it all

					if (xmlStreamReader.hasText()) {										// sometimes there is no text in "text" field
						wholeText = wholeText + xmlStreamReader.getText();					// before it was getText - problems with short output!; getElementText
					}
				}

				// System.out.println("Text: " + wholeText);
				BufferedReader reader = new BufferedReader(new StringReader(wholeText));
				String line;

				while ((line = reader.readLine()) != null) {
					// if line have: beggining of line, char '|', "Meno", char '='
					if (line.matches("^ *\\| *[Mm]eno *=.*") || line.matches("^ *\\| *[Pp]ln� [Mm]eno *=.*")) {

						String foundSubstring = line.replaceAll(".*?=[\\[ ']*([\\p{L}0-9|'. ()�]+[\\p{L}.)]).*", "$1");
						// System.out.println(line + " new: " + foundSubstring);

						/* it have to be null so it doesn't owerwrite good name with character from book */
						if (!foundSubstring.equals("") && !foundSubstring.equals(" ") && foundName == null) {
							if (foundSubstring.contains("{{PAGENAME}}")) {
								foundName = lastTitle;
							} else {
								foundName = foundSubstring;
							}

							//numberOfPeople++;
						}

					} else if (line.matches("^ *\\| *[Dd]�tum narodenia *=.*") || line.matches("^ *\\| *[Nn]arodenie *=.*") 
							|| line.matches("^ *\\| *[Dd]�tum a miesto narodenia  *=.*")) {
						Pattern pattern = Pattern.compile("= *(.*?)$");
						Matcher matcher = pattern.matcher(line);
						if (matcher.find()) {
							String birthDate = matcher.group(1);
							if (!birthDate.equals("") && !birthDate.equals(" ")) {
								foundBirthDate = birthDate;
								// parseDate(foundBirthDate);
							}
						}
					} else if (line.matches("^ *\\| *[Dd]�tum �mrtia *=.*") || line.matches("^ *\\| *[��]mrtie *=.*") 
							|| line.matches("^ *\\| *[Dd]�tum a miesto �mrtia *=.*")) {
						Pattern pattern = Pattern.compile("= *(.*?)$");
						Matcher matcher = pattern.matcher(line);
						if (matcher.find()) {
							String deathDate = matcher.group(1);
							if (!deathDate.equals("") && !deathDate.equals(" ")) {
								// foundDeathDate = deathDate;
								foundDeathDate = parseDate(deathDate);		//TODO zmeni sa na raw format, parsovat sa bude az dole
							}
						}
					}
				}
			}
		}

		/* ak sa naslo meno a aspon jeden datum, ulozi sa do suboru */

		if (foundName != null) {															// if name is found

			if (foundBirthDate != null || foundDeathDate != null) {							// if there is any time	//TODO az tu sa bude parsovat ak existuju raw data
				fileSave.addLineToFile(foundName, true);

				if (foundBirthDate != null) {
					// fileSave.addLineToFile("##" + foundBirthDate, false);
				}
				if (foundDeathDate != null) {
					fileSave.addLineToFile("," + foundDeathDate, false);
				}
			}
		}
	}

	String parseDate(String sourceDate) {

		String returnDate = "";

		if ((sourceDate.contains("d�v") || sourceDate.contains("duv")) && !sourceDate.contains("rokumrtia|mesiac")) {
			Pattern pattern = Pattern.compile("\\{\\{d[u�]v\\|(.*?)\\}\\}");
			String foundSubstring = "";

			Matcher matcher = pattern.matcher(sourceDate);
			if (matcher.find()) {
				foundSubstring = matcher.group(1);

				String[] tokens = foundSubstring.split("\\|");

				if (tokens.length > 5) {
					returnDate = tokens[5] + "." + tokens[4] + "." + tokens[3] + "," + tokens[2] + "." + tokens[1] + "." + tokens[0];
				} else {
					return null;
				}
			}
		}
		return returnDate;
	}
}
