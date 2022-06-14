
public class InstrMem {
	static short InstrArray[]=new short[1024];

	public InstrMem() {
		// TODO Auto-generated constructor stub
	}
	
	public short readMemory(PC pc) {
		if(pc.getValue() < 1024) {
		short temp=InstrArray[pc.getValue()];
		pc.setValue((short)(pc.getValue()+1));
		
		String fetchedBinary = Integer.toBinaryString(temp);
		System.out.println("Fetching: " + fetchedBinary + " From Instruction Memory");
		
		String binary = Integer.toBinaryString(pc.value);
		System.out.println(
				"Program Counter Updated To: Binary Content = " + binary + " Decimal Content = " + pc.value);

		return temp;
		}
		else 
			return -1;
		
	}
	
//	public void writeMemory(short instruction) {
//		if(pointer<1023) {
//		InstrArray[pointer]=instruction;
//		pointer++;
//		}
//		else {
//			System.out.println("Instruction Memory is Full !");
//		}
//	}
	
		
	

}
