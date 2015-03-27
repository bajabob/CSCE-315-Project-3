import java.util.ArrayList;

public class LogicBase
{
	public static final String GATE_NONE = "NONE";
	public static final String GATE_AND = "AND";
	public static final String GATE_NOT = "NOT";
	public static final String GATE_OR = "OR";

	/**
	 * The type of gate this is
	 */
	protected String gate;
	
	/**
	 * The inputs for this gate
	 */
	protected int inputA, inputB;

	/**
	 * The line this gate outputs to
	 */
	protected int output;
	
	/**
	 * Create a new logic base gate
	 * @param gate - the type of gate
	 * @param inputA - the output line to take an input from
	 * @param inputB - the output line to take an input from
	 * @param output - the line this gate outputs to
	 */
	public LogicBase( String gate, int inputA, int inputB, int output )
	{
		this.gate = gate;
		this.inputA = inputA;
		this.inputB = inputB;
		this.output = output;
	}
	
	/**
	 * Create a new logic base gate
	 * @param gate - the type of gate
	 * @param inputA - the output line to take an input from
	 * @param output - the line this gate outputs to
	 */
	public LogicBase( String gate, int inputA, int output)
	{
		this.gate = gate;
		this.inputA = inputA;
		this.output = output;
	}
	
	/**
	 * Set the inputs of this gate
	 * @param inputA int
	 * @param inputB int
	 */
	public void setInputs(int inputA, int inputB)
	{
		this.inputA = inputA;
		this.inputB = inputB;
	}
	
	/**
	 * Get this gate
	 * @return LogicBase
	 */
	public String getGate()
	{
		return gate;
	}
	
	/**
	 * Evaluate this gate against an already generated list of 
	 *  outputs (IE all outputs preceeding this gate have been
	 *  calculated)
	 * @param outputs ArrayList<Boolean>
	 */
	void evaluate(ArrayList<Boolean> outputs)
	{
		if (gate == GATE_AND)
		{
			boolean A = outputs.get(inputA);
			boolean B = outputs.get(inputB);
			boolean out = A && B;
			outputs.add(out);
		} else if (gate == GATE_OR)
		{
			boolean A = outputs.get(inputA);
			boolean B = outputs.get(inputB);
			boolean out = A || B;
			outputs.add(out);
		} else if (gate == GATE_NOT)
		{
			boolean A = outputs.get(inputA);
			boolean out = !A;
			outputs.add(out);
		}
	}
	
	@Override
	public String toString()
	{
		String response = output + "\t"
			 + gate.toUpperCase() + "\t" + " " +
			 inputA + " ";
			
		if(gate != GATE_NOT && gate != GATE_NONE)
		{
			response += inputB;
		}
		return response;
	}
	
	/**
	 * Convert this gate object to a file formatted friendly string
	 * @return String
	 */
	public String toFileFormat()
	{
		String toFile = output + "\t" + gate.toUpperCase() + "\t" + inputA;
		if(this.gate.equals("AND") == true || this.gate.equals("OR") == true)
		{
			toFile += "\t" + inputB;
		}
		return toFile;
	}

	public static void main(String[] args){
		LogicBase NONE = new LogicBase(LogicBase.GATE_NONE, 0, 0);
		LogicBase NOT = new LogicBase(LogicBase.GATE_NOT, 0, 1);
		LogicBase AND = new LogicBase(LogicBase.GATE_AND, 0, 1, 2);
		LogicBase OR = new LogicBase(LogicBase.GATE_OR, 1, 2, 3);
		
		System.out.println("NONE: " + NONE);
		System.out.println("NOT: " + NOT);
		System.out.println("AND: " + AND);
		System.out.println("OR: " + OR);
		
		System.out.println("Testing Get Gate: " + NONE.getGate());
		OR.setInputs(0, 1);
		System.out.println("Testing Set Inputs: " + OR);
		ArrayList<Boolean> outputs = new ArrayList<Boolean>();
		outputs.add(true);
		outputs.add(false);
		AND.evaluate(outputs);
		System.out.println(outputs);
		
	}
}
