import java.util.ArrayList;

public class LogicBase
{
	public static final String GATE_NONE = "none";
	public static final String GATE_AND = "and";
	public static final String GATE_NOT = "not";
	public static final String GATE_OR = "or";

	protected String gate;
	
	protected int inputA, inputB;

	protected int output;

	public LogicBase( String gate, int inputA, int inputB, int output )
	{
		this.gate = gate;
		this.inputA = inputA;
		this.inputB = inputB;
		this.output = output;
	}
	
	public LogicBase( String gate, int inputA, int output)
	{
		this.gate = gate;
		this.inputA = inputA;
		this.output = output;
	}

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
		
		/*else if (gate == GATE_NONE)
		{
			boolean A = outputs.get(inputA);
			boolean out = A;
			outputs.add(output, out);
		}*/
	}
	
	@Override
	public String toString(){
		String response = "Output: " + output + " "
			 + "Gate : " +  gate + " " + "InputA: " +
			 inputA + " "+ "InputA: "  + inputB;
		return response;
	}

}
