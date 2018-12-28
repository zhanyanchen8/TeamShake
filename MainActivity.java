/**
 * Main Activity operated by teacher via GUI interface
 * 
 * @author Yanchen Zhan
 */

// *** WORK THRU FIRST TIME OPENING THE APP ***//

import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.util.Scanner;

@SuppressWarnings("serial")
public class MainActivity extends JFrame {
	
	private Teacher instance;
	private JButton addClass, sortClass, viewClass, deleteClass, end;
	
	public static PrintWriter writer;
	public static Scanner reader;
	public static String teachInfo;
	
	public MainActivity() throws IOException {
		super ("Advanced TEAM SHAKE");
		reader = new Scanner(new File ("teachers.txt"));
		setupGui();
	}
	
	public void setupGui() throws IOException {
		firstTimeSetUp();
		
		//place buttons down for classes teacher is teaching (keep list written down)
		addClass = new JButton ("Add Class");
		//addClass.setVisible(true);
		add(addClass);
		addClass.addActionListener(new AddClassListener(instance));
		
		sortClass = new JButton ("Make Teams");
		add(sortClass);
		sortClass.addActionListener(new SortClassListener(instance));
		
		viewClass = new JButton ("View Class");
		add(viewClass);
		viewClass.addActionListener(new ViewClassListener(instance));
		
		deleteClass = new JButton ("Delete All Classes");
		add(deleteClass);
		deleteClass.addActionListener (new DeleteClassListener(instance));
		
		end = new JButton ("CLICK HERE TO CLOSE");
		//end.setVisible(true);
		add (end);
		//add (Box.createHorizontalStrut(25));
		end.addActionListener(new EndClassListener());
		
	}
	
	/**
	 * has procedure for teacher to set up account, writes it to file lName_fName.txt
	 * @throws IOException 
	 */
	private void firstTimeSetUp() throws IOException{
		String[] input = MainActivity.inputNames("Teacher Information"); //get name of teacher
		
		//get names of teacher from file teachers.txt
		String teachNames = "";
		while (reader.hasNext()) {
			teachNames += reader.nextLine() + "\n";
		}
		System.out.println(teachNames);
		
		String teachDesire = input[0] + ", " + input[1];
		teachDesire = teachDesire.toUpperCase();
		
		//if the teacher exists
		if (teachNames.indexOf(teachDesire) != -1) {
			reader.close();
			reader = new Scanner (new File (input[0].toUpperCase() + "_" + input[1].toUpperCase() + ".txt"));
			writer = new PrintWriter (new FileWriter(input[0].toUpperCase() + "_" + input[1].toUpperCase() + ".txt", true));
			teachInfo = "";
			/**
			 * use the reader to parse through information in text file and fill in the blanks to the group (i.e. creating classes with students, etc.)
			 */
			
			while (reader.hasNext()) {
				teachInfo += reader.nextLine() + "\n";
			}
			instance = new Teacher(input[0], input[1]);
			
			if (teachInfo.equals("")) {
				newTeacher(input[0], input[1]);
			}
			
			else {
				int index = 0; //do not forget handout: https://docs.google.com/document/d/17L_L0ch21LXT0G9_2rhVxqBkLIdvSJQxs86mLW_dVNM/edit
				while (index != -1) {
					index = teachInfo.indexOf("ClassName: ", index);
					String className = (teachInfo.substring(index + "ClassName: ".length(), teachInfo.indexOf(";", index)));
					int numStudents = Integer.parseInt(teachInfo.substring(teachInfo.indexOf("NumStudents: ", index) + "NumStudents: ".length(), 
							teachInfo.indexOf(";", teachInfo.indexOf("NumStudents: ", index) + "NumStudents: ".length()))); 
					System.out.println ("firsttimesetup " + className + " " + numStudents + " " + index);
					instance.addExistingClass(className, numStudents, index);
					
					index = teachInfo.indexOf("ClassName", index + "ClassName".length() + 2);
					
				}
			}
			
			System.out.println("teacher exists");
			
		}
		
		//if teacher does not exist
		else {
			writer = new PrintWriter (new FileWriter("teachers.txt", true));
			writer.println(input[0].toUpperCase() + ", " + input[1].toUpperCase());
			writer.close();
			newTeacher(input[0], input[1]);
		}
	}
	
	private void newTeacher(String l, String f) throws IOException {
		instance = new Teacher (l, f);
		writer = new PrintWriter (new FileWriter (new File (instance.getFileName())));
		reader = new Scanner (new File (instance.getFileName()));
	}
	
	/**
	 * common method used to control input of names
	 * @param header
	 * @return
	 */
	public static String[] inputNames (String header) {
		String[] ret = new String[2]; //first index is LastName, second index is FirstName
		ret[0] = (String)JOptionPane.showInputDialog(
				null, "Last Name:", header, JOptionPane.PLAIN_MESSAGE);
		ret[1] = (String)JOptionPane.showInputDialog(
				null, "First Name:", header, JOptionPane.PLAIN_MESSAGE);
		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		MainActivity window = new MainActivity();
		window.setBounds(25, 12, 1200, 750);
	    window.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    window.setLayout(new GridLayout(5, 1));
	    window.setVisible(true);
	}
	
}


/**
 * operates the addClass JButton
 */
class AddClassListener implements ActionListener {
	
	private Teacher instance;
	
	public AddClassListener (Teacher i) {
		instance = i;
	}
	
	public void actionPerformed (ActionEvent e) {
		String newClass = (String)JOptionPane.showInputDialog(
				null, "Class Name: ","Add Class", JOptionPane.PLAIN_MESSAGE);
		int numStudents = Integer.parseInt((String)JOptionPane.showInputDialog(
					null, "Number of Students: ","Add Class", JOptionPane.PLAIN_MESSAGE));
		instance.addClass(newClass, numStudents);
	}
	
}

/**
 * operates the sortClass JButton
 */
class SortClassListener implements ActionListener {
	
	private Teacher instance;
	
	public SortClassListener (Teacher i) {
		instance = i;
	}
	
	public void actionPerformed (ActionEvent e) {
		
		String classChoice = (String)JOptionPane.showInputDialog(
				null, "Class to Sort:","Create Groups", JOptionPane.PLAIN_MESSAGE);
		
		int numGroups = Integer.parseInt((String)JOptionPane.showInputDialog(
				null, "Number of Groups: ","Create Groups", JOptionPane.PLAIN_MESSAGE));
		
		Object[] possibilities = {"effort", "participation", "skill", "cooperation", "leadership", "reliability", "affability"};
		String weak = (String)JOptionPane.showInputDialog(null, "Weakness to Expose: ","Create Groups", 
				JOptionPane.PLAIN_MESSAGE, null, possibilities, "effort");
		
		try {
			String[] teams = instance.getClass(classChoice.toUpperCase()).makeTeams(numGroups, weak);
			for (int i = 0; i < teams.length; i++) {
				JOptionPane.showMessageDialog(null, "Team "+ (i+1) + ":\n" + teams[i]);
			}
		}
		catch (NullPointerException ex) {
			JOptionPane.showMessageDialog(null, "Error - Try Again");
		}
		
	}
	
}

class ViewClassListener implements ActionListener {
	
	private Teacher instance;
	
	public ViewClassListener (Teacher i) {
		instance = i;
	}
	
	public void actionPerformed (ActionEvent e) {
		String className = (String)JOptionPane.showInputDialog(
				null, "Class Name: ","Find Class", JOptionPane.PLAIN_MESSAGE);
		try {
			Class toView = instance.getClass(className);
			JOptionPane.showMessageDialog(null, toView.toString());
		}
		catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(null, "Error - Try Again");
		}

	}
}

class DeleteClassListener implements ActionListener {
	
	private Teacher instance;
	
	public DeleteClassListener (Teacher i) {
		instance = i;
	}
	
	public void actionPerformed (ActionEvent e) {
		instance.deleteAllClasses();
		
		try {
			MainActivity.writer = new PrintWriter (new File(instance.getFileName()));
			MainActivity.writer.close();
			JOptionPane.showMessageDialog(null, "All Class Information Deleted.");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		File file = new File (instance.getFileName());
		
		if (file.delete())
			System.out.println ("file deleted");
		else
			System.out.println ("file not deleted");
		
		file = new File (instance.getFileName());
		*/
		
	}
}

class EndClassListener implements ActionListener {
	public void actionPerformed (ActionEvent e) {
		JOptionPane.showMessageDialog(null, "Click OK to exit...");
		MainActivity.writer.close();
		MainActivity.reader.close();
		System.exit(1);
	}
}
