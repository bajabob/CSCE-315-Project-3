

public class Main {

	public static void main(String[] args) {
		
		// generate a new truth table to represent the carry out circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		
		System.out.println(carryOutTable);
		
		// create a new circuit tree 
		CircuitTree ct = new CircuitTree(carryOutTable, 3);

	}
}
