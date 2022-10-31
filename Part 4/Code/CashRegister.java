package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JFrame;

public class CashRegister {
	
	Keyboard keyboard;
	BarcodeScanner scanner;
	Display display;
	TicketPrinter printer;
	String mode;
	Product dummyProduct;
	boolean validProduct;
	int counter;
	
	
	public static ReceiptEntry[] currentItems = new ReceiptEntry[50];
	public static String[][] convertedItems = new String[50][5];
	
	public CashRegister(Keyboard keyboard, BarcodeScanner scanner, Display display, TicketPrinter printer) {
		
		this.keyboard = keyboard;
		this.keyboard.attach(this);
		this.scanner = scanner;
		this.scanner.attach(this);
		
		this.display = display;
		this.printer = printer;
		
		dummyProduct = new Product();
		
	}
	
	// Creates main window
	public void initiate() {
		
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setTitle("Cash Register");
		display.setSize(1080,600);
		display.setVisible(true);
		
		String input = "";
		
		while (!(input.equals("start"))) {
			input = promptInput("Enter 'start' to start a session.");
		}
		
			reset();
		
	}
	
	// Prompts user for input through main window, returns input
	public String promptInput(String prompt) {
		
		String input;
		
		display.display(prompt);
		keyboard.getInput();
		input = keyboard.input;
		keyboard.clear();
		
		return input;
		
	}
	
	// Clears currentItems, resets state of application
	public void reset() {
		
		Arrays.fill(currentItems, null);
		convert();
		counter = 0;
		
		mode = "Add";
		display.displayMainScreen();
		menu();
		
		
	}
	
	// Main menu
	public void menu() {
		
		String selection = "";
		
		while (!(selection.equals("scan") || selection.equals("restart") || selection.equals("changemode") || selection.equals("done"))) 
		{
		selection = promptInput("Ready. Enter 'scan' to scan a product, 'restart' to restart the session, 'changemode' to switch modes (add/remove product), or 'done' to proceed to payment. Mode: " + mode + ".");
		}
		
		switch(selection) {
		
		// In actual deployment this would not be an option, instead scanning an item with a barcode scanner would invoke the scan() method.
		case "scan":
			scanner.scan();
			break;
			
		case "restart":
			reset();
			break;
			
		case "changemode":
			if (mode == "Add") {
				mode = "Remove";
			} else if (mode == "Remove") {
				mode = "Add";
			}
			display.refresh();
			menu();
			break;
			
		case "done":
			payment();
			break;
			
		}
	}
	
	// Processes scan
	public void update() {
		
		display.refresh();
		
		// Scan failed
		if (scanner.code == "-1") {
			
			display.displayError("Unknown Product. Please enter the product code manually.");
			scanner.setCode(promptInput(""));
			
		}
		
		// Successful scan
		if (scanner.code != "") {
			
			display.refresh();
			
			dummyProduct.productCode = scanner.code;
			dummyProduct = getProductInfo(dummyProduct);
			
			if(mode == "Add") {
				addItem(dummyProduct);
			} else if (mode == "Remove") {
				removeItem(dummyProduct);
			}
			
			scanner.setCode("");
		}
		
		
		menu();
		
	}
	
	// Adds one occurrence of a product to currentItems
	public void addItem(Product temp) {
		if (validProduct == true) {
			if (itemIndex(temp) != -1) 
			{
				currentItems[itemIndex(temp)].quantity++;
			} else {
				currentItems[counter] = new ReceiptEntry(temp.productCode, temp.productName, temp.productPrice, 1);
				counter++;
			}
			
		}
		
		convert();

	}
	
	// Removes all occurrences of a product from currentItems
	public void removeItem(Product temp) {
		
		if (itemIndex(temp) != -1) 
		{
			
			for (int i = itemIndex(temp); i < counter; i++)
			{
				currentItems[i] = currentItems[i+1];
			}

			currentItems[counter] = null;
			convert();
			counter--;
			
		} else {
			display.displayError("Product not found in current session.");
		}
		
	}
	
	// Converts currentItems to a 2-D String array for display
	public void convert() {
		
		final DecimalFormat decfor = new DecimalFormat("0.00"); 
		
		for (int i = 0; i < counter; i++) {
			
			if (currentItems[i] == null)
			{
				for (int j = 0; j < 5; j++)
				{
					convertedItems[i][j] = null;
				}
				
			} else {
				convertedItems[i][0] = currentItems[i].productCode;
				convertedItems[i][1] = currentItems[i].productName;
				convertedItems[i][2] = decfor.format(currentItems[i].unitPrice);
				convertedItems[i][3] = Integer.toString(currentItems[i].quantity);
				convertedItems[i][4] = decfor.format(currentItems[i].unitPrice * currentItems[i].quantity);
			}
			
		}
	}
	
	
	// Searches for given product code in currentItems, returns index if found, else returns -1.
	public int itemIndex(Product temp) {
		
		int index = -1;
		
		for (int i = 0; i < counter; i++) 
		{
			if (temp.productCode.equals(currentItems[i].productCode)) 
			{
				index = i;
			}
		}
		
		return index;
		
	}
		
	// Starts payment procedure, prints receipt as well
	public void payment() {
		
		display.refresh();
		
		final DecimalFormat decfor = new DecimalFormat("0.00"); 
		
		String input = "";
		double total = 0;
		
		for (int i = 0; i < counter; i++) {
			total += currentItems[i].quantity * currentItems[i].unitPrice;
		}
		
		while(!(input.equals("verified")))
		{
			input = promptInput("Verifying payment. Enter \"verified\" once payment is made. Grand total: $" + decfor.format(total) + ".");
		}
		

		printer.printReceipt(convertedItems, counter, total);
		
		while(!(input.equals("restart")))
		{
			input = promptInput("Payment Successful. Printing receipt. Enter \"restart\" to start a new session.");
		}
		reset();
		
	}
	
	// Retrieves product name and price from database file.
		public Product getProductInfo(Product product) {
			
			try {
				File productsDatabase = new File("products.txt");
			    Scanner myReader = new Scanner(productsDatabase);
				
			    boolean found = false;
			    
			    while (myReader.hasNextLine()) {
			        String data = myReader.nextLine();
			        
			        if (data.equals(product.productCode))
			        {
			        	found = true;
			        	product.productName = myReader.nextLine();
			        	product.productPrice = myReader.nextDouble();
			        }
			      }
			    
			    myReader.close();
			    
			    if (found == false) {
			    	display.displayError("No product with given code found.");
			    	validProduct = false;
			    } else {
			    	validProduct = true;
			    }
			    
			} catch (FileNotFoundException e) {
			      display.displayError("No database file found.");
			      e.printStackTrace();
			}
			
			
			return product;
			
		}
	

}
