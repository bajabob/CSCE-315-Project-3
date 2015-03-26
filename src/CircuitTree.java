import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class CircuitTree {

	/**
	 * The root of the tree
	 */
	private Node<LogicBase> root;
	
	
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
	 * @param treeDepth - the total depth of the tree
	 */
	public CircuitTree(TruthTable tt, int treeDepth){

		// create the base root of the tree
		root = new Node(new LogicBase(LogicBase.GATE_NONE, 0, 0));
		
		// add any additional NONE gates
		//  this will create a linear chain of
		//  nodes. The last none gate is where 
		//  the tree will be started.
		for(int i = 0; i < tt.getTableWidth()-1; i++){
			Node<LogicBase> child = new Node(new LogicBase(LogicBase.GATE_NONE, 0, i+1));
			child.setParent(root);
			root.addChild(child);
			root = child;
		}		
		
		// using a queue to keep track of all previous parent nodes
		//  created in the tree. When we dequeue a parent node we can 
		//  add all of its children and then proceed to add the children
		//  to the end of the queue.
		Queue<Node<LogicBase>> queue = new LinkedList<Node<LogicBase>>();
		queue.add(root);
		
		for(int i = 0; i < treeDepth; i++ )
		{
			System.out.println("Tree Level: " + i);
			for(int j = 0; j < Math.pow( 3, i+1 ); j += 3)
			{
				
				// get the parent of these children we are about to create
				Node<LogicBase> parent = queue.remove();
				
				// for now we are using a set recipe for our circuit
				//  in the future we may experiment with permutated 
				//  gate inputs. This just makes the previous two
				//  outputs these gates inputs
				Random rand = new Random();
				int inA = rand.nextInt(tt.getTableWidth() + i);
				int inB = rand.nextInt(tt.getTableWidth() + i);
				
				// output is equal to i+number of GATE_NONE's added
				//  before constructing the tree
				Node<LogicBase> and = new Node(new LogicBase(LogicBase.GATE_AND, inA, inB, i+tt.getTableWidth()));
				Node<LogicBase> or = new Node(new LogicBase(LogicBase.GATE_OR, inA, inB, i+tt.getTableWidth()));
				Node<LogicBase> not = new Node(new LogicBase(LogicBase.GATE_NOT, inA, i+tt.getTableWidth()));
				
				and.setParent(parent);
				or.setParent(parent);
				not.setParent(parent);
				
				parent.addChild(and);
				parent.addChild(or);
				parent.addChild(not);
				
				queue.add(and);
				queue.add(or);
				queue.add(not);
				
				Circuit c = new Circuit();
				this.populateCircuit(c, and);
				
				// @todo: here we should test the fitness of the circuit 
				//  before attempting to evaluating it
				boolean foundCircuit = c.evaluate(tt);
				
				if(foundCircuit){
					System.out.println("Found valid circuit");
					return;
				}
			}
			System.out.println("");
		}
	}
	
	/**
	 * Recursively traverse a node's parent nodes until reaching the root
	 *  adding each gate to a Circuit as it is visited
	 * @param c Circuit
	 * @param node Node<LogicBase>
	 */
	private void populateCircuit(Circuit c, Node<LogicBase> node){
		
		// visiting tree in reverse order, have to add gates to
		//  front of gate collection
		c.addGateFront( node.getData() );
		
		if(node.getParent() != null){
			populateCircuit(c, node.getParent());
		}
	}
	
	
}
