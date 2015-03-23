import java.util.ArrayList;

public abstract class LogicBase
{
	protected int inputA, inputB;

	protected int output;

	public LogicBase( int inputA, int inputB, int output )
	{
		this.inputA = inputA;
		this.inputB = inputB;
		this.output = output;
	}
	
	public LogicBase( int inputA, int output){
		this.inputA = inputA;
		this.output = output;
	}

	abstract void evaluate(ArrayList<Boolean> outputs);
}
