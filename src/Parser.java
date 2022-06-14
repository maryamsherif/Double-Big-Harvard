import java.io.BufferedReader;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files


public class Parser {
	
	public Parser() {	
		
	}
	
	public ArrayList<String[]> result(String name){
		ArrayList<String> commands = new ArrayList<String>();
		ArrayList<String[]> finalCommands = new ArrayList<String[]>();
		String currentLine = "";
		FileReader fileReader;
		try {
			fileReader = new FileReader(name);
			BufferedReader br = new BufferedReader(fileReader);
			while ((currentLine = br.readLine()) != null) {
				commands.add(currentLine);
				// Parsing the currentLine String
				}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i<commands.size();i++) {
			String [] result= ((String)commands.get(i)).split(" ");
			finalCommands.add(result);
		}
		return finalCommands;
	}
	
	public short[] interpreter(ArrayList<String[]> arr) {
		short[] result= new short[1024];
		
	 for (int i=0;i<arr.size();i++) {
			short op = 0;
			boolean typeR=true;
	///////////////////////////////// opcode		
			switch(arr.get(i)[0]) {
				case "ADD": op=0; typeR=true;break;
				case "SUB":	op=1; typeR=true;break;
				case "MUL": op=2;typeR=true; break;
				case "MOVI": op=3;typeR=false; break;
				case "BEQZ": op=4;typeR=false; break;
				case "ANDI":op=5;typeR=false; break;
				case "EOR":op=6;typeR=true; break;
				case "BR":op=7; typeR=true;break;
				case "SAL":op=8;typeR=false; break;
				case "SAR":op=9; typeR=false;break;
				case "LDR":op=10;typeR=false; break;
				case "STR":op=11;typeR=false; break;
			
			}
		////////////////////////////////////	R1	
			String s="";
			for (int x=1;x<arr.get(i)[1].length();x++) {
				s+=arr.get(i)[1].charAt(x);
			}
			short regNum=(short) Integer.parseInt(s);
			
			short regNum1=0;
			if(typeR) {
		//////////////////////////////////// R2 aw Immediate
				String s1="";
				for (int x=1;x<arr.get(i)[2].length();x++) {
					s1+=arr.get(i)[2].charAt(x);
				}
				regNum1=(short) Integer.parseInt(s1);
				
			}
			else {
				regNum1=(short)Integer.parseInt(arr.get(i)[2]);
			}
		////////////////////////////////// Gam3nahom
			op=(short) (op<<12);
			regNum=(short) (regNum<<6);
			regNum1 = (short) (regNum1 & 0b111111);
			short res=(short) (op+regNum+regNum1);
			result[i]=res;
			

		}
	 	
	 	return result;
	
	}


}