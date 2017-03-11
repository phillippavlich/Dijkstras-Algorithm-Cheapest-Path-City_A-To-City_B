/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: StateGasPrices Class
 * Class Description: This is a class that was created in order to search through the dataset StateGasPrices.csv in order to find the gas price in cents/L
 * of the specific state given.
 *
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StateGasPrices {
	public StateGasPrices(){
		
	}
	//gets the state gas Price
	protected int getPriceFuel(String initials){
		int statePrice=checkPrice(initials);
		return statePrice;
	}
	
	//implements the search in the data set to find the corresponding gas price for a given state
	private int checkPrice(String stateWanted){
		try {
			int priceGas=0;
			FileReader Fr= new FileReader("2XB3_A3_DataSets/StateGasPrice.csv");
			BufferedReader Br=new BufferedReader(Fr);
			boolean i=true;
			
			while(i){
				String test=Br.readLine();
				String state=test.substring(0, 2);
				
				if(test==null||state.equals(stateWanted)){
					i=false;
					priceGas=Integer.parseInt(test.substring(4));
				}
			}
			
			Br.close();
			return priceGas;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
