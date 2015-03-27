import java.util.LinkedList;
import java.util.Queue;


public class CircuitTree {
	
	
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
	public static void findCircuit(TruthTable tt){

		// create the base root of the tree
		Node<LogicBase> root = new Node(new LogicBase(LogicBase.GATE_NONE, 0, 0), 0);
		
		// add any additional NONE gates
		//  this will create a linear chain of
		//  nodes. The last none gate is where 
		//  the tree will be started.
		for(int i = 0; i < tt.getTableWidth()-1; i++){
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
		queue.add(root);
		
		while(!queue.isEmpty())
		{
			
			// get the parent of these children we are about to create
			Node<LogicBase> parent = queue.remove();
			
			
			int newDepth = parent.getDepth() + 1;
			
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
			for(int gates = 0; gates < nodes.length; gates++){
				
				Circuit c = new Circuit();
				populateCircuit(c, nodes[gates]);
				
				/**
				 * Check fitness score of new circuit, if the circuit is deemed
				 *  unfit, it will not be added to the queue for further processing
				 */
				if(c.getFitnessScore() > 20000){
					continue;
				}

				queue.add( nodes[gates] );

				c.shuffleInputs( System.currentTimeMillis() );
				
				// @todo: here we should test the fitness of the circuit 
				//  before attempting to evaluating it
				boolean foundCircuit = c.evaluate(tt);

				if(foundCircuit){
					System.out.println("Found valid circuit for "+tt.getName()+" saving to disk.");
					c.save( tt.getName() );
					return;
				}
			}
		}
	}
	
	/**
	 * Recursively traverse a node's parent nodes until reaching the root
	 *  adding each gate to a Circuit as it is visited
	 * @param c Circuit
	 * @param node Node<LogicBase>
	 */
	private static void populateCircuit(Circuit c, Node<LogicBase> node){
		
		// visiting tree in reverse order, have to add gates to
		//  front of gate collection
		c.addGateFront( node.getData() );
		
		if(node.getParent() != null){
			populateCircuit(c, node.getParent());
		}
	}
	
	
}
