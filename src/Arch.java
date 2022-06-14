import java.security.interfaces.RSAKey;
import java.util.ArrayList;

public class Arch {
	PC pc;
	DataMem DM;
	InstrMem IM;
	StatusRegister SR;
	RegistersFile RF;
	Parser parser;

	public Arch() {
		this.pc = new PC();
		DM = new DataMem();
		IM = new InstrMem();
		SR = new StatusRegister();
		RF = new RegistersFile();
		parser = new Parser();
		pc.setValue((short) 0);
	}

	public int readInstructions(String filename, ArrayList<String[]> resultOfParser) {
		IM.InstrArray = parser.interpreter(resultOfParser);
		return resultOfParser.size();

	}

	public short fetch() {

		short result = IM.readMemory(pc);
		return result;
	}

	public int[] decode(short result) {
		System.out.println("Decoding: " + Integer.toBinaryString(result));
		int opcode = 0;
		int r1 = 0;
		int r2 = 0;
		int temp = result & 0b1111000000000000;
		opcode = temp >>> 12;
		temp = result & 0b0000111111000000;
		r1 = temp >>> 6;
		r2 = result & 0b0000000000111111;
		int[] instruction = { opcode, r1, r2 };

		System.out.println("Decoded Instruction For Next Cycle:-");
		System.out.println("OPcode= " + opcode + " ,R1= " + r1 + " ,R2/IMM/OFF= " + r2);

		return instruction;
	}
	
	public static byte signExtend(byte imm) {
		int sign = imm>>5;
		if (sign ==1) {
			imm = (byte)(0b11000000 | imm);
		}
		return imm;
	}

	public boolean excute(int[] instruction) {
		boolean branch = false;
		switch (instruction[0]) {

		case 0:
			RF.setReg(instruction[1], ADD(instruction[1], (byte) instruction[2]));
			break;
		case 1:
			RF.setReg(instruction[1], SUB(instruction[1], (byte) instruction[2]));
			break;
		case 2:
			RF.setReg(instruction[1], MUL(instruction[1], (byte) instruction[2]));
			break;
		case 3:
			RF.setReg(instruction[1], signExtend((byte)instruction[2]));
			break;
		case 4:
			branch = BEQZ(instruction[1], signExtend((byte)instruction[2]));
			break;
		case 5:
			RF.setReg(instruction[1], ANDI(instruction[1], signExtend((byte)instruction[2])));
			break;
		case 6:
			RF.setReg(instruction[1], EOR(instruction[1], (byte) instruction[2]));
			break;
		case 7:
			pc.setValue(BR(instruction[1], (byte) instruction[2]));
			String binary = Integer.toBinaryString(pc.value);
			System.out.println(
					"Program Counter Updated To: Binary Content = " + binary + " Decimal Content = " + pc.value);

			branch = true;
			break;
		case 8:
			RF.setReg(instruction[1], SAL(instruction[1], signExtend((byte) instruction[2])));
			break;
		case 9:
			RF.setReg(instruction[1], SAR(instruction[1], signExtend((byte) instruction[2])));
			break;
		case 10:
			RF.setReg(instruction[1], LDR((byte) instruction[2]));
			break;
		case 11:
			STR(instruction[1], (byte) instruction[2]);
			break;
		}
		String statusR = Integer.toBinaryString(SR.getValue());
		System.out.println("Status Register: " + statusR + " ,C= " + SR.carry + " ,V= " + SR.overflow + " ,N= " + SR.negative
				+ " ,S= " + SR.sign + " ,Z= " + SR.zero);
		return branch;

	}

	public byte ADD(int r1, int r2) {
		int x = RF.getRegData(r1);
		int y = RF.getRegData(r2);
		
		System.out.println("Register "+ r1 + "content: "+ x);
		System.out.println("Register "+ r2 + "content: "+ y);
		
		int result = x + y;
		int carry2 = 0;
		if ((result & 0b100000000) == 0b100000000) {
			SR.setCarry(1);
			carry2 = 1;
		} else
			SR.setCarry(0);
		int temp1 = x & 0b1111111;
		int temp2 = y & 0b1111111;
		int result1 = temp1 + temp2;
		int carry1 = 0;
		if ((result1 & 0b10000000) == 0b10000000) {
			carry1 = 1;
		}
		if ((carry1 ^ carry2) == 1)
			SR.setOverflow(1);
		else
			SR.setOverflow(0);

		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		SR.setSign(((SR.getNegative()) ^ (SR.getOverflow())));

		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) result;
	}

	public byte SUB(int r1, int r2) {
		int x = RF.getRegData(r1);
		int y = RF.getRegData(r2);
		
		System.out.println("Register "+ r1 + "content: "+ x);
		System.out.println("Register "+ r2 + "content: "+ y);
		
		int result = x - y;
		int carry2 = 0;
		if ((result & 0b100000000) == 0b100000000) {
			carry2 = 1;
		}
		int temp1 = x & 0b1111111;
		int temp2 = y & 0b1111111;
		int result1 = temp1 - temp2;
		int carry1 = 0;
		if ((result1 & 0b10000000) == 0b10000000) {
			carry1 = 1;
		}
		if ((carry1 ^ carry2) == 1)
			SR.setOverflow(1);
		else
			SR.setOverflow(0);
		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		SR.setSign(((SR.getNegative()) ^ (SR.getOverflow())));
		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) (x - y);
	}

	public byte MUL(int r1, int r2) {
		int x = RF.getRegData(r1);
		int y = RF.getRegData(r2);
		
		System.out.println("Register "+ r1 + " content: "+ x);
		System.out.println("Register "+ r2 + " content: "+ y);
		
		int result = x * y;
		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) (result);
	}

	public byte EOR(int r1, int r2) {
		int x = RF.getRegData(r1);
		int y = RF.getRegData(r2);
		
		System.out.println("Register "+ r1 + " content: "+ x);
		System.out.println("Register "+ r2 + " content: "+ y);
		
		int result = x ^ y;
		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) (result);
	}

	public short BR(int r1, int r2) {
		int x = RF.getRegData(r1);
		int y = RF.getRegData(r2);
		
		System.out.println("Register "+ r1 + " content: "+ x);
		System.out.println("Register "+ r2 + " content: "+ y);
		
		x = x << 8;
		return (short) (x + y);
	}

	public boolean BEQZ(int r1, int imm) {
		int x = RF.getRegData(r1);
		
		System.out.println("Register "+ r1 + " content: "+ x);
		System.out.println("Immediate value: "+imm);
		
		if (x == 0) {
			pc.setValue((short) (pc.getValue() -2 + 1 + imm));
			String binary = Integer.toBinaryString(pc.value);
			System.out.println(
					"Program Counter Updated To: Binary Content = " + binary + " Decimal Content = " + this.pc.value);

			return true;
		}
		return false;
	}

	public byte ANDI(int r1, int imm) {
		int x = RF.getRegData(r1);
		
		System.out.println("Register "+ r1 + " content: "+ x);
		System.out.println("Immediate value: "+imm);
		
		int result = x & imm;
		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) (result);
	}

	public byte SAL(int r1, int imm) {
		int x = RF.getRegData(r1);
		
		System.out.println("Register "+ r1 +  " content: "+ x);
		System.out.println("Immediate value: "+imm);
		
		int result = x << imm;
		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) (result);
	}

	public byte SAR(int r1, int imm) {
		int x = RF.getRegData(r1);
		
		System.out.println("Register "+ r1 + " content: "+ x);
		System.out.println("Immediate value: "+imm);
		
		int result = x >> imm;
		if ((byte) result < 0) {
			SR.setNegative(1);
		} else
			SR.setNegative(0);
		if (result == 0) {
			SR.setZero(1);
		} else
			SR.setZero(0);
		return (byte) (result);
	}

	public byte LDR(int address) {
		System.out.println("From address: " + address+ " ("+ Integer.toBinaryString(address) + ")  Data: "
				+DM.readData(address)+" ("+ Integer.toBinaryString(DM.readData(address)) + ")  is read");
		
		return (byte) (DM.readData(address));
	}

	public void STR(int r1, int address) {
		int x = RF.getRegData(r1);
		System.out.println("From Register "+ r1 + " Data: " + x + " is read");
		System.out.println(
				"In address: " + address + " ("+ Integer.toBinaryString(address) + ") " + " Data: " + x + " ("+Integer.toBinaryString(x)+ ") " + " is stored");

		DM.writeData(address, (byte) x);
	}

}
