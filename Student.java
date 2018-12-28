import javax.swing.JOptionPane;

/**
 * 
 * Student class that creates Students for use in sorting through their traits
 * 
 * @author Yanchen Zhan
 *
 */
public class Student {

	private String lastName, firstName;
	private int[] traits;
	/** TRAITS
	 * 0. Effort
	 * 1. Participation
	 * 2. Skill
	 * 3. Cooperation
	 * 4. Leadership
	 * 5. Reliability
	 * 6. Affability
	 * will be ranked 1-5
	 */
	
	/**
	 * constructor making the student
	 */
	public Student(String lName, String fName) {
		lastName = lName.toUpperCase();
		firstName = fName.toUpperCase();
		MainActivity.writer.println("StudentName: " + lName + ", " + fName + ";");
		determineTraits();
	}
	
	public Student (String stuName, int t0, int t1, int t2, int t3, int t4, int t5, int t6) {
		lastName = stuName.substring(0, stuName.indexOf(","));
		firstName = stuName.substring(stuName.indexOf(",") + 1);
		traits = new int[7];
		traits[0] = t0;
		traits[1] = t1;
		traits[2] = t2;
		traits[3] = t3;
		traits[4] = t4;
		traits[5] = t5;
		traits[6] = t6;
	}
	
	/**
	 * change for reading in file information
	 */
	private void determineTraits() {
		traits = new int[7];
		Object[] ranks = {"1", "2", "3", "4", "5"};
		for (int i = 0; i < 7; i++) {
			traits[i] = Integer.parseInt((String)JOptionPane.showInputDialog(null, "Rank of " + getCorrespondingTrait(i) + " : ","Create Groups", 
					JOptionPane.PLAIN_MESSAGE, null, ranks, "3"));
			MainActivity.writer.println(lastName + "_" + firstName + "_" + "Trait " + i + ": " + traits[i] + ";");
		}
	}
	
	private String getCorrespondingTrait(int num) {
		switch(num) {
			case 0:
				return "Effort";
			case 1:
				return "Participation";
			case 2:
				return "Skill";
			case 3:
				return "Cooperation";
			case 4:
				return "Leadership";
			case 5:
				return "Reliability";
			case 6:
				return "Affability";
			default:
				return null;
		}
	}
	
	public int getTraitRanking(int trait) {
		return traits[trait];
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String toString() {
		String ret = lastName + ", " + firstName + "\n";
		for (int i = 0; i < 7; i++) {
			ret += getCorrespondingTrait(i) + ": " + getTraitRanking(i) + " | ";
		}
		ret += "\n\n";
		return ret;
	}
}
