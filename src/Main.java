import java.util.LinkedList;
import java.util.Queue;


public class Main {

	public static void main(String[] args) {
		Circuit c = new Circuit();
		
		Node<LogicBase> root = new Node(new LogicBase(LogicBase.GATE_NONE, 0, 0));
		
		Node<LogicBase> none2 = new Node(new LogicBase(LogicBase.GATE_NONE, 0, 1));
		none2.setParent(root);
		root.addChild(none2);
		
		// be sure to update for larger input sizes
		int noneCount = 2;
		
		Queue<Node<LogicBase>> queue = new LinkedList<Node<LogicBase>>();
		queue.add(none2);
		
		for(int i = 0; i < 2; i++ )
		{
			System.out.println("Tree Level: " + i);
			for(int j = 0; j < Math.pow( 3, i+1 ); j += 3)
			{
				
				Node<LogicBase> parent = queue.remove();
				
				// output is equal to i+number of GATE_NONE's added
				//  before constructing the tree
				Node<LogicBase> and = new Node(new LogicBase(LogicBase.GATE_AND, 0, 0, i+noneCount));
				Node<LogicBase> or = new Node(new LogicBase(LogicBase.GATE_OR, 0, 0, i+noneCount));
				Node<LogicBase> not = new Node(new LogicBase(LogicBase.GATE_NOT, 0, 0, i+noneCount));
				
				and.setParent(parent);
				or.setParent(parent);
				not.setParent(parent);
				
				parent.addChild(and);
				parent.addChild(or);
				parent.addChild(not);
				
				queue.add(and);
				queue.add(or);
				queue.add(not);
				
				visitParent(and);
			}
		}

	}
	
	public static void visitParent(Node<LogicBase> node){
		System.out.println(node);
		if(node.getParent() != null){
			visitParent(node.getParent());
		}
	}

}
