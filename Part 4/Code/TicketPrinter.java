package assignment2;

public class TicketPrinter {
	
	CashRegister register;
	
	public TicketPrinter() {
	}
	
	public void printReceipt(String[][] convertedList, int length, double total) {
		
		System.out.println("Receipt:");
		
		for (int i = 0; i < length; i++)
		{
			System.out.println(convertedList[i][0] + " " + convertedList[i][1] + " " + convertedList[i][2] + " " + convertedList[i][3] + " " + convertedList[i][4] + " ");
		}
		
		System.out.println("Grand Total: " + total);
	}

}
