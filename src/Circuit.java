import java.util.ArrayList;

public class Circuit {
	
	private int counterAnd;
	private int counterOr;
	private int counterNot;
	
	private ArrayList<Boolean> outputs;
	
	private ArrayList<LogicBase> gates;
	
	public Circuit()
	{
		outputs = new ArrayList<Boolean>();
		gates = new ArrayList<LogicBase>();
		
		
	}
	
	void updateGate(LogicBase gate, int index)
	{
		gates.set(index, gate);
	}
	
	void addGate(LogicBase gate)
	{
		
		gates.add(gate);
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
		
	}
	
	int getFitnessScore()
	{
		return counterNot * 10000 + 10 * (counterAnd + counterOr);
		
	}
	
	int getInputSize()
	{
		return gates.size();
	}
	
	void evaluate( boolean[] inputs )
	{
		System.out.println("EVALUATE");
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
			//System.out.println(";" + outputs.get(i));
		}
		
		System.out.println(this);
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
