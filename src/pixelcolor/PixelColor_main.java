package pixelcolor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PixelColor_main {

	public static void main(String[] args) {
		//String filename;
		int teacher_number = 13;//教師データ数
		
		////////変更必要なパラメタ///////
		int test_number = 217; //検査データ数
		String picture_date = "2019-11-04";
		String picture_date2 = "2019_11_04";
		String t_filename = "test20191104";
		String path = "/img/test/2019_11_04/";
		//////////////////////////////
		
		//String test_filename = "2019_11_01/test20191101";
		String test_filename = picture_date2+"/"+t_filename;
		//教師データの作成(011,012,013はダメデータ、6/11,7/1,6/20)
		PixelColor_lib tlib = new PixelColor_lib();
		//tlib.createTeacher(teacher_number);//必要な時に有効にする
		
		//テストデータのcos類似度の算出
		tlib.getTestData(teacher_number, test_number, test_filename+"_", test_filename); //必要な時に有効にする
		
		//cos類似度の大きいものをアップロード
		tlib.testUpload(test_number, teacher_number, test_filename, t_filename, path, picture_date, 0.95);
		
	}
	
	//1次元配列の取り込み
	public int[] getCSV2(String path, int column) {
		//CSVから取り込み
		int [] r = new int[column];
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
				 
			String[] data = new String[column]; 
			String line = br.readLine();
			//for (int i = 0; line != null; i++) {
				data = line.split(",", 0);
				line = br.readLine();
			//}
			br.close();
			// CSVから読み込んだ配列の中身を処理
			
			for(int i = 0; i < data.length; i++) {
				r[i] = Integer.parseInt(data[i]);
			} 
		} catch (IOException e) {
			System.out.println(e);
		}
		return r;
		//CSVから取り込みここまで
	}
	
	//2次元配列の取り込み
	public double[][] getCSV3(String path, int row, int column) {
		//CSVから取り込み
		double r[][] = new double[row][column];
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
				 
			String[][] data = new String[row][column]; 
			String line = br.readLine();
			for (int i = 0; line != null; i++) {
				data[i] = line.split(",", 0);
				line = br.readLine();
			}
			br.close();
			// CSVから読み込んだ配列の中身を処理
			for(int i = 0; i < data.length; i++) {
				for(int j = 0; j < data[0].length; j++) {
					r[i][j] = Double.parseDouble(data[i][j]);
				}
			} 
		} catch (IOException e) {
			System.out.println(e);
		}
		return r;
		//CSVから取り込みここまで
	}
}