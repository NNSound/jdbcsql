package FinallyProgage;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.PreparedStatement; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
 /**
  * �\�����ӭn���n�J�n�X�B�s�W�C����T(�]�A�쯸�ɶ�)�B��ܯZ����T(�C�@�Z�C�������ۤv���s���A�N��C�����a�����@��)
  * 
  * ���aid �W�h
  * 1�N����,0�N��S��
  * �`�@�|��12bit,ex:001100111100
  * �A�N�L�q2�i���ର10�i�� ��찱�aid
  * �̤j�Ȭ�2��12����-1
  * 
  * ���table <Platform> <TrainNumber>
  * 
  * <TrainNumber>�u�������H���ݨ�
  * <Platform>�@���U�ȻP�������u����ݨ�
  * 
  * ���᪺�u�@ 
  * �s��&��p��W�h
  * insert <Platform>
  * 4�Ӥp�����ӧ˱o��
  */
public class C { 
  private static int A;//�sTable����`�ơA�]��return���F��ǩǪ� �ҥH�H�K�˧�
  private static int PlatformLong[]={10,8,12,15,11,8,7,10,15,16,7};//12�Ӥ�x�����Z�ҭn��p�ɶ�
  //PlatformLong[0]= �n��~�x�_�ҭn��O���ɶ�
  private Connection con = null; //Database objects 
  //�s��object 
  private Statement stat = null; 
  //����,�ǤJ��sql������r�� 
  private ResultSet rs = null; 
  //���G�� 
  private PreparedStatement pst = null; 
  //����,�ǤJ��sql���w�x���r��,�ݭn�ǤJ�ܼƤ���m 
  //���Q��?�Ӱ��Х� 
  
  private static String dropdbSQL_Platform = "DROP TABLE stops ";//�ŧi�R���Ϊ��r��
  private static String dropdbSQL_TrainNumber = "DROP TABLE runs ";//�ŧi�R���Ϊ��r��
  
  private static String createdbSQL_Platform = "CREATE TABLE stops (" + //�r��Ψӫإ�Table
    "  	STID    INT(255) " + //��ܦbJframe���ɭ�
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
    " , zuoying		varchar(255) )"; //�쥻���A�Otime �i�O���n��J �ܳ·� �ݬݬO���Φb���譱 �p�G�u�O�ݬݦӤw �i�H���ӫ��A
  private static String createdbSQL_TrainNumber = "CREATE TABLE runs ("+
    " RID			INT(11)"+
	",schedule		varchar(11)	"+//�P��
    ",SID			INT(11)	)";//�Ѱ��a��(2�i��)�ର10�i��s���id
  
  private String insertdbSQL_platform = "INSERT INTO `stops` (`STID`, `nangang`, `taipei`, `banqiao`, `taiyuan`, `hsinchu`,"+
		  								" `miaoli`, `taichung`, `changhua`, `yunlin`, `chiayi`, `tainan`, `zuoying`)"+
		  								" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)"; //�s�W�C�����ӭn��ʿ�J��� 
  
  private String insertdbSQL_trainnumber = "INSERT INTO `runs` (`RID`, `schedule`, `SID`) SELECT IFNULL(MAX(`RID`),0)+1,123,? FROM `runs`";//�y����,��p�P��,���a���O
  
  private String selectSQL_trainnumber = "select * from runs "; //���table
  
  public C() //�s���Ʈw ���ᰵ���@�ӰƵ{�� �n�J���ˬd�b�K�O�_���T ���ӭn���b���K�X
  { 
    try { 
      Class.forName("com.mysql.jdbc.Driver"); 
      //���Udriver 
      con = DriverManager.getConnection( 
      "jdbc:mysql://localhost/105db_thsr_main?useUnicode=true&characterEncoding=Big5", 
      "root",""); //�b���K�X���ӷ|��table���������ڳB�z�v����ԣ��
      //���oconnection
 
//jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
//localhost�O�D���W,test�Odatabase�W
//useUnicode=true&characterEncoding=Big5�ϥΪ��s�X 
      
    } 
    catch(ClassNotFoundException e) 
    { 
      System.out.println("DriverClassNotFound :"+e.toString()); 
    }//���i��|����sqlexception 
    catch(SQLException x) { 
      System.out.println("Exception :"+x.toString()); 
    } 
  }//C end
  
  //�إ�table���覡 ----------------------------------------------
  //�i�H�ݬ�Statement���ϥΤ覡 
  public void createTable(String SQLStatement)//��J�إ�Table���r�� 
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(SQLStatement); //�W��SQL���O
    } 
    catch(SQLException e) 
    { 
      System.out.println("CreateDB Exception :" + e.toString()); //�Y�X�{���~�|��ܦb�D���x
    } 
    finally 
    { 
      Close(); //�M�Ŧr�굥 �o��O�@�ӰƵ{��
    } 
  }//createTable end-----------------------------------------------
  
  //�s�W��� -------------------------------------------------------
  //�i�H�ݬ�PrepareStatement���ϥΤ覡 
  public void insertTable_trinnumber(int id) //�q�o��s�W��ƶi�h �j���bmain �o�̱M���Ψ�insert trinnumber�o��Table
  { 
    try 
    { 
      pst = con.prepareStatement(insertdbSQL_trainnumber); //�y����,��p�P��0~63,���a���O0~4095
      //pst.setInt(1, date); //���  �n���@�U
      pst.setInt(1, id);//id�O���a�� �N��o�Z�C�����O���F���ǯ�      
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
  public void insertTable_Platform(int id,int time) //�q�o��s�W��ƶi�h �j���bmain �o�̱M���Ψ�insert Platform�o��Table
  { //��J���ǯ������٦��o���ɶ� time=�q�ȩ]0�I�_�⪺������
    try 
    { 
      pst = con.prepareStatement(insertdbSQL_platform); //�y����,��p�P��0~63,���a���O0~4095
      String A=tenToTwo(id);
      int lastStation=0;//���ݵo���a�I �A�ݶ��f**�٨S�ݶ��f
      for(int i=0;i<12;i++){
    	  if(A.substring(i, i+1).equals("1")){
    		  lastStation=i;//��ܵo��(�Χ���)�a�I����i��
    		  break;
    	  }
      }      
      pst.setInt(1, id);//id�O���a�� �N��o�Z�C�����O���F���ǯ�       
      for(int i=0;i<12;i++){//��12�Ӥ�x���쯸�ɶ�
    	  if(A.substring(i, i+1).equals("1")){//��C�����������ɡA��g��F�ɶ�
    		  for(int j=lastStation;j<i;j++)//��F�ɶ���  <�C����F�W�@�����ɶ�>+<�q�W�@���쥻���һݭn�ɶ�>
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
  
  //�R��Table-------------------------------------------------------- 
  //��إ�table�ܹ� 
  public void dropTable(String SQLStatement) //�P���e���� ���A�حz
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
  
  //�d�߸��----------------------------------------------------------- 
  //�i�H�ݬݦ^�ǵ��G���Ψ��o��Ƥ覡 
  public String [][] SelectTable(String arr[][],int pageNumber)//��� Table runs
  { 
    try 
    { 
      int i=0;
      stat = con.createStatement(); 
      rs = stat.executeQuery("SELECT * FROM `runs` WHERE `RID`>"+10*(pageNumber-1)+" AND `RID`<="+pageNumber*10+" "); //���{���@���u���10����� �ҥH��WHERE������
      while(rs.next()) //�v����o���
      {   //Integer.toString(rs.getInt("RID"));//�쥻�����k�O�o�� �ҥH�������A�OINT
    	  arr[i][0]=rs.getString("RID"); //�N��Ƶ���String ���U�� �]���ڨS���n�B��u�O�n���
    	  arr[i][1]=rs.getString("schedule");
    	  arr[i][2]=rs.getString("SID");
    	  i++;    	 
    	  /**�p�Ƶ� getShort�n�����ள�^��r��**/
      }      
    } 
    catch(SQLException e) 
    { 
      System.out.println("SelectDB Exception :" + e.toString()); //���o��ƿ��~ ��KDebug
    } 
    finally 
    { 
      Close(); //�M�Ŧr�� ���Ƶ{��
    }
	return arr; //�^�Ǹ��
  }//--------------------------------------------------------------------
  
  
  
  public int rownum()//��� Table runs
  { 
  	try 
    {       
      stat = con.createStatement(); 
      //rs = stat.executeQuery("Select Count(`RID`) FROM runs"); //���{���@���u���10����� �ҥH��WHERE������
      rs =  stat.executeQuery("Select  Count(*) FROM runs");
      rs.next();
      rs.getInt(1);     
      A=rs.getInt(1);
     
    } 
    catch(SQLException e) 
    { 
      System.out.println("SelectDB Exception :" + e.toString()); //���o��ƿ��~ ��KDebug
    } 
    finally 
    { 
      Close(); //�M�Ŧr�� ���Ƶ{��
    }
	 return A;//�^�Ǹ��
  }
  //����ϥΧ���Ʈw��,�O�o�n�����Ҧ�Object 
  //�_�h�b����Timeout��,�i��|��Connection poor�����p 
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
  
  public static String tenToTwo(int input) { //10�i����2�i��
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
    //���ݬݬO�_���` 
    C test = new C(); 
    //CJframe_test frm = new CJframe_test();
    //frm.setVisible(true);//�I�s����
    
    test.dropTable(dropdbSQL_Platform); 
    test.dropTable(dropdbSQL_TrainNumber); 

    test.createTable(createdbSQL_Platform); 
    test.createTable(createdbSQL_TrainNumber); 
    
    for(int i=0;i<120;i++){//120�Z��
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