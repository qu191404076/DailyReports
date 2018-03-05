package dayreport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;



public class profile {

	private String name = "name";// 报告人
	private String host = "host";// 邮箱smtp服务器地址
	private String user = "user";// 用户名
	private String pwd = "pwd";// 密码
	private String from = "from";// 邮箱名
	
	private JTextArea jta1;
	private JTextArea jta2;
	private JTextArea jta3;
	private JTextArea jta4;
	private JTextArea jta5;
	private JPasswordField jpassword;
	
	private String password;
	private JTable jtable;
	private List<String> list;
	private Properties pro;
	private FileOutputStream fos;

	public void out() {
		Font font=new Font("黑体",Font.BOLD,13);
		JFrame jframe = new JFrame("信息录入");
		JPanel jpanel = new JPanel();
		String[] header = { "" };
		Object[][] message = new Object[][] { { "smtp服务器地址" },
				{ "报告人" }, { "用户名" }, {"密码"},{ "邮箱地址"}};

		jtable = new JTable(message, header){
			public boolean isCellEditable(int row, int column){
				if(column==0){
					return false;
				}else{
					return true;
				}
			}
		};
		jtable.setFont(font);
		jtable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		jpanel.add(jtable);
		for (int i = 0; i < 5; i++) {
			jtable.setRowHeight(i, 27);
		}
		for (int i = 0; i < 1; i++) {
			TableColumn tc = jtable.getColumnModel().getColumn(i);
			tc.setPreferredWidth(110);
		}
		jpanel.setBounds(0, 0, 110, 200);
		
		//内容输入区
		jta1=new JTextArea("mail.sqage.com");
		jta1.setBounds(109, 5, 244, 27);
		jta1.setFont(font);
		jta1.setBorder(new LineBorder(Color.gray, 1));
		jta1.setEditable(false);
		
		jta2=new JTextArea();
		jta2.setBounds(109, 31, 244, 28);
		jta2.setFont(font);
		jta2.setBorder(new LineBorder(Color.gray,1));
		
		jta3=new JTextArea();
		jta3.setBounds(109, 58, 244, 28);
		jta3.setFont(font);
		jta3.setBorder(new LineBorder(Color.gray,1));
		
		//密码输入 
		jpassword=new JPasswordField();
		jpassword.setFont(font);
		jpassword.setBorder(new LineBorder(Color.gray,1));
		jpassword.setBounds(109, 85, 246, 29);

		
		//邮箱域名
		jta4=new JTextArea();
		jta4.setBounds(109, 113, 245, 27);
		jta4.setFont(font);
		jta4.setBorder(new LineBorder(Color.gray,1));
		
		list = new ArrayList<String>();

		
		
		JPanel j_b = new JPanel();
		JButton jbutton = new JButton("提交");
		jbutton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				password=String.valueOf(jpassword.getPassword());
				list.add(jta1.getText());
				list.add(jta2.getText());
				list.add(jta3.getText());
				list.add(password);
				Pattern pattern=Pattern.compile("^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
				Matcher matcher=pattern.matcher(jta4.getText());
				boolean b=matcher.matches();
				if(b==false){
					error();
				}else{
					list.add(jta4.getText());
					try {
						pro = new Properties();
						fos = new FileOutputStream("D:\\staff.properties");
						pro.setProperty(host, list.get(0));
						pro.setProperty(name, list.get(1));
						pro.setProperty(user, list.get(2));
						pro.setProperty(pwd, list.get(3));
						pro.setProperty(from, list.get(4));
						pro.store(fos, null);
						fos.flush();
						fos.close();
						File file = new File("D:\\staff.properties");
						if (!file.exists()) {
							try {
								file.createNewFile();
								new DayReport();
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							new DayReport();
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		j_b.add(jbutton);
		j_b.setBounds(155, 140, 70, 35);

		jframe.add(jta4);
		jframe.add(jta3);
		jframe.add(jta2);
		jframe.add(jta1);
		jframe.add(jpassword);
		jframe.add(j_b);
		jframe.add(jpanel);
		jframe.setLayout(null);
		jframe.setLocationRelativeTo(null);
		jframe.setSize(360, 210);
		jframe.setVisible(true);
		jframe.setResizable(false);
		
		
		
	}
	
	private String String(char[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public void error(){
		JFrame jframe=new JFrame("");
		JPanel jpanel=new JPanel();
		JLabel jlb=new JLabel("邮箱格式有误");
		jpanel.add(jlb);
		
		jframe.setSize(40, 80);
		jframe.add(jpanel);
		jframe.setVisible(true);
		jframe.setLocationRelativeTo(null);
		
	}
	
	
}
