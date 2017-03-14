import org.springframework.util.Base64Utils;

public class Base64Test {

    public static void main(String[] args) {
        
        System.out.println(Base64Utils.encodeToString("root".getBytes()));
    }
}
