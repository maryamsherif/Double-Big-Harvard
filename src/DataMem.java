import java.util.HashMap;

public class DataMem {
	static byte DataArr[]=new byte[2048];
	boolean found=true;
	
	public DataMem() {
		// TODO Auto-generated constructor stub
	}
	
	public byte readData(int address) {
		if (0<=address && address<=2047) {
			byte temp=DataArr[address];
			found=true;
			return temp;
		}
		else {
			found=false;
			return -1;
		}
			
	}
	
	public void writeData(int address,byte data) {
		if(0<=address && address<=2047) {
			DataArr[address]=data;
		}
		else {
			System.out.println("Data Memory is Full !");
		}
		

	}
	

}
