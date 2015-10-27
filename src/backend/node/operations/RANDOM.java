package backend.node.operations;

import java.util.Random;

import backend.node.Node;
import responses.Response;
import responses.Success;
import sharedobjects.ManipulateController;

/**
 * @author loganrooper
 *
 */
public class RANDOM extends Node{

	@Override
	public Response run(ManipulateController mc) {
		Double max = getAndRun(0, mc).getDoubleValue();
		Random rand = new Random();
		int i = rand.nextInt((int) Math.round(max));
		
		return new Success(i);
	}
}
