package FinallyProgage;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
/**
 * 第二個panel要看到選擇的結果,並設定發車時間
 * @author Littlesound
 *
 */
public class InsertTrain extends JFrame {
	C jdbcsql =new C();
	//CJframe_test index =new CJframe_test();
	private JPanel contentPane;
	private final JPanel panel_index = new JPanel();
	int arrDate[]=new int[7];
	int arrPlatfror[]=new int[12];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertTrain frame = new InsertTrain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InsertTrain() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 237, 432);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//---------------------panel_index---------------------------------------------
		panel_index.setBounds(0, 0, 231, 403);		
		contentPane.add(panel_index);
		panel_index.setLayout(null);
		
		JCheckBox chckbxNewCheckBox_0 = new JCheckBox("\u661F\u671F\u65E5");//日
		chckbxNewCheckBox_0.setBounds(10, 31, 71, 23);
		panel_index.add(chckbxNewCheckBox_0);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("\u661F\u671F\u4E00");//一
		chckbxNewCheckBox_1.setBounds(10, 56, 71, 23);
		panel_index.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("\u661F\u671F\u4E8C");//二
		chckbxNewCheckBox_2.setBounds(10, 81, 71, 23);
		panel_index.add(chckbxNewCheckBox_2);
		
		JCheckBox chckbxNewCheckBox_3 = new JCheckBox("\u661F\u671F\u4E09");//三
		chckbxNewCheckBox_3.setBounds(10, 106, 71, 23);
		panel_index.add(chckbxNewCheckBox_3);
		
		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("\u661F\u671F\u56DB");//四
		chckbxNewCheckBox_4.setBounds(10, 131, 71, 23);
		panel_index.add(chckbxNewCheckBox_4);
		
		JCheckBox chckbxNewCheckBox_5 = new JCheckBox("\u661F\u671F\u4E94");//五
		chckbxNewCheckBox_5.setBounds(10, 156, 71, 23);
		panel_index.add(chckbxNewCheckBox_5);
		
		JCheckBox chckbxNewCheckBox_6 = new JCheckBox("\u661F\u671F\u516D");//六
		chckbxNewCheckBox_6.setBounds(10, 181, 71, 23);
		panel_index.add(chckbxNewCheckBox_6);
		
		JLabel label_1 = new JLabel("\u505C\u9760\u7AD9\u5225");
		label_1.setBounds(107, 10, 48, 15);
		panel_index.add(label_1);
		
		JLabel label = new JLabel("\u884C\u99DB\u6642\u9593");
		label.setBounds(10, 10, 48, 15);
		panel_index.add(label);
		
		JCheckBox checkBox_0 = new JCheckBox("\u5357\u6E2F");//站別
		checkBox_0.setBounds(107, 31, 97, 23);
		panel_index.add(checkBox_0);
		
		JCheckBox checkBox_1 = new JCheckBox("\u53F0\u5317");//
		checkBox_1.setBounds(107, 56, 97, 23);
		panel_index.add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("\u677F\u6A4B");//
		checkBox_2.setBounds(107, 81, 97, 23);
		panel_index.add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("\u6843\u5712");//
		checkBox_3.setBounds(107, 106, 97, 23);
		panel_index.add(checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox("\u65B0\u7AF9");//
		checkBox_4.setBounds(107, 131, 97, 23);
		panel_index.add(checkBox_4);
		
		JCheckBox checkBox_5 = new JCheckBox("\u82D7\u6817");//
		checkBox_5.setBounds(107, 156, 97, 23);
		panel_index.add(checkBox_5);
		
		JCheckBox checkBox_6 = new JCheckBox("\u53F0\u4E2D");//
		checkBox_6.setBounds(107, 181, 97, 23);
		panel_index.add(checkBox_6);
		
		JCheckBox checkBox_7 = new JCheckBox("\u5F70\u5316");//
		checkBox_7.setBounds(107, 206, 97, 23);
		panel_index.add(checkBox_7);
		
		JCheckBox checkBox_8 = new JCheckBox("\u96F2\u6797");//
		checkBox_8.setBounds(107, 231, 97, 23);
		panel_index.add(checkBox_8);
		
		JCheckBox checkBox_9 = new JCheckBox("\u5609\u7FA9");
		checkBox_9.setBounds(107, 256, 97, 23);
		panel_index.add(checkBox_9);
		
		JCheckBox checkBox_10 = new JCheckBox("\u53F0\u5357");
		checkBox_10.setBounds(107, 281, 97, 23);
		panel_index.add(checkBox_10);
		
		JCheckBox checkBox_11 = new JCheckBox("\u5DE6\u71DF");
		checkBox_11.setBounds(107, 306, 97, 23);
		panel_index.add(checkBox_11);
		
		JButton btnYes = new JButton("\u78BA\u5B9A");		
		btnYes.setBounds(10, 349, 87, 23);
		panel_index.add(btnYes);
		
		JButton btnNo = new JButton("\u53D6\u6D88");		
		btnNo.setBounds(134, 349, 87, 23);
		panel_index.add(btnNo);
		//--------------------------------------panel_index end------------------------------------------
		
		
		JPanel panel_checkon = new JPanel();
		panel_checkon.setBounds(0, 0, 231, 403);
		//contentPane.add(panel_checkon);///**********************
		panel_checkon.setLayout(null);
		
		JLabel label_2 = new JLabel("\u884C\u99DB\u65E5");
		label_2.setBounds(10, 10, 46, 15);
		panel_checkon.add(label_2);
		
		JLabel label_3 = new JLabel("\u505C\u9760\u7AD9\u5225");
		label_3.setBounds(110, 10, 46, 15);
		panel_checkon.add(label_3);
		
		JButton btnCheck = new JButton("\u78BA\u8A8D");
		btnCheck.setBounds(10, 370, 87, 23);
		panel_checkon.add(btnCheck);
		
		JButton btnback = new JButton("\u8FD4\u56DE");
		btnback.setBounds(134, 370, 87, 23);
		panel_checkon.add(btnback);
		
		JTextArea textArea_Date = new JTextArea();
		textArea_Date.setText("");
		textArea_Date.setBounds(10, 35, 87, 250);
		panel_checkon.add(textArea_Date);
		
		JTextArea textArea_Platfror = new JTextArea();
		textArea_Platfror.setText("");
		textArea_Platfror.setBounds(120, 35, 87, 250);
		panel_checkon.add(textArea_Platfror);
		
		//-----------------------------------------------
		
		btnYes.addActionListener(new ActionListener() {//btn確定
			public void actionPerformed(ActionEvent e) {
				textArea_Date.setText("");
				textArea_Platfror.setText("");
				if(chckbxNewCheckBox_0.isSelected()){
					arrDate[0]=1;
					textArea_Date.append("星期日\n");
				}					
				if(chckbxNewCheckBox_1.isSelected()){
					arrDate[1]=1;
					textArea_Date.append("星期一\n");
				}					
				if(chckbxNewCheckBox_2.isSelected()){
					arrDate[3]=1;
					textArea_Date.append("星期二\n");
				}				
				if(chckbxNewCheckBox_3.isSelected()){
					arrDate[4]=1;
					textArea_Date.append("星期三\n");
				}
				if(chckbxNewCheckBox_4.isSelected()){
					arrDate[5]=1;
					textArea_Date.append("星期四\n");	
				}
				if(chckbxNewCheckBox_5.isSelected()){
					arrDate[5]=1;
					textArea_Date.append("星期五\n");	
				}
				if(chckbxNewCheckBox_6.isSelected()){
					arrDate[6]=1;
					textArea_Date.append("星期六\n");	
				}
				if(checkBox_0.isSelected()){
					arrPlatfror[0]=1;
					textArea_Platfror.append("南港\n");
				}
				if(checkBox_1.isSelected()){
					arrPlatfror[1]=1;
					textArea_Platfror.append("台北\n");	
				}
				if(checkBox_2.isSelected()){
					arrPlatfror[2]=1;
					textArea_Platfror.append("板橋\n");
				}
				if(checkBox_3.isSelected()){
					arrPlatfror[3]=1;
					textArea_Platfror.append("桃園\n");	
				}
				if(checkBox_4.isSelected()){
					arrPlatfror[4]=1;
					textArea_Platfror.append("新竹\n");	
				}
				if(checkBox_5.isSelected()){
					arrPlatfror[5]=1;
					textArea_Platfror.append("苗栗\n");	
				}
				if(checkBox_6.isSelected()){
					arrPlatfror[6]=1;
					textArea_Platfror.append("台中\n");	
				}
				if(checkBox_7.isSelected()){
					arrPlatfror[7]=1;
					textArea_Platfror.append("彰化\n");	
				}
				if(checkBox_8.isSelected()){
					arrPlatfror[8]=1;
					textArea_Platfror.append("雲林\n");	
				}
				if(checkBox_9.isSelected()){
					arrPlatfror[9]=1;
					textArea_Platfror.append("嘉義\n");	
				}
				if(checkBox_10.isSelected()){
					arrPlatfror[10]=1;
					textArea_Platfror.append("台南\n");	
				}
				if(checkBox_11.isSelected()){
					arrPlatfror[11]=1;
					textArea_Platfror.append("左營\n");	
				}		
				//要顯示下一頁的東東
				contentPane.remove(panel_index);
				contentPane.add(panel_checkon);
				contentPane.revalidate();//登入成功後轉換panel
				contentPane.repaint();//
			}
		});
		
		btnNo.addActionListener(new ActionListener() {//btn取消 
			public void actionPerformed(ActionEvent e) {
				
			    InsertTrain.this.dispose();//關掉本JFrame
			}
		});
		
		btnCheck.addActionListener(new ActionListener() {//btn最終確認
			public void actionPerformed(ActionEvent e) {
				//上傳結果
				int SID=0;
				for(int i=0;i<arrPlatfror.length;i++)
					SID=(arrPlatfror[i]+SID)*2;//將arrPlatfror從2進位弄成10進位存到SID
				jdbcsql.insertTable_trinnumber(SID);//結果要改
			    InsertTrain.this.dispose();//關掉本JFrame
			    //應該要對Cjframe做Reprint			    
			}
		});
		btnback.addActionListener(new ActionListener() {//btn返回上一頁
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(panel_checkon);
				contentPane.add(panel_index);
				contentPane.revalidate();//登入成功後轉換panel
				contentPane.repaint();//
			    
			}
		});
	}
}
