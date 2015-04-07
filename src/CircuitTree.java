import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class CircuitTree
{
	
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
	 * The total number of solutions found by this instance
	 */
	private int totalSolutionsFound;
	
	/**
	 * The current depth of the algorithm (in the tree)
	 */
	private int currentTreeDepth;
	
	/**
	 * total nodes analyzed
	 */
	private long totalNodesAnalyzed;
	
	/**
	 * Is this search implementation running?
	 */
	private boolean isRunning;
	
	
	public CircuitTree()
	{
		totalSolutionsFound = 0;
		currentTreeDepth = 0;
		totalNodesAnalyzed = 0;
	}
	
	/**
	 * Get the total number of solutions this instance has found
	 * @return int
	 */
	public int getTotalSolutionsFoundCount()
	{
		return totalSolutionsFound;
	}
	
	/***
	 * get the current tree depth
	 * @return int
	 */
	public int getCurrentTreeDepth()
	{
		return currentTreeDepth;
	}
	
	/**
	 * get the total number of nodes analyzed
	 * @return long
	 */
	public long getTotalNodesAnalyzed()
	{
		return totalNodesAnalyzed;
	}
	
	/**
	 * toggle isRunning
	 */
	public boolean toggleRunning()
	{
		isRunning = !isRunning;
		return isRunning;
	}
	
	/**
	 * Create a new circuit tree that starts with NONE gates
	 * 		NONE
	 * 		  |
	 * 		NONE
	 * 		  |
	 * 		NONE
	 * 	   /  |  \
	 * 	AND  OR  NOT	
	 *   |	  |   |
	 *  ...  ... ...
	 *  
	 * Once a tree is created, we can traverse the tree in any order to create 
	 *  a unique circuit. 
	 *  
	 * @param totalInputs - the total number of NONE/input gates for the circuit
	 */
	public void findCircuit(TruthTable[] tt, String algorithmName)
	{

		isRunning = false;
		
		// create the base root of the tree
		Node<LogicBase> root = new Node(new LogicBase(LogicBase.GATE_NONE, 0, 0), 0);
		
		// add any additional NONE gates
		//  this will create a linear chain of
		//  nodes. The last none gate is where 
		//  the tree will be started.
		for(int i = 0; i < tt[0].getTableWidth()-1; i++)
		{
			Node<LogicBase> child = new Node(new LogicBase(LogicBase.GATE_NONE, 0, i+1), i+1);
			child.setParent(root);
			root.addChild(child);
			root = child;
		}		
		
		// using a queue to keep track of all previous parent nodes
		//  created in the tree. When we dequeue a parent node we can 
		//  add all of its children and then proceed to add the children
		//  to the end of the queue.
		Queue<Node<LogicBase>> queue = new LinkedList<Node<LogicBase>>();
		activeCircuits = new ArrayList<Circuit>(MAX_CIRCUITS);
		queue.add(root);
		
		while(!queue.isEmpty())
		{
			System.out.print( "" );
			if(!isRunning){
				continue;
			}
			
			// get the parent of these children we are about to create
			Node<LogicBase> parent = queue.remove();
			
			
			int newDepth = parent.getDepth() + 1;
			
			currentTreeDepth = newDepth;
			
			// for now we are using a set recipe for our circuit
			//  in the future we may experiment with permutated 
			//  gate inputs. This just makes the previous two
			//  outputs these gates inputs
			int inA = newDepth - 2;
			int inB = newDepth - 1;
			
			// output is equal to i+number of GATE_NONE's added
			//  before constructing the tree
			Node<LogicBase> and = new Node(new LogicBase(LogicBase.GATE_AND, inA, inB, newDepth), newDepth);
			Node<LogicBase> or = new Node(new LogicBase(LogicBase.GATE_OR, inA, inB, newDepth), newDepth);
			Node<LogicBase> not = new Node(new LogicBase(LogicBase.GATE_NOT, inA, newDepth), newDepth);
			
			and.setParent(parent);
			or.setParent(parent);
			not.setParent(parent);
			
			parent.addChild(and);
			parent.addChild(or);
			parent.addChild(not);
			
			Node[] nodes = {and, or, not};
			for(int gates = 0; gates < nodes.length; gates++)
			{
				
				Circuit c = new Circuit();
				populateCircuit(c, nodes[gates]);

				if(queue.size() < MAX_CIRCUITS*5)
				{
					queue.add( nodes[gates] );
				}
				
				c.shuffleInputs( System.currentTimeMillis() );
				
				addToPool( c );
				
				Circuit random = this.pollRandom();
				System.out.println(random);
				if(random.evaluate(tt)){
					random.save( algorithmName+"-"+random.hashCode() );
					totalSolutionsFound++;
				}
				
				addToPool( random );
				
				totalNodesAnalyzed += random.getGateCount();

			}
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
		if(activeCircuits.size() == 0){
			return new Circuit();
		}
		Random r = new Random(System.currentTimeMillis());
		int index = r.nextInt( activeCircuits.size() );
		Circuit a = activeCircuits.get( index );
		activeCircuits.remove( index );
		return a;
	}
	
	/**
	 * Recursively traverse a node's parent nodes until reaching the root
	 *  adding each gate to a Circuit as it is visited
	 * @param c Circuit
	 * @param node Node<LogicBase>
	 */
	private void populateCircuit(Circuit c, Node<LogicBase> node)
	{
		
		// visiting tree in reverse order, have to add gates to
		//  front of gate collection
		c.addGateFront( node.getData() );
		
		if(node.getParent() != null){
			populateCircuit(c, node.getParent());
		}
	}
}
