/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: SearchingZips Class
 * Class Description: This is a class that is used to search through the  zips1990.csv file to retrieve a longitude and a latitude value for a given city
 * in a specific state. New York and St. Louis were not spelt correctly in the file so I altered the spelling so that proper longitude and latitude values 
 * would be found.
 *
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchingZips {
	private double cityLong;
	private double cityLat;
	
	//constructor
	public SearchingZips(String actualCity,String[] actualStates){
		this.cityLong=0;
		this.cityLat=0;
		
		findLocation(actualCity ,actualStates);
	}
	
	//get the longitude
	protected double getCityLong(){
		return this.cityLong;
	}
	
	//get the latitude
	protected double getCityLat(){
		return this.cityLat;
	}
	
	//find the values for longitude and latitude through the data set
	private void findLocation(String actualCity,String[] actualStates){
		String actualCity1=actualCity.toUpperCase().trim();
		if(actualCity1.equals("ST. LOUIS")){
			actualCity1="SAINT LOUIS";
		}
		if(actualCity1.equals("NEW YORK CITY")){
			actualCity1="NEW YORK";
		}
		try {
			FileReader Fr= new FileReader("2XB3_A3_DataSets/zips1990.csv");
			BufferedReader Br=new BufferedReader(Fr);
			boolean i=true;
			
			while(i){
				String test=Br.readLine();
				
				if(test==null){
					i=false;
				}
				else{
					int commaone=test.indexOf(",");
					String temp5=test.substring(commaone+1);
					int commatwo=temp5.indexOf(",");
					String state=temp5.substring(commatwo+1,commatwo+3);
					
					String rest=temp5.substring(commatwo+4);
					int comma=rest.indexOf(",");
					String city=rest.substring(0,comma);
					
					for(int j=0;j<actualStates.length;j++){
						
						if(actualStates[j].equals(state) && city.contains(actualCity1)){
							i=false;
							String lastBit=rest.substring(comma+1);
							int comma1=lastBit.indexOf(",");
							this.cityLong=-1*Double.parseDouble(lastBit.substring(0, comma1));
							
							String lastBit1=lastBit.substring(comma1+1);
							int comma2=lastBit1.indexOf(",");
							this.cityLat=Double.parseDouble(lastBit1.substring(0, comma2));
						
						}
						
					}
					
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
