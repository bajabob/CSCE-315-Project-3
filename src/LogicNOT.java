import java.util.ArrayList;


public class LogicNOT extends LogicBase
{
	public LogicNOT(int inputA, int output)
	{
		super(inputA, output);
	}
	
	void evaluate(ArrayList<Boolean> outputs)
	{
		boolean A = outputs.get(inputA);
		boolean out = !A;
		outputs.add(output, out);
	}
}
