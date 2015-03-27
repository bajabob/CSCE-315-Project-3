import java.util.ArrayList;
import java.util.List;


public class Node<T> {
	private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    private int depth;
    
    Node(T data, int depth){
    	this.data = data;
    	this.depth = depth;
    	this.children = new ArrayList<Node<T>>();
    }
    
    public int getDepth(){
    	return depth;
    }
    
    
    public void setParent(Node<T> parent){
    	this.parent = parent;
    }
    
    public Node<T> getParent(){
    	return this.parent;
    }
    
    /**
     * get the data stored in this element
     * @return T
     */
    public T getData(){
    	return this.data;
    }
    
    public void addChild(Node<T> node){
    	this.children.add(node);
    }
    
    @Override
    public String toString(){
    	return this.data.toString();
    }
    
}
