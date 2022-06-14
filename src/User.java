import java.util.ArrayList;

public class User {
	static int cycle = 1;
	static Arch arch;
	static short fetched;
	static int[] decoded;
	
	public User() {
		arch = new Arch();
	}

	public static String reconnect(String[] inst) {
		String result = "";
		for (int i = 0; i < inst.length; i++) {
			result += inst[i] + " ";
		}
		return result;
	}

	public static void main(String[] args) {
		User u = new User();
		boolean isFetched = true;
		boolean isDecoded = true;
		ArrayList<String[]> result = u.arch.parser.result("file.txt");

		int ExecutingNum = 0;

		int n = u.arch.readInstructions("file.txt", result);

		int maxCycles = 3 + ((n - 1) * 1);
		boolean branch1 = false;
		boolean branch2 = false;
		
		for (cycle =1 ; cycle < maxCycles; cycle++) {
			if ((!(isDecoded)) && (!(isFetched))) {
				break;
			}
			System.out.println("Start of cycle: " + cycle);
			
			String binary = Integer.toBinaryString(u.arch.pc.value);
			System.out.println("Program Counter: Binary Content = " + binary + " Decimal Content = " + u.arch.pc.value);
			
			if (cycle == 1 || branch1 == true) {
				if (u.arch.pc.getValue() < n) {
					fetched = u.arch.fetch();
					
				} else {
					isFetched = false;
					}
				if(branch1) {
					branch1 = false;
					branch2 = true;
				}				
			} else if (cycle == 2 || branch2 == true) {
				if (isFetched == true) {
					decoded = u.arch.decode(fetched);
				} else
					isDecoded = false;
				if (u.arch.pc.getValue() < n) {
					fetched = u.arch.fetch();
				} else {
					isFetched = false;
				}
					branch2 = false;
				
			} else {
				if (isDecoded == true) {
					
					System.out.println("Executing: " + reconnect(result.get(ExecutingNum)));
					branch1 = u.arch.excute(decoded);
					ExecutingNum++;

					

					if (branch1 == true) {
						ExecutingNum= u.arch.pc.value;
						maxCycles+=2;
						System.out.println("-------------------------------------------------------");
						continue;
					}
					
				} else {
					System.out.println("-------------------------------------------------------");
					break;
				}
				if (isFetched == true) {
					decoded = u.arch.decode(fetched);
				} else
					isDecoded = false;
				

				if (u.arch.pc.getValue() < n) {
					fetched = u.arch.fetch();
				} else
					isFetched = false;
				
			}
			
			System.out.println("-------------------------------------------------------");
		}
		System.out.println("Instruction memory: ");

		
		for (int i=0;i<result.size();i++){
			System.out.print("Instruction "+ i +" is : ");
			for (int j=0;j<result.get(i).length;j++) {
			   System.out.print(result.get(i)[j]+" ");
		
			}
				
			 System.out.print("( "+Integer.toBinaryString(u.arch.IM.InstrArray[i])+" )");
			System.out.println();
		}
		System.out.println("-------------------------------------------------------");
		System.out.println("Data memory: ");
		for (int i=0;i< arch.DM.DataArr.length ;i++) {
			if (arch.DM.DataArr[i]!=0) {
      	System.out.println("Data "+i+" is : "+arch.DM.readData(i)+" ");
			}
		}
		System.out.println("-------------------------------------------------------");
		System.out.println("Content of Registers : ");
		for (int i=0;i<u.arch.RF.reg.length;i++) {
			if ((u.arch.RF.reg[i].getValue()!=0 )) {
			System.out.println("Register number "+ i + " content is " + u.arch.RF.reg[i].getValue());
			}
		}
		
		System.out.println("-------------------------------------------------------");
		System.out.println("End");

	}

}
