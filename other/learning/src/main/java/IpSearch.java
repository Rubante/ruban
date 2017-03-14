import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class IpSearch {

    public static void main(String[] args) {
        System.out.println(getAddressByIP("124.42.70.230"));
    }

    public static String getAddressByIP(String strIP) {
        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php=" + strIP);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();

            return result.toString();
        } catch (IOException e) {
            return "读取失败";
        }
    }
}
