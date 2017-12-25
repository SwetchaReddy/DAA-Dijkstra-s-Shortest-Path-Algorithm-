package daaProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Dijkstra {
	//Helper method which returns mean value for given distribution, alpha and beta values
	public static double mean(int d, double a, double b){
        double m = 0.0;
        switch(d){
        case 1:
        	m = a;
            break;
        case 2:
        	m = (a + b)/2;
            break;
        case 3:
        	m = (1/a);
            break;
        case 4:
        	m = (b + 1/a);
            break;
        case 5:
        	m = a;
            break;
        case 6:
        	m = a;
            break;
        }
        return m;
    }
    
	//Helper method which returns standard deviation for given distribution, alpha and beta values
    public static double sd(int d, double a, double b){
        double s = 0.0;
        double v;
        switch(d){
        case 1:
            break;
        case 2:
        	v = Math.pow(b-a, 2)/12;
        	s = Math.sqrt(v);
            break;
        case 3:
        	v = 1/Math.pow(a, 2);
        	s = Math.sqrt(v);
            break;
        case 4:
        	v = 1/Math.pow(a, 2);
        	s = Math.sqrt(v);
            break;
        case 5:
        	v = b;
        	s = Math.sqrt(v);
            break;
        case 6:
        	v = Math.pow(a, 2) * b;
        	s = Math.sqrt(v);
            break;
        }
        return s;
    }
    
    //Helper method which returns coefficient of variation for given distribution, alpha and beta values
    public static double c2(int d, double a, double b){
        double c = 0.0;
        switch(d){
        case 1:
            break;
        case 2:
        	c = (Math.pow(b-a, 2) * 1)/(Math.pow(a+b, 2) * 3);
            break;
        case 3:
        	c = 1.0;
            break;
        case 4:
        	c = 1/(Math.pow(1+b*a, 2));
            break;
        case 5:
        	c = b/(Math.pow(a, 2));
            break;
        case 6:
        	c = b;
            break;
        }
        return c;
    }
    
    //Helper method which prints the adjacency matrix
    public static void printMatrix(double[][] m){
    	for(int i=1; i<m.length; i++){
    		for(int j=1; j<m[0].length; j++){
    			System.out.print(m[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
    
    //Helper method which returns the neighbors of a node as a list
    public static ArrayList<Integer> getNeighbors(double[][] m, int r){
    	ArrayList<Integer> neighbors = new ArrayList<Integer>();
    	
    	for(int i=1;i<m[r].length;i++){
    		if(m[r][i] >= 0 ){
    			neighbors.add(i);
    		}
    	}
    	
    	return neighbors;
    }
    
    //Helper method which generates the shortest path for the given input graph
    public static HashMap<Integer,Integer> dijkstraShortestPath(double[][] m, int startVertex, int endVertex){
    	HashMap<Integer,Integer> shortestPath = new HashMap<Integer,Integer>();
    	
    	PriorityQueue<Path> nextVertices = new PriorityQueue<Path>();
    	HashSet<Integer> visitedVertices = new HashSet<Integer>();
    	
    	double[] verticeLeastDistance = new double[m.length];
    	
    	for(int i=0; i<verticeLeastDistance.length; i++){
    		verticeLeastDistance[i] = Double.MAX_VALUE;
    	}
    	
    	Path pObj = new Path();
    	pObj.vertex = startVertex;
    	pObj.distance = 0;
    	
    	nextVertices.add(pObj);
    	
    	while(!nextVertices.isEmpty()){
    		Path currObj = nextVertices.remove();
    		if(!visitedVertices.contains(currObj.vertex)){
    			visitedVertices.add(currObj.vertex);
    			if(currObj.vertex == endVertex){
    				System.out.println("Shortest Distance "+currObj.distance);
    				return shortestPath;
    			}
    			for(Integer n : getNeighbors(m,currObj.vertex)){
    				if(!visitedVertices.contains(n)){
    					double newDistance = m[currObj.vertex][n]+currObj.distance;
    					
    					if(newDistance < verticeLeastDistance[n]){
    						verticeLeastDistance[n] = newDistance;
    						shortestPath.put(n, currObj.vertex);
    					}
    					
    					pObj = new Path();
    					pObj.vertex = n;
    					pObj.distance = newDistance;
    					nextVertices.add(pObj);
    				}
    			}
    		}
    	}
    	
    	
    	return shortestPath;
    }
    
    //Helper method which prints the shortest path
    public static void printPath(HashMap<Integer,Integer> shortestPath, int startVertex, int endVertex, String graphName, RandomVariableDistribution[][] rvd){
    	Deque<Integer> stack = new ArrayDeque<Integer>();
    	stack.push(endVertex);
    	int currVertex = endVertex;
    	int hopCount = 0;
    	double m = 0;
    	double std = 0;
    	double cc = 0;
    	while(currVertex != startVertex)
    	{
    		RandomVariableDistribution rv = rvd[shortestPath.get(currVertex)][currVertex];
    		m += mean(rv.distribution,rv.alpha,rv.beta);
    		std += sd(rv.distribution,rv.alpha,rv.beta);
    		cc += c2(rv.distribution,rv.alpha,rv.beta);
    		stack.push(shortestPath.get(currVertex));
    		currVertex = shortestPath.get(currVertex);
    		hopCount++;
    	}
    	
    	System.out.println("Shortest path: "+graphName);
    	System.out.println("Hop Count "+hopCount);
    	System.out.println("Mean - std "+(m-std));
    	System.out.println("Mean "+m);
    	System.out.println("Mean + std "+(m+std));
    	System.out.println("Mean + 2 * std "+(m+2*std));
    	System.out.println("C2 "+cc);
    	
    	while(!stack.isEmpty()){
    		System.out.print(stack.pop()+"->");
    	}
    	System.out.println();
    }
    
    //Helper method which initializes the adjacency matrix
    public static void initMat(double[][] mat){
    	for(int i=0;i < mat.length; i++){
    		for(int j=0; j < mat[0].length; j++){
    			mat[i][j] = -1;
    		}
    	}
    }

    public static void main(String[] args) {
        //Scanner sc = new Scanner(System.in);
        String fileName = "input.txt";
    	//Creating the adjacency matrices for all the criteria
    	double[][] adjMatMean = null;
        double[][] adjMatOpt = null;
        double[][] adjMatPest = null;
        double[][] adjMatDPest = null;
        double[][] adjMatStable = null;
        double[][] adjMatRisk = null;
        
        RandomVariableDistribution[][] rvd = null; 
        int startVertex=0, endVertex=0, nodes;
        
        String line = null;
        
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            String[] parts = line.split(",");
            nodes = 1 + Integer.parseInt(parts[0].trim());
            startVertex = Integer.parseInt(parts[1].trim());
            endVertex = Integer.parseInt(parts[2].trim());
            //Allocating memory and initializing the adjacency matrix
            adjMatMean = new double[nodes][nodes];
            initMat(adjMatMean);
            adjMatOpt = new double[nodes][nodes];
            initMat(adjMatOpt);
            adjMatPest = new double[nodes][nodes];
            initMat(adjMatPest);
            adjMatDPest = new double[nodes][nodes];
            initMat(adjMatDPest);
            adjMatStable = new double[nodes][nodes];
            initMat(adjMatStable);
            adjMatRisk = new double[nodes][nodes];
            initMat(adjMatRisk);
            rvd = new RandomVariableDistribution[nodes][nodes];
            //reading the input file and calculating the edge weights and storing in adjacency matrices
            while((line = bufferedReader.readLine()) != null) {
            	parts = line.split(",");
            	int r = Integer.parseInt(parts[1].trim());
            	int c = Integer.parseInt(parts[2].trim());
            	int dist = Integer.parseInt(parts[3].trim());
            	double a = Double.parseDouble((parts[4]).trim());
            	double b = Double.parseDouble(parts[5].trim());
            	RandomVariableDistribution rv = new RandomVariableDistribution(dist,a,b); 
            	rvd[r][c] = rv;
            	double m = mean(dist,a,b);
            	double std = sd(dist,a,b);
            	double cc = c2(dist,a,b);
            	adjMatMean[r][c] = m;  
            	adjMatOpt[r][c] = m - std;
            	adjMatPest[r][c] = m + std;
            	adjMatDPest[r][c] = m + 2 * std;
            	adjMatStable[r][c] = cc;
            	adjMatRisk[r][c] = a + b;
            }   

            // Always close files.
            bufferedReader.close();        
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
        
        //Passing the adjacency matrix to the Dijkstra algorithm and printing the path
        printPath(dijkstraShortestPath(adjMatMean,startVertex,endVertex),startVertex,endVertex,"Mean",rvd);
        
        printPath(dijkstraShortestPath(adjMatOpt,startVertex,endVertex),startVertex,endVertex,"Optimist",rvd);
        
        printPath(dijkstraShortestPath(adjMatPest,startVertex,endVertex),startVertex,endVertex,"Pessimist",rvd);
        
        printPath(dijkstraShortestPath(adjMatDPest,startVertex,endVertex),startVertex,endVertex,"DoublePessimist",rvd);

        printPath(dijkstraShortestPath(adjMatStable,startVertex,endVertex),startVertex,endVertex,"Stable",rvd);
        
        printPath(dijkstraShortestPath(adjMatRisk,startVertex,endVertex),startVertex,endVertex,"AlphaBeta",rvd);
        
        
    }
    
}


