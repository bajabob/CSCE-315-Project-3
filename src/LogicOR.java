import java.util.ArrayList;


public class LogicOR extends LogicBase
{
	public LogicOR(int inputA, int inputB, int output)
	{
		super(inputA, inputB, output);
	}
	
	void evaluate(ArrayList<Boolean> outputs)
	{
		boolean A = outputs.get(inputA);
		boolean B = outputs.get(inputB);
		boolean out = A || B;
		outputs.add(output, out);
	}
}
