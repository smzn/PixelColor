package pixelcolor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class PixelColor_main {

	public static void main(String[] args) {
		String filename;
		//教師データの作成(011,012,013はダメデータ、6/11,7/1,6/20)
		PixelColor_lib tlib = new PixelColor_lib();
		tlib.createTeacher(13);
		
		//教師データの作成
		/*
		for(int i = 0; i < 10; i++) {
			filename = "teacher"+String.format("%03d", i+1);
			PixelColor_lib plib = new PixelColor_lib("image/"+filename+".jpg");
	        int [] color_dist = plib.getColor();
	        Graph graph = new Graph(color_dist);
	        graph.setBounds(5,5,755,455);
			graph.setVisible(true);
			plib.exportCsv(filename, color_dist);
		}
		*/
		/*
		//教師データCSV取り込み
		int [][]teacher = new int[10][18];
		double [][]teacher_normal = new double[10][18];
		for(int i = 0; i < 10; i++) {
			filename = "teacher"+String.format("%03d", i+1);
			PixelColor_main pmain = new PixelColor_main();
			teacher[i] = pmain.getCSV2("csv/"+filename+".csv", 18);
		}
		//教師データを100%に直す
		for(int i = 0; i < teacher.length;i++) {
			double sum = 0;
			for(int j = 0; j < teacher[i].length; j++) {
				sum += teacher[i][j];
			}
			for(int j = 0; j < teacher[i].length; j++) {
				teacher_normal[i][j] = teacher[i][j] * 100 / sum;
			}
			
			//教師データをDBにアップロード(必要な時に有効にする)
			MySQL mysql = new MySQL();
			mysql.insertTeacher(teacher_normal[i], "teacher"+String.format("%03d", i+1)+".jpg","/img/teacher/teacher"+String.format("%03d", i+1)+".jpg");//連番に注意
		}
		System.out.println("teacher_data : "+Arrays.deepToString(teacher_normal));
		*/
		/*
		//テストデータの作成とcos類似度
		filename = "2019_06_13/test20190613_";
		String outputname = "2019_06_13/test20190613";
		int row = 369;
		double [][] cos = new double[row][18];
		PixelColor_lib plib = null;
		for(int i = 1; i <= row; i++) {
			//filename = filename+i;
			plib = new PixelColor_lib("test/"+filename+i+".jpg");
	        int [] color_dist = plib.getColor();
	        cos[i-1] = plib.getCos(teacher_normal, color_dist);
	        Graph graph = new Graph(color_dist);
	        graph.setBounds(5,5,755,455);
			graph.setVisible(true);
		}
		plib.exportCsv2(outputname, cos);
		*/
	}
	
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
}