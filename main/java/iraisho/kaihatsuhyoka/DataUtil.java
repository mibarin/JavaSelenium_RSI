package iraisho.kaihatsuhyoka;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class DataUtil {

	public static String[][] getTableArray(String xlFilePath, String sheetName, String startPoint, String endPoint) throws Exception{

		int startRow,startCol, endRow, endCol;
		String[][] tabArray=null;

		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = workbook.getSheet(sheetName); 
		//find a cell labeled with "StartPoint"
		jxl.Cell tableStart=sheet.findCell(startPoint);
		// get a row of that cell
		startRow=tableStart.getRow();
		// get a column of that cell
		startCol=tableStart.getColumn();  
		//find a cell#2 labeled with "TestData1"
		jxl.Cell tableEnd= sheet.findCell(endPoint);   
		//get a row of that cell
		endRow=tableEnd.getRow();
		//get a row of that cell
		endCol=tableEnd.getColumn();

//		System.out.println("Start Row: " + startRow);
//		System.out.println("EndRow: " + endRow);
//		System.out.println("Start Col: " + startCol);
//		System.out.println("End Col: " + endCol);

		tabArray= new String[endRow-1][endCol-1];

		for (int i=startRow+1, ci=0; i<endRow; ci++, i++){
			for(int j=startCol+1, cj=0; j<endCol;j++, cj++){    		  

				tabArray[ci][cj] = sheet.getCell(j,i).getContents();
//				System.out.println(ci +" "+ cj);
//				System.out.println("in the loop: "+tabArray[ci][cj]);
			}
		}

		workbook.close();
		return tabArray;
	} 

}
