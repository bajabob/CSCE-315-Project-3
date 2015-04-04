import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Circuit
{
	
	/**
	 * Total number of individual gates in this circuit
	 */
	private int counterAnd;
	private int counterOr;
	private int counterNot;
	private int counterNone;
	
	/**
	 * the total number of "failed" tests from the last evaluation
	 */
	private int totalFailedTests = -1;
	
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
	public Circuit(String filename)
	{
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
	private void countGate(LogicBase gate)
	{
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
		int andOr = 0;
		
		if(counterAnd > 1000 || counterOr > 1000){
			andOr = 10 * (counterAnd + counterOr);
		}
		
		if(totalFailedTests != -1)
		{
			return (totalFailedTests * 1000000) + (counterNot * 10000) + andOr;
		}
		return (counterNot * 10000) + andOr;
	}
	
	/**
	 * Save this circuit to disk
	 * @param filename String
	 */
	void save(String filename)
	{
		PrintWriter writer;
		try
		{
			writer = new PrintWriter("solutions/"+filename+".txt", "UTF-8");
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
	void load(String filename)
	{
		BufferedReader br = null;
		try
		{
			String currentLine;
 
			br = new BufferedReader(new FileReader("solutions/"+filename+".txt"));
 
			ArrayList<LogicBase> list =  new ArrayList<LogicBase>();
			while ((currentLine = br.readLine()) != null)
			{
				String[] split = currentLine.split("\t");
				
				if(split[1].equals("NONE"))
				{
					LogicBase gate = new LogicBase(LogicBase.GATE_NONE, Integer.valueOf(split[2]),
							Integer.valueOf(split[0]));
					list.add( gate );
				} else if(split[1].equals("NOT"))
				{
					LogicBase gate = new LogicBase(LogicBase.GATE_NOT, Integer.valueOf( split[2]), 
							Integer.valueOf(split[0]) );
					list.add( gate );
				} else if(split[1].equals("AND"))
				{
					LogicBase gate = new LogicBase(LogicBase.GATE_AND, Integer.valueOf(split[2]),
							Integer.valueOf(split[3]), Integer.valueOf(split[0]));
					list.add( gate );
				} else if(split[1].equals("OR"))
				{
					LogicBase gate = new LogicBase(LogicBase.GATE_OR, Integer.valueOf( split[2]), 
							Integer.valueOf(split[3]), Integer.valueOf(split[0]) );
					list.add( gate );
				} else
				{
					System.err.println("Not a gate..");
				}
			}
 
			for(int i = list.size() - 1; i >= 0 ; i--)
			{
				this.addGateFront( list.get( i ) );
			}
			
			br.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * Get the current number of gates in this circuit
	 * @return int
	 */
	int getGateCount()
	{
		return gates.size();
	}
	
	
	/**
	 * Shuffle the inputs of this circuit
	 */
	void shuffleInputs(long l){
		Random rand = new Random(l);
		
		for(int i = gates.size()-1; i >= 0 ; i--)
		{
			if(gates.get( i ).getGate() == LogicBase.GATE_NONE)
			{
				continue;
			}
			int inA = rand.nextInt(gates.get( i ).output);
			int inB = rand.nextInt(gates.get( i ).output);
			LogicBase gate = gates.get(i);
			if(gate.getGate() == LogicBase.GATE_AND || gate.getGate() == LogicBase.GATE_OR)
			{
				gates.get( i ).setInputs( inA, inB );
			}else if(gate.getGate() == LogicBase.GATE_NOT)
			{
				gates.get( i ).setInput( inA );
			}
		}
	}
	
	
	/**
	 * Evaluate this circuit based off an array of inputs. The total number of 
	 *  input should equal the total number of NONE gates at the beginning of 
	 *  this circuit.
	 * @param tt TruthTable
	 * @return boolean - is this a valid circuit?
	 */
	int evaluate( TruthTable tt )
	{
		if(tt.getTableWidth() != counterNone)
		{
			System.err.println("Circuit.evaluate :: Total number of NONE gates in circuit "
					+ "does not equal number of inputs.");
			System.err.println(this);
			return 0;
		}
		
		int pass = 0;
		
		ArrayList<Boolean> testResults = new ArrayList<Boolean>();
		
		for(int test = 0; test < tt.getRowCount(); test++ )
		{
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
			 * Did the test pass? - then keep going!
			 * 
			 * Did our circuit pass for this row in the truth table?
			 * Compare test results for this row with the value in 
			 *  the truth table
			 */
			if(testResults.get(testResults.size()-1) == tt.getOutput(test))
			{
				pass++;
			}
		}
		
		// calculate the total number of failed tests
		totalFailedTests = tt.getRowCount() - pass;
		
		return pass;
	}
	
	/**
	 * Get the last half of this circuit
	 * @return ArrayList<LogicBase>
	 */
	public ArrayList<LogicBase> getLastHalf(){
		ArrayList<LogicBase> half = new ArrayList<LogicBase>(gates);
		while(half.size() > 3+(gates.size()/2))
		{
			half.remove( half.size()-1 );
		}
		return half;
	}
	
	/**
	 * Get the first half of this circuit
	 * @return ArrayList<LogicBase>
	 */
	public ArrayList<LogicBase> getFirstHalf(){
		ArrayList<LogicBase> half = new ArrayList<LogicBase>(gates);
		while(half.size() > 3+(gates.size()/2))
		{
			half.remove( 0 );
		}
		return half;
	}
	
	/**
	 * Splice the last half of this circuit with the first half of Circuit c
	 *  and return a new circuit that has been validated for new output lines
	 *  and shuffled for new input gates
	 * @param c Circuit
	 * @return Circuit
	 */
	public Circuit splice(Circuit c)
	{
		
		// get the last half of gates from this circuit
		ArrayList<LogicBase> backHalf = this.getLastHalf();
		
		// get the first half of gates from the other circuit
		ArrayList<LogicBase> frontHalf = c.getFirstHalf();
		
		Circuit a = new Circuit();
		
		// the new calculate output size
		int output = backHalf.size() + frontHalf.size() - 1;
		
		/**
		 * need to recalculate new output lines
		 * NOTE: "output--" below handles this 
		 */		
		// add the back half of gates to new circuit
		for(int i = 0; i < backHalf.size(); i++){
			backHalf.get( i ).setOutput( output-- );
			a.addGateFront( backHalf.get( i ) );
		}
		
		// add the front half of gate to new circuit
		for(int i = 0; i < frontHalf.size(); i++){
			frontHalf.get( i ).setOutput( output-- );
			a.addGateFront( frontHalf.get( i ) );
		}
		
		/**
		 * Required as we have new input gates to select from
		 */
		a.shuffleInputs( System.currentTimeMillis() );
		
		return a;
	}
	
	/**
	 * Paint this circuit to a graphics layer
	 * @param g Graphics
	 */
	public void onPaint(Graphics g){
		if(totalFailedTests == 0){
			g.setColor( Color.GREEN );
			g.fillRect( -4, (gates.size()/2) - 4, 8, 8 );
			g.setColor( Color.WHITE );
			g.fillRect( -2, (gates.size()/2) - 2, 4, 4 );
		}
		for(int i = gates.size() - 1; i >= 0; i--)
		{
			gates.get(i).onPaint(g, i);
		}
	}
	
	/**
	 * Get the total number of failed tests
	 * @return int
	 */
	public int getTotalFailedTests(){
		return this.totalFailedTests;
	}
	
	public void trim()
	{
		int[] usedGates = new int[this.gates.size()];
		int size = this.gates.size() - 1;
		
		LogicBase gate = this.gates.get(0);
		usedGates[0] = 1;
		System.out.println(gate.getInputA());
		usedGates[size - gate.getInputA()] = 1;
		if(gate.getGate() == LogicBase.GATE_AND || gate.getGate() == LogicBase.GATE_OR)
		{
			usedGates[size - gate.getInputB()] = 1;
		}
		
		int counter = 1;
		while(counter < (this.gates.size()-this.counterNone))
		{
			if(usedGates[counter] == 0)
			{
				counter++;
			}else
			{
				gate = this.gates.get(counter);
				usedGates[size - gate.getInputA()] = 1;
				if(gate.getGate() == LogicBase.GATE_AND || gate.getGate() == LogicBase.GATE_OR)
				{
					usedGates[size - gate.getInputB()] = 1;
				}
				counter++;
			}
		}
		
		for(int i = size - this.counterNone; i > -1; i--)
		{
			if(usedGates[i] == 0)
			{
				this.gates.removeElementAt(i);
			}
		}
	}
	
	@Override
	public int hashCode()
	{
		return this.gates.size() + counterAnd + counterNot + 
				counterOr + getFitnessScore();
	}
	
	@Override
	public String toString()
	{
		String response = "";
		response += "Fitness: " + getFitnessScore() + "\n";
		for(int i = gates.size() - 1; i >= 0; i--)
		{
			response += gates.get(i) + "\n";
		}
		
		return response;
	}
	
	public static void main(String[] args)
	{
		Circuit c = new Circuit();
		c.addGateFront(new LogicBase(LogicBase.GATE_OR, 4, 5, 6));
		c.addGateFront(new LogicBase(LogicBase.GATE_AND, 1, 2, 5));
		c.addGateFront(new LogicBase(LogicBase.GATE_AND, 0, 3, 4));
		c.addGateFront(new LogicBase(LogicBase.GATE_NOT, 1, 3));
		c.addGateFront(new LogicBase(LogicBase.GATE_NOT, 0, 2));
		c.addGateFront(new LogicBase(LogicBase.GATE_NONE, 1, 1));
		c.addGateFront(new LogicBase(LogicBase.GATE_NONE, 0, 0));
		
		Circuit d = new Circuit();
		d.addGateFront(new LogicBase(LogicBase.GATE_OR, 1, 2, 5));
		d.addGateFront(new LogicBase(LogicBase.GATE_NOT, 0, 3, 4));
		d.addGateFront(new LogicBase(LogicBase.GATE_AND, 1, 3));
		d.addGateFront(new LogicBase(LogicBase.GATE_OR, 0, 2));
		d.addGateFront(new LogicBase(LogicBase.GATE_NONE, 1, 1));
		d.addGateFront(new LogicBase(LogicBase.GATE_NONE, 0, 0));
		
		System.out.println(c);
		System.out.println(c.hashCode());
		//System.out.println("Testing Fitness: " + c.getFitnessScore());
		
		boolean[] expOuts = {false, true, true, false};
		TruthTable tt = new TruthTable("Carry-Out", 2, expOuts);
		boolean[] badOuts = {true, true, true, false};
		TruthTable badtt = new TruthTable("Bad", 2, badOuts);
		
		System.out.println("Testing evaluate with good tt: " + c.evaluate(tt));
		System.out.println("Testing evaluate with bad tt: " + c.evaluate(badtt));
		System.out.println("Testing Get Gate Count: " + c.getGateCount());
		
		c.save( "testFileIO" );
		System.out.println("Saved Circuit:");
		System.out.println(c);
		
		Circuit cLoad = new Circuit("testFileIO");
		System.out.println("Loaded Circuit:");
		System.out.println(cLoad);
		
		Circuit toTrim = new Circuit();
		toTrim.load("Carry-Out-171");
		System.out.println(toTrim);
		
		toTrim.trim();
		System.out.println(toTrim);
		
		System.out.println(c);
		System.out.println(d);
		
		Circuit cd = c.splice(d);
		System.out.println(cd);
		
		Circuit dc = d.splice(c);
		System.out.println(dc);
	}
}
