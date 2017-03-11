/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: CityPaths Class
 * Class Description: This is a class that uses a state and city name to find all adjacent cities to this city.
 * Returns an arraylist of all cities that are connected
 *
 */


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class CityPaths {
	
	public CityPaths(){
	
	}
	
	//get method for arraylist of cities connected
	protected ArrayList<String> getNextCity(String initials){
		ArrayList<String> adjacentCity=checkCities(initials);
		return adjacentCity;
	}
	
	//searched through connected cities in order to find all adjacent nodes to this city's node
	private ArrayList<String> checkCities(String State){
		ArrayList<String> connectedCities=new ArrayList<String>();
		int len=State.length();
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
					String before=test.substring(0,endBracket+1);
					String after=test.substring(endBracket+3);
					
					if(before.length()>=len ){
						if(before.substring(0,len).equals(State)){
							int endBracket1=after.indexOf("(");
							after=after.substring(0,endBracket1-1);
							connectedCities.add(after);
						
						}
					}
					if(after.length()>=len){
						if(after.substring(0,len).equals(State)){
							int endBracket2=before.indexOf("(");
							before=before.substring(0,endBracket2-1);
							connectedCities.add(before);
						
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
	
		return connectedCities;
	}
}
