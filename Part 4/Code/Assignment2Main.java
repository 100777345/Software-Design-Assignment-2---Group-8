package assignment2;

public class Assignment2Main {

	public static void main(String[] args) {
		
		Keyboard keyboard1 = new Keyboard(System.in);
		BarcodeScanner scanner1 = new BarcodeScanner();
		Display display1 = new Display();
		TicketPrinter printer1 = new TicketPrinter();
		CashRegister register1 = new CashRegister(keyboard1, scanner1, display1, printer1);

		register1.initiate();
		
	}

}
