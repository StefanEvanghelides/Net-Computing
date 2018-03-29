import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import client.backend.Packet;


public class TestFiles {

	private final static String PATH = "cache/message.txt";
	
	public static String readData(String path) throws IOException {		
		File f = new File(path);
		String data = "";
		if(f.exists() && !f.isDirectory()) {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			data = new String(encoded, Charset.defaultCharset());
		}
		
		return data;
	}
	
	public static void storeData(Packet packet) throws IOException {
		if(!Files.isDirectory(Paths.get("cache"))) {
			Files.createDirectories(Paths.get("cache"));
		}
	
		String data = readData(PATH);
		data = packet.toString() + data;
		
        FileWriter fileWriter = new FileWriter(PATH);
        fileWriter.write(data);
		fileWriter.close();  
	}
	
	public static void main(String[] args) throws IOException {
		Packet packet = new Packet("Stefan", "wut7");
		
		storeData(packet);
		
		System.out.println("Data:\n" + readData(PATH));
	}
	
	

}
