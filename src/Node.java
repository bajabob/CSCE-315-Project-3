import java.util.ArrayList;
import java.util.List;

public class Node<T>
{
	private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    private int depth;
    
    /**
     * constructor
     */
    Node(T data, int depth)
    {
    	this.data = data;
    	this.depth = depth;
    	this.children = new ArrayList<Node<T>>();
    }
    
    /**
     * get the depth of the node
     * @return int
     */
    public int getDepth()
    {
    	return depth;
    }
    
    /**
     * set the parent node
     * @param parent Node<T>
     */
    public void setParent(Node<T> parent)
    {
    	this.parent = parent;
    }
    
    /**
     * get the parent node
     * @return Node<T>
     */
    public Node<T> getParent()
    {
    	return this.parent;
    }
    
    /**
     * get the data stored in this element
     * @return T
     */
    public T getData()
    {
    	return this.data;
    }
    
    /**
     * add a child node
     * @param node Node<T>
     */
    public void addChild(Node<T> node)
    {
    	node.setParent(this);
    	node.depth = this.depth + 1;
    	this.children.add(node);
    }
    
    /**
     * get a child node by index
     * @param index int
     * @return Node<T>
     */
    public Node<T> getChild(int index)
    {
    	return this.children.get(index);
    }
    
    @Override
    public String toString()
    {
    	return "\tData: " + this.data.toString() + "\tDepth: " + this.depth;
    }
    
    public static void main(String[] args)
    {
    	Node parentNode = new Node("hello", 0);
    	Node child1Node = new Node("child1", 2);
    	Node child2Node = new Node("child2", 1);
    	Node child1aNode = new Node("child1a", 5);
    	Node child1bNode = new Node("child1b", 12);
    	Node child2aNode = new Node("child2a", 1);
    	Node child2bNode = new Node("child2b", 2);
    	
    	parentNode.addChild(child1Node);
    	parentNode.addChild(child2Node);
    	child1Node.addChild(child1aNode);
    	child1Node.addChild(child1bNode);
    	child2Node.addChild(child2aNode);
    	child2Node.addChild(child2bNode);
    	
    	System.out.println("Parent Data: " + child1Node.getParent());
    	System.out.println("Child 1 Data: " + child1Node);
    	System.out.println("Child 2 Data: " + child2Node);
    	System.out.println("Child 1a Data: " + child1Node.getChild(0));
    	System.out.println("Child 1b Data: " + child1Node.getChild(1));
    	System.out.println("Child 2a Data: " + child2Node.getChild(0));
    	System.out.println("Child 2b Data: " + child2Node.getChild(1));
    }
}
