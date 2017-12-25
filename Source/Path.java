package daaProject;

public class Path implements Comparable<Path>{
	int vertex;
	double distance;
	
	public int compareTo(Path p) {
		// TODO Auto-generated method stub
		if(this.distance < p.distance){
			return -1;
		}
		else if(this.distance > p.distance){
			return 1;
		}
		return 0;
	}
	
	
}
