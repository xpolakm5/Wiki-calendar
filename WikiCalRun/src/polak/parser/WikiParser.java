package polak.parser;

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

import polak.dataclasses.DeathBirthParsedData;

public class WikiParser {

	File sourceFilePath;
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

		String rawBirthDate = null;
		String rawDeathDate = null;

		if (currentStartTag.equals("title")) {
			xmlStreamReader.next();
			lastTitle = xmlStreamReader.getText();
		} else {

			/* Every person processing starts here (starts for every page) */
			if (currentStartTag.equals("text") && xmlStreamReader.hasNext()) {				// when start tag "text is found" (which contains all the needed data)...

				String wholeText = "";

				while ((xmlStreamReader.next()) == XMLEvent.CHARACTERS) {					// .getText() return text in smaller parts, this reads it all

					if (xmlStreamReader.hasText()) {										// sometimes there is no text in "text" field
						wholeText = wholeText + xmlStreamReader.getText();					// before it was getText - problems with short output!; getElementText
					}
				}

				BufferedReader reader = new BufferedReader(new StringReader(wholeText));
				String line;

				while ((line = reader.readLine()) != null) {
					// if line have: beggining of line, char '|', "Meno", char '='
					if (line.matches("^ *\\| *[Mm]eno *=.*") || line.matches("^ *\\| *[Pp]ln� [Mm]eno *=.*")) {

						String foundSubstring = line.replaceAll(".*?=[\\[ ']*([\\p{L}0-9|'. ()�]+[\\p{L}.)]).*", "$1");

						/* it have to be null so it doesn't owerwrite good name with character from book */
						if (!foundSubstring.equals("") && !foundSubstring.equals(" ") && foundName == null) {
							if (foundSubstring.contains("{{PAGENAME}}") || foundSubstring.matches(".*[Mmeno].*")) {
								foundName = lastTitle;
							} else {
								foundName = foundSubstring;
							}
						}

					} else if (line.matches("^ *\\| *[Dd]�tum narodenia *=.*") || line.matches("^ *\\| *[Nn]arodenie *=.*") || line.matches("^ *\\| *[Dd]�tum a miesto narodenia  *=.*")) {
						Pattern pattern = Pattern.compile("= *(.*?)$");
						Matcher matcher = pattern.matcher(line);
						if (matcher.find()) {
							String birthDate = matcher.group(1);
							if (!birthDate.equals("") && !birthDate.equals(" ")) {
								rawBirthDate = birthDate;
							}
						}
					} else if (line.matches("^ *\\| *[Dd]�tum �mrtia *=.*") || line.matches("^ *\\| *[��]mrtie *=.*") || line.matches("^ *\\| *[Dd]�tum a miesto �mrtia *=.*")) {
						Pattern pattern = Pattern.compile("= *(.*?)$");
						Matcher matcher = pattern.matcher(line);
						if (matcher.find()) {
							String deathDate = matcher.group(1);
							if (!deathDate.equals("") && !deathDate.equals(" ")) {
								rawDeathDate = deathDate;
							}
						}
					}
				}
			}
		}

		/* when the name is found - we can try parsing dates */

		if (foundName != null) {															// if name is found

			DeathBirthParsedData parsedData = null;

			if (rawDeathDate != null) {
				parsedData = DateParser.parseDeathDate(rawDeathDate);							// it could
			}

			if (parsedData == null) {
				parsedData = new DeathBirthParsedData();								// when parsedData returns null (avoiding nullpointerexception)
			}

			/* When deathDate found just date of death and not birth date */
			if (parsedData.birthDate == null && rawBirthDate != null) {

				String birthParsed = DateParser.parseSimpleTime(rawBirthDate);
				if (birthParsed != null) {
					parsedData.birthDate = birthParsed;
				}
			}

			writeToFile(parsedData, foundName);

			/* When birth date wasn't found from date of death */

		}
	}

	/**
	 * Now both dates are parsed and ready to be written (if they are not null)
	 */
	private void writeToFile(DeathBirthParsedData parsedData, String foundName) {

		if (parsedData != null && (parsedData.birthDate != null || parsedData.deathDate != null)) {
			fileSave.addLineToFile(foundName, true);								// only names with at least one date will be saved

			if (parsedData.birthDate != null) {
				fileSave.addLineToFile("," + parsedData.birthDate, false);

			} else {
				fileSave.addLineToFile(",", false);
			}

			if (parsedData.deathDate != null) {
				fileSave.addLineToFile("," + parsedData.deathDate, false);
			}
		}
	}
}
