package assignment2;

public class BarcodeScanner extends InputPeripheral {
	
	CashRegister register;
	String code;
	
	public BarcodeScanner() {
	}
	
	public void scan() {
		
		double key = Math.random();
		
		if (key < 0.33)
		{
			code = "100";
		} else if (key < 0.66) {
			code = "101";
		} else {
			// Failed to scan
			code = "-1";
		}
		
		notifyRegister();
		
	}
	
	public void setCode(String temp)
	{
		this.code = temp;
	}
	
	public void clear() {
		this.code = "";
	}

}
