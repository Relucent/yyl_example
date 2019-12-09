package yyl.example.basic.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiExample {
    public static void main(String[] args) {
        ServiceLoader<SpiService> loader = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> iterator = loader.iterator();
        while (iterator.hasNext()) {
            SpiService service = iterator.next();
            System.out.println(service.getClass());
            service.hello();
        }
    }
}
