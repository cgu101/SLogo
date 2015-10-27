package backend.node.control;

import backend.node.types.Constant;
import backend.node.types.ControlStructure;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;
import sharedobjects.Variables;

public class FOR extends ControlStructure {
	@Override
	public Response run(ManipulateController mc) {
		String variableName = get(0).getName();

		int startInt = (int) getAndRun(1, mc).getDoubleValue();
		int endInt = (int) getAndRun(2, mc).getDoubleValue();
		int increment = (int) getAndRun(3, mc).getDoubleValue();
		mc.executeOnWorkspaceVariables((Variables v) -> {
			v.setVariable(variableName, new Constant(1));
			return new Constant(0);
		});

		// Run - return 0 by default
		Response s = new Success(0);
		for (int i = startInt; i < endInt; i += increment) {
			s = getAndRun(4, mc);
			// Increment
			mc.executeOnWorkspaceVariables((Variables v) -> {
				v.incrementVariable(variableName);
				return new Constant(0);
			});
		}

		// Return
		return s;
	}
}
