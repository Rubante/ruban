package memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.monitor.memcached.MemcachedConfiguration;
import com.ruban.monitor.memcached.MemcachedOperator;
import com.ruban.monitor.memcached.bean.ServerStats;
import com.ruban.monitor.memcached.bean.StatsSlab;
import com.ruban.monitor.memcached.domain.Server;
import com.ruban.monitor.memcached.executor.DumpCommandExcutor;
import com.ruban.monitor.memcached.executor.ItemCommandExcutor;
import com.ruban.monitor.memcached.executor.SettingCommandExcutor;
import com.ruban.monitor.memcached.executor.SizeCommandExcutor;
import com.ruban.monitor.memcached.executor.SlabCommandExcutor;
import com.ruban.monitor.memcached.executor.StatsCommandExcutor;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactoryBuilder.Locator;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.auth.AuthDescriptor;

public class MemcachedManager {

    private MemcachedClient client;

    public void connect(MemcachedConfiguration configuration) {

        configuration.validate();

        List<Server> serverList = configuration.getServerList();

        List<InetSocketAddress> inetAddrList = new ArrayList<>();
        for (int i = 0; i < serverList.size(); i++) {

            Server server = serverList.get(i);
            InetSocketAddress inetAddr = new InetSocketAddress(server.getHost(), server.getPort());
            inetAddrList.add(inetAddr);
        }

        try {
            ConnectionFactoryBuilder builder = new ConnectionFactoryBuilder();
            
            //sasl 默认安装，支持的：getpwent kerberos5 pam rimap shadow ldap
            builder.setAuthDescriptor(AuthDescriptor.typical("", ""));
            
            // 默认为数组（hash值对数组长度取余，因此采用的客户端及其算法决定了监控数据项是否能够有效）
            // 采用一致性哈希算法：要指明各地址的权重(将IP地址重复传入代替权重)

            builder.setLocatorType(Locator.CONSISTENT);
            builder.setHashAlg(DefaultHashAlgorithm.NATIVE_HASH);
            client = new MemcachedClient(builder.build(), inetAddrList);
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MemcachedManager manager = new MemcachedManager();
        MemcachedConfiguration configuration = new MemcachedConfiguration();
        configuration.addServer("139.199.189.214:11211");
        manager.connect(configuration);

        ItemCommandExcutor itemCmd = new ItemCommandExcutor(manager.client);

        List<ServerStats> result = itemCmd.execute();

        System.out.println(result.get(0).getItems());

        StatsCommandExcutor statsCmd = new StatsCommandExcutor(manager.client);
        List<ServerStats> result2 = statsCmd.execute();

        System.out.println(result2.get(0).getStats());

        // stats settings
        SettingCommandExcutor settingCmd = new SettingCommandExcutor(manager.client);
        List<ServerStats> result3 = settingCmd.execute();

        System.out.println(result3.get(0).getSettings());

        // stats sizes
        SizeCommandExcutor sizeCmd = new SizeCommandExcutor(manager.client);
        List<ServerStats> result4 = sizeCmd.execute();

        System.out.println(result4.get(0).getSizes());

        // stats slab
        SlabCommandExcutor slabCmd = new SlabCommandExcutor(manager.client);
        List<ServerStats> result5 = slabCmd.execute();

        List<StatsSlab> slabs = result5.get(0).getSlabs();

        Map<String, StatsSlab> slabMap = new HashMap<>();
        for (int i = 0; i < slabs.size(); i++) {
            StatsSlab slab = slabs.get(i);

            if (slabMap.get(slab.getId() + "") == null) {
                DumpCommandExcutor dataCmd = new DumpCommandExcutor(manager.client, slab.getId(), 0);

                List<ServerStats> result6 = dataCmd.execute();

                System.out.println("slab:" + slab.getId());
                System.out.println(result6.get(0).getValues());

                slabMap.put(slab.getId() + "", slab);
            }
        }

        Collection<MemcachedNode> nodes = manager.client.getNodeLocator().getAll();

        for (MemcachedNode node : nodes) {
            System.out.println(node);
        }
    }

    @Test
    public void saveTest() {
        MemcachedManager manager = new MemcachedManager();
        MemcachedConfiguration configuration = new MemcachedConfiguration();
        configuration.addServer("139.199.189.214:11211");
        manager.connect(configuration);

        MemcachedOperator utils = new MemcachedOperator();
        utils.setClient(manager.client);

        String str = (String) utils.get("test");
        System.out.println(str);

        boolean result = utils.add("test", "2222222222");

        System.out.println(result);

        for (int i = 0; i < 1000; i++) {
            StringBuffer sb = new StringBuffer();

            for (int j = 0; j < 1; j++) {
                sb.append(RandomUtil.nextStringByDigit(1024));
            }

            boolean s = utils.set("llllll" + i, 60 * i, sb.toString());
            System.out.println(s);
        }

    }

}
