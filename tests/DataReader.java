package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//Andrew Zhong 5/6/18 Client Project Testing
//Reads MER spreadsheet file and creates PDF
//data expected to be 1 item per line and in the following format: ID,Alpha_name,PD,TCHL,TestName,APExam

public class DataReader {
	
	public static ArrayList<Teacher> readData(String fileAddress) {
		File file = new File(fileAddress);
		Scanner reader;
		ArrayList<String[]> exams = new ArrayList<String[]>();	//stores data as arrays in arraylists
		ArrayList<Teacher> teachList = new ArrayList<Teacher>();
		
		try {								//create file reader
			reader = new Scanner(file);
		}
		catch(FileNotFoundException e){		//for me not u
			reader = new Scanner("FileNotFound");
		}
		reader.nextLine();
		
		while(reader.hasNextLine()) {	//go through each item
			String line = reader.nextLine();
			line = line.substring(1,line.length() - 1); //trim non-delimiter quotation marks
			exams.add(line.split("\",\""));		//each exam is an array of ID,Alpha_name,PD,TCHL,TestName,APExam
			
		}
		
		Collections.sort(exams, (String[] item1, String[] item2) -> item1[3].compareTo(item2[3]));	//sort by teacher
		
		for(int ind = 0; ind < exams.size(); ind++) {	//move items to corresponding to Teacher object's array
			int lastSame = ind;						
			while(lastSame < exams.size() && exams.get(lastSame)[3].equals(exams.get(ind)[3])) {	//goes to last item with this teacher
				lastSame++;
			}
			teachList.add(new Teacher(new ArrayList<String[]>(exams.subList(ind, lastSame))));	//make new Teacher object
			ind = lastSame;
		}
		
		reader.close();
		return teachList;
	}

	public static void main(String[] args) {
		ArrayList<Teacher> teachers = readData("test_data.mer");
		for(Teacher t: teachers) {
			System.out.println(t);
		}

	}

}
