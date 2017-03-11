/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: NodeCreation Class
 * Class Description: This is a class that creates all the nodes for each individual city. It also creates a edge weighted graph for dijkstra's implementation
 * of shortest path.
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class NodeCreation {
	private NodeCities[] graphUS;
	private ArrayList<String> allCitiesAvailable;
	private int numberOfTotalCities;
	
	//constructor to create array to store the nodes for all cities
	public NodeCreation(){
		this.allCitiesAvailable=numCities();
		this.numberOfTotalCities=this.allCitiesAvailable.size();
		this.graphUS=new NodeCities[this.numberOfTotalCities];
		createNodes();
		
	}
	 
	//returns the total number of cities
	protected int numberCities(){
		return this.numberOfTotalCities;
	}
	
	//reads through connected cities to find the total number of cities that need to be created as nodes as displays them as an arraylist
	private ArrayList<String> numCities(){
		ArrayList<String> cityNames=new ArrayList<String>();
		
		try {
			FileReader Fr= new FileReader("2XB3_A3_DataSets/connectedCities.txt");
			BufferedReader Br=new BufferedReader(Fr);
			boolean i=true;
			
			while(i){
				String test=Br.readLine();
				
				if(test==null){
					i=false;
				}
				else{
					int endBracket=test.indexOf(")");
					String before=test.substring(0,endBracket+1).trim();
					String after=test.substring(endBracket+3).trim();
					
					boolean first=true;
					boolean second=true;
					for(int a=0;a<cityNames.size();a++){
						if(cityNames.get(a).equals(before)){
							first=false;
						}
					}
					for(int b=0;b<cityNames.size();b++){
						if(cityNames.get(b).equals(after)){
							second=false;
						}
					}
					if(first){
						cityNames.add(before);
					}
					if(second){
						cityNames.add(after);
					}
				}
			}
	
			Br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cityNames;
	}
	
	//returns all nodes in an array format
	protected NodeCities[] getAllNodes(){
		return this.graphUS;
	}
	
	//for loop that iterates through creating a node for each individual city.
	//takes in the name of city, states that it belongs to, its longitude and its latitude
	private void createNodes(){
		for(int i=0;i<this.graphUS.length;i++){
			String eachCity=this.allCitiesAvailable.get(i);
			int bracket=eachCity.indexOf("(");
			int bracket1=eachCity.indexOf(")");
			String cityName=eachCity.substring(0,bracket-1 );
			
			String[] connectedStates=eachCity.substring(bracket+1,bracket1).split(",");
			for(int k=0;k<connectedStates.length;k++){
				connectedStates[k].replace(" ", "");
				
			}
			SearchingZips zipFile=new SearchingZips(cityName,connectedStates);
			double longLoc=zipFile.getCityLong();
			double latLoc=zipFile.getCityLat();
			
			this.graphUS[i]=new NodeCities(cityName,connectedStates,longLoc,latLoc);
			
		}
	}
	
	//used to find the index of a specific city in the node array
	protected int getIndexOfCity(String temp, NodeCities[] graphCities){
		int index=-1;
		for(int i=0;i<graphCities.length;i++){
			if(graphCities[i].getCity().equals(temp)){
				index=i;
			}
		}
		return index;
	}
	
	//creates an edge weighted graph for Dijkstra's algorithm to find the shortest path between two cities
	protected EdgeWeightedGraph createEdges(NodeCities[] graphCities,int startingIndex,int endingIndex){
		
		ArrayList<Edge> weightedEdgeList=new ArrayList<Edge>();
	
		try{
			FileReader fr2 = new FileReader("2XB3_A3_DataSets/connectedCities.txt");
			BufferedReader br2 = new BufferedReader(fr2);
		
			String lineRead1 = br2.readLine();
			
			while (lineRead1 != null) {
				
				String[] tempArray = lineRead1.split("\\),");
				for (int i = 0; i < tempArray.length; i++) {
					int index = tempArray[i].indexOf("(");
					
					tempArray[i] = tempArray[i].substring(0, index).trim();
					
					
					
				}
				int first = getIndexOfCity(tempArray[0], graphCities);
				int second = getIndexOfCity(tempArray[1], graphCities);
				Pricing B=new Pricing();
				double weighting = B.getPriceBetweenCities(graphCities[first], graphCities[second]);
				
				Edge tempEdge = new Edge(first, second, weighting);
				
				weightedEdgeList.add(tempEdge);
				
				
				
				lineRead1 = br2.readLine();
			}
			
		}
		catch (FileNotFoundException fne) {
			System.out.println("File Was Not Found");
		}
		catch (IOException ex) {
			System.out.println("Error reading file");
		}
		
		
		
		//Creating edge weighted graph with 32 (number of nodes) with nodes you've created
		EdgeWeightedGraph properGraph = new EdgeWeightedGraph(graphCities.length);
		
		for(int m = 0; m < weightedEdgeList.size(); m++){
			properGraph.addEdge(weightedEdgeList.get(m));
		}
		return properGraph;
		
	}
}
