import java.util.*;
import java.lang.*;
import java.io.*;

public class CircuitGA{
	
	public final int MAX_POP_POOL = 100;
	
	private PriorityQueue<Circuit> activeCircuits;

	public CircuitGA(TruthTable tt)
	{
		activeCircuits = new PriorityQueue<Circuit>();
		for(int i = 0; i < MAX_POP_POOL; i++)
		{
			Circuit a = new Circuit();
			Random rand = new Random();
			LogicBase and = new LogicBase(LogicBase.GATE_AND, 0, 0, 0);
			LogicBase or = new LogicBase(LogicBase.GATE_OR, 0, 0, 0);
			LogicBase not = new LogicBase(LogicBase.GATE_NOT, 0, 0);
			LogicBase[] gates = {and, or, not};

			a.addGateFront(gates[rand.nextInt(gates.length]);


			for(int k = 0; k < tt.getTableWidth(); k++)
			{
				LogicBase none = new LogicBase(LogicBase.GATE_NONE, 0, 0);
				a.addGateFront(none);
			}		
		}

	}

	public void main(String[] args)
	{
		// Carry-Out Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		CircuitGA GA = new CircuitGA(carryOutTable);


	}




}