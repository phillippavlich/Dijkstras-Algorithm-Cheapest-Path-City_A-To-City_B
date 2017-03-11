/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: main Class
 * Class Description: This is a class that allows takes in two cities read in from a file
 * and calls on several different classes to compute DFS, BFS, shortest path algorithm(dijkstra),
 * creating the nodes and creating the graph
 *
 */


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class main {
	public static NodeCities[] graphCities;
	
	public static void main(String[] args) {
		String[] input=new String[2];
		main h=new main();
		input=h.readInputFile();//read from input file
		String startCity=input[0];
		String endCity=input[1];

		NodeCreation all=new NodeCreation();//create all nodes
		
		graphCities=all.getAllNodes();//save nodes to an array
		
		boolean ifExists=false;
		boolean ifExists1=false;
		int startingIndex=-1;
		int endingIndex=-1;
		
		//getting index of starting and ending city
		for(int d=0;d<all.numberCities();d++){
			if(graphCities[d].getCity().equals(startCity)){
				ifExists=true;
				startingIndex=d;
			}
			
			if(graphCities[d].getCity().equals(endCity)){
				ifExists1=true;
				endingIndex=d;
			}
			
		}
		
		
		if(startingIndex==endingIndex){//if the user puts in the same starting city as ending city
			try{
				FileWriter fw = new FileWriter("data/a3_out.txt", false);
				BufferedWriter bw = new BufferedWriter(fw);
				
				
				bw.write("BFS: " + graphCities[startingIndex].getCity());
				bw.newLine();
				bw.write("DFS: " + graphCities[startingIndex].getCity());
				bw.newLine();
				bw.newLine();
				bw.write("You entered the same destination city as your starting city!");
				bw.newLine();
				bw.write("The trip will cost zero dollars and you will not stop at any restaurants!");
				
				
			
				bw.close();
			}
			catch (IOException ex) {
				System.out.println("Error reading file");
			}
		}
		
		else if(ifExists && ifExists1){//valid input 
			Searching search=new Searching(graphCities,startingIndex,endingIndex);
			EdgeWeightedGraph newGraph=all.createEdges(graphCities,startingIndex,endingIndex);//creating edge weighted graph for Dijkstra implementation
			
			Dijkstra findShort = new Dijkstra(newGraph, all.getIndexOfCity(graphCities[startingIndex].getCity(), graphCities));// creating a Dijkstra object in order to allow a shortest path algorithm 
			ArrayList<NodeCities> path=search.findShortestPath(findShort ,graphCities,startingIndex,endingIndex);//use dijkstra's algorithm to find shortest path
			
			Pricing total=new Pricing();
			Restaurants food=new Restaurants();//saving menu data to two array lists
			
			double totalPrice=total.findShortestPathPrice(findShort, graphCities, startingIndex,endingIndex,path,all,food);//saving shortest path price
			search.makeA3Out(graphCities,startingIndex,endingIndex);//make the first part of a3_out.txt (BFS, DFS)
			h.output(path,food,totalPrice);//write the shortest path in a table format to the file
			
		}
		else if(ifExists){
			System.out.println("That city is not listed as a possible ending destination");
		}
		else if(ifExists1){
			System.out.println("That city is not listed as a possible starting destination");
		}
		else{
			System.out.println("Neither city is a possible starting/ending destination");
		}
		
	}
	
	//this method reads from a3_in.txt and saves the starting and ending city
	private String[] readInputFile(){
		String[] input=new String[2];
		try {
			FileReader Fr= new FileReader("data/a3_in.txt");
			BufferedReader Br=new BufferedReader(Fr);
			input[0]=Br.readLine();
			input[1]=Br.readLine();
			Br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("No input given!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	//this creates an arraylist of type string with all the data properly formatted into a table format
	//this allows it to be written using a for loop
	private void output(ArrayList<NodeCities> path, Restaurants food, double totalPrice){
		String tempDouble="";
		ArrayList<String> table = new ArrayList<String>();
	
		int longestString1 = 0; int longestString2 = 0; int longestString3 = 0; int longestString4 = 0;
		char[] spaces1; char[] spaces2; char[] spaces3; char[] spaces4;
		char[] s1; char[] s2; char[] s3; char[] s4;
		
		int a = "Meal".length();
		int b = "Meal Choice".length();
		int c = "Cost Of Meal".length();
		int d = "Cost of Fuel".length();
		
		//Finding the Longest City length
		for(int i = 0; i < path.size(); i++){
			String tempString1 = path.get(i).getCity();
			if(tempString1.length()>longestString1){
				longestString1 = tempString1.length();
			}
		}
		String[] menuItems=food.getItemArray();
		Double[] menuPrices=food.getPriceArray();
		
		
		//Finding the Longest Meal Name length
		for(int j = 0; j < path.size()-1; j++){
			if(menuItems[j].length()>longestString2){
				longestString2 = menuItems[j].length();
			}
		}
		
		//Finding the Longest Meal Price length
		for(int k = 0; k < path.size()-1; k ++){
			if(Double.toString(menuPrices[k]).length()>longestString3){
				longestString3 = Double.toString(menuPrices[k]).length();
			}
		}
		
		
		
		//Finding the Longest Cost of Fuel length
		String[] gasPricesIndividual = new String[path.size()-1];
		
		for(int h = 0; h < gasPricesIndividual.length; h++){
			
			Pricing X=new Pricing();
			double priceBetweenCities = X.getPriceBetweenCities(path.get(h), path.get(h+1));
			
			
			int index = Double.toString(priceBetweenCities).indexOf(".");
			String tempString4 = Double.toString(priceBetweenCities).substring(0, index+3);
			
			gasPricesIndividual[h] = tempString4;
			
			if(tempString4.length()>longestString4){
				longestString4 = tempString4.length();
			}
		}
		
		
		if(a<longestString1){ 
			spaces1 = new char[Math.abs(a - longestString1)+3];
			Arrays.fill(spaces1, ' ');
		}
		else{
			spaces1 = new char[3]; 
			Arrays.fill(spaces1, ' ');
		}
		
	    if (b<longestString2){
	    	spaces2 = new char[Math.abs(b - longestString2)+3];
	    	Arrays.fill(spaces2, ' ');
	    }
	    
	    else{
	    	spaces2 = new char[3];
	    	Arrays.fill(spaces2, ' ');
	    }
		
	    if(c<longestString3){
	    	spaces3 = new char[Math.abs(c - longestString3)+3];
	    	Arrays.fill(spaces3, ' ');
	    }
	    else{
	    	spaces3 = new char[3];
	    	Arrays.fill(spaces3, ' ');
	    } 
		
		if(d<longestString4){
			spaces4 = new char[Math.abs(d - longestString4)+3];
			Arrays.fill(spaces4, ' ');
		}
		else{
			spaces4 = new char[3];
			Arrays.fill(spaces4, ' ');
		}
		
	
		// Find spaces for first city
		//different due to dashes
		char[] firstCitySpace;
		if(path.get(0).getCity().length()<longestString1){
			firstCitySpace = new char[Math.abs(path.get(0).getCity().length() - longestString1)+3];
			Arrays.fill(firstCitySpace, ' ');
		}
		else{
			firstCitySpace = new char[3];
			Arrays.fill(firstCitySpace, ' ');
		}
		
		char[] firstMealChoiceSpace = new char[Math.abs("-".length() - longestString2)+3];
		Arrays.fill(firstMealChoiceSpace, ' ');
		
		char[] firstCostOfMealSpace;
		if("Cost of Meal".length() < longestString3){
			firstCostOfMealSpace = new char[Math.abs("-".length() - longestString3)+3];
			Arrays.fill(firstCostOfMealSpace, ' ');
		}
		else{
			firstCostOfMealSpace = new char[Math.abs("-".length() - "Cost of Meal".length())+3];
			Arrays.fill(firstCostOfMealSpace, ' ');
		}
		
		char[] firstCostOfFuelSpace;
		if("Cost of Meal".length() < longestString4){
			firstCostOfFuelSpace = new char[Math.abs("-".length() - longestString4)+3];
			Arrays.fill(firstCostOfFuelSpace, ' ');
		}
		else{
			firstCostOfFuelSpace = new char[Math.abs("-".length() - "Cost of Fuel".length())+3];
			Arrays.fill(firstCostOfFuelSpace, ' ');
		}
		
		table.add("City"+new String(spaces1)+"Meal Choice"+new String(spaces2)+"Cost of Meal"+new String(spaces3)+"Cost of Fuel"+new String(spaces4)+"Total");
		table.add("");
		table.add(path.get(0).getCity()+new String(firstCitySpace)+"-"+new String(firstMealChoiceSpace)+"-"+new String(firstCostOfMealSpace)+"-"+new String(firstCostOfFuelSpace)+"-");
		double finalTotal = 0;
		
		
		for (int w=0; w< path.size()-1; w++){ 
			if (a>path.get(w+1).getCity().length()&&a>longestString1){
				s1 = new char[3+a-path.get(w+1).getCity().length()];
				Arrays.fill(s1, ' ');
			}
			else if (a>path.get(w+1).getCity().length()){
				s1 = new char[Math.abs(a - longestString1)+3+a-path.get(w+1).getCity().length()];
				Arrays.fill(s1, ' ');
			}
			else{
				s1 = new char[longestString1-path.get(w+1).getCity().length()+3];
				Arrays.fill(s1, ' ');
			}
			
			if (b>menuItems[w].length()&&b>longestString2){
				s2 = new char[3+b-menuItems[w].length()];
				Arrays.fill(s2, ' ');
			}
			else if (b>menuItems[w].length()){
				s2 = new char[Math.abs(b - longestString2)+3+b-menuItems[w].length()];
				Arrays.fill(s2, ' '); 
			}
			else{ 
				
				s2 = new char[longestString2-menuItems[w].length()+3];
				Arrays.fill(s2, ' '); 
			}
			
			if(c>Double.toString(menuPrices[w]).length()&&c>longestString3){
				s3 = new char[2+c-Double.toString(menuPrices[w]).length()]; 
				Arrays.fill(s3, ' ');
			}
			else if(c>Double.toString(menuPrices[w]).length()){
				s3 = new char[Math.abs(c - longestString3)+2+c-Double.toString(menuPrices[w]).length()];
				Arrays.fill(s3, ' '); 
			} 
			else{
				s3 = new char[longestString3-Double.toString(menuPrices[w]).length()+2];
				Arrays.fill(s3, ' ');
			} 
			
			if(d>gasPricesIndividual[w].length()&&d>longestString4){ 
				s4 = new char[2+d-gasPricesIndividual[w].length()];
				Arrays.fill(s4, ' ');
			} 
			else if(d>gasPricesIndividual[w].length()){ 
				s4 = new char[Math.abs(d - longestString4)+2+d-gasPricesIndividual[w].length()];
				Arrays.fill(s4, ' ');
			} 
			else{ 
				s4 = new char[longestString4-gasPricesIndividual[w].length()+2];
				Arrays.fill(s4, ' ');
			}
			
			
			
			finalTotal += menuPrices[w] + Double.parseDouble(gasPricesIndividual[w]);
			Double actualTotal=menuPrices[w] + Double.parseDouble(gasPricesIndividual[w]);
			
			tempDouble = Double.toString(actualTotal);
			
			int index9 = tempDouble.indexOf(".");
			if(tempDouble.length()>6){
				tempDouble = tempDouble.substring(0,index9+3);
			}
			
			
			
			table.add(path.get(w+1).getCity()+new String(s1)+menuItems[w]+new String(s2)+"$"+menuPrices[w]+new String(s3)+"$"+gasPricesIndividual[w]+new String(s4)+"$"+tempDouble);
		} 	

		for(int x = 0; x < path.size()-1; x++){
				
		}
		
		double totalMealCost = 0;
		double totalFuelCost = 0;
		
		for(int x = 0; x < path.size()-1; x++){
			
			totalMealCost += menuPrices[x];
			totalFuelCost += Double.parseDouble(gasPricesIndividual[x]);
		}
		String mealStr;
		int index1 = Double.toString(totalMealCost).indexOf(".");
		
		if(Double.toString(totalMealCost).length()<5 && Double.toString(totalMealCost).substring(0, index1).length() > 1){
			
			mealStr = "$" + Double.toString(totalMealCost).substring(0, index1+2);
		}
		else{
			mealStr = "$" + Double.toString(totalMealCost).substring(0, index1+3);
		}
		
		String fuelStr;
		int index2 = Double.toString(totalFuelCost).indexOf(".");
		
		if(Double.toString(totalFuelCost).length()<5 && Double.toString(totalFuelCost).substring(0, index1).length() > 1){
			fuelStr = "$" + Double.toString(totalFuelCost).substring(0, index2+2);
		}
		else{
			fuelStr = "$" + Double.toString(totalFuelCost).substring(0, index2+3);
		}
		
		
		
	    
	    char[] newSpace = new char[Math.abs("Cost of Meal".length() - mealStr.length())+3];
	    Arrays.fill(newSpace, ' ');
	   
	    char[] newSpace1 = new char[Math.abs("Cost of Fuel".length() - fuelStr.length())+3];
	    Arrays.fill(newSpace1, ' ');
		
		table.add("    "+new String(spaces1)+"           "+new String(spaces2)+"------"+"         "+"--------"+"       "+"--------");
		table.add("    "+new String(spaces1)+"           "+new String(spaces2)+mealStr + new String(newSpace) + fuelStr +new String(newSpace1) + "$"+Math.round(finalTotal*100.0)/100.0);
		
		a3OutputTable(table);
}
//method to write the string arraylist to a file in a table format	
private void a3OutputTable(ArrayList<String> tableList){
	
	try{
		FileWriter fw = new FileWriter("data/a3_out.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (int i = 0; i < tableList.size(); i++){
			bw.write(tableList.get(i));
			bw.newLine();
		}
		
	
		bw.close();
	}
	catch (IOException ex) {
		System.out.println("input error");
	}
	
	
}
	
	

}
