/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: Searching Class
 * Class Description: This is a class that deals with most of the searching in the graph. It uses BFS and DFS to search for the first path that it can find.
 * It writes the BFS and DFS path to a3_out.txt. It also uses Dijkstra's algorithm to find the shortest path between a starting and ending city. 
 *
 */
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Searching {
	public Searching(NodeCities[] graphCities,int startIndex,int endIndex){
		
		
	}
	
	protected ArrayList<String> getDFS(NodeCities[] graphCities, int startIndex, int endIndex){
		return performDFS(graphCities, startIndex, endIndex);
	}
	
	protected ArrayList<String> getBFS(NodeCities[] graphCities, int startIndex, int endIndex){
		return performBFS(graphCities,startIndex,endIndex);
	}
	
	//performs DFS on the city nodes to a path based on a depth first attempt in searching
	private ArrayList<String> performDFS(NodeCities[] graphCities, int startIndex, int endIndex){
		 Stack<NodeCities> stack = new Stack<>(); // initialize a stack
		 ArrayList<String> path = new ArrayList<>();  
		 ArrayList<NodeCities> visited = new ArrayList<>();//maintains order of visited nodes
		 NodeCities prev = null;
		 stack.push(graphCities[startIndex]); // push the start
		 NodeCities popped; 
		 Stack<NodeCities> test=new Stack<NodeCities>();
		    
		    while(!stack.isEmpty()){ //check if stack is empty
		    	popped = stack.pop(); // pop the top of the stack
		       
		        if(!visited.contains(popped)){ //backtrack if the vertex is already visited
		            visited.add(popped); //mark it as visited as it is not yet visited
		          
		            for(int i = 0; i < popped.getAdjacentCities().size(); i++){ //get the adjacents of the vertex as add them to the stack
		                   
		            	int index = getIndexOfCity(popped.getAdjacentCities().get(i), graphCities);
		            	
		            	if(popped.getCity().equals(graphCities[endIndex].getCity())){
		            	
	        				boolean count=false;
	        				test.push(graphCities[endIndex]);
	        				
	        				//for(int h=0;h<32;h++){
	        					//System.out.println(h+" this is the city "+graphValues[h].getCity()+"     "+graphValues[h].getPrevIndex());
	        				//}
	        				index=endIndex;
	        				while(!count){
	        					int b=graphCities[index].getPrevIndex();
	        					if(b!=startIndex){
	        						test.push(graphCities[b]);
	        						index=b;
	        					}
	        					else{
	        						
	        						test.push(graphCities[startIndex]);
	        						count=true;
	        					}
	        				}
	        				
	        				for(int j=0;j<test.size();j++){
	        					
	        					path.add(test.get(test.size()-1-j).getCity());
	        					
	        				}
	        				return path;
						}	
		            	
		            	
		            	
		            	else if(!visited.contains(graphCities[index])){
		            		stack.add(graphCities[index]);
		            		int a = getIndexOfCity(popped.getCity(), graphCities);
							graphCities[index].fixIndex(a);
		            	}
		            	
		            }
		        }
		        
		        prev = popped;
		    }
		    return path;
		    
		
	}
	
	//performs a BFS to find the first path when expanding away from the starting layer by searching all its adjacent cities and then continuing further
	private ArrayList<String> performBFS(NodeCities[] graphCities,int startIndex, int endIndex){
		ArrayList<String> path=new ArrayList<String>();
		Queue queue = new LinkedList();
	    queue.add(graphCities[startIndex]);
	    ArrayList<NodeCities> visited=new ArrayList<NodeCities>();
	    visited.add(graphCities[startIndex]);
	    NodeCities node;
	    Stack<NodeCities> test=new Stack<NodeCities>();
	    while(!queue.isEmpty()) {
	        node = (NodeCities)queue.remove();
	       
	        if(!node.getCity().equals(graphCities[endIndex].getCity())){
	        	for(int i=0;i<node.getAdjacentCities().size();i++){
	        		int index=getIndexOfCity(node.getAdjacentCities().get(i),graphCities);
	        		
	        		if(index==endIndex){
	        			int a=getIndexOfCity(node.getCity(),graphCities);
	        			graphCities[index].fixIndex(a);
	        			boolean count=false;
	        			test.push(graphCities[endIndex]);
	        			
	        			index=endIndex;
	        			while(!count){
	        				int b=graphCities[index].getPrevIndex();
	        				if(b!=startIndex){
	        					test.push(graphCities[b]);
	        					index=b;
	        				}
	        				else{
	        					test.push(graphCities[startIndex]);
	        					count=true;
	        				}
	        			}
	        			path=new ArrayList<String>();
	        			for(int j=0;j<test.size();j++){
	        				path.add(test.get(test.size()-1-j).getCity());
	        			}
	        			
	        			return path;
	        		}
	        		else if(!visited.contains(graphCities[index])){
	        			visited.add(graphCities[index]);
	        			queue.add(graphCities[index]);
	        			int a=getIndexOfCity(node.getCity(),graphCities);
	        			graphCities[index].fixIndex(a);
	        		}
	        	}
	    
	        }
	    }
	    return path;
	}
	
	//given a city name, it returns the index where that city is located in our array of nodes/cities
	protected int getIndexOfCity(String cityName, NodeCities[] graphCities){
		int index=-1;
		for(int i=0;i<graphCities.length;i++){
			if(graphCities[i].getCity().equals(cityName)){
				index=i;
			}
		}
		return index;
	}
	
	//writes the results of BFS and DFS to a3_out.txt
	protected void makeA3Out(NodeCities[] graphCities,int startIndex,int endIndex){
		
		ArrayList<String> firstPathD=performDFS(graphCities,startIndex, endIndex);
		
		ArrayList<String> firstPathB=performBFS(graphCities,startIndex, endIndex);
		
		try {
			FileWriter Fr= new FileWriter("data/a3_out.txt",false);
			BufferedWriter Br=new BufferedWriter(Fr);
			Br.write("BFS: ");
			for(int i=0;i<firstPathB.size()-1;i++){
				Br.write(firstPathB.get(i)+", ");
			}
			Br.write(firstPathB.get(firstPathB.size()-1));
			
			Br.newLine();
			
			Br.write("DFS: ");
			for(int I=0;I<firstPathD.size()-1;I++){
				Br.write(firstPathD.get(I)+", ");
			}
			Br.write(firstPathD.get(firstPathD.size()-1));
			
			Br.newLine();
			Br.newLine();			
			Br.close();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	//uses Dijkstra's algorithm to find the shortest path in between nodes
	protected ArrayList<NodeCities> findShortestPath(Dijkstra findShort, NodeCities[] graphCities, int startingIndex, int endingIndex){
		Stack<Edge> path = (Stack<Edge>) findShort.pathTo(getIndexOfCity(graphCities[endingIndex].getCity(), graphCities));
		ArrayList<NodeCities> shortestPath=new ArrayList<NodeCities>();
		shortestPath.add(graphCities[startingIndex]);
		for (int n = path.size()-1; n > -1; n--){
			if(!shortestPath.contains(graphCities[path.get(n).either()])){
				shortestPath.add(graphCities[path.get(n).either()]);
			}
			else{
				shortestPath.add(graphCities[path.get(n).that()]);
			}
		}
		return shortestPath;
		
	}
	
	
}
