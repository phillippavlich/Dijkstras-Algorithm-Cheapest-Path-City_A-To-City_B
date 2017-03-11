/*
 * Name & Student Number: Phillip Pavlich, 001414960
		  				  
 * Class Name: Junit Test
 * Class Description: This is a class that tests both BFS and DFS for incorrect paths and non distinct cities in the paths
 *
 */
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class Test_DFSBFS {
	@Test
	public void testDFSImplementation() throws IOException {
		NodeCreation all=new NodeCreation();
		NodeCities[] values= all.getAllNodes();
		
		ArrayList<String> pathDFS = new ArrayList<String>();
		String start="Los Angeles";
		String end="Boston";
		
		int startIndex=-1;
		int endIndex=-1;
		
		for(int j=0;j<values.length;j++){
			if(values[j].getCity().equals(start)){
				startIndex=j;
			}
			if(values[j].getCity().equals(end)){
				endIndex=j;
			}
		}
		
		Searching search=new Searching(values, startIndex, endIndex);
		pathDFS= search.getDFS(values, startIndex, endIndex);
		for(int i=0;i<pathDFS.size()-1;i++){
			if(pathDFS.get(i)==pathDFS.get(i+1)){
				fail("they are not distinct for DFS");
			}
		}
		
	}
	@Test
	public void testpathDFS() throws IOException{
		NodeCreation all=new NodeCreation();
		NodeCities[] values= all.getAllNodes();
		ArrayList<String> pathDFS = new ArrayList<String>();
		Searching ind=new Searching(values,0,0);
		
		String start="Los Angeles";
		String end="Boston";
		
		int startIndex=ind.getIndexOfCity(start, values);
		int endIndex=ind.getIndexOfCity(end, values);
		
		
		Searching search=new Searching(values, startIndex, endIndex);
		pathDFS= search.getDFS(values, startIndex, endIndex);
		boolean error=true;
		for(int i=0;i<pathDFS.size()-1;i++){
			int index=ind.getIndexOfCity(pathDFS.get(i), values);
			error=true;
			for(int K=0;K<values[index].getAdjacentCities().size();K++){
				if(values[index].getAdjacentCities().get(K).equals(pathDFS.get(i+1))){
					error=false;
				}
			}
			if(error){
				fail("the path for DFS is incorrect");
			}
		}
	
	}
	@Test
	public void testBFSImplementation() throws IOException {
		NodeCreation all=new NodeCreation();
		NodeCities[] values= all.getAllNodes();
		
		ArrayList<String> pathBFS = new ArrayList<String>();
		String start="Los Angeles";
		String end="Boston";
		
		int startIndex=-1;
		int endIndex=-1;
		
		for(int j=0;j<values.length;j++){
			if(values[j].getCity().equals(start)){
				startIndex=j;
			}
			if(values[j].getCity().equals(end)){
				endIndex=j;
			}
		}
		
		Searching search=new Searching(values, startIndex, endIndex);
		pathBFS= search.getBFS(values, startIndex, endIndex);
		for(int i=0;i<pathBFS.size()-1;i++){
			if(pathBFS.get(i)==pathBFS.get(i+1)){
				fail("they are not distinct for BFS");
			}
		}
		
	}
	@Test
	public void testpathBFS() throws IOException{
		NodeCreation all=new NodeCreation();
		NodeCities[] values= all.getAllNodes();
		ArrayList<String> pathBFS = new ArrayList<String>();
		Searching ind=new Searching(values,0,0);
		
		String start="Los Angeles";
		String end="Boston";
		
		int startIndex=ind.getIndexOfCity(start, values);
		int endIndex=ind.getIndexOfCity(end, values);
		
		
		Searching search=new Searching(values, startIndex, endIndex);
		pathBFS= search.getBFS(values, startIndex, endIndex);
		boolean error=true;
		for(int i=0;i<pathBFS.size()-1;i++){
			int index=ind.getIndexOfCity(pathBFS.get(i), values);
			error=true;
			for(int K=0;K<values[index].getAdjacentCities().size();K++){
				if(values[index].getAdjacentCities().get(K).equals(pathBFS.get(i+1))){
					error=false;
				}
			}
			if(error){
				fail("the path for BFS is incorrect");
			}
		}
	
	}
}