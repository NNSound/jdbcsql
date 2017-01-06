package FinallyProgage;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
 /**
  * 功能應該要有登入登出、新增列車資訊(包括到站時間)、顯示班次資訊(每一班列車都有自己的編號，就算列車停靠站都一樣)
  * 
  * 停靠id 規則
  * 1代表有停,0代表沒停
  * 總共會有12bit,ex:001100111100
  * 再將他從2進位轉為10進位 放到停靠id
  * 最大值為2的12次方-1
  * 
  * 兩個table <Platform> <TrainNumber>
  * 
  * <TrainNumber>只有內部人員看到
  * <Platform>一般顧客與內部員工都能看到
  * 
  * 之後的工作 
  * 編號&行駛日規則
  * insert <Platform>
  * 4個小時應該弄得完
  */
public class C { 
	
  private Connection con = null; //Database objects 
  //連接object 
  private Statement stat = null; 
  //執行,傳入之sql為完整字串 
  private ResultSet rs = null; 
  //結果集 
  private PreparedStatement pst = null; 
  //執行,傳入之sql為預儲之字申,需要傳入變數之位置 
  //先利用?來做標示 
  
  private static String dropdbSQL_Platform = "DROP TABLE Platform ";//宣告刪除用的字串
  private static String dropdbSQL_TrainNumber = "DROP TABLE TrainNumber ";//宣告刪除用的字串
  
  private static String createdbSQL_Platform = "CREATE TABLE Platform (" + //字串用來建立Table
    "  	id     SMALLINT(6) " + //顯示在Jframe的時候
    " , 南港    	SMALLINT(2) " + 
    " , 台北		SMALLINT(2) " + 
    " , 板橋		SMALLINT(2) " + 
    " , 桃園		SMALLINT(2) " +
    " , 新竹		SMALLINT(2) " + 
    " , 苗栗		SMALLINT(2) " +
    " , 台中		SMALLINT(2) " + 
    " , 彰化		SMALLINT(2) " +
    " , 雲林		SMALLINT(2) " +
    " , 嘉義		SMALLINT(2) " +
    " , 台南		SMALLINT(2) " +
    " , 左營		SMALLINT(2) )"; //原本型態是time 可是不好輸入 很麻煩 看看是應用在哪方面 如果只是看看而已 可以換個型態
  private static String createdbSQL_TrainNumber = "CREATE TABLE TrainNumber ("+
    " Number	SMALLINT(4)"+
	",date		SMALLINT(6)"+//星期
    ",id		SMALLINT(6))";//由停靠站(2進位)轉為10進位存放到id
  
  private String insertdbSQL_platform = "INSERT INTO `platform` (`id`, `南港`, `台北`, `板橋`, `桃園`, `新竹`, `苗栗`, `台中`, `彰化`, `雲林`, `嘉義`, `台南`, `左營`)"+
  " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"; //新增列車應該要手動輸入資料 
  
  private String insertdbSQL_trainnumber = "INSERT INTO `trainnumber` (`Number`, `date`, `id`) VALUES (ifNULL(max(Number),0)+1, '?', '?');";//流水號,行駛星期,停靠站別
  
  private String selectSQL_trainnumber = "select * from trainnumber "; //顯示table
  
  public C() //連到資料庫
  { 
    try { 
      Class.forName("com.mysql.jdbc.Driver"); 
      //註冊driver 
      con = DriverManager.getConnection( 
      "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5", 
      "root",""); //帳號密碼應該會有table之類的讓我處理權限還啥的
      //取得connection
 
//jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
//localhost是主機名,test是database名
//useUnicode=true&characterEncoding=Big5使用的編碼 
      
    } 
    catch(ClassNotFoundException e) 
    { 
      System.out.println("DriverClassNotFound :"+e.toString()); 
    }//有可能會產生sqlexception 
    catch(SQLException x) { 
      System.out.println("Exception :"+x.toString()); 
    } 
  }
  
  //建立table的方式 
  //可以看看Statement的使用方式 
  public void createTable(String SQLStatement) 
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(SQLStatement); //上傳SQL指令
    } 
    catch(SQLException e) 
    { 
      System.out.println("CreateDB Exception :" + e.toString()); //若出現錯誤會顯示在主控台
    } 
    finally 
    { 
      Close(); //清空字串等 這邊是一個副程式
    } 
  } 
  //新增資料 
  //可以看看PrepareStatement的使用方式 
  public void insertTable_trinnumber(int date,int id) //從這邊新增資料進去 迴圈放在main
  { 
    try 
    { 
      pst = con.prepareStatement("INSERT INTO `trainnumber` (`Number`, `date`, `id`) SELECT IFNULL(MAX(`Number`),0)+1,?,? FROM trainnumber"); //流水號,行駛星期0~63,停靠站別0~4095
      pst.setInt(1, date); //日期  隨機下
      pst.setInt(2, id);
      
      pst.executeUpdate(); 
    } 
    catch(SQLException e) 
    { 
      System.out.println("InsertDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  //刪除Table, 
  //跟建立table很像 
  public void dropTable(String SQLStatement) //同之前註解 不再贅述
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(SQLStatement); 
    } 
    catch(SQLException e) 
    { 
      System.out.println("DropDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  //查詢資料 
  //可以看看回傳結果集及取得資料方式 
  public String [][] SelectTable(String arr[][],int pageNumber) 
  { 
    try 
    { 
      int i=0;
      stat = con.createStatement(); 
      rs = stat.executeQuery("SELECT * FROM `trainnumber` WHERE `Number`>"+10*(pageNumber-1)+" AND `Number`<="+pageNumber*10+""); //本程式一次只顯示10筆資料 所以有WHERE的條件
      
      //System.out.println("ID\t南港\t台北\t板橋\t桃園\t新竹\t苗栗\t台中\t彰化\t雲林\t嘉義\t台南\t左營"); 
      while(rs.next()) //逐行取得資料
      {     	  
    	  arr[i][0]=Integer.toString(rs.getInt("Number"));//取得的資料型態為INT 為了顯示方便 轉為String
    	  arr[i][1]=Integer.toString(rs.getInt("date"));
    	  arr[i][2]=Integer.toString(rs.getInt("id"));
    	  i++;    	 
      }      
    } 
    catch(SQLException e) 
    { 
      System.out.println("SelectDB Exception :" + e.toString()); //取得資料錯誤 方便Debug
    } 
    finally 
    { 
      Close(); //清空字串 為副程式
    }
	return arr; //回傳資料
  } 
  //完整使用完資料庫後,記得要關閉所有Object 
  //否則在等待Timeout時,可能會有Connection poor的狀況 
  private void Close() 
  { 
    try 
    { 
      if(rs!=null) 
      { 
        rs.close(); 
        rs = null; 
      } 
      if(stat!=null) 
      { 
        stat.close(); 
        stat = null; 
      } 
      if(pst!=null) 
      { 
        pst.close(); 
        pst = null; 
      } 
    } 
    catch(SQLException e) 
    { 
      System.out.println("Close Exception :" + e.toString()); 
    } 
  } 
  
  public static String tenToTwo(int input) { //10進位轉2進位
	  if (input / 2 == 0) 
		  return Integer.toString(input % 2); 
	  return tenToTwo(input / 2) + (input % 2) ; 
	  }
 
  public static void main(String[] args) 
  { 
    //測看看是否正常 
    C test = new C(); 
    //CJframe_test frm = new CJframe_test();
    //frm.setVisible(true);//呼叫視窗
    
    test.dropTable(dropdbSQL_Platform); 
    test.dropTable(dropdbSQL_TrainNumber); 

    test.createTable(createdbSQL_Platform); 
    test.createTable(createdbSQL_TrainNumber); 
    
    for(int i=0;i<120;i++){//120班車
    	if(i<20)
    		test.insertTable_trinnumber(i,3617);
    	else if(i<40)
    		test.insertTable_trinnumber(i,3619);
    	else if(i<60)
    		test.insertTable_trinnumber(i,3647);
    	else if(i<80)
    		test.insertTable_trinnumber(i,4064);
    	else if(i<100)
    		test.insertTable_trinnumber(i,4007);
    	else
    		test.insertTable_trinnumber(i,7409);
    }
    
    //test.SelectTable(); 
  
  } 
}