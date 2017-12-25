package daaProject;

import java.util.PriorityQueue;
import java.util.Random;

public class PriorityQueueCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PriorityQueue<Path> shortestPath = new PriorityQueue<Path>();
		Random rand = new Random();
		for(int i=1; i<=10; i++){
			Path pObj = new Path();
			pObj.vertex = rand.nextInt(10)+ 1;
			pObj.distance = rand.nextInt(30)+ 11;
			shortestPath.add(pObj);
		}
		
		System.out.println("Printing the elements in Priority Queue");
		for(Path pObj: shortestPath){
			System.out.println("Vertex "+pObj.vertex+" Distance "+pObj.distance);
		}
		
		System.out.println("Printing the elements in Priority Queue according to the priority");
		for(int i=1; i<=10; i++){
			Path pObj = shortestPath.remove();
			System.out.println("Vertex "+pObj.vertex+" Distance "+pObj.distance);
		}
		
	}

}
