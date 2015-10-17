/**
 * 
 */
package backend.node;

import java.util.List;

import SharedObjects.WorkSpaceController;

/**
 * @author loganrooper
 *
 */
public abstract class SingleValuedObject extends Node{
	public SingleValuedObject(String name, int num) {
		super();
		super.setName(name);
		super.setChildrenNum(num);
	}
}