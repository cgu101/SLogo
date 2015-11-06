// This entire file is part of my masterpiece.
// Logan Rooper

package backend.node;

import java.util.ArrayList;
import java.util.List;

import responses.Response;
import sharedobjects.ManipulateController;

public abstract class Node implements Cloneable {
	private String name;
	private List<Node> myChildren;
	private double myValue;

	public Node() {
		myChildren = new ArrayList<Node>();
	}
	
	/**
	 * @param node
	 * @return the added node
	 */
	public Node addChild(Node node) {
		myChildren.add(node);
		return node;
	}

	/**
	 * @param nodes
	 * @return the first added node
	 */
	public Node addChildren(Node... nodes) {
		for (Node n : nodes)
			myChildren.add(n);

		return nodes[0];
	}

	/**
	 * @return number of children
	 */
	public int getChildrenNum() {
		return myChildren.size();
	}
	
	/**
	 * @param name to be set
	 * @return this node
	 */
	public Node setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * @return whether this node has children
	 */
	public Boolean hasChildren() {
		return (myChildren.size() > 0);
	}

	/**
	 * @return this node's children list
	 */
	public List<Node> getChildren() {
		return myChildren;
	}
	
	/**
	 * @param child index
	 * @param manipulate controller
	 * @return the result of running the child at specified index
	 */
	public Response getAndRun(int index, ManipulateController mc) {
		return myChildren.get(index).run(mc);
	}
	
	/**
	 * @param index
	 * @return the child at specified index
	 */
	public Node get(int index) {
		return myChildren.get(index);
	}

	/**
	 * @return my name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return my value
	 */
	public double getDoubleValue() {
		return myValue;
	}

	/**
	 * @return my value as an int
	 */
	public int getIntegerValue() {
		return (int) myValue;
	}

	/**
	 * @param myValue
	 * @return sets my value
	 */
	public Node setValue(double myValue) {
		this.myValue = myValue;
		return this;
	}

	@Override
	/**
	 * @return this node
	 */
	public Node clone() throws CloneNotSupportedException {
		return (Node) super.clone();
	}
	
	/**
	 * @param ManipulateController
	 * @return a response object-either success or failure
	 */
	public abstract Response run(ManipulateController mc);
}
