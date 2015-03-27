

public class Main {

	public static void main(String[] args) {
		
		// Carry-Out Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		
		System.out.println(carryOutTable);
		CircuitTree.findCircuit(carryOutTable);
		
		// Sum Circuit
//		boolean[] s = {false, true, true, false, true, false, false, true};
//		TruthTable sumTable = new TruthTable("Sum", 3, s);
//		
//		System.out.println(sumTable);
//		CircuitTree.findCircuit(sumTable);
		
		// NOT(A) Circuit
		boolean[] na = {true, true, true, true, false, false, false, false};
		TruthTable notATable = new TruthTable("NOT-A", 3, na);
		
		System.out.println(notATable);
		CircuitTree.findCircuit(notATable);
		
		// NOT(B) Circuit
		boolean[] nb = {true, true, false, false, true, true, false, false};
		TruthTable notBTable = new TruthTable("NOT-B", 3, nb);
		
		System.out.println(notBTable);
		CircuitTree.findCircuit(notBTable);
		
		// NOT(C) Circuit
		boolean[] nc = {true, false, true, false, true, false, true, false};
		TruthTable notCTable = new TruthTable("NOT-C", 3, nc);
				
		System.out.println(notCTable);
		CircuitTree.findCircuit(notCTable);
		
	}
	
	/**
	 * Test saving/loading a circuit from disk
	 */
	private static void testFileIO(){
		
		// the circuit we will save to disk
		Circuit toSave = new Circuit();
		
		// gates are stored in a stack, add in reverse order
		toSave.addGateFront( new LogicBase(LogicBase.GATE_OR, 3, 4, 5) );
		toSave.addGateFront( new LogicBase(LogicBase.GATE_AND, 1, 2, 4) );
		toSave.addGateFront( new LogicBase(LogicBase.GATE_NOT, 0, 3) );
		toSave.addGateFront( new LogicBase(LogicBase.GATE_NONE, 0, 2) );
		toSave.addGateFront( new LogicBase(LogicBase.GATE_NONE, 0, 1) );
		toSave.addGateFront( new LogicBase(LogicBase.GATE_NONE, 0, 0) );
		toSave.save( "testFileIO" );
		System.out.println("Saved Circuit:");
		System.out.println( toSave );
		
		// now attempt to load that same file into a cirecuit obj
		Circuit toLoad = new Circuit("testFileIO");
		System.out.println("Loaded Circuit:");
		System.out.print( toLoad );
	}
	
}
