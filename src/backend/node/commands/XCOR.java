package backend.node.commands;

import backend.node.types.Command;
import responses.Response;
import responses.Success;
import sharedobjects.ITurtleLambda;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 */
public class XCOR extends Command{
	@Override
	public Response run(ManipulateController mc) {	
		ITurtleLambda l = (Turtle t) -> {
			return t.getPosition()[0];
		};

		double result = mc.executeOnAllActiveTurtles(l);		
		return new Success(result);
	}
}