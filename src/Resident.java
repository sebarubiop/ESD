package ESD15s1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Sebastian Rubio
 * (Created on Monday 20 April 2015)
 */
public class Resident {

	private String name;
	private String birthday;
	private String address;
	private int phone;
	private String qualification;
	private String salary;
	private String email;

	/**
	 * Creates a new instance of Resident by default
	 */
	public Resident(){		
	}
	
	/**
	 * Creates a new instance of Resident by parameters
	 * @param nameR name
	 * @param dob birthday
	 * @param addr address
	 * @param pho phone
	 * @param qua qualification
	 * @param sal salary
	 * @param ema email 
	 */
	public Resident(String nameR, String dob, String addr, int pho, String qua, String sal, String ema){
		name = nameR;
		birthday = dob;
		address = addr;
		phone = pho;
		qualification = qua;
		salary = sal;
		email = ema;
	}
	
	/**
	 * Valid name of a resident according to punctuation characters and number
	 * @param name resident name
	 * @return valName if return true, name is valid.
	 */
	public boolean isValidName(String name){

		String given_punct =null;
		String given_num = null;
		boolean valName=true;
		
		if(name.equals(""))
			valName=false;
		//Valid if there are any punctuation character
		given_punct = name.replaceAll("\\W", " ");
		if(!name.equals(given_punct)){
			valName=false;
		}
		//System.out.print(val_punct);
		
		//Valid if there are any number			
		for(int i=0 ; i<=9 ; i++){
			String num = String.valueOf(i);
			given_num=name.replaceAll(num, " ");
			if(!name.equals(given_num)){
				valName=false;
				break;
			}
		}
		return valName;//if return true, name is valid.
	}

	/**
	 * Valid birthday of a resident according to format and valid date
	 * @param date the birthday of a resident
	 * @return  true if birthday is valid
	 */
	public boolean isValidDOB(String date){
		   
	    ArrayList<SimpleDateFormat> sdf = new ArrayList<SimpleDateFormat>();
	    sdf.add(new SimpleDateFormat("d-M-yyyy"));
	    sdf.add(new SimpleDateFormat("d-MM-yyyy"));// set date format
	    sdf.add(new SimpleDateFormat("dd-M-yyyy"));// set date format
	    sdf.add(new SimpleDateFormat("dd-MM-yyyy"));// set date format
	    
	    ArrayList<Integer> vdate = new ArrayList<Integer>();
	    Date testDate = null;	    
	    for(SimpleDateFormat s : sdf){
	    	//s.setLenient(false);
		    try {
		    	testDate = s.parse(date);// parse the string into date form
		    }
		    // if the format of the string provided doesn't match the format
		    // declared in SimpleDateFormat() we will get an exception
		    catch (ParseException e) {
		    	vdate.add(0);//0 is equal to false
		    }
		    // This statement will make sure that once the string
		    // has been checked for proper formatting that the date is still the
		    // date that was entered, if it's not, we assume that the date is invalid
		    if (!s.format(testDate).equals(date)) {
		    	vdate.add(0);//0 is equal to false
		    }
		    else
		    	vdate.add(1);//1 is equal to true
	    }
	    for(int i : vdate){
	    	if(i == 1)
	    		return true;
	    }
	    
	    return false;
	} 
	
	/**
	 * Makes date consistent with format dd-MM-yyyy
	 * @param dob birthday
	 * @return date in format dd-MM-yyyy
	 */
	public String convertDate(String dob){
		String date = dob;
		if (date.charAt(1) == '-') 
			date = "0" + date;
		if (date.charAt(4) == '-') 
			date = date.substring(0,3) + "0" + date.substring(3);		
		return date;//3-6-2005 or 03-6-2005 or 3-06-2005 (are valid) to 03-06-2005
	}
	
	/**
	 * Valid address of a resident according to format name address, suburb, post code
	 * @param addr address
	 * @return validAddr if true, address is valid
	 */
	public boolean isValidAddress(String addr){
		String[] addrTokens = addr.split(",");//separate token by comma
		boolean validAddr = false;
		
		if(addrTokens.length>3 ||addrTokens.length<3){//102 Smith St, Summer hill, NSW 2130
			return validAddr;
		}
		else{
			String[] postCode = addrTokens[2].substring(1).split(" ");//Split post code in 2 (e.i. NSW 2010). Post code start from N in this example 
			if(postCode[1].length()!=4){
				return validAddr;
			}else
				validAddr = true;			
		}		
		return validAddr;
	}
	
	/*^							#start of the line
	  [_A-Za-z0-9-\\+]+			#  must start with string in the bracket [ ], must contains one or more (+)
	  (							#   start of group #1
	    \\.[_A-Za-z0-9-]+		#     follow by a dot "." and string in the bracket [ ], must contains one or more (+)
	  )*						#   end of group #1, this group is optional (*)
	    @						#     must contains a "@" symbol
	     [A-Za-z0-9-]+      	#       follow by string in the bracket [ ], must contains one or more (+)
	      (						#         start of group #2 - first level TLD checking
	       \\.[A-Za-z0-9]+  	#           follow by a dot "." and string in the bracket [ ], must contains one or more (+)
	      )*					#         end of group #2, this group is optional (*)
	      (						#         start of group #3 - second level TLD checking
	       \\.[A-Za-z]{2,}  	#           follow by a dot "." and string in the bracket [ ], with minimum length of 2
	      )						#         end of group #3
	$							#end of the line
	*/
	/**
	 * Valid email of a resident according to email format
	 * @param email email of a resident
	 * @return valEmail if true, email is valid
	 */
	public boolean isValidEmail(String email){
	      //String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	      String EMAIL_REGEX2="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	      boolean valEmail = false;
	      if(email==null){
	    	  return valEmail=true;
	      }
	      else
	    	  valEmail = email.matches(EMAIL_REGEX2);
	     
	      return valEmail;//if true, email is valid
	}
	
	/**
	 * Valid qualification of a resident according to range of qualifications
	 * @param qual qualification
	 * @return validQual if true, qualification is valid
	 */
	public boolean isValidQualification(String qual){
		
		boolean validQual = false;
		String[] q = {"Diploma","Bachelor or higher degree","Advanced Diploma or Diploma","Vocational","Not stated",
				"Bachelor", "Diploma", "Higher degree", "Advanced Diploma"};
		
		for(String i:q){
			if (i.equalsIgnoreCase(qual)){
				validQual = true;
				break;
			}
		}		
		
		return validQual;//if true, qualification is valid 
	}

	/**
	 * Valid salary of a resident according to $ sign and non-negative
	 * @param stringSalary salary of a resident
	 * @return isValidSal if true, salary is valid
	 */
	public boolean isValidSalary(String stringSalary){
		double salary;
		boolean isValidSal=false;
		if(stringSalary==null){
			return isValidSal=true;
		}
		else{
			boolean isvalid_sign = stringSalary.substring(0, 1).equals("$");//determine if token start with $
			boolean isvalid_neg = stringSalary.substring(0, 1).equals("-");	    	
			
			if(isvalid_sign==true && isvalid_neg == false){//determine if token start with $ and it don't start with - (for negative numbers)
	
	    		salary = Query.parseSalaryToDouble(stringSalary);            		
	    		if(salary >=0){            			
	    			isValidSal = true;
	    		}
	    		else{
	    			//System.out.println();
	    			//System.out.println(stringSalary+" > Salary must be a non-negative number");
	    		}	            	
	    	}
	    	else{
	    		//System.out.println();
	    		//System.out.println(stringSalary+" > It isn't a valid salary");
	    	}
		}
		return isValidSal;//if true, salary is valid
    }
		
	/**
	 * Gets the name of an instance resident
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets the birthday of an instance resident
	 * @return birthday
	 */
	public String getBirthday(){
		return birthday;
	}
	
	/**
	 * Gets the address of an instance resident
	 * @return address
	 */
	public String getAddress(){
		return address;
	}
	
	/**
	 * Gets the phone of an instance resident
	 * @return phone
	 */
	public int getPhone(){
		return phone;
	}
	
	/**
	 * Gets the qualification of an instance resident
	 * @return qualification
	 */
	public String getQualification(){
		return qualification;
	}
	
	/**
	 * Gets the salary of an instance resident
	 * @return salary
	 */
	public String getSalary(){
		return salary;
	}
	
	/**
	 * Gets the email of an instance resident
	 * @return email
	 */
	public String getEmail(){
		return email;
	}
	
	/**
	 * Valid if an instance resident has name
	 * @return hasN true if name exist in the instance resident 
	 */
	public boolean hasName(){
		boolean hasN = false;
		if(name == null)
			return hasN;
		else
			return hasN=true;
	}
	
	/**
	 * Valid if an instance resident has birthday
	 * @return hasB true if birthday exist in the instance resident 
	 */
	public boolean hasBirthday(){
		boolean hasB = false;
		if(birthday == null)
			return hasB;
		else
			return hasB=true;
	}
	
	/**
	 * Valid if an instance resident has address
	 * @return hasA true if address exist in the instance resident 
	 */
	public boolean hasAddress(){
		boolean hasA = false;
		if(address == null)
			return hasA;
		else
			return hasA=true;
	}
	
	public void setName(String nameR){
		name = nameR;
	}
	
	public void setBirthday(String dobR){
		birthday = dobR;
	}
	
	public void setAddress(String addrR){
		address = addrR;
	}
	
	public void setPhone(int phoneR){
		phone = phoneR;
	}
	
	public void setQualification(String quaR){
		qualification = quaR;
	}
	
	public void setSalary(String salR){
		salary = salR;
	}
	
	public void setEmail(String emailR){
		email = emailR;
	}
	
}
