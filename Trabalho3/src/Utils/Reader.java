package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Reader {
	
	private File file;
	private String absolutePath;
	private String fileName;
	private BufferedReader bufferRead;
	private BufferedWriter bufferWrite;
	
	public Reader(String path) {
		file = new File(path);
		absolutePath = file.getAbsolutePath();
		fileName = file.getName();
		
		System.out.println("Reader Pronto");
	}
	
	public String getAbsolutePath() {
		return absolutePath;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public boolean checkFile() {
		if(!file.exists()) {
			return false;
		}
		return true;
	}
	
	public void createNewFile() {
		try {
			file.createNewFile();
			System.out.println("O ficheiro foi criado com sucesso \n" + file.getAbsolutePath() + " \n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void getContent() throws IOException {
		System.out.println("A iniciar a leitura do ficheiro: " + file.getName());
		bufferRead = new BufferedReader(new FileReader(file));
		String line;
		while((line = bufferRead.readLine()) != null) {
			System.out.println(line);
		}
		bufferRead.close();
		System.out.println("Fim da leitura do ficheiro...");
	}
	
	public void writeContent(String[] texto) throws IOException {
		bufferWrite = new BufferedWriter(new FileWriter(file));
		for(int i = 0 ; i < texto.length; i++){
			bufferWrite.write(texto[i]);
			bufferWrite.newLine();
		}
		bufferWrite.close();
	}
	
	public static void main(String[] args) {
		Reader r = new Reader("D:\\Jorge Silva\\Projectos\\testes\\testesRobot.txt");
		try {
			r.getContent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
