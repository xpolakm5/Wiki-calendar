package wikical.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import wikical.settings.Settings;

public class PeopleOutputTest {

	/**
	 * Compare tested output with hard coded strings - if they match, test will write it's OK. Otherwise will write test has failed.
	 * 
	 * Pre vytvorenie unit testu je nutn� postupova� pod�a nasleduj�cich krokov:

	1. Je nutn� si vytvori� XML s�bor, ktor�ho zlo�enie je nasleduj�ce:

		Na za�iatok treba vlo�i� z�znam root elementu Wikip�die
		<mediawiki xmlns="http://www.mediawiki.org/xml/export-0.9/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.mediawiki.org/xml/export-0.9/ http://www.mediawiki.org/xml/export-0.9.xsd" version="0.9" xml:lang="sk">
		Na slovenskej str�nke Wikip�die je nutn� n�js� str�nku:

		<page>
    		<title>Alan Mathison Turing</title>
		Od tohto bodu po koniec </page>, ktor� obsahovala title "George Boole" je nutn� skop�rova� d�ta do vytvoren�ho XML s�boru.
		Na koniec je nutn� element mediawiki ukon�i�
		</mediawiki>
		Pre tento s�bor je nutn�, aby bol k�dovan� pomocou UTF-8
 
	2. XML s�bor, ktor� bude obsahova� asi 340 riadkov treba n�sledne v programe vybra� a spusti� pomocou tla�idla  "Wikipedia source file".
	N�sledne pomocou tla�idla "Generate" sa vygeneruje s�bor WikiCalPeople.txt, ktor� sa automaticky zvol� ako zdroj v pravej strane aplik�cie - "Folder 
	of parsed files". Pri zvolen� tla�idla "Run unity test" prebehne test spr�vnosti v�stupu. V pr�pade �spechu bude v konzole programu vyp�san� SUCCESS. 
	 * 
	 * 
	 * Tento navod sa nachadza aj na stranke http://vi.ikt.ui.sav.sk/User:Martin_Polak?view=home
	 * 
	 * @param sourceFolder
	 */
	public PeopleOutputTest(String sourceFolder) {
		File sourceFolderFile = new File(sourceFolder + Settings.nameOfPeopleFile);

		if (sourceFolderFile.exists()) {

			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(sourceFolderFile));

				int goodElements = 0;
				int i = 0;
				while (in.ready()) {
					String s = in.readLine();
					if (i == 0 && s.equals("Alan Mathison Turing;23.6.1912;7.6.1954")) {
						System.out.println("1. element OK");
						goodElements++;
					} else if (i == 1 && s.equals("George Boole;2.11.1815;8.12.1864")) {
						System.out.println("2. element OK");
						goodElements++;
					}
					i++;
				}

				if (goodElements == 2) {
					System.out.println("Test SUCCESSFULL");
				} else {
					System.out.println("Test FAILED");
				}

				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Testing source file "+ Settings.nameOfPeopleFile + " not found!");
		}
	}
}
