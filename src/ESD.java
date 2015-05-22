package ESD15s1;

/**
 * @author Sebastian Rubio
 * (Created on Monday 20 April 2015)
 */
public class ESD {
	
	public static void main(String[] args){

		String file="residents_7.txt";
		String instruction = "instructions_7.txt";
		String result = "result_7.txt";
		String report = "report_7.txt";
		ResidentList resL = new ResidentList();
		
		resL.fillResidentList(file);
		
	    //Read queries: add, delete and sort
		//Make queries by: Name, Birthday and Post code (Statistic Query by Suburb according to Age, Qualification and Salary) >> 5 queries in total
		resL.readInstruction(instruction,report);
				
		resL.printResidentList(result);
		
	}	

}
