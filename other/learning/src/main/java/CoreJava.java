
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.junit.Test;

public class CoreJava {

    @Test
    public void hashmap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        map.put("1", "1");
        map.put("2", "2");
        map.put("3", null);
        map.put("4", null);
        map.put(null, null);
        map.put(null, null);

        Iterator<String> a = map.keySet().iterator();

        while (a.hasNext()) {
            String key = a.next();
            System.out.println(key + ":::" + map.get(key));
        }
    }

    @Test
    public void hashtable() {

        Hashtable<String, String> table = new Hashtable<>();

        table.put("2", "");

        System.out.println("yy".hashCode());
        System.out.println("zZ".hashCode());

    }

    @Test
    public void testHashset() {
        LinkedHashSet<String> hashSet = new LinkedHashSet<>();
        hashSet.add("yy");
        hashSet.add("zZ");
        
        System.out.println(hashSet.size());
    }

    @Test
    public void hascodeTest() {
        for (int i = 1179395; i <= 1179395; i++) {
            for (int j = 19968; j <= 40869; j++) {
                for (int m = 19968; m <= 40869; m++) {
                    if (i == 31 * j + m) {
                        System.out.println((char) j + "" + (char) m);
                    }
                }
            }
        }

        for (int i = 0; i <= 1179395; i++) {
            int count = 0;
            String before = "";
            for (int j = 65; j <= 122; j++) {
                for (int m = 65; m <= 122; m++) {
                    if (i == 31 * j + m) {
                        count++;
                        if (count > 1) {
                            System.out.println(before);
                            System.out.println(i + ":" + (char) j + "" + (char) m);
                        } else {
                            before = i + ":" + (char) j + "" + (char) m;
                        }
                    }
                }
            }
        }
    }
}
