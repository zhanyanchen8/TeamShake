/**
 * Class.java
 * has array of names of students
 * has abilities to shuffle students into teams in class, add/delete some student in the class
 * students stored in ArrayList
 * 
 * @author Yanchen Zhan
 *
 */

import java.util.ArrayList;

public class Class {
	
	private String className; //all upper case
	private ArrayList<Student> students;
	private int cnt; //represents number of students in the class
	
	public Class(String n, int num) {
		className = n;
		students = new ArrayList<Student>(num);
		cnt = 0;
		MainActivity.writer.println("ClassName: " + className + ";");
		MainActivity.writer.println("NumStudents: " + num + ";");
		addStudents(num);
	}
	
	/**
	 * creation of class for existing teacher
	 * @param n
	 * @param num
	 * @param b
	 */
	public Class(String n, int num, boolean b, int index) {
		className = n;
		students = new ArrayList<Student>(num);
		cnt = 0;
		addExistingStudents(num, index);
	}

	public String getName() {
		return className;
	}
	
	/**
	 * change so works when importing student information into this (name of students)
	 * @param num
	 */
	public void addStudents(int num) {
		for (int i = 0; i < num; i++) {
			String[] ret = MainActivity.inputNames("Student " + i +  " Information");
			students.add(cnt, new Student(ret[0].toUpperCase(), ret[1].toUpperCase())); //input traits in student constructor
			cnt++;
		}
	}
	
	public void addExistingStudents(int num, int index) {
		//manipulate MainActivity.teachInfo here to get info
		String studentName = "";
		int traits0 = 0, traits1 = 0, traits2 = 0, traits3 = 0, traits4 = 0, traits5 = 0, traits6 = 0; 
		int in = index;
		while (in != MainActivity.teachInfo.length())
		{
			studentName = MainActivity.teachInfo.substring(MainActivity.teachInfo.indexOf("StudentName: ", in) + "StudentName: ".length(), 
					MainActivity.teachInfo.indexOf(";", MainActivity.teachInfo.indexOf("StudentName: ", in)));
			in = MainActivity.teachInfo.indexOf(studentName, in) + studentName.length() + 2;
			int endIndex = MainActivity.teachInfo.indexOf("ClassName", in);
			
			//figure out part below - null pointer exception
			traits0 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 0:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 0: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			traits1 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 1:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 1: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			traits2 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 2:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 2: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			traits3 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 3:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 3: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			traits4 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 4:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 4: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			traits5 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 5:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 5: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			traits6 = Integer.parseInt(MainActivity.teachInfo.substring(
					MainActivity.teachInfo.indexOf("Trait 6:", in) + "Trait 0: ".length(), 
							MainActivity.teachInfo.indexOf(";", in)));
			in = MainActivity.teachInfo.indexOf("Trait 6: ", in);
			in = MainActivity.teachInfo.indexOf(";", in) + 2;
			
			students.add(cnt, new Student(studentName, traits0, traits1, traits2, traits3, traits4, traits5, traits6));
			
			if (in == endIndex) {
				break;
			}
		}
		
	}
	
	public boolean deleteStudent (String lName, String fName) {
		for (int i = 0; i < students.size(); i++) {
			if ((students.get(i).getLastName().equals(lName.toUpperCase())) && 
					students.get(i).getFirstName().equals(fName.toUpperCase())) {
				students.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * makeTeams
	 * takes number of groups desired, weakness to exploit OR "mix"
	 * outputs different groups onto GUI application
	 * 
	 * WORK THIS OUT
	 * 
	 * @param groups, weakness
	 */
	public String[] makeTeams (int groups, String weakness) {
		
		//change weakness into corresponding value stored in traits array
		int weaknessInt = -1;
		switch (weakness) {
			case "effort":
				weaknessInt = 0;
				break;
			case "participation":
				weaknessInt = 1;
				break;
			case "skill":
				weaknessInt = 2;
				break;
			case "cooperation":
				weaknessInt = 3;
				break;
			case "leadership":
				weaknessInt = 4;
				break;
			case "reliability":
				weaknessInt = 5;
				break;
			case "affability":
				weaknessInt = 6;
				break;
		}
		
		//copy over data to keep original student list
		ArrayList<Student> temp = new ArrayList<Student>(students);
		temp.trimToSize();
		
		//create array to put string of names of ppl in a team together for groups teams
		String[] teams = new String[groups];
		for (int i = 0; i < groups; i++) {
			teams[i] = "";
		}
		
		for (int i = 0; i < ((int)(Math.random() * 100)); i++)
		{
			shuffle(temp); //regenerate list to keep random
		}
		
		//group is index parsing through the arraylist
		int rank = 5, group = 0, index = temp.size() - 1;
		while (rank > 0 && temp.size() > 0 && index >= 0) {
			//group index greater than size of team array --> reset to beginning of array
			if (group >= teams.length) {
				group = 0;
			}
			
			//person of right rank (high to low) --> put in respective group, remove from temp array
			if (temp.get(index).getTraitRanking(weaknessInt) == rank) {
				teams[group] += (temp.get(index).getLastName() + ", " + temp.get(index).getFirstName() + "\n");
				temp.remove(index);
				group++;
			}
			
			//move on to next person in the array
			index--;
			
			//reset index if reach end of ArrayList from finding person of certain rank
			if (index < 0) {
				index = temp.size() - 1;
				rank--;
			}
			
		}
		
		return teams;
	}
	
	/**
	 * reorder student order in the student array
	 */
	private void shuffle(ArrayList<Student> t) {
		while (t.remove(null));
    	for(int i=0; i<t.size(); i++)
    	{
    		swap((int)(Math.random()*(t.size())), t);
    	}
    }
    
	private void swap(int j, ArrayList<Student> t) {
    	Student c = t.remove(j);
    	t.add(c);
    }
    
	public String toString() {
		return className + "\n" + students.toString();
	}

}
