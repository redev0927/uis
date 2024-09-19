package Common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Common - ReadProps.java
// DB������ ����Ǿ� �ִ� Properties ������ �ҷ����� ��� ����

public class ReadProps {
	 private static final String resource = "./db.properties";	// properties ���� ��� ����
	 private Properties props = new Properties();
	
	public ReadProps(){
		try {
			FileInputStream file = new FileInputStream(resource);	// properties ���� �ҷ�����
			props.load(file);
			file.close();
		}catch(IOException e) {
			System.out.println("[ERROR]Properties ������ �ҷ��� �� �����ϴ�.");	// ���� ó��
		}
	}
	
	// �� �׸񺰷� �����Ǵ� ���� ������
	public String getDBName() {
		return props.getProperty("DRIVER");
	}
	
	public String getDBUrl() {
		return props.getProperty("URL");
	}
	
	public String getDBId() {
		return props.getProperty("ID");
	}
	
	public String getDBPw() {
		return props.getProperty("PW");
	}
}