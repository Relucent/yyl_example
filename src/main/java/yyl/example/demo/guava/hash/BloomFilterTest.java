package yyl.example.demo.guava.hash;

import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器
 */
public class BloomFilterTest {
    public static void main(String[] args) {
        BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 1000, 0.03);
        System.out.println(bloomFilter.put("hello"));
        System.out.println(bloomFilter.put("hello"));
        System.out.println(bloomFilter.put("world"));
    }
}
