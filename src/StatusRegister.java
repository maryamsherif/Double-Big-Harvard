
public class StatusRegister extends Register {
	int carry;
	int overflow;
	int negative;
	int sign;
	int zero;
	
	public byte getStatusRegister() {
		carry=carry<<4;
		overflow=overflow<<3;
		negative=negative<<2;
		sign=sign<<1;
		this.value= (byte) (carry+overflow+negative+sign+zero);
		return this.value;
	}
	
	
	public int getCarry() {
		return carry;
	}


	public void setCarry(int carry) {
		this.carry = carry;
	}


	public int getOverflow() {
		return overflow;
	}


	public void setOverflow(int overflow) {
		this.overflow = overflow;
	}


	public int getNegative() {
		return negative;
	}


	public void setNegative(int negative) {
		this.negative = negative;
	}


	public int getSign() {
		return sign;
	}


	public void setSign(int sign) {
		this.sign = sign;
	}


	public int getZero() {
		return zero;
	}


	public void setZero(int zero) {
		this.zero = zero;
	}


	public StatusRegister() {
		// TODO Auto-generated constructor stub
	}

}
