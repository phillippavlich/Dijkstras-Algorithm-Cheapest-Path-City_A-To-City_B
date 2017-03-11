/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: Pricing Class
 * Class Description: This is a class computes distances and prices that are needed to find the shortest path between cities 
 *
 */

import java.util.ArrayList;
import java.util.Stack;

public class Pricing {
	public Pricing(){
		
	}
	
	//retrieves the distance as a double between two cities
	private double getDistance(NodeCities A, NodeCities B){
		double earthRadius = 6373;
	    double dLat = Math.toRadians(B.getLatitude()-A.getLatitude());
	    double dLng = Math.toRadians(B.getLongitude()-A.getLongitude());
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +Math.cos(Math.toRadians(A.getLatitude())) * Math.cos(Math.toRadians(B.getLatitude())) *Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
		
	}
	
	//returns the price for fuel between two cities based on the distance, car efficiency, and fuel costs calculated by averaging out all the states gas prices of the
	//starting and ending city gas prices
	protected double getPriceBetweenCities(NodeCities from, NodeCities to){
		double carEfficiency=8.2/100;
		
		double distance=getDistance(from ,to);
		int price=0;
		int counter=0;
		StateGasPrices fuelPrice=new StateGasPrices();
		for(int k=0;k<to.getState().length;k++){
			int PriceState=fuelPrice.getPriceFuel(to.getState()[k]);
			price+=PriceState;
			counter++;
		}
		for(int K=0;K<from.getState().length;K++){
			int PriceStates=fuelPrice.getPriceFuel(from.getState()[K]);
			price+=PriceStates;
			counter++;
		}
		double averageGas=price/counter;//need conversion for gass efficiency
		double totalPrice=averageGas*carEfficiency*distance/100;//divided my 100 for value in dollars
		return totalPrice;
	}
	
	//calculates total price for a whole trip based on a given path
	protected double totalPricePath(ArrayList<NodeCities> path){
		double totalPrice=0;
		for(int i=0;i<path.size()-1;i++){
			totalPrice+=getPriceBetweenCities(path.get(i),path.get(i+1));
		}
		return totalPrice;
	}
	
	//uses Dijkstra's algorithm to find the shortest path costs 
	protected double findShortestPathPrice(Dijkstra sp,NodeCities[] graphCities, int startingIndex, int endingIndex,ArrayList<NodeCities> path,NodeCreation all, Restaurants food){
		double totalPrice = sp.distTo(all.getIndexOfCity(graphCities[endingIndex].getCity(), graphCities));
		
		String[] items=food.getItemArray();
		Double[] prices=food.getPriceArray();
	
		
		for(int y=0;y<path.size()-1;y++){
			totalPrice+=prices[y];
		}
		
		return totalPrice;
	}
	
}
