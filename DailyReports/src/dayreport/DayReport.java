package dayreport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;



import dayreport.DateChooser;

import dayreport.JEmail;



public class DayReport extends JFrame {

	/**
	 * @param args
	 */
	private JPanel jpanel;
	private JLabel jlabel_title;
	private List<String> list;
	private JTextArea jta1;
	private JTextArea jta2;
	private JTextArea jta3;
	public static String end;
	
	public DayReport(String end){
		this.end=end;
	}
	
	public DayReport() {
		// 标题
		Font font = new Font("黑体", Font.BOLD, 18);
		Font font2 = new Font("黑体", Font.BOLD, 14);
		jpanel = new JPanel();
		jlabel_title = new JLabel("XXOL_日报");
		jlabel_title.setFont(font);
		jpanel.setBounds(185, 0, 105, 25);
		jpanel.add(jlabel_title);

		// 表格
		JPanel jpanel_table = new JPanel();
		String[] s = { "" };
		Object object[][] = { { "工作内容及完成度(%)" }, { "遇到的问题及解决方案" }, { "明日工作安排" } };
		JTable jtable = new JTable(object, s) {
			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return false;
				} else {
					return true;
				}
			}
		};
		TableColumn tc = jtable.getColumnModel().getColumn(0);
		tc.setPreferredWidth(135);
		for (int i = 0; i < 3; i++) {
			jtable.setRowHeight(145);
		}
		jpanel_table.add(jtable);
		jpanel_table.setBounds(5, 30, 135, 450);

		// 内容输入区

		jta1 = new JTextArea();
		JScrollPane jsp = new JScrollPane(jta1);
		// jta1.setBorder(new LineBorder(new Color(127,157,185),1,false));
		jsp.setBounds(140, 34, 350, 146);

		jta2 = new JTextArea();
		JScrollPane jsp2 = new JScrollPane(jta2);
		// jta2.setBorder(new LineBorder(new Color(127,157,185),1,false));
		jsp2.setBounds(140, 179, 350, 146);

		jta3 = new JTextArea();
		JScrollPane jsp3 = new JScrollPane(jta3);
		jsp3.setBounds(140, 324, 350, 146);
		
		//选择日期
		JPanel jpanel_date=new JPanel();
		Date date2=new Date();
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
		String now_date=sdf2.format(date2);
		JButton jb_date=new JButton(now_date);
		jb_date.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				final DateChooser mp = new DateChooser("yyyy-MM-dd");

				JFrame jf = new JFrame();
				jf.setResizable(false);
				jf.add(mp, BorderLayout.CENTER);
				jf.pack();
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);
				
				
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
		jpanel_date.add(jb_date);
		jpanel_date.setBounds(25, 500, 95, 60);

		// 提交按钮
		JPanel jpanel_button = new JPanel();
		JButton jb_submit=new JButton("发送");
		jb_submit.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Properties pro = new Properties();
				InputStream is = null;
				try {
					is = new BufferedInputStream(new FileInputStream(
							"D:\\staff.properties"));
					pro.load(is);
					
					pro.load(is);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				Date date=new Date();
				if(end==null){
					end=sdf.format(date);
				}
				
				list=new ArrayList<String>();
				list.add(pro.getProperty("name"));
				list.add(end);
				list.add(jta1.getText());
				list.add(jta2.getText());
				list.add(jta3.getText());
				XmlWord xw=new XmlWord();
				xw.outword(list);
				
				try {
					new JEmail().send(pro.getProperty("name") + "_XXOL_服务器技术日报");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
		jpanel_button.add(jb_submit);
		jpanel_button.setBounds(400, 500, 80, 60);
		
		

		
		
		this.add(jpanel_date);
		this.add(jpanel_button);
		this.add(jsp3);
		this.add(jsp2);
		this.add(jsp);
		this.add(jpanel_table);
		this.add(jpanel);
		Properties pro_title = new Properties();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(
					"D:\\staff.properties"));
			pro_title.load(is);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setTitle("XXOL_日报_服务器_欢迎"+pro_title.getProperty("name")+"使用");
		this.setLayout(null);
		this.setSize(500, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
		 
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		File file = new File("D:\\staff.properties");
		if (!file.exists()) {
			profile pro = new profile();
			pro.out();
		} else {
			new DayReport();
		}
	}

}
