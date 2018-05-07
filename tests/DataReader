package tests;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//Andrew Zhong 5/6/18 Client Project Testing
//Reads MER spreadsheet file and creates PDF
//data expected to be 1 item per line and in the following format: ID,Alpha_name,PD,TCHL,TestName,APExam

public class PDFMaker {
	
	public static ArrayList<String[]> readData(String fileAddress) {
		File file = new File(fileAddress);
		Scanner reader;
		ArrayList<String[]> exams = new ArrayList<String[]>();	//stores data as arrays in arraylists
		
		try {								//create file reader
			reader = new Scanner(file);
		}
		catch(FileNotFoundException e){
			reader = new Scanner("FileNotFound");
		}
		reader.nextLine();
		
		while(reader.hasNextLine()) {	//go through each item
			String line = reader.nextLine();
			exams.add(line.split(","));		//each exam is an array of ID,Alpha_name,PD,TCHL,TestName,APExam
			
		}
		
		reader.close();
		return exams;
	}

	public static void main(String[] args) {
		ArrayList<String[]> exams = readData("test_data.mer");
		for(String[] item: exams) {
			for(String s: item) {
				System.out.print(s + ",");
			}
			System.out.println();
		}

	}

}
