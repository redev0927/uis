//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
import Login.Graphic;

// Login화면 이후 작업이 정상적인지 확인

public class Main {
    public static void main(String[] args) {
        try {
            Graphic frame = new Graphic();
            frame.setLocationRelativeTo(null);
        } catch (Exception e) {
            System.out.println("[ERROR]로그인 화면 생성 실패");
        }
    }
}