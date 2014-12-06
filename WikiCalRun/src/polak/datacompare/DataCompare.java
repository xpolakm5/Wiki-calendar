package polak.datacompare;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import polak.dataclasses.SearchData;

public class DataCompare {
	
	public static boolean CouldTheyMeet(SearchData searchData, SearchData selectedItem) {

			Date firstDateDeath = returnDate(selectedItem.getDeathDate());
			Date secondDateBirth = returnDate(searchData.getBirthDate());
			
			Date firstDateBirth = returnDate(selectedItem.getBirthDate());
			Date secondDateDeath = returnDate(searchData.getDeathDate());
			
			int comparedDates1 = 0;
			int comparedDates2 = 0;
			
			if(firstDateDeath == null && secondDateDeath == null) {
				return true;																//oba konce su aktualne
			}
			
			if(firstDateDeath != null && secondDateBirth != null) {							//ak su zadane oba datumy
				comparedDates1 = firstDateDeath.compareTo(secondDateBirth);
			}
			//else if(firstDateDeath == null && secondDateBirth == null) {
			else {
				if(firstDateBirth.compareTo(secondDateDeath) >= 0) {						//datum narodenia je vacsi ako datum umrtia - nestretli sa
					return false;
				} else {
					return true;
				}
			}
//			else if(firstDateDeath == null) {												//first este zije, second nie (osetrene vyssie)
//				
//			}
//			else if(secondDateBirth == null) {
//				
//			}
			
			
			if(firstDateBirth != null && secondDateDeath != null) {							//ak su zadane oba datumy
				comparedDates2 = firstDateBirth.compareTo(secondDateDeath);
			}
			//else if(firstDateBirth == null && secondDateDeath == null) {
			else {
				if(secondDateBirth.compareTo(firstDateDeath) >= 0) {						//datum narodenia je vacsi ako datum umrtia - nestretli sa
					return false;
				} else {
					return true;
				}
			}
//			else if(firstDateBirth == null) {
//				
//			}
//			else if(secondDateDeath == null) {												//second este zije, first nie (osetrene vyssie)
//				
//			}
			
			
			
			
			if(comparedDates1 >= 0 && comparedDates2 <= 0) {		// (datum konca 1. je vacsi ako datum zaciatku 2.) (datum zaciatku 1. je mensi ako datum konca 2.)
				System.out.println("YES. He did.");
				return true;
			}

		
		return false;
	}
	
	private static Date returnDate(String input) {
				
		try {
			if(input.equals("")) {
				return null;
			}
			
			DateFormat dateFormat = new SimpleDateFormat("d.M.y");
			DateFormat dateFormatYear = new SimpleDateFormat("..y");
			
			if(input.contains("..")) {
				return dateFormatYear.parse(input);
			} else {
				return dateFormat.parse(input);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
