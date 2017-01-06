package FinallyProgage;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 * ���U�n�Jbtn�� ����index_panel
 * 
 * index_panel
 * �n����ݨ�Z�� 
 * �n��s�W�Z�� �s�W��reprint()�@��
 * 		�n�]�w(�Z���s��?)�B��p��B����X�� �X�I�q���o�� �����U���ɶ��|�ۤv�]�n
 * �n��R���Z���R����reprint()�@��
 * 
 * �̫�n��n�X
 * UI�|�Ӥp������OK
 * �\��3�Ӥp��
 * 
*/

public class CJframe_test extends JFrame {
	C jdbcsql =new C();
	InsertTrain  Newrow = new InsertTrain();
	
	ArrayList table = new ArrayList();
	String arr[][]=new String [10][3];
	int pageNumber=1;
	
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CJframe_test frame = new CJframe_test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	public String[][] arrClear(String arr[][]){
		for(int i=0;i<arr.length;i++){
			for(int j=0;j<arr[i].length;j++)
				arr[i][j]=null;
		}
		return arr;
	}
	/**
	 * Create the frame.
	 */
	public CJframe_test() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 295, 353);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);//�̩��U��panel
		
		//--------------------------------------------------�n�J�e��------------------------------------------------------------------
		JPanel login_panel = new JPanel();//�n�J�e��
		login_panel.setBounds(0, 0, 289, 324);
		contentPane.add(login_panel);
		
		textField = new JTextField();
		textField.setBounds(51, 107, 200, 21);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(51, 138, 197, 21);
		
		JLabel labAccount = new JLabel("\u5E33\u865F");
		labAccount.setBounds(10, 110, 46, 15);
		
		JLabel labPassword = new JLabel("\u5BC6\u78BC");
		labPassword.setBounds(10, 141, 46, 15);
		JButton btnLogin = new JButton("\u767B\u5165");
		btnLogin.setBounds(90, 170, 87, 23);		
		
		login_panel.setLayout(null);
		login_panel.add(textField);
		login_panel.add(passwordField);
		login_panel.add(labAccount);
		login_panel.add(labPassword);
		login_panel.add(btnLogin);
		//-------------------------------------------------------�C����T------------------------------------------------------------
		JPanel index_panel = new JPanel();//�C����ܵe��
		index_panel.setBounds(0, 0, 289, 324);
		//contentPane.add(index_panel);//�����Ubtn�~ADD,����n�R��
		index_panel.setLayout(null);		
		
		JButton btnNew = new JButton("\u65B0\u589E");//�s�W		
		btnNew.setBounds(10, 10, 70, 23);
		index_panel.add(btnNew);
		
		JButton btnRewrite = new JButton("\u4FEE\u6539");
		btnRewrite.setBounds(83, 10, 70, 23);
		index_panel.add(btnRewrite);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 40, 250, 231);		
		index_panel.add(textArea);
		textArea.setText("Number\tdate\tid");//�N��Ƴ��নString ���ʺA�}�C���� textAraysetText()
		
		JButton btnLogout = new JButton("\u767B\u51FA");
		btnLogout.setBounds(190, 10, 70, 23);
		index_panel.add(btnLogout);
		
		JButton btnLastpage = new JButton("\u4E0A\u4E00\u9801");
		btnLastpage.setBounds(10, 281, 87, 23);
		index_panel.add(btnLastpage);
		
		JButton btnNextpage = new JButton("\u4E0B\u4E00\u9801");
		btnNextpage.setBounds(173, 281, 87, 23);
		index_panel.add(btnNextpage);
		
		JLabel labPagenumber = new JLabel("��"+Integer.toString(pageNumber)+"��");
		labPagenumber.setBounds(119, 285, 44, 15);
		index_panel.add(labPagenumber);
		
		jdbcsql.SelectTable(arr,pageNumber);
		for(int i=pageNumber-1;i<10*pageNumber;i++){
			textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
		}
		//----------------------------------------------------------------------------------------------------------
		
		btnLogin.addActionListener(new ActionListener() {	//�n�J���s		
			public void actionPerformed(ActionEvent arg0) {
				contentPane.remove(login_panel);
				contentPane.add(index_panel);
				contentPane.revalidate();//�n�J���\���ഫpanel
				contentPane.repaint();// �ϥλ��� http://mqjing.blogspot.tw/2008/01/java-revalidate-repaint.html
			}
		});
		btnNew.addActionListener(new ActionListener() {//�s�W
			public void actionPerformed(ActionEvent arg0) {
				//jdbcsql.insertTable_trinnumber(21, 7000);//�i�H��
				Newrow.setVisible(true);
			}
		});
		btnLogout.addActionListener(new ActionListener() {//�n�X
			public void actionPerformed(ActionEvent arg0) {
				contentPane.remove(index_panel);
				contentPane.add(login_panel);
				pageNumber=1;
				textArea.setText("Number\tdate\tid");
				arrClear(arr);
				jdbcsql.SelectTable(arr, pageNumber);
				for(int i=0;i<10;i++){					
					textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
				}
				labPagenumber.setText("��"+Integer.toString(pageNumber)+"��");
				contentPane.revalidate();//�n�X���\���ഫpanel
				contentPane.repaint();
				
			}
		});
		btnNextpage.addActionListener(new ActionListener() {//�U�@��
			public void actionPerformed(ActionEvent arg0) {
				if(pageNumber<14){
					pageNumber++;
					arrClear(arr);
					jdbcsql.SelectTable(arr, pageNumber);
					textArea.setText("Number\tdate\tid");
					for(int i=0;i<10;i++){					
						textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
					}
					labPagenumber.setText("��"+Integer.toString(pageNumber)+"��");
				}
			}
		});
		btnLastpage.addActionListener(new ActionListener() {//�W�@��
			public void actionPerformed(ActionEvent arg0) {
				if(pageNumber>1){
					pageNumber--;
					arrClear(arr);
					jdbcsql.SelectTable(arr, pageNumber);
					textArea.setText("Number\tdate\tid");
					for(int i=0;i<10;i++){					
						textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
					}
					labPagenumber.setText("��"+Integer.toString(pageNumber)+"��");
				}				
			}
		});
	}
}
