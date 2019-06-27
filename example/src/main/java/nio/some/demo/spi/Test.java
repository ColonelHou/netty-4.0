package nio.some.demo.spi;

import java.sql.Driver;
import java.util.ServiceLoader;

/**
 * 从头开始写一个迷你dubbo
 * https://cxis.me/2017/04/14/%E4%BB%8E%E5%A4%B4%E5%BC%80%E5%A7%8B%E5%86%99%E4%B8%80%E4%B8%AA%E8%BF%B7%E4%BD%A0dubbo/
 */
public class Test {
    public static void main(String[] args) {
        ServiceLoader<IShout> iShouts = ServiceLoader.load(IShout.class);
        for (IShout shout: iShouts) {
            shout.shout();
        }
    }
}
