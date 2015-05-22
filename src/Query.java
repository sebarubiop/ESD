package ESD15s1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * @author Sebastian Rubio
 * (Created on Monday 20 April 2015)
 */
public class Query {//This class is just for break down query methods from the main classes such as Resident and ResidentList

	/**
	 * This method return a resident ArrayList of all records with the same name
	 * @param residents the ArrayList of residents
	 * @param name the name of a resident
	 * @return queryList resident ArrayList
	 */
	public static ArrayList<Resident> getQueryName(ArrayList<Resident> residents, String name){//Save all records with same name
	
		ArrayList<Resident> queryList = new ArrayList<Resident>();
		for(Resident a : residents){
			if(a.getName().equalsIgnoreCase(name)){
				queryList.add(a);
			}
		}		
		return queryList;		
	}
	
	/**
	 * This method return a resident ArrayList of all records with the same birthday
	 * @param residents the ArrayList of residents
	 * @param dob the birthday of a resident
	 * @return queryList resident ArrayList
	 */
	public static ArrayList<Resident> getQueryDOB(ArrayList<Resident> residents, String dob){//Save all records with same DOB
		
		ArrayList<Resident> queryList = new ArrayList<Resident>();
		
		for(Resident a : residents){
			if(a.getBirthday().equals(dob)){
				queryList.add(a);
			}
		}		
		return queryList;	
	}
	
	/**
	 * This method return a resident ArrayList of all records with the same post code
	 * @param residents the ArrayList of residents
	 * @param post the post code of a resident
	 * @return queryList resident ArrayList
	 */
	public static ArrayList<Resident> getListByPost(ArrayList<Resident> residents, String post){
		ArrayList<Resident> queryList = new ArrayList<Resident>();
		
		for(Resident a : residents){
			String addr = a.getAddress();//Get post code by split address			
			String postCode = getPostCode(addr); 
			if(postCode.equals(post)){
				queryList.add(a);
			}
		}		
		return queryList;
	}
	
	/**
	 * This method return the average age of a resident ArrayList
	 * @param resident the ArrayList of residents
	 * @return ave average age in a list of suburb
	 */
	public static double averageAge(ArrayList<Resident> resident){
		double ave=0.0;
		double sum=0.0;
		for(Resident s : resident){
			String dob = s.getBirthday();
			String[] dobA = dob.split("-");//[0]day , [1] month, [2] year
			int age = getAge(Integer.parseInt(dobA[0]),Integer.parseInt(dobA[1]),Integer.parseInt(dobA[2]));
			sum = sum + age;
		}
		ave = 1.0 * sum / resident.size();
		return ave;
	}
	
	/**
	 * Percentage by ages in a list
	 * @param resident the ArrayList of residents
	 * @param range the age range under 18, (+)18-45, (+)45-65 and 65+
	 * @return percent percentage of age in a list of suburb
	 */	
	public static double percentageAge(ArrayList<Resident> resident, String range){
		double percent=0.0;
		int count=0;
		for(Resident s : resident){
			String dob = s.getBirthday();
			String[] dobA = dob.split("-");//[0]day , [1] month, [2] year
			int age = getAge(Integer.parseInt(dobA[0]),Integer.parseInt(dobA[1]),Integer.parseInt(dobA[2]));			
			if(range.equalsIgnoreCase("under 18")){
				if(age<18){
					count++;
				}
				percent = 1.0*count*100/resident.size();				
			}			
			else if(range.equalsIgnoreCase("18-45")){
				if(age>=18 && age<=45){
					count++;
				}
				percent = 1.0*count*100/resident.size();
			}
			else if(range.equalsIgnoreCase("45-65")){
				if(age>45 && age<=65){
					count++;
				}
				percent = 1.0*count*100/resident.size();
			}		
			else if(range.equalsIgnoreCase("65+")){
				if(age>65){
					count++;
				}
				percent = 1.0*count*100/resident.size();
			}			
		}
		return percent;
	}

	/**
	 * Percentage by qualification in a list
	 * @param resident the ArrayList of residents
	 * @param range "Bachelor or Higher degree", "Advanced Diploma or Diploma", "Vocational" or "Not stated"
	 * @return percent percentage of age in a list of suburb
	 */
	public static double percentageQualif(ArrayList<Resident> resident, String range){
		double percent=0.0;
		int count=0;
		for(Resident s : resident){
			String qua = s.getQualification();
						
			if(range.equalsIgnoreCase("bachelor") || range.equalsIgnoreCase("higher degree") || range.equalsIgnoreCase("bachelor or higher degree")){
				if(range.equalsIgnoreCase(qua)){
					count++;
				}
				percent = 1.0*count*100/resident.size();				
			}			
			else if(range.equalsIgnoreCase("Advanced Diploma") || range.equalsIgnoreCase("Diploma") || range.equalsIgnoreCase("Advanced Diploma or Diploma")){
				if(range.equalsIgnoreCase(qua)){
					count++;
				}
				percent = 1.0*count*100/resident.size();				
			}
			else if(range.equalsIgnoreCase("vocational")){
				if(range.equalsIgnoreCase(qua)){
					count++;
				}
				percent = 1.0*count*100/resident.size();				
			}
			else if(range.equalsIgnoreCase("not stated")){
				if(range.equalsIgnoreCase(qua)){
					count++;
				}
				percent = 1.0*count*100/resident.size();				
			}
		}
		return percent;
	}
		
	/**
	 * Percentage by Salary in a list
	 * @param resident the ArrayList of residents
	 * @return average of salary in a list of suburb
	 */
	public static double averageSalary(ArrayList<Resident> resident){
		double average = 0.0;
		double salary = 0.0;
		double sum = 0.0;
		
		for(Resident s : resident){
			String sal = s.getSalary();
			if(sal==null){
				continue;
			}else{
				salary = parseSalaryToDouble(sal);
				sum = sum + salary;
			}
		}		
		average = 1.0 * sum / resident.size();
		
		return average;
	}
	
	/**
	 * Parse String salary to double salary
	 * @param stringSalary salary
	 * @return salary
	 */
	public static double parseSalaryToDouble(String stringSalary){//parse this ($5,000) to 5000.00
		double salary;	
		String[] value = stringSalary.substring(1).split(",");//separate token by comma. Start from substring 1 to avoid to read $ sign
		String value2="";//new String token without comma to pass it to Double
		for(int i=0 ; i<value.length ; i++){
			value2+=value[i];
		}
		salary = Double.valueOf(value2);  
		return salary;
	}
	
	/**
	 * Calculate median number in a resident list by salaries
	 * @param res an ArrayList of residents
	 * @return  median
	 */
	public static double mediaSalary(ArrayList<Resident> res){
		
		ArrayList<String> salary = new ArrayList<String>();
		for(Resident r : res){
			salary.add(r.getSalary());			
		}
		for(int i=0 ; i< salary.size() ; i++){
			if(salary.get(i)==null){
				salary.remove(i);
			}
		}
		ArrayList<Double> douSal = new ArrayList<Double>();
		for(String s:salary){
			douSal.add(parseSalaryToDouble(s));
		}
		
		Collections.sort(douSal);
		
		int middle = douSal.size()/2;
		double sal1 = 0.0;
		double sal2 = 0.0;	
		if(douSal.size() == 0)
			return 0;
		else if(douSal.size() == 1){
			sal1 = douSal.get(0);
			return sal1;
		}
		else if(douSal.size() == 2){
			sal1 = douSal.get(0);
			sal2 = douSal.get(1);
	    	return (sal1 + sal2) / 2.0;
		}
		else if(douSal.size()%2 == 1){
			sal1 = douSal.get(middle);
	        return sal1;
		}
	    else{
	    	sal1 = douSal.get(middle);
			sal2 = douSal.get(middle-1);
	    	return (sal1 + sal2) / 2.0;
	    }
	}
	
	/**
	 * Get post code of an address
	 * @param addr the address to get the post code
	 * @return postCode post code number 
	 */	
	public static String getPostCode(String addr){
		String[] addrTokens = addr.split(",");//separate token by comma. [0] name address, [1] suburb, [2] post code
		String[] postCode = addrTokens[2].substring(1).split(" ");//Split post code in 2 (e.i. NSW 2010). Post code start from N in this example
		return postCode[1];//Only number: (e.i. NSW 2010) >> 2010
	}

	/**
	 * Get suburb name of an address
	 * @param addr the address to get the suburb name
	 * @return suburb name
	 */	
	public static String getSuburbName(String addr){
		String[] addrTokens = addr.split(",");//separate token by comma. [0] name address, [1] suburb, [2] post code
		String suburb = addrTokens[1].substring(1);// Suburb start from first letter (avoid white space at beginning)
		return suburb;
	}
	
	/**
	 * Calculate age
	 * @param day the year of resident's birthday
	 * @param month the month of resident's birthday
	 * @param year the day of resident's birthday
	 * @return age
	 */	
	public static int getAge(int day, int month, int year) {
	    int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
	    int nowYear = Calendar.getInstance().get(Calendar.YEAR);
	    int age = nowYear - year;
	    if (month > nowMonth) {
	        age--;
	    }
	    else if (month == nowMonth) {
	        int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

	        if (day > nowDay) {
	            age--;
	        }
	    }
	    return age;
	}
	
	/**
	 * Print any ArrayList of residents in a out put file
	 * @param residents an ArrayList of residents
	 * @param writereport the name of output file report
	 * @param nam_dob if the list is from query name or birthday
	 * @param value it could be a name or birthday of a resident
	 */
	public static void printAnyList(ArrayList<Resident> residents,String writereport, String nam_dob, String value){
		
		IOFiles.writeFileT(writereport);
		
		if (nam_dob.equalsIgnoreCase("name")){
			IOFiles.putf("========== query name %s ============", value);
			IOFiles.putln("");
			for(int i=0 ; i<residents.size() ; i++){			
				IOFiles.putf("name: %s",residents.get(i).getName());
				IOFiles.putln("");
				IOFiles.putf("birthday: %s",residents.get(i).getBirthday());
				IOFiles.putln("");
				IOFiles.putf("address: %s",residents.get(i).getAddress());
				IOFiles.putln("");
				int pho =residents.get(i).getPhone();
				if(pho != 0){
					IOFiles.putf("phone: %d",pho);
					IOFiles.putln("");
				}
				String qua =residents.get(i).getQualification();
				if(qua != null){
					IOFiles.putf("qualification: %s",qua);
					IOFiles.putln("");
				}
				String sal =residents.get(i).getSalary();
				if(sal != null){
					IOFiles.putf("salary: %s",sal);
					IOFiles.putln("");
				}
				String ema =residents.get(i).getEmail();
				if(ema != null){
					IOFiles.putf("email: %s",ema);
					IOFiles.putln("");
				}
				IOFiles.putln("");
				//IOFiles.putln("");
			}
			IOFiles.putln("--------------------------------------------");
		}
		else if (nam_dob.equalsIgnoreCase("birthday")){
			IOFiles.putf("============= query birthday %s =============", value);
			IOFiles.putln("");
			for(int i=0 ; i<residents.size() ; i++){			
				IOFiles.putf("name: %s",residents.get(i).getName());
				IOFiles.putln("");
				IOFiles.putf("birthday: %s",residents.get(i).getBirthday());
				IOFiles.putln("");
				IOFiles.putf("address: %s",residents.get(i).getAddress());
				IOFiles.putln("");
				int pho =residents.get(i).getPhone();
				if(pho != 0){
					IOFiles.putf("phone: %d",pho);
					IOFiles.putln("");
				}
				String qua =residents.get(i).getQualification();
				if(qua != null){
					IOFiles.putf("qualification: %s",qua);
					IOFiles.putln("");
				}
				String sal =residents.get(i).getSalary();
				if(sal != null){
					IOFiles.putf("salary: %s",sal);
					IOFiles.putln("");
				}
				String ema =residents.get(i).getEmail();
				if(ema != null){
					IOFiles.putf("email: %s",ema);
					IOFiles.putln("");
				}
				IOFiles.putln("");
			}
			IOFiles.putln("---------------------------------------------");
		}
	}
	

	/**
	 * Print query suburb by age in a out put file
	 * @param listSuburb an ArrayList of residents by suburb
	 * @param postCode post code number
	 * @param address the address to get the suburb name
	 * @param writereport the name of output file report
	 */
	public static void printQuerySuburbAge(ArrayList<Resident> listSuburb, String postCode, String address, String writereport){
		
		IOFiles.writeFileT(writereport);
		
		//3° Query: Statistic Query by Suburb and Age
		IOFiles.putf("========== query %s age ==========",postCode);
		IOFiles.putln("");
		IOFiles.putf("Suburb name: %s ", Query.getSuburbName(address));
		IOFiles.putln("");
		
		//Show size population for a list. This case for query age list.
		int sizeQAge = listSuburb.size();
		IOFiles.putf("Population size: %d ", sizeQAge); 
		IOFiles.putln("");
		
		//Show average of ages for a list. This case for query age list.
		IOFiles.putln("Age profile");
		double aveQAge = Query.averageAge(listSuburb);
		IOFiles.putf("Average: %1.1f years \n", aveQAge); 
		IOFiles.putln("");
		
		//Percentage of ages in a suburb. Range percentage: Under 18, (+)18-45, (+)45-65 and 65+
		double perQAge18 = Query.percentageAge(listSuburb, "under 18");
		IOFiles.putf("Under 18: %1.1f", perQAge18);
		IOFiles.putln("%");

		//Percentage of ages in a suburb. Range percentage: Under 18, (+)18-45, (+)45-65 and 65+
		double perQAge1845 = Query.percentageAge(listSuburb, "18-45");
		IOFiles.putf("18 - 45: %1.1f", perQAge1845);
		IOFiles.putln("%");
			
		//Percentage of ages in a suburb. Range percentage: Under 18, (+)18-45, (+)45-65 and 65+
		double perQAge4565 = Query.percentageAge(listSuburb, "45-65");
		IOFiles.putf("45 - 65: %1.1f", perQAge4565);
		IOFiles.putln("%");
		
		//Percentage of ages in a suburb. Range percentage: Under 18, (+)18-45, (+)45-65 and 65+
		double perQAge65 = Query.percentageAge(listSuburb, "65+");
		IOFiles.putf("65+: %1.1f", perQAge65);
		IOFiles.putln("%");
		IOFiles.putln("----------------------------");
	}
	
	/**
	 * Print query suburb by qualification in a out put file
	 * @param listSuburb an ArrayList of residents by suburb
	 * @param postCode post code number
	 * @param address the address to get the suburb name
	 * @param writereport the name of output file report
	 */
	public static void printQuerySuburbQualif(ArrayList<Resident> listSuburb, String postCode, String address, String writereport){
		
		IOFiles.writeFileT(writereport);
		
		//4° Query: Statistics Query by Suburb and Qualification
		//Range: "Bachelor or Higher degree", "Advanced Diploma or Diploma", "Vocational" or "Not stated"
		
		IOFiles.putf("======= query %s qualification =======",postCode);
		IOFiles.putln("");
		IOFiles.putf("Suburb name: %s ", Query.getSuburbName(address));
		IOFiles.putln("");
		
		//Show size population for a list. This case for query age list.
		int sizeQAge = listSuburb.size();
		IOFiles.putf("Population size: %d", sizeQAge); 
		IOFiles.putln("");
		
		IOFiles.putln("Qualification profile");
		double perBach = Query.percentageQualif(listSuburb, "Bachelor") + Query.percentageQualif(listSuburb, "Higher degree")
				+ Query.percentageQualif(listSuburb, "Bachelor or Higher degree");
		IOFiles.putf("Bachelor or Higher Degree: %1.1f", perBach);
		IOFiles.putln("%");
		
		double perDiplo = Query.percentageQualif(listSuburb, "Advanced Diploma") + Query.percentageQualif(listSuburb, "Diploma")
				+ Query.percentageQualif(listSuburb, "Advanced Diploma or Diploma");
		IOFiles.putf("Advanced Diploma or Diploma: %1.1f", perDiplo);
		IOFiles.putln("%");
		
		double perVoc = Query.percentageQualif(listSuburb, "Vocational");
		IOFiles.putf("Vocational: %1.1f", perVoc);
		IOFiles.putln("%");
		
		double perNs = Query.percentageQualif(listSuburb, "Not stated");
		IOFiles.putf("Not stated: %1.1f", perNs);
		IOFiles.putln("%");
		IOFiles.putln("-------------------------------");
	}
	
	/**
	 * Print query suburb by salary in a out put file
	 * @param listSuburb an ArrayList of residents by suburb
	 * @param postCode post code number
	 * @param address the address to get the suburb name
	 * @param writereport the name of output file report
	 */
	public static void printQuerySuburbSalary(ArrayList<Resident> listSuburb, String postCode, String address, String writereport){
		
		IOFiles.writeFileT(writereport);
		
		//5° Query: Calculate salary average in a suburb
		
		IOFiles.putf("======== query %s salary ======",postCode);
		IOFiles.putln("");
		IOFiles.putf("Suburb name: %s ", Query.getSuburbName(address));
		IOFiles.putln("");
		
		//Show size population for a list. This case for query age list.
		int sizeQAge = listSuburb.size();
		IOFiles.putf("Population size: %d ", sizeQAge); 
		IOFiles.putln("");
		
		IOFiles.putln("Weekly salary profile");
		double aveSalary = Query.averageSalary(listSuburb);
		double medSalary = Query.mediaSalary(listSuburb);
		IOFiles.putf("Average: $%,1.2f ", aveSalary);
		IOFiles.putln("");
		IOFiles.putf("Median: $%,1.2f ", medSalary);
		IOFiles.putln("");
		IOFiles.putln("-------------------------");
	}
}
