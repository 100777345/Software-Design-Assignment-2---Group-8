package assignment2;

public class ReceiptEntry {
	
	String productCode;
	String productName;
	double unitPrice;
	int quantity;
	
	public ReceiptEntry(String productCode, String productName, double unitPrice, int quantity) {
		
		this.productCode = productCode;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		
	}

}
