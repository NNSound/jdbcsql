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
  private static int A;//存Table資料總數，因為return的東西怪怪的 所以隨便弄弄
  private static int PlatformLong[]={10,8,12,15,11,8,7,10,15,16,7};//12個月台的間距所要行駛時間
  //PlatformLong[0]= 南港~台北所要花費的時間
  private Connection con = null; //Database objects 
  //連接object 
  private Statement stat = null; 
  //執行,傳入之sql為完整字串 
  private ResultSet rs = null; 
  //結果集 
  private PreparedStatement pst = null; 
  //執行,傳入之sql為預儲之字申,需要傳入變數之位置 
  //先利用?來做標示 
  
  private static String dropdbSQL_Platform = "DROP TABLE stops ";//宣告刪除用的字串
  private static String dropdbSQL_TrainNumber = "DROP TABLE runs ";//宣告刪除用的字串
  
  private static String createdbSQL_Platform = "CREATE TABLE stops (" + //字串用來建立Table
    "  	STID    INT(255) " + //顯示在Jframe的時候
    " , nangang    	varchar(255) " + 
    " , taipei		varchar(255) " + 
    " , banqiao		varchar(255) " + 
    " , taiyuan		varchar(255) " +
    " , hsinchu		varchar(255) " + 
    " , miaoli		varchar(255) " +
    " , taichung	varchar(255) " + 
    " , changhua	varchar(255) " +
    " , yunlin		varchar(255)" +
    " , chiayi		varchar(255) " +
    " , tainan		varchar(255) " +
    " , zuoying		varchar(255) )"; //原本型態是time 可是不好輸入 很麻煩 看看是應用在哪方面 如果只是看看而已 可以換個型態
  private static String createdbSQL_TrainNumber = "CREATE TABLE runs ("+
    " RID			INT(11)"+
	",schedule		varchar(11)	"+//星期
    ",SID			INT(11)	)";//由停靠站(2進位)轉為10進位存放到id
  
  private String insertdbSQL_platform = "INSERT INTO `stops` (`STID`, `nangang`, `taipei`, `banqiao`, `taiyuan`, `hsinchu`,"+
		  								" `miaoli`, `taichung`, `changhua`, `yunlin`, `chiayi`, `tainan`, `zuoying`)"+
		  								" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //新增列車應該要手動輸入資料 
  
  private String insertdbSQL_trainnumber = "INSERT INTO `runs` (`RID`, `schedule`, `SID`) SELECT IFNULL(MAX(`RID`),0)+1,123,? FROM `runs`";//流水號,行駛星期,停靠站別
  
  private String selectSQL_trainnumber = "select * from runs "; //顯示table
  
  public C() //連到資料庫 之後做成一個副程式 登入後檢查帳密是否正確 應該要有帳號密碼
  { 
    try { 
      Class.forName("com.mysql.jdbc.Driver"); 
      //註冊driver 
      con = DriverManager.getConnection( 
      "jdbc:mysql://localhost/105db_thsr_main?useUnicode=true&characterEncoding=Big5", 
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
  }//C end
  
  //建立table的方式 ----------------------------------------------
  //可以看看Statement的使用方式 
  public void createTable(String SQLStatement)//輸入建立Table的字串 
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
  }//createTable end-----------------------------------------------
  
  //新增資料 -------------------------------------------------------
  //可以看看PrepareStatement的使用方式 
  public void insertTable_trinnumber(int id) //從這邊新增資料進去 迴圈放在main 這裡專門用來insert trinnumber這個Table
  { 
    try 
    { 
      pst = con.prepareStatement(insertdbSQL_trainnumber); //流水號,行駛星期0~63,停靠站別0~4095
      //pst.setInt(1, date); //日期  要橋一下
      pst.setInt(1, id);//id是停靠站 代表這班列車分別停了哪些站      
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
  } //insertTable_trinnumber END------------------------------------
  public void insertTable_Platform(int id,int time) //從這邊新增資料進去 迴圈放在main 這裡專門用來insert Platform這個Table
  { //輸入哪些站有停還有發車時間 time=從午夜0點起算的分鐘數
    try 
    { 
      pst = con.prepareStatement(insertdbSQL_platform); //流水號,行駛星期0~63,停靠站別0~4095
      String A=tenToTwo(id);
      int lastStation=0;//先看發車地點 再看順逆**還沒看順逆
      for(int i=0;i<12;i++){
    	  if(A.substring(i, i+1).equals("1")){
    		  lastStation=i;//表示發車(或尾站)地點為第i站
    		  break;
    	  }
      }      
      pst.setInt(1, id);//id是停靠站 代表這班列車分別停了哪些站       
      for(int i=0;i<12;i++){//填12個月台的到站時間
    	  if(A.substring(i, i+1).equals("1")){//當列車有停本站時，填寫到達時間
    		  for(int j=lastStation;j<i;j++)//到達時間為  <列車抵達上一站的時間>+<從上一站到本站所需要時間>
    			  time=time+PlatformLong[j];
    		  pst.setString(i+2, Integer.toString(time));
    		  lastStation=i;
    	  }
    	  else{
    		  pst.setString(i+2, "00:00");
    	  }
      }
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
  
  //刪除Table-------------------------------------------------------- 
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
  }//dropTable---------------------------------------------------------
  
  //查詢資料----------------------------------------------------------- 
  //可以看看回傳結果集及取得資料方式 
  public String [][] SelectTable(String arr[][],int pageNumber)//顯示 Table runs
  { 
    try 
    { 
      int i=0;
      stat = con.createStatement(); 
      rs = stat.executeQuery("SELECT * FROM `runs` WHERE `RID`>"+10*(pageNumber-1)+" AND `RID`<="+pageNumber*10+" "); //本程式一次只顯示10筆資料 所以有WHERE的條件
      while(rs.next()) //逐行取得資料
      {   //Integer.toString(rs.getInt("RID"));//原本的拿法是這個 所以拿的型態是INT
    	  arr[i][0]=rs.getString("RID"); //將資料視為String 拿下來 因為我沒有要運算只是要顯示
    	  arr[i][1]=rs.getString("schedule");
    	  arr[i][2]=rs.getString("SID");
    	  i++;    	 
    	  /**小備註 getShort好像不能拿英文字母**/
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
  }//--------------------------------------------------------------------
  
  
  
  public int rownum()//顯示 Table runs
  { 
  	try 
    {       
      stat = con.createStatement(); 
      //rs = stat.executeQuery("Select Count(`RID`) FROM runs"); //本程式一次只顯示10筆資料 所以有WHERE的條件
      rs =  stat.executeQuery("Select  Count(*) FROM runs");
      rs.next();
      rs.getInt(1);     
      A=rs.getInt(1);
     
    } 
    catch(SQLException e) 
    { 
      System.out.println("SelectDB Exception :" + e.toString()); //取得資料錯誤 方便Debug
    } 
    finally 
    { 
      Close(); //清空字串 為副程式
    }
	 return A;//回傳資料
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
  public static int  TwotoTen(int input){
	  int A=0;
	  A=2*(input+A);
	  return A;
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
    	if(i<20){
    		test.insertTable_trinnumber(3617);
    		test.insertTable_Platform(3617, 10);
    	}
    	else if(i<40){
    		test.insertTable_trinnumber(3619);
    		test.insertTable_Platform(3619, 10);
    	}
    	else if(i<60){
    		test.insertTable_trinnumber(3647);
    		test.insertTable_Platform(3647, 10);
    	}
    	else if(i<80){
    		test.insertTable_trinnumber(4064);
    		test.insertTable_Platform(4064, 10);
    	}
    	else if(i<100){
    		test.insertTable_trinnumber(4007);
    		test.insertTable_Platform(4007, 10);
    	}
    	else{
    		test.insertTable_trinnumber(7409);
    		test.insertTable_Platform(7409, 10);
    	}
    }
  } 
}