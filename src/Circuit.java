import java.util.ArrayList;

public class Circuit {
	
	private ArrayList<Boolean> outputs;
	
	private ArrayList<LogicBase> gates;
	
	public Circuit()
	{
		outputs = new ArrayList<Boolean>();
		gates = new ArrayList<LogicBase>();
		gates.add(new LogicBase(LogicBase.GATE_NONE, 0, 0));
		gates.add(new LogicBase(LogicBase.GATE_NONE, 1, 1));
		gates.add(new LogicBase(LogicBase.GATE_NOT, 0, 2));
		gates.add(new LogicBase(LogicBase.GATE_NOT, 1, 3));
		gates.add(new LogicBase(LogicBase.GATE_AND, 0, 3, 4));
		gates.add(new LogicBase(LogicBase.GATE_AND, 1, 2, 5));
		gates.add(new LogicBase(LogicBase.GATE_OR, 4, 5, 6));
	}
	
	void evaluate( boolean[] inputs )
	{
		outputs.clear();
		
		for( int i = 0; i < inputs.length; i++ )
		{
			outputs.add(inputs[i]);
		}
		
		for( int i = 0; i < gates.size(); i++ )
		{
			gates.get(i).evaluate(outputs);
		}
		
		for( int i = 0; i < outputs.size(); i++ )
		{
			System.out.println(";" + outputs.get(i));
		}
	}
	
	public static void main(String[] args)
	{
		Circuit c = new Circuit();
		boolean[] inputs = {true, true};
		c.evaluate(inputs);
	}
}
