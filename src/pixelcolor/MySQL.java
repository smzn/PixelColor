package pixelcolor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	String driver;// JDBCドライバの登録
    String server, dbname, url, user, password;// データベースの指定
    Connection con;
    Statement stmt;
    ResultSet rs;
 
	public MySQL() {
		this.driver = "org.gjt.mm.mysql.Driver";
		this.server = "mznsada.mizunolab.info";
		this.dbname = "mznsada";
		this.url = "jdbc:mysql://" + server + "/" + dbname + "?useUnicode=true&characterEncoding=UTF-8";
		this.user = "mznsada";
		this.password = "kanso";
		try {
			this.con = DriverManager.getConnection(url, user, password);
			this.stmt = con.createStatement ();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//文字列を挿入時にはシングルクオートが必要
	public void insertTeacher(double value[], String name, String path){
		System.out.println("教師CSV : Insert開始");
        try{
        	StringBuffer buf = new StringBuffer();
			buf.append("INSERT INTO teachers(name, path, white,olive,yellow,fuchsia,silver,aqua,lime,red,gray,blue,green,purple,black,navy,teal,maroon,madder,dawn) VALUES");
            buf.append("('"+name+"','"+path+"',"+value[0]+","+value[1]+","+value[2]+","+value[3]+","+value[4]+","+value[5]+","+value[6]+","+value[7]+","+value[8]+","+value[9]+","+value[10]+","+value[11]+","+value[12]+","+value[13]+","+value[14]+","+value[15]+","+value[16]+","+value[17]+")");
            
        	String sql = buf.toString();
            stmt.execute (sql);
        }
        catch (SQLException e) {
                e.printStackTrace();
        }
        System.out.println("教師CSV : Insert完了");
	}
	
	public void insertTest(String name, String picture_date, String path, int teacher_id, double value) {
		System.out.println("cos類似度 : Insert開始");
        try{
        	StringBuffer buf = new StringBuffer();
			buf.append("INSERT INTO tests(name, date_id, path, teacher_id, value) VALUES");
            buf.append("('"+name+"','"+picture_date+"','"+path+"',"+teacher_id+","+value+")");
            
        	String sql = buf.toString();
            stmt.execute (sql);
        }
        catch (SQLException e) {
                e.printStackTrace();
        }
        System.out.println("cos類似度 : Insert完了");
	}
}
