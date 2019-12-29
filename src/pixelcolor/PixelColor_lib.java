package pixelcolor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class PixelColor_lib {
	//色の基本16色 https://note.cman.jp/color/base_color.cgi
	//茜色(夕焼け)、曙色(朝焼け)を追加
	private String[] color_name = {"white","olive","yellow","fuchsia","silver","aqua","lime","red","gray","blue","green","purple","black","navy","teal","maroon","madder","dawn"};
	private int[][] color_value = {
			{255,255,255}, //white
			{128,128,0}, //olive
			{255,255,0}, //yellow
			{255,0,255}, //fuchsia
			{192,192,192}, //silver
			{0,255,255}, //aqua
			{0,255,0}, //lime
			{255,0,0}, //red
			{128,128,128}, //gray
			{0,0,255}, //blue
			{0,128,0}, //green
			{128,0,128}, //purple
			{0,0,0}, //black
			{0,0,128}, //navy
			{0,128,128}, //teal
			{128,0,0}, //maroon
			{183,40,46}, //madder
			{241,144,114} //dawn
	};
	private String filename;
	File f;
	private BufferedImage read;
	private Color color;
	private int w,h;
	
	public PixelColor_lib(String filename) {
		this.filename = filename;
		this.f = new File(filename);
		try {
			read = ImageIO.read(f);
			w = read.getWidth();
			h=read.getHeight();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PixelColor_lib() {
		// TODO Auto-generated constructor stub
	}

	public int[] getColor() {
		int [] color_dist = new int[18];
		int count = 0;
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				count ++;
				color = new Color(read.getRGB(i, j));
				int r = color.getRed();
        		int g = color.getGreen();
        		int b = color.getBlue();
				double distance = 10000000;
				int index = -1;
				for(int k = 0; k < color_value.length; k++) {
					double tmp = Math.pow(r - color_value[k][0], 2) + Math.pow(g - color_value[k][1], 2) + Math.pow(b - color_value[k][2], 2);
					if(tmp < distance) {
						index = k;
						distance = tmp;
					}
				}
				color_dist[index]++;
				System.out.println("Color = "+color_name[index]);
			}
		}
		//100%に変換
		//for(int i = 0; i < color_dist.length; i++) {
		//	color_dist[i] = (color_dist[i] / count) * 100;
		//}
		return color_dist;
	}
	
	public void exportCsv(String filename, int [] color_dist){
		try {
			FileWriter f = new FileWriter("csv/"+filename+".csv", false);
			PrintWriter p = new PrintWriter(new BufferedWriter(f));
			for(int i = 0; i < color_dist.length; i++) {
				if(i == color_dist.length -1) p.print(color_dist[i]);
				else {
					p.print(color_dist[i]);
					p.print(",");	
				}
			}
			p.close();
            System.out.println("ファイル出力完了！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void exportCsv2(String filename, double [][] cos){
		try {
			FileWriter f = new FileWriter("test/"+filename+".csv", false);
			PrintWriter p = new PrintWriter(new BufferedWriter(f));
			for(int i = 0; i < cos.length; i++) {
				for(int j = 0; j < cos[i].length; j++) {
					if(j == cos[i].length -1) {
						p.print(cos[i][j]);
						p.println("");
					}
					else {
						p.print(cos[i][j]);
						p.print(",");
					}
				}
			}
			p.close();
            System.out.println("ファイル出力完了！");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double[] getCos(double [][]teacher, double []test){
		double [] cos = new double[teacher.length];
		double test_scalar = 0;
		//testのスカラーを求める
		for(int i = 0; i < test.length; i++) test_scalar += Math.pow(test[i],2);
		test_scalar = Math.sqrt(test_scalar);
		for(int i = 0; i < teacher.length; i++) {
			double teacher_scalar = 0;
			//teacherのスカラーを求める
			for(int j = 0; j < teacher[i].length; j++) {
				teacher_scalar += Math.pow(teacher[i][j],2);
			}
			teacher_scalar = Math.sqrt(teacher_scalar);
			double inner_product = 0;
			//内積を求める
			for(int j = 0; j < test.length; j++) inner_product += teacher[i][j] * test[j];
			//cosを求める
			cos[i] = inner_product / (teacher_scalar * test_scalar);
		}
		return cos;
	}
	
	public void createTeacher(int teacher_number) {
		for(int i = 0; i < teacher_number; i++) {
			filename = "teacher"+String.format("%03d", i+1);
			PixelColor_lib plib = new PixelColor_lib("image/"+filename+".jpg");
	        int [] color_dist = plib.getColor();
	        Graph graph = new Graph(color_dist);
	        graph.setBounds(5,5,755,455);
			graph.setVisible(true);
			plib.exportCsv(filename, color_dist);
		}
		//教師データCSV取り込み
		int [][]teacher = new int[teacher_number][18];
		double [][]teacher_normal = new double[teacher_number][18];
		for(int i = 0; i < teacher_number; i++) {
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
	}
	
	public void getTestData(int teacher_number, int test_number, String test_filename, String outputname) {
		//教師データCSV取り込み
		int [][]teacher = new int[teacher_number][18];
		double [][]teacher_normal = new double[teacher_number][18];
		for(int i = 0; i < teacher_number; i++) {
			String filename = "teacher"+String.format("%03d", i+1);
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
		}
		//テストデータの作成とcos類似度
		//filename = "2019_11_01/test20191101_";
		//String outputname = "2019_11_01/test20191101";
		//int row = 369;
		double [][] cos = new double[test_number][18];
		PixelColor_lib plib = null;
		for(int i = 1; i <= test_number; i++) {
			//filename = filename+i;
			plib = new PixelColor_lib("test/"+test_filename+i+".jpg");
			int [] color_dist = plib.getColor();
			//テストデータを100%に直す
			double sum = 0;
			double color_dist_normal[] = new double[18];
			for(int j = 0; j < color_dist.length; j++) {
				sum += color_dist[j];
			}
			for(int j = 0; j < color_dist.length; j++) {
				color_dist_normal[j] = color_dist[j] * 100 / sum;	
			}
			cos[i-1] = plib.getCos(teacher_normal, color_dist_normal);
			Graph graph = new Graph(color_dist);
			graph.setBounds(5,5,755,455);
			graph.setVisible(true);
		}
		plib.exportCsv2(outputname, cos);
	}
}
