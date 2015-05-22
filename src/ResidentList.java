package ESD15s1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

/**
 * @author Sebastian Rubio
 * (Created on Monday 20 April 2015)
 */
public class ResidentList {

	private ArrayList<Resident> residents;
	
	/**
	 * Creates a new instance of ResidentList
	 */
	public ResidentList(){
		residents = new ArrayList<Resident>();
	}
	
	/**
	 * Fill the resident list according to input file
	 * @param file input file 
	 */
    public void fillResidentList(String file){
    	Scanner s=null;
    	int count = 0;//it increases each time that there is a newline
	    try {
	        s = new Scanner(new BufferedReader(new FileReader(file)));
	        Resident r = new Resident();
	        String line = null;
	        boolean hasline = s.hasNextLine();	        
	        boolean addressActive = false;
	        while (hasline) {            	
	        	line = s.nextLine();// read line	        	
	        	if(line.equals("")){
	        		for(int i=count-1 ; i > 0 ; i--){
	        			residents.remove(i);
	        		}
	        		count=0;
	        		r=new Resident();
            	}
	        	Scanner t = new Scanner(line);
	        	if(t.hasNext()){
	        		String tok = t.next();
	        		if(tok.equalsIgnoreCase("name")){
	        			String[] subname = t.nextLine().split(" ");
	        			String fname="";
	        			for(String i : subname){
	        				if(!i.equals(""))
	        					fname += i + " ";
	        			}
	            		r.setName(fname.trim());
	            		residents.add(count,r);
	            		addressActive = false;
	            		count++;
	            	}
	            	else if(tok.equalsIgnoreCase("birthday")){
	            		String d = t.nextLine().trim();          			
	        			String dob=r.convertDate(d);
	        			r.setBirthday(dob);
	        			residents.add(count,r);
	        			addressActive = false;
	        			count++;
	            	} 
	            	else if(tok.equalsIgnoreCase("address")){
	            		r.setAddress(t.nextLine().trim());
	            		residents.add(count, r);
	            		addressActive = true;
	            		count++;
	            	} 
	            	else if(tok.equalsIgnoreCase("phone")){
	            		if(t.hasNextInt()){
	            			int ph = t.nextInt();
		            		r.setPhone(ph);
		            		residents.add(count, r);		            		
		            		count++;
	            		}
	            		addressActive = false;
	            	} 
	            	else if(tok.equalsIgnoreCase("qualification")){
	            		r.setQualification(t.nextLine().trim());
	            		residents.add(count, r);
	            		addressActive = false;
	            		count++;
	            	} 
	            	else if(tok.equalsIgnoreCase("salary")){
	            		r.setSalary(t.nextLine().trim());
	            		residents.add(count, r);
	            		addressActive = false;
	            		count++;
	            	} 
	            	else if(tok.equalsIgnoreCase("email")){
	            		r.setEmail(t.nextLine().trim());
	            		residents.add(count, r);
	            		addressActive = false;
	            		count++;
	            	}
	            	else if(addressActive){
	            		String addr = r.getAddress()+" "+line.trim();
	            		r.setAddress(addr);
	            		residents.add(count, r);
	            		count++;
	            	}	            	
	        	}
	        } 	        
	    } catch (Exception e) {
	    }finally {
		    if (s != null) {
		    	if(count>0){
	        		for(int i=count-1 ; i > 0 ; i--){
	        			residents.remove(i);
	        		}
            	}
		    	validResidentList();
		        s.close();
		    }
	    }
    }
    
    /**
     * Valid data of resident list, if data doesn't valid, it's removed or reset from resident list 
     */
    public void validResidentList(){
    	int index=0;//save index to delete record from list
    	ArrayList<Integer> indices = new ArrayList<Integer>();//list of invalid indices 
    	
    	for(Resident r : residents){
    		    		
    		if(r.hasName()==false || r.hasBirthday()==false || r.hasAddress()==false){//remove records if it doesn't have whatever of these one.
    			indices.add(index);
    		}    		
    		else if(r.isValidName(r.getName())==false || r.isValidDOB(r.getBirthday())==false || r.isValidAddress(r.getAddress())==false){
    			//if some of compulsory fields are invalid, remove record from list.
    			indices.add(index);
    		}
    		else if(r.isValidQualification(r.getQualification())==false){
    			residents.set(index, new Resident(r.getName(),r.getBirthday(),r.getAddress(),r.getPhone(),null,r.getSalary(),r.getEmail()));
    		}
    		else if(r.isValidSalary(r.getSalary())==false){
    			residents.set(index, new Resident(r.getName(),r.getBirthday(),r.getAddress(),r.getPhone(),r.getQualification(),null,r.getEmail()));
    		}
    		else if(r.isValidEmail(r.getEmail())==false){
    			residents.set(index, new Resident(r.getName(),r.getBirthday(),r.getAddress(),r.getPhone(),r.getQualification(),r.getSalary(),null));
    		}
    		else if(r.hasBirthday()==true){
    		String date = r.getBirthday();
    		String[] dat = date.split("-");//[0] day, [1] month, [2] year
    		int year = Integer.parseInt(dat[2]); 
	    		if(year > 2015){ // if year of birthday is greather than 2015, it's removed from resident list 
	    			indices.add(index);
	    		}
    		}
    		index++;
		}
    	//Remove all indices invalid saved in indices list    	
    	deleteResidents(indices);
    }
	
    /**
     * Read instructions from a input file
     * Make queries as: add, delete and sort list by name or birthday
	 * Make statistic query by Suburb according to Age, Qualification and Salary
	 * 1° statistic Query: Statistic Query by Suburb and Age
	 * 2° statistic Query: Statistics Query by Suburb and Qualification
	 * 3° statistic Query: Calculate salary average in a suburb
	 * @param file instruction input file
	 * @param report output file report
     */
	public void readInstruction(String file,String report){				
		
		Scanner s = null;		
		String name=null;
		String dob=null;
		String addr=null;
		int phoR=0;
		String qua=null;
		String sal=null;
		String ema=null;		
		String line = null;
		String token = null;
		
		ArrayList<Resident> qName = new ArrayList<Resident>();
		ArrayList<Resident> qName_sortDOB = new ArrayList<Resident>();
		ArrayList<Resident> qDOB = new ArrayList<Resident>();
		ArrayList<Resident> qDOB_sortName = new ArrayList<Resident>();
		ArrayList<Resident> listSuburb = new ArrayList<Resident>();
				
		try {
	        s = new Scanner(new BufferedReader(new FileReader(file)));
	        boolean hasline = s.hasNextLine();
	        
	        while (hasline) {   
	        	token = s.next();
	        	line = s.nextLine();// read rest of the line            	
	        	String[] piece = line.split(";"); //split rest of the line by semicolon 
	        	
	        	if(token.equalsIgnoreCase("add")){
            		
            		for(int i=0 ; i<piece.length ; i++){
	                	Scanner s2 = new Scanner(piece[i]);
	                	String token2=null;
	                	String line2=null;
	                	while(s2.hasNext()){
	                		token2 = s2.next();
	                		line2 = s2.nextLine();
	                		if(token2.equalsIgnoreCase("name")){
	                			name = line2.trim();
	                		}
	                		else if(token2.equalsIgnoreCase("birthday")){
	                			Resident res=new Resident();            			
	                			dob=res.convertDate(line2.trim());
	                		}
	                		else if(token2.equalsIgnoreCase("address")){
	                			addr = line2.trim();
	                		}
	                		else if(token2.equalsIgnoreCase("phone")){
	                			String pho = line2.trim();
	                			phoR = Integer.parseInt(pho);
	                		}
	                		else if(token2.equalsIgnoreCase("qualification")){
	                			qua = line2.trim();
	                		}
	                		else if(token2.equalsIgnoreCase("salary")){
	                			sal = line2.trim();
	                		}
	                		else if(token2.equalsIgnoreCase("email")){
	                			ema = line2.trim();
	                		}	                		
	                	}
            		}
                	boolean findR = findResident(name,dob);
            		if(findR == true){
            			int ind = getIndexList(name,dob);
            			residents.set(ind, new Resident(name,dob,addr,phoR,qua,sal,ema));
            			validResidentList();
            		}
            		else{
            			residents.add(new Resident(name,dob,addr,phoR,qua,sal,ema));
            			validResidentList();
            		}
            	}         	
            	else if (token.equalsIgnoreCase("delete")){
            		String[] restline=line.split(";");//[0]name and [1]birthday
            		Resident res=new Resident();            			
        			String d=res.convertDate(restline[1].trim());
            		boolean findR = findResident(restline[0].trim(),d);
            		int index=0;
            		if(findR==true){
            			index = getIndexList(restline[0].trim(),d);
            			residents.remove(index);
            		}            		
            	}
            	else if (token.equalsIgnoreCase("sort")){
            		if(line.trim().equals("name")){
            			sortByName(residents);
            		}
            		else if(line.trim().equals("birthday")){
            			sortByDOB(residents);
            		}
            	}
            	else if (token.equalsIgnoreCase("query")){
            		String[] restline = line.split(" ");//[0] null, [1] name or birthday or post code, [2+] rest of the line
            		
            		if(restline[1].trim().equals("name")){
            			String value = "";
            			for(int i=2 ; i<restline.length ; i++){
            				value = value + restline[i] + " ";
            			}            			
            			qName = Query.getQueryName(residents, value.trim());
            			qName_sortDOB = sortByDOB(qName); // Sort query Name by DOB
            			Query.printAnyList(qName_sortDOB,report,restline[1].trim(),value.trim());
            		}
            		else if(restline[1].trim().equals("birthday")){
            			//String value = "";
            			Resident res=new Resident();            			
            			String d=res.convertDate(restline[2].trim());
            			qDOB = Query.getQueryDOB(residents, d);
            			qDOB_sortName = sortByName(qDOB);
            			Query.printAnyList(qDOB_sortName,report,restline[1].trim(),d);
            		} 
            		else if(!restline[1].trim().equals("name") && !restline[1].trim().equals("birthday")){
            			String address = null;  
            			listSuburb = Query.getListByPost(residents, restline[1].trim());//First get a list by suburb  
            			for(Resident r : listSuburb){         				
            				address = r.getAddress();            				
            			}
            			if(restline[2].trim().equals("age")){
                			Query.printQuerySuburbAge(listSuburb, restline[1].trim(), address,report);
                		}
            			else if(restline[2].trim().equals("qualification")){
                			Query.printQuerySuburbQualif(listSuburb, restline[1].trim(), address, report);
                		}
            			else if(restline[2].trim().equals("salary")){
                			Query.printQuerySuburbSalary(listSuburb, restline[1].trim(), address, report);
                		}
            		}            	
            	}
	        }
	    	
	    } catch (Exception e) {
	    }finally {
		    if (s != null) {
		    	validResidentList();
		        s.close();
		    }
	    }
	}
	
	/**
	 * Delete records in resident list according to a list of integer save it in method validResidentList() 
	 * @param index ArrayList of indices to be deleted in the resident list
	 */
	public void deleteResidents(ArrayList<Integer> index){
		//Remove all indices with delete instruction
    	Collections.sort(index, Collections.reverseOrder());//reverse order to start to remove indices from bigger to smaller to avoid bounder exception
    	//System.out.print("Indices delete are: ");
    	for(int i : index){
    		residents.remove(i);
    		//System.out.printf("%d/",i);
       	}
    	//System.out.println();
	}
	
	/**
	 * Sort residents according to name in ascending order
	 * @param res ArrayList of residents
	 * @return res sort by name
	 */
	public ArrayList<Resident> sortByName(ArrayList<Resident> res){
	    for (int i = 0; i < res.size() - 1; i++) {
	        for (int j = i + 1; j < res.size(); j++) {
	        	String s1 = res.get(i).getName();
	        	String s2 = res.get(j).getName();
	        	int comp = s1.compareTo(s2);
	            if (comp > 0) {
	            	Resident temp = new Resident(res.get(j).getName(),res.get(j).getBirthday(),res.get(j).getAddress(),
	            			res.get(j).getPhone(),res.get(j).getQualification(),res.get(j).getSalary(),res.get(j).getEmail());
	                res.set(j, new Resident(res.get(i).getName(),res.get(i).getBirthday(),res.get(i).getAddress(),
	            			res.get(i).getPhone(),res.get(i).getQualification(),res.get(i).getSalary(),res.get(i).getEmail()));
	                res.set(i, temp);
	            }
	        }
	    }
	    return res;
	}
	
	/**
	 * Sort residents according to birthday in ascending order
	 * @param res ArrayList of residents
	 * @return res sort by birthday
	 */
	public ArrayList<Resident> sortByDOB(ArrayList<Resident> res){
	    for (int i = 0; i < res.size() - 1; i++) {
	        for (int j = i + 1; j < res.size(); j++) {
	        	SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-yyyy");
	        	Date d1;
	        	Date d2;
				try {
					d1 = (Date)formatter.parse(res.get(i).getBirthday());
		        	d2 = (Date)formatter.parse(res.get(j).getBirthday());
		        	int comp = (d1).compareTo(d2);
		            if (comp > 0) {
		            	Resident temp = new Resident(res.get(j).getName(),res.get(j).getBirthday(),res.get(j).getAddress(),
		            			res.get(j).getPhone(),res.get(j).getQualification(),res.get(j).getSalary(),res.get(j).getEmail());
		                res.set(j, new Resident(res.get(i).getName(),res.get(i).getBirthday(),res.get(i).getAddress(),
		            			res.get(i).getPhone(),res.get(i).getQualification(),res.get(i).getSalary(),res.get(i).getEmail()));
		                res.set(i, temp);
		            }
				} catch (ParseException e) {
					e.printStackTrace();
				}

	        }
	    }
	    return res;
	}
	
	/**
	 * Print resident list into output file
	 * @param writeresult out put file result
	 */
	public void printResidentList(String writeresult){
		
		IOFiles.writeFile(writeresult);
		
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
		
	}
	
	public Resident retrieveResident(String name, String dob){// retrieve an account based on a given owner name
		
		for(Resident a : residents){
			if(a.getName().equals(name) && a.getBirthday().equals(dob)){
				return a;
			}
		}
		return null;//no match in the entire arraylist
	}
	
	/**
	 * Find a resident by name and birthday in the ArrayList residents
	 * @param name the name of a resident
	 * @param dob the birthday of a resident
	 * @return findRes if true, parameters match with a record in resident list 
	 */
	public boolean findResident(String name, String dob){// retrieve an account based on a given owner name
		boolean findRes= false;
		for(Resident a : residents){
			if(a.getName().equalsIgnoreCase(name) && a.getBirthday().equalsIgnoreCase(dob)){
				findRes = true;
				break;
			}				
		}		
		return findRes;//no match in the entire arraylist
	}
	
	/**
	 * Gets index in the resident list by name and birthday
	 * @param name the name of a resident
	 * @param dob the birthday of a resident
	 * @return index 
	 */
	public int getIndexList(String name, String dob){
		int index=0;
		for(Resident r: residents){
			if(r.getName().equals(name) && r.getBirthday().equals(dob)){
				index = residents.indexOf(r);
				break;
			}
		}
		return index;
	}
	
	/**
	 * Gets the current resident list.
	 * @return residents
	 */
	public ArrayList<Resident> getResidentList(){
		return residents;
	}
	
}
