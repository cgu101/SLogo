package sharedobjects;

public enum MapType {
	
	//width = 800
	//height = 580
	 WINDOW {
	        @Override
	        public double[] getNextPosition(double[] p, double w, double h) {
	            return p;
	        }
	},
	WRAP {
        @Override
        public double[] getNextPosition(double[] p, double w, double h) {
        	if(p[0] > w ){
        		p[0] = 0 + (p[0] - w);
        	}else if (p[0] < 0){
        		p[0] = w + p[0];
        	}else if(p[1] > h){
        		p[1] = 0 + (p[1] - h);
        	}else if(p[1] < 0){
        		p[1] = h + p[1];
        	}
            return p;
        }
    },
    FENCE {
        @Override
        public double[] getNextPosition(double[] p, double w, double h) {
        	if(p[0] > w ){
        		p[0] = w;
        	}else if (p[0] < 0){
        		p[0] = 0;
        	}else if(p[1] > h){
        		p[1] = h;
        	}else if(p[1] < 0){
        		p[1] = 0;
        	}
        	return p;
        }
    };

	public abstract double[] getNextPosition(double[] p, double w, double h);
}
