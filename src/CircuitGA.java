import java.awt.Color;
import java.awt.Graphics;
import java.util.PriorityQueue;
import java.util.Random;

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
	 * The highest current fitness score in the system
	 */
	private int currentHighestFitnessScore;

	/**
	 * Create a new genetic alorithm
	 * @param tt TruthTable
	 */
	public CircuitGA(TruthTable tt)
	{
		currentHighestFitnessScore = 0;
		
		activeCircuits = new PriorityQueue<Circuit>(MAX_CIRCUITS, new CircuitComparator());
		
		// create MAX_CIRCUITS number of random circuits
		for(int i = 0; i < MAX_CIRCUITS; i++)
		{
			Circuit a = new Circuit();

			// pick a random color
			Random rand = new Random();
			// add 16 = 256 (to avoid black colors)
			int r = rand.nextInt(224) + 32;
			int g = rand.nextInt(224) + 32;
			int b = rand.nextInt(224) + 32;
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
			
			this.addToPoolByFitness( a );
		}

	}
	
	
	private void addToPoolByFitness(Circuit a){
		
		if(activeCircuits.size() < MAX_CIRCUITS)
		{
			if(a.getFitnessScore() > currentHighestFitnessScore)
			{
				currentHighestFitnessScore = a.getFitnessScore();
			}
			activeCircuits.add( a );
		}else{
			if(a.getFitnessScore() < currentHighestFitnessScore)
			{
				PriorityQueue<Circuit> old = new PriorityQueue<Circuit>(activeCircuits);
				activeCircuits.clear();
				currentHighestFitnessScore = 0;
				while(old.size() > 1)
			    {
					Circuit i = old.poll();
					
					if(i.getFitnessScore() > currentHighestFitnessScore)
					{
						currentHighestFitnessScore = i.getFitnessScore();
					}
					activeCircuits.add(i);
			    }
				activeCircuits.add(a);
			}
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
		Circuit childA = a.splice(b);
		Circuit childB = b.splice(a);
		
		// attempt to add all circuits back to pool
		addToPoolByFitness(a);
		addToPoolByFitness(b);
		addToPoolByFitness(childA);
		addToPoolByFitness(childB);
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
	
	/**
	 * Paint the pool to a graphics layer
	 * @param g Graphics
	 */
	public void onPaint(Graphics g, int centerY){
		g.translate(0, centerY);
		
		PriorityQueue<Circuit> circuits = new PriorityQueue<Circuit>(activeCircuits);
		for(int i = 0; !circuits.isEmpty(); i++)
		{
			Circuit c = circuits.poll();
			g.translate(4, 0);
			g.translate(0, -(c.getGateCount()/2));
			c.onPaint(g);
			g.translate(0, (c.getGateCount()/2));
		}
		
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