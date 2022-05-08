package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {
	public String index = "";
	public List<String> listAddress = new ArrayList<>();
	public List<String> fullData = new ArrayList<>();

	public void ReadData() throws IOException {
		File dir = new File("C:\\Users\\user\\eclipse-workspace\\StarBucksProject\\csvdata");
		File files[] = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			int checkLine = 0;
			BufferedReader reader = new BufferedReader(new FileReader(files[i]));
			String line;
			System.out.println("Start merging csv files.(filename : " + files[i] + " )");
			while ((line = reader.readLine()) != null) {
				if (checkLine == 15) {
					index = line + ",스타벅스가는시간(분),스타벅스가는거리(m)";
				} else if (checkLine > 16) {
					fullData.add(line);
					String[] temp = line.split(",");
					if(temp[2].equals("0")) {
						listAddress.add(temp[0]+" "+temp[1]);
					}else {
					listAddress.add(temp[0]+" "+temp[1]+"-"+temp[2]);
					}
				}
					
				checkLine++;
			}
		}
		System.out.println("csv file merge is complete.");
	}

	public void WriteFinalData(List<String> a) throws IOException {
		System.out.println("Start writing data to the final csv file.");
		File file = new File("C:\\Users\\user\\eclipse-workspace\\StarBucksProject\\csvdata\\FinalBackData.csv");
		FileWriter fw = new FileWriter(file, true);
		fw.write(index + "\n");
		for (int i = 0; i < a.size(); i++) {
			fw.write(a.get(i) + "\n");
		}
		fw.close();
		System.out.println("The final csv file has been saved.");
	}

	public void WriteMergeData(List<String> a) throws IOException {
		System.out.println("Start writing data to new csv file.");
		File file = new File("C:\\Users\\user\\eclipse-workspace\\StarBucksProject\\csvdata\\MergeBackData.csv");
		FileWriter fw = new FileWriter(file, true);
		fw.write(index + "\n");
		for (int i = 0; i < a.size(); i++) {
			fw.write(a.get(i) + "\n");
		}
		fw.close();
		System.out.println("The new csv file has been saved.");
	}
}
