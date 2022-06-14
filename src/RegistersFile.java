
public class RegistersFile extends Register{
	Register reg[]=new Register[64];
	
	public RegistersFile() {
		// TODO Auto-generated constructor stub
		for(int i=0;i<reg.length;i++) {
			reg[i]=new Register();
		}
	}

	public Register getReg(int index) {
		return reg[index];
	}
	
	public byte getRegData(int index) {
		return reg[index].getValue();
	}

	public void setReg(int index,byte data) {
		System.out.println("Writing back To Register "+index);
		
		//String binaryOld = Integer.toBinaryString(this.reg[index].getValue());
		byte old = this.reg[index].getValue();
		this.reg[index].setValue(data);
		//String binaryNew = Integer.toBinaryString(this.reg[index].getValue());
		

		System.out.println("Register " +index+ " Content Updated From =" + old+ " to  = " + this.reg[index].getValue());
		
	}
	
	

}
