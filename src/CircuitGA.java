import java.util.*;
import java.lang.*;
import java.awt.Color;
import java.io.*;

public class CircuitGA{
	
	/**
	 * The max number of gates to keep alive at any time
	 */
	public final int MAX_CIRCUITS = 100;
	
	/**
	 * The collection of active circuits, with the most 
	 * viable (highest fitness) floated to front of the list
	 */
	private PriorityQueue<Circuit> activeCircuits;

	/**
	 * Create a new genetic alorithm
	 * @param tt TruthTable
	 */
	public CircuitGA(TruthTable tt)
	{
		activeCircuits = new PriorityQueue<Circuit>(MAX_CIRCUITS, new CircuitComparator());
		
		// create MAX_CIRCUITS number of random circuits
		for(int i = 0; i < MAX_CIRCUITS; i++)
		{
			Circuit a = new Circuit();

			// pick a random color
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);
			
			// pick a random number of gates
			int randTotalGates = 1 + rand.nextInt(9);
			
			// add some random gates
			for(int k = 0; k < randTotalGates; k++)
			{
				a.addGateFront(new LogicBase((randTotalGates-(k+1))+tt.getTableWidth(), randomColor));
			}

			// fill the rest of the circuit with NONE gates (amount needed by truth table)
			for(int k = 0; k < tt.getTableWidth(); k++)
			{
				LogicBase none = new LogicBase(LogicBase.GATE_NONE, 0, tt.getTableWidth()-(k+1));
				none.setColor(randomColor);
				a.addGateFront(none);
			}
			
			// eval this circuit
			a.evaluate(tt);
			
			activeCircuits.add(a);
		}

	}

	/**
	 * Pick two of the most viable parents and create two offspring
	 */
	public void reproduce()
	{
		Circuit a = activeCircuits.poll();
		Circuit b = activeCircuits.poll();
		
		// splice these two circuits together
		a.splice(b);
		
		// add parents back to pool
		activeCircuits.add(a);
		activeCircuits.add(b);
		
		// add new children to pool
	}
	
	
	/**
	 * @return String
	 */
	public String toString()
	{
		String s = "Ordered set of circuits (based on fitness):\n";
		PriorityQueue<Circuit> circuits = new PriorityQueue<Circuit>(activeCircuits);
		for(int i = 0; !circuits.isEmpty(); i++)
		{
			s += (i+1) + ") ------------\n";
			s += circuits.poll();
		}
		return s;
	}
	
	

	public static void main(String[] args)
	{
		// Carry-Out Circuit
		boolean[] co = {false, false, false, true, false, true, true, true};
		TruthTable carryOutTable = new TruthTable("Carry-Out", 3, co);
		CircuitGA ga = new CircuitGA(carryOutTable);

		// print out the circuits in order of fitness (best first)
		System.out.println(ga);
		
	}

	


}