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
 * 按下登入btn後 跳到index_panel
 * 
 * index_panel
 * 要先能看到班次 
 * 要能新增班次 新增後reprint()一次
 * 		要設定(班次編號?)、行駛日、到哪幾站 幾點從哪發車 之後到各站時間會自己跑好
 * 要能刪除班次刪除後reprint()一次
 * 
 * 最後要能登出
 * UI四個小時應該OK
 * 功能3個小時
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
		contentPane.setLayout(null);//最底下的panel
		
		//--------------------------------------------------登入畫面------------------------------------------------------------------
		JPanel login_panel = new JPanel();//登入畫面
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
		//-------------------------------------------------------列車資訊------------------------------------------------------------
		JPanel index_panel = new JPanel();//列車顯示畫面
		index_panel.setBounds(0, 0, 289, 324);
		//contentPane.add(index_panel);//等按下btn才ADD,之後要刪掉
		index_panel.setLayout(null);		
		
		JButton btnNew = new JButton("\u65B0\u589E");//新增		
		btnNew.setBounds(10, 10, 70, 23);
		index_panel.add(btnNew);
		
		JButton btnRewrite = new JButton("\u4FEE\u6539");
		btnRewrite.setBounds(83, 10, 70, 23);
		index_panel.add(btnRewrite);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 40, 250, 231);		
		index_panel.add(textArea);
		textArea.setText("Number\tdate\tid");//將資料都轉成String 放到動態陣列之後 textAraysetText()
		
		JButton btnLogout = new JButton("\u767B\u51FA");
		btnLogout.setBounds(190, 10, 70, 23);
		index_panel.add(btnLogout);
		
		JButton btnLastpage = new JButton("\u4E0A\u4E00\u9801");
		btnLastpage.setBounds(10, 281, 87, 23);
		index_panel.add(btnLastpage);
		
		JButton btnNextpage = new JButton("\u4E0B\u4E00\u9801");
		btnNextpage.setBounds(173, 281, 87, 23);
		index_panel.add(btnNextpage);
		
		JLabel labPagenumber = new JLabel("第"+Integer.toString(pageNumber)+"頁");
		labPagenumber.setBounds(119, 285, 44, 15);
		index_panel.add(labPagenumber);
		
		jdbcsql.SelectTable(arr,pageNumber);
		for(int i=pageNumber-1;i<10*pageNumber;i++){
			textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
		}
		//----------------------------------------------------------------------------------------------------------
		
		btnLogin.addActionListener(new ActionListener() {	//登入按鈕		
			public void actionPerformed(ActionEvent arg0) {
				contentPane.remove(login_panel);
				contentPane.add(index_panel);
				contentPane.revalidate();//登入成功後轉換panel
				contentPane.repaint();// 使用說明 http://mqjing.blogspot.tw/2008/01/java-revalidate-repaint.html
			}
		});
		btnNew.addActionListener(new ActionListener() {//新增
			public void actionPerformed(ActionEvent arg0) {
				//jdbcsql.insertTable_trinnumber(21, 7000);//可以用
				Newrow.setVisible(true);
			}
		});
		btnLogout.addActionListener(new ActionListener() {//登出
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
				labPagenumber.setText("第"+Integer.toString(pageNumber)+"頁");
				contentPane.revalidate();//登出成功後轉換panel
				contentPane.repaint();
				
			}
		});
		btnNextpage.addActionListener(new ActionListener() {//下一頁
			public void actionPerformed(ActionEvent arg0) {
				if(pageNumber<14){
					pageNumber++;
					arrClear(arr);
					jdbcsql.SelectTable(arr, pageNumber);
					textArea.setText("Number\tdate\tid");
					for(int i=0;i<10;i++){					
						textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
					}
					labPagenumber.setText("第"+Integer.toString(pageNumber)+"頁");
				}
			}
		});
		btnLastpage.addActionListener(new ActionListener() {//上一頁
			public void actionPerformed(ActionEvent arg0) {
				if(pageNumber>1){
					pageNumber--;
					arrClear(arr);
					jdbcsql.SelectTable(arr, pageNumber);
					textArea.setText("Number\tdate\tid");
					for(int i=0;i<10;i++){					
						textArea.append("\n"+arr[i][0]+"\t"+arr[i][1]+"\t"+arr[i][2]);
					}
					labPagenumber.setText("第"+Integer.toString(pageNumber)+"頁");
				}				
			}
		});
	}
}
