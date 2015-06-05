class PriorTuple{
		Point p;
		int d;
		
		public PriorTuple(int d, Point p){
			this.d = d;
			this.p = p;
		}
		
		public int getDist(){
			return d;
		}
		
		public Point getPoint(){
			return p;
		}
	}