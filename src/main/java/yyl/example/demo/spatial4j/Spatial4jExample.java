package yyl.example.demo.spatial4j;

import java.math.BigDecimal;

import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.io.GeohashUtils;


/**
 * 经度(Longitude:X)<br>
 * 纬度(Latitude:Y)<br>
 */
public class Spatial4jExample {

    public static void main(String[] args) {
        double longitude1 = 116.3125333347639;
        double latitude1 = 39.98355521792821;
        double longitude2 = 116.312528;
        double latitude2 = 39.983733;
        String geohash1 = GeohashUtils.encodeLatLon(latitude1, longitude1);
        System.out.println(geohash1);
        double distance = DistanceUtils.distVincentyRAD(//
                latitude1, longitude1, //
                latitude2, longitude2//
        );
        System.out.println(BigDecimal.valueOf(distance).toPlainString());
    }
}
