package yyl.example.demo.spatial4j;

import java.math.BigDecimal;

import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.distance.DistanceCalculator;
import org.locationtech.spatial4j.distance.DistanceUtils;
import org.locationtech.spatial4j.io.GeohashUtils;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.Rectangle;

/**
 * 经度(Longitude:X)<br>
 * 纬度(Latitude:Y)<br>
 */
public class Spatial4jExample {

	public static void main(String[] args) {
		double longitudeX1 = 116.3125333347639;
		double latitudeY1 = 39.98355521792821;
		double longitudeX2 = 116.312528;
		double latitudeY2 = 39.983733;

		// 获得GEOHASH
		String geohash1 = GeohashUtils.encodeLatLon(latitudeY1, longitudeX1);
		System.out.println(geohash1);

		// 获得两个坐标的距离
		double distance = DistanceUtils.distVincentyRAD(//
				latitudeY1, longitudeX1, //
				latitudeY2, longitudeX2//
		);
		System.out.println(BigDecimal.valueOf(distance).toPlainString());

		// 地理空间四角点坐标计算
		SpatialContext geo = SpatialContext.GEO;
		DistanceCalculator distanceCalculator = geo.getDistCalc();
		double radiusKm = 1.0d;
		@SuppressWarnings("deprecation")
		Point point = geo.makePoint(longitudeX1, latitudeY1);
		Rectangle rectangle = distanceCalculator.calcBoxByDistFromPt(point, radiusKm * DistanceUtils.KM_TO_DEG, geo, null);
		System.out.println("longitudeX=" + rectangle.getMaxX() + "," + rectangle.getMaxX());
		System.out.println("latitudeY=" + rectangle.getMinY() + "," + rectangle.getMaxY());
	}
}
