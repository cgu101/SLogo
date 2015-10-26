package backend.node.control;


import backend.node.types.ControlStructure;
import responses.Error;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

public class REPEAT extends ControlStructure {

	@Override
	public Response run(ManipulateController mc) {
		//Number of runs
		int c = (int) getAndRun(0, mc).getDoubleValue();
		
		//Run
		Response s = new Success(0);
		for (int i = 0; i < c; i++) {
			s = getAndRun(1, mc);
		}
		
		//Return
		return s;
	}
}