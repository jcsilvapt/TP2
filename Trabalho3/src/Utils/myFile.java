package Utils;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class myFile {

	private final String DEFAULTPATH = "..\\Trabalho3\\configs\\";
	private final String DEFAULTCONFIG = "default";

	private InputStream input;
	private OutputStream output;

	private File file;

	private boolean isToRead;

	public myFile(String fileName, boolean isToRead) throws IOException {
		this.isToRead = isToRead;
		file = new File(DEFAULTPATH + fileName + ".txt");
		if (this.isToRead) {
			if (!file.exists()) {
				System.out.println("OH");
				input = new FileInputStream(DEFAULTPATH + fileName + ".txt");
			} else {
				input = new FileInputStream(DEFAULTPATH + DEFAULTCONFIG + ".txt");
			}
		} else {
			System.out.println("1212");
			if(!file.exists()) {
				file.createNewFile();
			}
			output = new FileOutputStream(DEFAULTPATH + fileName + ".txt");
		}
	}

	public String[] read() throws IOException {
		int data;
		ArrayList<String> values = new ArrayList<String>();
		String fi = "";
		while ((data = input.read()) != -1) {
			String temp = "";
			if (data == '\n') {
				values.add(fi);
				fi = "";
			} else {
				temp = "" + (char) data;
				fi += temp;
			}
		}
		System.out.println(values.toString());
		String[] toReturn = values.toArray(new String[values.size()]);
 		return toReturn;
	}

	public void write(String phrase) throws IOException {
		String newLine = "\n";
		try {
			output.write(phrase.getBytes());
			output.write(newLine.getBytes());
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeChannel() throws IOException {
		if (isToRead) {
			input.close();
		} else {
			output.close();
		}
	}

}
