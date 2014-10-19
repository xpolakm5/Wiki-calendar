package polak.wikicalrun;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileSave {

	BufferedWriter bufferedWriter;
	
	public FileSave(String pathToFile) {
		File fout = new File(pathToFile);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(fout);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	 
	}
	
	public void addLineToFile(String addingLine, boolean addNewLine) {
		try {
			bufferedWriter.write(addingLine);
			if(addNewLine) {
				bufferedWriter.newLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopWritingToFile() {
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
