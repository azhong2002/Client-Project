package tests;

import java.util.ArrayList;
import com.itextpdf.text.*;

public class Teacher {

	public ArrayList<String[]> examList;
	public String name = "no_name";
	
	public Teacher() {
		examList = new ArrayList<String[]>();
	}
	
	public Teacher(ArrayList<String[]> examParam) {
		examList = examParam;
		name = examList.get(0)[3];
		if(name.length() >= 10 && name.substring(0,10).equals("No Teacher")){
			name = "No Teacher";
		}
	}
	
	public void display() {
		for(String[] item: examList) {
			for(String metaTag: item) {
				System.out.print(metaTag + ",");
			}
			System.out.println();
		}
	}
	
	public String toString() {
		return name;
	}
	
	public void makePDF(){
		
	}
	
}
