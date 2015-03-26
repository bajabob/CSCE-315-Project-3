import java.util.ArrayList;
import java.util.List;


public class Node<T> {
	private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    
    Node(T data){
    	this.data = data;
    	this.children = new ArrayList<Node<T>>();
    }
    
    public void setParent(Node<T> parent){
    	this.parent = parent;
    }
    
    public Node<T> getParent(){
    	return this.parent;
    }
    
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
