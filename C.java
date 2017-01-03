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
	
  private Connection con = null; //Database objects 
  //�s��object 
  private Statement stat = null; 
  //����,�ǤJ��sql������r�� 
  private ResultSet rs = null; 
  //���G�� 
  private PreparedStatement pst = null; 
  //����,�ǤJ��sql���w�x���r��,�ݭn�ǤJ�ܼƤ���m 
  //���Q��?�Ӱ��Х� 
  
  private static String dropdbSQL_Platform = "DROP TABLE Platform ";
  private static String dropdbSQL_TrainNumber = "DROP TABLE TrainNumber ";
  
  private static String createdbSQL_Platform = "CREATE TABLE Platform (" + 
    "  	id     SMALLINT(6) " + //��ܦbJframe���ɭ�
    " , �n��    	SMALLINT(2) " + 
    " , �x�_		SMALLINT(2) " + 
    " , �O��		SMALLINT(2) " + 
    " , ���		SMALLINT(2) " +
    " , �s��		SMALLINT(2) " + 
    " , �]��		SMALLINT(2) " +
    " , �x��		SMALLINT(2) " + 
    " , ����		SMALLINT(2) " +
    " , ���L		SMALLINT(2) " +
    " , �Ÿq		SMALLINT(2) " +
    " , �x�n		SMALLINT(2) " +
    " , ����		SMALLINT(2) )"; //�쥻���A�Otime �i�O���n��J �ܳ·� �ݬݬO���Φb���譱 �p�G�u�O�ݬݦӤw �i�H���ӫ��A
  private static String createdbSQL_TrainNumber = "CREATE TABLE TrainNumber ("+
    " Number	SMALLINT(4)"+
	",date		SMALLINT(6)"+//�P��
    ",id		SMALLINT(6))";
  
  private String insertdbSQL_platform = "INSERT INTO `platform` (`id`, `�n��`, `�x�_`, `�O��`, `���`, `�s��`, `�]��`, `�x��`, `����`, `���L`, `�Ÿq`, `�x�n`, `����`)"+
  " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"; //�s�W�C�����ӭn��ʿ�J��� �N��
  
  private String insertdbSQL_trainnumber = "INSERT INTO `trainnumber` (`Number`, `date`, `id`) VALUES (ifNULL(max(Number),0)+1, '?', '?');";//�y����,��p�P��,���a���O
  
  private String selectSQL_trainnumber = "select * from trainnumber "; 
  
  public C() 
  { 
    try { 
      Class.forName("com.mysql.jdbc.Driver"); 
      //���Udriver 
      con = DriverManager.getConnection( 
      "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5", 
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
  } 
  //�إ�table���覡 
  //�i�H�ݬ�Statement���ϥΤ覡 
  public void createTable(String A) 
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(A); 
    } 
    catch(SQLException e) 
    { 
      System.out.println("CreateDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    } 
  } 
  //�s�W��� 
  //�i�H�ݬ�PrepareStatement���ϥΤ覡 
  public void insertTable_trinnumber(int date,int id) //�q�o��s�W��ƶi�h �j���main
  { 
    try 
    { //INSERT INTO `trainnumber` (`Number`, `date`, `id`) SELECT IFNULL(MAX(`Number`),0)+1,10,10 FROM trainnumber
      //pst = con.prepareStatement("INSERT INTO `trainnumber` (`Number`, `date`, `id`) VALUES (?, ?, ?)"); //�y����,��p�P��0~63,���a���O0~4095
      pst = con.prepareStatement("INSERT INTO `trainnumber` (`Number`, `date`, `id`) SELECT IFNULL(MAX(`Number`),0)+1,?,? FROM trainnumber"); //�y����,��p�P��0~63,���a���O0~4095

      //pst.setInt(1, num);
      pst.setInt(1, date); //��� �H���U
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
  //�R��Table, 
  //��إ�table�ܹ� 
  public void dropTable(String A) 
  { 
    try 
    { 
      stat = con.createStatement(); 
      stat.executeUpdate(A); 
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
  //�d�߸�� 
  //�i�H�ݬݦ^�ǵ��G���Ψ��o��Ƥ覡 
  public String [][] SelectTable(String arr[][],int pageNumber) 
  { 
    try 
    { 
      int i=0;
      stat = con.createStatement(); 
      rs = stat.executeQuery("SELECT * FROM `trainnumber` WHERE `Number`>"+10*(pageNumber-1)+" AND `Number`<="+pageNumber*10+""); 
      
      //System.out.println("ID\t�n��\t�x�_\t�O��\t���\t�s��\t�]��\t�x��\t����\t���L\t�Ÿq\t�x�n\t����"); 
      while(rs.next()) 
      { 
    	  //System.out.println(rs.getCharacterStream("Number")+"\t"+rs.getInt("date")+"\t"+rs.getInt("id"));
    	  //arr[i][0]=rs.getCharacterStream("Number");
    	  arr[i][0]=Integer.toString(rs.getInt("Number"));
    	  arr[i][1]=Integer.toString(rs.getInt("date"));
    	  arr[i][2]=Integer.toString(rs.getInt("id"));
    	  i++;
    	  /*
        System.out.println(rs.getInt("id")+"\t"+rs.getTime("�n��")+"\t"+rs.getTime("�x�_")+"\t"+
    	rs.getTime("�O��")+"\t"+rs.getTime("���")+"\t"+rs.getTime("�s��")+"\t"+rs.getTime("�]��")+
    	"\t"+rs.getTime("�x��")+"\t"+rs.getTime("����")+"\t"+rs.getTime("���L")+"\t"+rs.getTime("�Ÿq")+
    	"\t"+rs.getTime("�x�n")+"\t"+rs.getTime("����")); 
    	 * */
    	  //System.out.println(arr[i][0]);
      }      
    } 
    catch(SQLException e) 
    { 
      System.out.println("DropDB Exception :" + e.toString()); 
    } 
    finally 
    { 
      Close(); 
    }
	return arr; 
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