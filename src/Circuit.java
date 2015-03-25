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
	public static void main(String[] args)
	{
		Circuit c = new Circuit();
		c.addGate(new LogicBase(LogicBase.GATE_NONE, 0, 0));
		c.addGate(new LogicBase(LogicBase.GATE_NONE, 1, 1));

		boolean[] inputs = {true, false};
		
		int offset = inputs.length;
		int gateCounter = 0;
		for(int i = 0; i < 3; i++)
		{
			c.addGate(new LogicBase(LogicBase.GATE_NONE, 1, 1));
			
			System.out.println("Tree Level: " + i);
			for(int j = 0; j < Math.pow(3,i+1); j++)
			{
				
				if(gateCounter == 0)
				{
					for(int k = 0; k < c.getInputSize() -1; k++)
					{
						for(int z = 0; z < c.getInputSize() -1; z++)
						{
							c.updateGate(new LogicBase(LogicBase.GATE_AND, k, z, i), i + offset);
							c.evaluate(inputs);
						}
					}	
				}
				
				
				if(gateCounter == 1)
				{
					for(int k = 0; k < c.getInputSize() -1; k++)
					{
						for(int z = 0; z < c.getInputSize() -1; z++)
						{
							c.updateGate(new LogicBase(LogicBase.GATE_OR, k, z, i), i + offset);
							c.evaluate(inputs);
						}
					}
				}
				if(gateCounter == 2)
				{
					for(int k = 0; k < c.getInputSize() -1; k++)
					{
						c.updateGate(new LogicBase(LogicBase.GATE_NOT, k, i), i + offset);
						c.evaluate(inputs);
					}
				}
				
				if(gateCounter == 2)
				{
					gateCounter = 0;
				} else
				{
					gateCounter++;
				}
				
				
				
			}
		}
	}
}
