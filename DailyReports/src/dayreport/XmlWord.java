package dayreport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class XmlWord {
	/*
	 * 设置标识 taglist 标识 datalist 数据
	 */
	private String end;
	
	private Map<String, String> dataMap = new HashMap<String, String>();

	public Map<String, String> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, String> dataMap) {
		this.dataMap = dataMap;
	}

	public void setData(List<String> taglist, List<String> datalist) {
		Iterator<String> it1 = taglist.iterator();
		Iterator<String> it2 = datalist.iterator();
		while (it1.hasNext()) {
			this.dataMap.put(it1.next(), it2.next());
		}
	}

	/*
	 * 载入xml文档
	 */

	public Document loadxml(String filename) {
		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filename));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return document;
	}

	/*
	 * 替换标识内容
	 */
	public Element replaceTagContext(Element element) {
		Element xelement = element;
		NodeList tElements = xelement.getElementsByTagName("w:t");
		Set<String> dataSet = this.dataMap.keySet();
		Iterator<String> iterator = dataSet.iterator();
		while (iterator.hasNext()) {
			String tag = iterator.next();
			String data = dataMap.get(tag);
			for (int i = 0; i < tElements.getLength(); i++) {
				Element tElement = (Element) tElements.item(i);
				if (tElement.getTextContent().equals(tag)) {
					tElement.setTextContent(data);
				}
			}

		}
		return xelement;

	}

	public Boolean outWord(Document document, File file) {
		boolean flag = true;
		try {
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer = tfactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(file.toURI().getPath());
			transformer.transform(source, result);
		} catch (Exception e) {
			// TODO: handle exception
			flag = false;
		}
		return flag;
	}

	public void outword(List<String> show_list) {
		XmlWord test = new XmlWord();
		// 载入模版
		Document doc = test.loadxml("config/XXOL_日报_服务器_XXX_date.xml");
		// 设置标记
		List<String> taglist = new ArrayList<String>();
		taglist.add("name");
		taglist.add("date");
		taglist.add("job");
		taglist.add("question");
		taglist.add("plan");
		// 填充标记
		List<String> datalist = new ArrayList<String>();
		for (int i = 0; i < 5; i++) {
			datalist.add(show_list.get(i));
		}
		// 存入
		test.setData(taglist, datalist);
		// 替换
		test.replaceTagContext(doc.getDocumentElement());
		// 写入
		Properties pro = new Properties();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("D:\\staff.properties"));
			pro.load(is);
			
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date date=new Date();
			if(!DayReport.end.equals(sdf.format(date))){
				end=DayReport.end;
			}else{
				end=sdf.format(date);
			}
			
			
			test.outWord(doc, new File("D:\\" + end + "XXOL_服务器技术日报_"+pro.getProperty("name")+".doc"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
