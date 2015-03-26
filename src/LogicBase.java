import java.util.ArrayList;

public class LogicBase
{
	public static final String GATE_NONE = "none";
	public static final String GATE_AND = "and";
	public static final String GATE_NOT = "not";
	public static final String GATE_OR = "or";

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
			
		if(gate != GATE_NOT)
		{
			response += inputB;
		}
		return response;
	}
	
	/**
	 * Convert this gate object to a file formatted friendly string
	 * @return String
	 */
	public String toFileFormat(){
		return output + "\t" + gate.toUpperCase() + "\t" + inputA + "\t" + inputB;
	}

}
