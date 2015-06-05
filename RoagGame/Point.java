class Point implements Comparable<Point>{
		int x, y;
		
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
		public boolean equals(Point p){
			if (this.x == p.getX() && this.y == p.getY()){
				return true;
			}
			
			return false;
		}
        
        @Override
        public int compareTo(Point p){
            if (this.x == p.x){
                if (this.y == p.y){
                    return 0;
                }
                
                else if (this.y < p.y){
                    return -1;
                }
                
                else {
                    return 1;
                }
            }
            
            else {
                if (this.x < p.x){
                    return -1;
                }
                
                else {
                    return 1;
                }
            }
        }
	}