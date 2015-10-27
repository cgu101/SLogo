package backend.node.types;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

public class Constant extends Node {
	public Constant() {
		super();
	}

	public Constant(double i) {
		super();
		setValue(i);
	}
	
	public Constant(int i) {
		super();
		setValue((double) i);
	}

	@Override
	public Response run(ManipulateController mc) {
		return new Success(getDoubleValue());
	}
}