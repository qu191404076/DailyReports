package dayreport;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class JEmail {

	/**
	 * @param args
	 */
	private String end;
	private String host = null;
	private String user = null;
	private String pwd = null;
	private String from = null;
	private String to = "xxxx@qq.com";
	private String subject = "XXOL_服务器技术日报";
	private File file;
	public void send(String txt) throws Exception {
		
		Properties pro = new Properties();
		InputStream is=new BufferedInputStream(new FileInputStream("D:\\staff.properties"));
		pro.load(is);
		host=pro.getProperty("host");
		user=pro.getProperty("user");
		pwd=pro.getProperty("pwd");
		from=pro.getProperty("from");
		pro.put("mail.smtp.host", host);
		pro.put("mail.smtp.auth", "true");
		Session session=Session.getDefaultInstance(pro);
		session.setDebug(true);
		MimeMessage mim=new MimeMessage(session);
		try {
			
			mim.setFrom(new InternetAddress(from)); //加载发信人地址
			mim.addRecipient(Message.RecipientType.TO, new InternetAddress(to));//加载收信人地址
			mim.setSubject(subject);//加载标题
			Multipart multipart=new MimeMultipart();
			//设置文本内容
			BodyPart contentpart=new MimeBodyPart();
			contentpart.setText(txt);
			multipart.addBodyPart(contentpart);
			
			//附件
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			end=sdf.format(date);
			if(!DayReport.end.equals(sdf.format(date))){
				end=DayReport.end;
			}else{
				end=sdf.format(date);
			}
			
			file=new File("D:\\"+end+"XXOL_服务器技术日报_"+pro.getProperty("name")+".doc");
			BodyPart messageBodyPart=new MimeBodyPart();
			DataSource fds=new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(fds));
			
			
			messageBodyPart.setFileName(MimeUtility.encodeText(file.getName()));
			messageBodyPart.addHeader("Content-Location", fds.getName());
			multipart.addBodyPart(messageBodyPart);
			
			
			mim.setContent(multipart);
			mim.saveChanges();//保存邮件
			Transport ts=session.getTransport("smtp");
			ts.connect(host, user, pwd);
			ts.sendMessage(mim, mim.getAllRecipients());
			ts.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	

}
