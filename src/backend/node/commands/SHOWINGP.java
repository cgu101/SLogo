package backend.node.commands;

import backend.node.types.Command;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;
import sharedobjects.Turtle;

/**
 * @author loganrooper
 *
 */
public class SHOWINGP extends Command{

	@Override
	public Response run(ManipulateController mc) {
		double d = mc.executeOnAllActiveTurtles((Turtle t) -> {
			if (t.isShowing())
				return 1;
			return 0;
		});
		return new Success(d);
	}

}