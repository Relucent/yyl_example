package yyl.example.demo.geohash;

import ch.hsr.geohash.GeoHash;

/**
 * 经度(longitudeX)<br>
 * 纬度(latitudeY)<br>
 */
public class GeohashExample {
    public static void main(String[] args) {
        double longitude = 116.403694;// 经度
        double latitude = 39.916263;// 纬度
        int precision = 1;// 精度 1~12
        // 经纬度转换 GeoHash
        GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, precision);
        System.out.println(geoHash.toBase32() + "->" + geoHash.toBinaryString());
        // 八个相邻区域 N, NE, E, SE, S, SW, W, NW
        GeoHash[] adjacents = geoHash.getAdjacent();
        for (GeoHash adjacent : adjacents) {
            System.out.println(adjacent.toBase32() + "->" + adjacent.toBinaryString());
        }
    }
}
