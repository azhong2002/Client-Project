package tests;

import java.util.ArrayList;

public class Teacher {

	public ArrayList<String[]> examList;
	public String name = "no_name";
	
	public Teacher() {
		examList = new ArrayList<String[]>();
	}
	
	public Teacher(ArrayList<String[]> examParam) {
		examList = examParam;
		name = examList.get(0)[3];
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
	
}
