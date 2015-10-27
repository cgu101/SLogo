package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 */
public class SETH extends Node {

	@Override
	public Response run(ManipulateController mc) {
		 //get headings
		 double newHeading = getAndRun(0, mc).getDoubleValue();
		 ITurtleLambda l = (Turtle t) -> {
			Double delta = Math.abs(t.getHeading() - newHeading);
			delta = Math.min(delta, 360 - delta);
			t.setHeading(newHeading);
			return delta;
		};
		Double delta = mc.executeOnAllActiveTurtles(l);
	   
		//return the delta
		return new Success(delta);
	}
}