package backend.node.commands;

import backend.node.Node;
import responses.Response;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 *
 */
public class ASK extends Node {
	@Override
	public Response run(ManipulateController mc) {
		// Run everything in the list
		Node list1 = get(0);
        Node list2 = get(1);
        int[] children = new int[list1.getChildrenNum()];
        for (int i = 0; i < list1.getChildrenNum(); i++) {
            children[i] = list1.getAndRun(i, mc).getIntegerValue();
        }
        mc.setTempTurtles(children);
        
        Response s = null;
        for (int i = 0; i < list2.getChildrenNum(); i++) {
            s = list2.getAndRun(i, mc);
        }
        
        mc.clearTempTurtles();
        return s;
	}
}