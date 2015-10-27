package backend.node.commands;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 *
 */
public class RT extends Node {

	@Override
	public Response run(ManipulateController sharedHandle) {
		int degrees = getAndRun(0, sharedHandle).getIntegerValue();
		ITurtleLambda l = (Turtle t) -> {
			double currHeading = t.getHeading();
			System.out.println(currHeading);
			currHeading -= degrees;
			while(currHeading < 0){
	               currHeading += 360;
			}
			t.setHeading(currHeading);
			System.out.println(currHeading);
			return degrees;
		};
		sharedHandle.executeOnAllActiveTurtles(l);
		
		// return argument 1 value
		return new Success(degrees);
	}
}