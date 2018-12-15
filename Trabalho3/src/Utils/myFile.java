package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

public class myFile {

	private final String DEFAULTPATH = "S:\\JAVA\\TP3\\stringExample\\configs\\";

	private InputStream input;
	private OutputStream output;
	private File file;
	private boolean isToRead;
	private String fileName;

	public myFile(String fileName) throws IOException {
		this.fileName = fileName;
		file = new File(DEFAULTPATH+this.fileName);
		fileExists();
//		this.isToRead = isToRead;
//		if (isToRead)
//			input = new FileInputStream(DEFAULTPATH + fileName);
//		else
//			output = new FileOutputStream(DEFAULTPATH + fileName);
	}
	
	public void fileExists() throws IOException {
		if(file.exists()) {
			Object[] options = {"Substituir", "Cancelar"};
			int n = JOptionPane.showOptionDialog(null, "Substituir ou Cancelar?", "Ficheiro já existe...", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[1]);
			if(n==0) {
				file.createNewFile();
			}
			
		}
	}
	
	public void fileCreate() throws IOException {
		file.createNewFile();
	}

	public String[] read() throws IOException {
		input = new FileInputStream(DEFAULTPATH + fileName);
		int data;
		int counter = 0;
		String[] teste = new String[3];
		String fi = "";
		while ((data = input.read()) != -1) {
			String temp = "";
			if(data == '\n') {
				teste[counter] = fi;
				counter += 1;
				fi = "";
			}else {
				temp = "" + (char) data;
				fi += temp;
			}
		}
		return teste;
	}

	public void write(String phrase) throws IOException {
		output = new FileOutputStream(DEFAULTPATH + fileName);
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
