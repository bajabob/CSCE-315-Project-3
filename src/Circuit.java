import java.util.ArrayList;
import java.util.Stack;

public class Circuit {
	
	/**
	 * Total number of individual gates in this circuit
	 */
	private int counterAnd;
	private int counterOr;
	private int counterNot;
	private int counterNone;
	
	/**
	 * Collection of gates in linear order
	 */
	private Stack<LogicBase> gates;
	
	public Circuit()
	{
		gates = new Stack<LogicBase>();
	}

	/**
	 * Add a gate to the end of this circuit
	 * @param gate
	 */
	void addGateEnd(LogicBase gate)
	{
		gates.add(gate);
		this.countGate(gate);
	}
	
	/**
	 * Add a gate to the front of this circuit
	 * @param gate
	 */
	void addGateFront(LogicBase gate)
	{
		gates.push(gate);
		this.countGate(gate);
	}
	
	
	/**
	 * Increment the local gate count
	 * @param gate
	 */
	private void countGate(LogicBase gate){
		if(gate.getGate() == LogicBase.GATE_AND)
		{
			counterAnd++;
		}
		
		if(gate.getGate() == LogicBase.GATE_OR)
		{
			counterOr++;
		}
		
		if(gate.getGate() == LogicBase.GATE_NOT)
		{
			counterNot++;
		}
		
		if(gate.getGate() == LogicBase.GATE_NONE)
		{
			counterNone++;
		}
	}
	
	/**
	 * Get this circuit's current fitness score
	 * @return int
	 */
	int getFitnessScore()
	{
		return counterNot * 10000 + 10 * (counterAnd + counterOr);
		
	}
	
	/**
	 * Get the current number of gates in this circuit
	 * @return int
	 */
	int getInputSize()
	{
		return gates.size();
	}
	
	/**
	 * Evaluate this circuit based off an array of inputs. The total number of 
	 *  input should equal the total number of NONE gates at the beginning of 
	 *  this circuit.
	 * @param inputs boolean[]
	 */
	void evaluate( TruthTable tt )
	{
		if(tt.getTableWidth() != counterNone){
			System.err.println("Circuit.evaluate :: Total number of NONE gates in circuit "
					+ "does not equal number of inputs.");
			return;
		}
		
		boolean hasPassedTest = false;
		ArrayList<Boolean> testResults = new ArrayList<Boolean>();
		
		for(int test = 0; test < tt.getRowCount(); test++ ){
			// clear any old results
			testResults.clear();
		
			// add inputs to beginning of test
			for( int i = 0; i < tt.getTableWidth(); i++ )
			{
				testResults.add(tt.getInput(test, i));
			}
			
			// evaluate the circuit
			for( int i = gates.size()-1; i >= 0; i-- )
			{
				gates.get(i).evaluate(testResults);
			}
			
			/**
			 * Did our circuit pass for this row in the truth table?
			 */
			if(testResults.get(testResults.size()-1) == tt.getOutput(test)){
				
				// if a test passes, should we minimize the fitness score?
				
				hasPassedTest = true;
				System.out.println("Test passed for truth table row: "+test);
				
				String s = "Output\tGate\n";
				for(int i = 0; i < gates.size(); i++){
					s += testResults.get(i) + "\t" + gates.get( i ) + "\n";
				}
				System.out.println(s);
			}
		}
		
		if(hasPassedTest){
			System.out.println(tt);
		}

	}
	
	@Override
	public String toString()
	{
		String response = "";
		for(int i = 0; i < gates.size(); i++)
		{
			response += gates.get(i) + "\n";
		}
		
		return response;
	}
}
