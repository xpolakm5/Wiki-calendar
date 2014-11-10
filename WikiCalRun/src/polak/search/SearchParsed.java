package polak.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import polak.settings.Settings;

public class SearchParsed {

	String txtDate;
	String txtName; 
	String txtEvent;
	File selectedFolder;
	File selectedFilePeople;
	File selectedFileEvents;
	
	public SearchParsed(String txtDate, String txtName, String txtEvent, File selectedFolder) {
		this.txtDate = txtDate;
		this.txtName = txtName; 
		this.txtEvent = txtEvent;
		this.selectedFolder = selectedFolder;
		selectedFilePeople =  new File(selectedFolder + Settings.nameOfPeopleFile);
		selectedFileEvents =  new File(selectedFolder + Settings.nameOfEventsFile);
	}
	
	/**
	 * Sarch is finding just output for the first inserted element. (Date, Name, Event in this order).
	 */
	public List<String> searchParsed() {
		
		
		if(selectedFilePeople.exists() && selectedFileEvents.exists()) {
			if(!txtDate.equals("")) {
				List<String> listOfPeople = findSubString(txtDate, selectedFilePeople);
				List<String> listOfEvents = findSubString(txtDate, selectedFileEvents);
				listOfPeople.addAll(listOfEvents);
				
				return listOfPeople;
			}
			else if(!txtName.equals("")) {
				return findSubString(txtName, selectedFilePeople);
			}
			else if(!txtEvent.equals("")) {
				return findSubString(txtEvent, selectedFileEvents);
			}
		}
		else {
			System.out.println("Path " + selectedFolder.getAbsolutePath() + " doesn't contain required files.");
		}
		return null;
	}
	
	private List<String> findSubString(String substring, File sourceFile) {
		
		List<String> foundStrings = new ArrayList<String>();
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(sourceFile), "UTF-8"));

			while (in.ready()) {
				String s = in.readLine();
				if (s.contains(substring)) {
					foundStrings.add(s);
				}
			}

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return foundStrings;
	}
}
