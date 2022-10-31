package assignment2;

public class InputPeripheral {
	
	CashRegister register;
	
	public void attach(CashRegister register){
	      this.register = register;		
	   }
	
	public void notifyRegister() {
		register.update();
	}

}
