package polak.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import polak.dataclasses.DeathBirthParsedData;

public class DateParser {

	public static DeathBirthParsedData parseDate(String sourceDate) {

		if ((sourceDate.contains("dúv") || sourceDate.contains("duv")) && !sourceDate.contains("rokumrtia|mesiac")) {
			Pattern pattern = Pattern.compile("\\{\\{d[uú]v\\|(.*?)\\}\\}");
			String foundSubstring = "";

			Matcher matcher = pattern.matcher(sourceDate);
			if (matcher.find()) {
				foundSubstring = matcher.group(1);

				String[] tokens = foundSubstring.split("\\|");

				if (tokens.length > 5) {

					return new DeathBirthParsedData(tokens[5] + "." + tokens[4] + "." + tokens[3], tokens[2] + "." + tokens[1] + "." + tokens[0]);
				} else {
					return null;
				}
			}
		}
		else if (sourceDate.contains("dnv")) {									//{{dnv|1941|2|19}}, returns just date of birth
			
			Pattern pattern = Pattern.compile("\\{\\{dnv\\|(.*?)\\}\\}");
			String foundSubstring = "";

			Matcher matcher = pattern.matcher(sourceDate);
			if (matcher.find()) {
				foundSubstring = matcher.group(1);

				String[] tokens = foundSubstring.split("\\|");
				
				if (tokens.length > 2) {
					
					return new DeathBirthParsedData(tokens[2] + "." + tokens[1] + "." + tokens[0]);
				} else {
					return null;
				}
			}
		}
		else if(sourceDate.matches("\\[\\[.*\\]\\] \\[\\[[0-9]*\\]\\]")) {		//[[23. január]] [[1840]]
			//System.out.println("druhy: " + sourceDate + " mesiac: " + SlovakMonthParser.getMonthNumber(sourceDate));
			
			
			
			String foundSubstringDay = "";
			String foundSubstringMonth = SlovakMonthParser.getMonthNumber(sourceDate);
			String foundSubstringYear = "";

			/* Date and month search*/
			Pattern pattern = Pattern.compile(".*\\[\\[([0-9]{1,2})\\.");
			Matcher matcher = pattern.matcher(sourceDate);
			if (matcher.find()) {
				foundSubstringDay = matcher.group(1);
			}

			/* Year search */
			pattern = Pattern.compile(".*\\[\\[([0-9]{1,4})\\]\\]");
			

			matcher = pattern.matcher(sourceDate);
			if (matcher.find()) {
				foundSubstringYear = matcher.group(1);
			}
			
			System.out.println("povodny: " + sourceDate + " teraz: " + foundSubstringDay + "." + foundSubstringMonth + "." + foundSubstringYear);
		
			//TODO povodny: [[Košice]] [[1619]] teraz: ..1619
			//povodny: [[11. september]] [[1063]] teraz: 11.9.1063
			
			// poifovat ked je ze iba mesiac a rok a iba mesiac alebo vsetko a dat to do stringu nejak (aby sa to potom dalo vyhladavat)
		}
		
		return null;
	}
}
