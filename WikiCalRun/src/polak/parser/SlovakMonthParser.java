package polak.parser;

public class SlovakMonthParser {
	
	/**
	 * Parse name of month to number
	 * @param month string containing 1 of 12 months in Slovak language
	 * @return number of month or null if month wasn't parsed
	 */
	public static String getMonthNumber(String month) {
		
		if(month.matches(".*[Jj]anu[a�]r.*")) {
			return "1";
		}
		else if(month.matches(".*[Ff]ebru[a�]r.*")) {
			return "2";
		}
		else if(month.matches(".*[Mm]ar[ec][ca].*")) {		//marec, marca
			return "3";
		}
		else if(month.matches(".*[Aa]pr[i�]l.*")) {
			return "4";
		}
		else if(month.matches(".*[Mm][a�]j.*")) {
			return "5";
		}
		else if(month.matches(".*[Jj][u�]n.*")) {
			return "6";
		}
		else if(month.matches(".*[Jj][u�]l.*")) {
			return "7";
		}
		else if(month.matches(".*[Aa]ugust.*")) {
			return "8";
		}
		else if(month.matches(".*[Ss]eptemb[er][ra].*")) {		//september, septembra
			return "9";
		}
		else if(month.matches(".*[Oo]kt[o�]b[er][ra].*")) {		//okt�ber, okt�bra
			return "10";
		}
		else if(month.matches(".*[Nn]ovemb[er][ra].*")) {
			return "11";
		}
		else if(month.matches(".*[Dd]ecemb[er][ra].*")) {
			return "12";
		}
		else {
			//System.out.println("MY_WARNING: Unrecognized month: " + month);
			return "";
		}
	}
	
}
