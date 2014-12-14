package polak.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import polak.settings.Settings;

public class PeopleOutputTest {

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
					if (i == 0 && s.equals("Alan Mathison Turing,23.6.1912,7.6.1954")) {
						System.out.println("1. element OK");
						goodElements++;
					} else if (i == 1 && s.equals("George Boole,2.11.1815,8.12.1864")) {
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
