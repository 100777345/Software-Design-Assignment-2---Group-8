package assignment2;

import java.io.InputStream;
import java.util.Scanner;

public class Keyboard extends InputPeripheral {
	
	Scanner in;
	String input;
	
	public Keyboard(InputStream inputStream) {
		in = new Scanner(inputStream);
	}
	

	public void getInput() {		
		input = in.nextLine();	
	}
	
	public void clear() {
		input = null;
	}
	
	public void close(){	
		in.close();	
	}

}
