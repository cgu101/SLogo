package backend.node.control;

import backend.node.Node;
import backend.node.types.ControlStructure;
import responses.Response;
import responses.Success;
import responses.Error;
import sharedobjects.Functions;
import sharedobjects.IWorkspaceFunctionsLambda;
import sharedobjects.ManipulateController;

public class TOCOMMAND extends ControlStructure {
	@Override
	public Response run(ManipulateController mc) {
		//Check if command was defined in parser or not
		IWorkspaceFunctionsLambda l = (Functions f) -> {
			return f.getCommand(getName());
		};
		Node n = mc.executeOnWorkspaceFunctions(l);
			
		if (n != null)
			return new Success(1);
		return new Error("Command not defined");
	}
}