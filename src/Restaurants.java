/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: Restaurants Class
 * Class Description: This is a class that deals with the menu.csv data set and the three restaurant data sets.
 * It retrieves the data from menu and sorts the data based on lowest price. It allows you to access the items and costs for each meal. 
 * It allows you to check if a restaurant is located within 0.5 km of the cities longitude and latitude.
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Restaurants {
	private Double[] priceArray;//stores the prices of all menu items
	private String[] itemArray;//stores all the meals
	
	//constructor to take the data and sort it
	public Restaurants(){
		int numitems=getMenuLength();
		this.priceArray=new Double[numitems];
		this.itemArray=new String[numitems];
		getMenuData();
		sortMenu();
	}
	
	//returns the longitude and latitude of the restaurant if it is in range of the city
	protected double[] isRestaurantInRange(String restaurant,NodeCities city){
		return restaurantInRange(restaurant,city);
	}
	
	//finds the longitude and latitude values for a restaurant if it is in range of the given city
	private double[] restaurantInRange(String restaurant,NodeCities city){
		String file="";
		double[] longlat=new double[2];
		if(restaurant.equals("McDonald’s")){
			file="2XB3_A3_DataSets/mcdonalds.csv";
		}
		else if(restaurant.equals("Burger King")){
			file="2XB3_A3_DataSets/burgerking.csv";
		}
		else{
			file="2XB3_A3_DataSets/wendys.csv";
		}
		try {
			FileReader Fr= new FileReader(file);
			BufferedReader Br=new BufferedReader(Fr);
			boolean i=true;
			
			while(i){
				String line=Br.readLine();
				if(line==null){
					i=false;
				}
				else{
					int comma=line.indexOf(",");
					double longitudeRest=Double.parseDouble(line.substring(0, comma));
					String rest=line.substring(comma+2);
					int comma1=rest.indexOf(",");
					double latitudeRest=Double.parseDouble(rest.substring(0, comma1));
					if(Math.abs(longitudeRest-city.getLongitude())<=0.5 && Math.abs(latitudeRest-city.getLatitude())<=0.5){
						i=false;
						longlat[0]=longitudeRest;
						longlat[1]=latitudeRest;
						return longlat;
					}
				}
				
			}
			Br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return longlat;
		
	}
	
	//return a lists of meal prices
	protected Double[] getPriceArray(){
		return this.priceArray;
	}
	
	//returns a list of meal items
	protected String[] getItemArray(){
		return this.itemArray;
	}
	
	//calculates the length of the menu
	private int getMenuLength(){
		int length=0;
		try {
			FileReader Fr= new FileReader("2XB3_A3_DataSets/menu.csv");
			BufferedReader Br=new BufferedReader(Fr);
			boolean i=true;
			Br.readLine();
			while(i){
				String test=Br.readLine();
				
				if(test==null){
					i=false;
				}
				else{
					length++;
					
				}
			}	
			Br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	//sorts both menuPrices and menuItems based on ascending pricing of meals
	protected void sortMenu(){
		//System.out.println(this.priceArray.length);
		for(int i=0;i<this.priceArray.length;i++){
			for(int j=i+1;j<this.priceArray.length;j++){
				if(this.priceArray[i]>this.priceArray[j]){
					Double temp=this.priceArray[i];
					this.priceArray[i]=this.priceArray[j];
					this.priceArray[j]=temp;
					String temp2=this.itemArray[i];
					this.itemArray[i]=this.itemArray[j];
					this.itemArray[j]=temp2;
				}
			}
			
		}
	}
	
	//retrieves the data for meals and prices from the data set
	protected void getMenuData(){
		int counter=0;
		try {
			FileReader Fr= new FileReader("2XB3_A3_DataSets/menu.csv");
			BufferedReader Br=new BufferedReader(Fr);
			boolean i=true;
			Br.readLine();
			while(i){
				String test=Br.readLine();
				
				if(test==null){
					i=false;
				}
				else{
					int commaone=test.indexOf(",");
					String restaurant=test.substring(0,commaone);
					String temp6=test.substring(commaone+1);
					
					int commatwo=temp6.indexOf(",");
					
					String mealChoice=temp6.substring(0,commatwo);
					Double mealPrice=Double.parseDouble(temp6.substring(commatwo+2,commatwo+6));
					this.priceArray[counter]=(mealPrice);
					this.itemArray[counter]=(mealChoice);
					counter++;
					
				}
			}	
			Br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
