import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class CircuitGA{
	
	/**
	 * The max number of gates to keep alive at any time
	 */
	public final int MAX_CIRCUITS = 1000;
	
	/**
	 * The collection of active circuits, with the most 
	 * viable (highest fitness) floated to front of the list
	 */
	private ArrayList<Circuit> activeCircuits;
	
	/**
	 * the total number of solutions found
	 */
	private int totalSolutionsFound;
	
	/**
	 * Create a new genetic alorithm
	 * @param tt TruthTable
	 */
	public CircuitGA(TruthTable tt)
	{
	
		totalSolutionsFound = 0;
		
		activeCircuits = new ArrayList<Circuit>(MAX_CIRCUITS);
		
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
			
			// pick a random number of gates that is at LEAST double
			//  the size of the number of inputs (so it splits easier) 
			int randTotalGates = tt.getTableWidth() * 3 + rand.nextInt(1000);
			
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
			
			// is this gate a solution?
			if(a.getTotalFailedTests() == 0){
				totalSolutionsFound++;
				a.save( tt.getName()+"-"+a.hashCode() );
			}
			
			this.addToPool( a );
		}
		
	}
	
	
	private void addToPool(Circuit a){
		activeCircuits.add( a );
		Collections.sort(activeCircuits, new CircuitComparator());
		
		while(activeCircuits.size() > MAX_CIRCUITS)
		{
			activeCircuits.remove( activeCircuits.size()-1 );
		}
	}
	
	private Circuit pollFront()
	{
		Circuit a = activeCircuits.get( 0 );
		activeCircuits.remove( 0 );
		return a;
	}
	
	private Circuit pollRandom()
	{
		Random r = new Random(System.currentTimeMillis()+activeCircuits.get( MAX_CIRCUITS-2 ).hashCode());
		int index = r.nextInt( activeCircuits.size() );
		Circuit a = activeCircuits.get( index );
		activeCircuits.remove( index );
		return a;
	}
	
	
	/**
	 * Pick two of the most viable parents and create two offspring
	 */
	public void reproduce(TruthTable tt)
	{
		
		Circuit a = pollFront();
		Circuit b = pollRandom();
		
		if(a == null || b == null){
			System.err.println("Error finding circuits");
		}
		
		// splice these two circuits together
		Circuit childA = a.splice(b);
		childA.evaluate( tt );
		// is this gate a solution?
		if(childA.getTotalFailedTests() == 0){
			totalSolutionsFound++;
			childA.save( tt.getName()+"-"+childA.hashCode() );
		}
		
		Circuit childB = b.splice(a);
		childB.evaluate( tt );
		if(childB.getTotalFailedTests() == 0){
			totalSolutionsFound++;
			childB.save( tt.getName()+"-"+childB.hashCode() );
		}
		
		// attempt to add all circuits back to pool
		addToPool(a);
		addToPool(b);
		addToPool(childA);
		addToPool(childB);
		
	}
	
	
	/**
	 * @return String
	 */
	public String toString()
	{
		String s = "Ordered set of circuits (based on fitness):\n";
		for(int i = 0; i < activeCircuits.size(); i++)
		{
			s += (i+1) + ") ------------\n";
			s += activeCircuits.get(i);
		}
		return s;
	}
	
	/**
	 * Paint the pool to a graphics layer
	 * @param g Graphics
	 */
	public void onPaint(Graphics g, int centerY){
		g.translate(0, centerY);
		
		for(int i = 0; i < activeCircuits.size(); i++)
		{
			Circuit c = activeCircuits.get(i);
			g.translate(4, 0);
			g.translate(0, -(c.getGateCount()/2));
			c.onPaint(g);
			g.translate(0, (c.getGateCount()/2));
		}
		
	}
	
	/**
	 * get the total number of solutions found
	 * @return int
	 */
	public int getTotalSolutionsFound(){
		return this.totalSolutionsFound;
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