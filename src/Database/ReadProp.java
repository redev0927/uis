package Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// DB정보가 저장되어 있는 Properties 파일을 불러오는 기능 수행

public class ReadProp {
    private static final String resource = "./db.properties";	// properties 파일 경로 지정
    private Properties props = new Properties();

    public ReadProp(){
        try {
            FileInputStream file = new FileInputStream(resource);	// properties 파일 불러오기
            props.load(file);
            file.close();
        }catch(IOException e) {
            System.out.println("[ERROR]Properties 파일을 불러올 수 없습니다.");	// 예외 처리
        }
    }

    // 각 항목별로 대응되는 값을 가져옴
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
