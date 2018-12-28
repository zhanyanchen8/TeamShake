/**
 * Teacher.java
 * Represents workload of a teacher
 * Teacher HAS-AN ArrayList of classes
 * 
 * @author Yanchen Zhan
 * @edit 05/14/2018
 */

import java.util.ArrayList;

public class Teacher {
	
	public static final int MAX_CLASSES = 5;
	private String lastName, firstName;
	private ArrayList<Class> classes;
	private int numClasses;
	
	/**
	 * generates teacher information
	 * @param fName
	 * @param lName
	 */
	public Teacher(String lName, String fName) {
		lastName = lName.toUpperCase();
		firstName = fName.toUpperCase();
		classes = new ArrayList<Class>(MAX_CLASSES);
		numClasses = 0;
	}
	
	/**
	 * adds a class in for teachers (only for creation of new teachers)
	 * @param className
	 * @param numStudents
	 * @return
	 */
	public boolean addClass(String className, int numStudents) {
		if (numClasses < MAX_CLASSES) {
			classes.add(numClasses, new Class(className.toUpperCase(), numStudents));
			numClasses++;
			return true;
		}
		return false;
	}
	
	public void addExistingClass(String className, int numStudents, int index) {
		classes.add(numClasses, new Class(className.toUpperCase(), numStudents, false, index));
		numClasses++;
	}
	
	/**
	 * enables teachers to delete a class after it is over
	 * @param className
	 * @return true if class desired was able to be removed
	 */
	public boolean deleteClass(String className) {
		
		if (numClasses > 0) {
			for (int i = 0; i < numClasses; i++) {
				if ((classes.get(i).getName()).equals(className.toUpperCase())) {
					classes.remove(i);
					numClasses--;
				}
			}
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * enables teachers to delete all classes once they conclude
	 * @return true if all classes deleted successfully
	 */
	public boolean deleteAllClasses() {
		for (int i = numClasses - 1; i >= 0; i--) {
			if (deleteClass(classes.get(i).getName()) == false) {
				System.out.println ("unsuccessful at index " + i);
				return false;
			}
		}
		return true;
	}
	
	public Class getClass(String name) {
		for (int i = 0; i < classes.size(); i++) {
			if (classes.get(i).getName().toUpperCase().equals(name.toUpperCase())) {
				return classes.get(i);
			}
		}
		return null;
	}
	
	public String classToString() {
		String ret = "";
		for (int i = 0; i < classes.size(); i++) {
			if (i == classes.size() - 1)
				ret += classes.get(i).getName();
			else
				ret += classes.get(i).getName() + ", "; 
		}
		return ret;
	}
	
	public String getName() {
		return lastName + ", " + firstName;
	}
	
	public String getFileName() {
		return lastName + "_" + firstName + ".txt";
	}
	
	public String toString() {
		return getName() + "\n\t" + classes.toString();
	}

}
