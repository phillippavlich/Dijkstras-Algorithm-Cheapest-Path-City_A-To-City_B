/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: NodeCities Class
 * Class Description: This is a class that is used for each node in the graph. Each node is a city and this class allows there to be properties and 
 * methods for each city. There a methods that return the city name, all the states that the city belongs to, its longitude, its latitude, all its 
 * adjacent nodes(where there exists paths between two cities). 
 *
 */

import java.util.ArrayList;

public class NodeCities {
//look at node examples
	private double longitude;
	private double latitude;
	private String[] states;
	private String city;
	private ArrayList<String> pathPossibilities;
	private double restaurantLongitude;
	private double restaurantLatitude;
	private int prevIndex;
	
	//constructor taking in a city name, states that it belongs to, its longitude/latitude values
	public NodeCities(String city, String[] states, double longitude, double latitude){
		this.longitude=longitude;
		this.latitude=latitude;
		this.states=states;
		this.city=city;
		this.pathPossibilities=new ArrayList<String>();
		this.restaurantLongitude=0;
		this.restaurantLatitude=0;
		this.prevIndex=-1;
		
		assignAdjacentCities();
	}
	
	//this saves the index of the node that it came from so that when a path between the starting city and ending city is found, it can be retraced from 
	//the destination city to starting city
	protected void fixIndex(int a){
		this.prevIndex=a;
	}
	
	//returns prevIndex
	protected int getPrevIndex(){
		return this.prevIndex;
	}
	
	//retrieves all adjacent cities to this city
	protected ArrayList<String> getAdjacentCities(){
		return this.pathPossibilities;
	}
	
	//calls on CityPaths class to find all connected cities to this one
	private void assignAdjacentCities(){
		CityPaths pathway=new CityPaths();
		this.pathPossibilities=pathway.getNextCity(this.getCity());
		
	}
	
	//returns city name
	protected String getCity(){
		return this.city;
	}
	
	//returns all the states that this city belongs to
	protected String[] getState(){
		for(int i = 0; i < this.states.length ; i++){
			this.states[i] = this.states[i].replace(" ", "");
		}
		
		return this.states;
	}
	
	//returns longitude 
	protected double getLongitude(){
		return this.longitude;
	}
	
	//returns latitude
	protected double getLatitude(){
		return this.latitude;
	}
	
	//returns restaurant longitude
	protected double getRestaurantLongitude(){
		return this.restaurantLongitude;
	}
	
	//returns restaurant latitude
	protected double getRestaurantLatitude(){
		return this.restaurantLatitude;
	}
	
	//checks if the restaurant is in range of the city to confirm that each specific restaurant is valid in that city
	//needed to check which meal would be the cheapest or most appropriate for each stop
	private void assignRestaurantLocation(String restaurant, NodeCities[] allCities,int whichCity){
		Restaurants pitStop=new Restaurants();
		double[] tempSpot=pitStop.isRestaurantInRange(restaurant,allCities[whichCity] );
		this.restaurantLongitude=tempSpot[0];
		this.restaurantLatitude=tempSpot[1];
	}
}
