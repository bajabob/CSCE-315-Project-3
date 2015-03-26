import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
	 * Load a circuit from disk
	 * @param filename String
	 */
	public Circuit(String filename){
		gates = new Stack<LogicBase>();
		this.load( filename );
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
	 * Save this circuit to disk
	 * @param filename String
	 */
	void save(String filename){
		PrintWriter writer;
		try
		{
			writer = new PrintWriter(filename+".txt", "UTF-8");
			for(int i = gates.size() - 1; i >= 0; --i ) {
				writer.println(gates.get( i ).toFileFormat());
			}
			writer.close();
		} catch ( FileNotFoundException | UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Load a circuit from disk
	 * @param filename String
	 */
	void load(String filename){
		BufferedReader br = null;
		try {
			String currentLine;
 
			br = new BufferedReader(new FileReader(filename+".txt"));
 
			ArrayList<LogicBase> list =  new ArrayList<LogicBase>();
			while ((currentLine = br.readLine()) != null) {
				String[] split = currentLine.split("\t");
				LogicBase gate = new LogicBase( split[1], Integer.valueOf( split[2]), 
						Integer.valueOf(split[3]), Integer.valueOf(split[0]) );
				list.add( gate );
			}
 
			for(int i = list.size() - 1; i >= 0 ; i--){
				this.addGateFront( list.get( i ) );
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
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
	boolean evaluate( TruthTable tt )
	{
		if(tt.getTableWidth() != counterNone){
			System.err.println("Circuit.evaluate :: Total number of NONE gates in circuit "
					+ "does not equal number of inputs.");
			return false;
		}
		
		// don't allow circuits with more than two nots
		if(counterNot > 2){
			return false;
		}
		
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
			 * Compare test results for this row with the value in 
			 *  the truth table
			 */
			if(testResults.get(testResults.size()-1) == tt.getOutput(test)){
				
				// if a test passes, should we minimize the fitness score?
				
				System.out.println("Test passed for truth table row: "+test);
				
				String s = "Output\tGate\n";
				for(int i = gates.size() - 1; i >= 0; i--){
					s += testResults.get(i) + "\t" + gates.get( i ) + "\n";
				}
				//System.out.println( s );
				//System.out.println( tt );
			}else{
				return false;
			}
		}
		
		this.save( tt.getName() + "-" + this.hashCode() );
		
		return true;

	}
	
	@Override
	public int hashCode(){
		return this.gates.size() + counterAnd + counterNot + 
				counterOr + getFitnessScore();
	}
	
	@Override
	public String toString()
	{
		String response = "";
		for(int i = gates.size() - 1; i >= 0; i--)
		{
			response += gates.get(i) + "\n";
		}
		
		return response;
	}
}
