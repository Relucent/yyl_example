package yyl.example.demo.geodesy;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;

/**
 * 大地测量计算器<br>
 * 开发中经常会遇到计算两个点（经纬度）之间的距离或者计算最近门店的场景，以下是计算两个经纬度之间相隔的距离的实现方法。<br>
 */
public class GeodesyExample {

	public static void main(String[] args) {
		GlobalCoordinates source = new GlobalCoordinates(39.914492D, 116.403694D);
		GlobalCoordinates target = new GlobalCoordinates(40.004860D, 116.278937D);
		double meter1 = getDistanceMeter(source, target, Ellipsoid.Sphere);
		double meter2 = getDistanceMeter(source, target, Ellipsoid.WGS84);
		System.out.println("Sphere坐标系计算结果：" + meter1 + "米");
		System.out.println("WGS84坐标系计算结果：" + meter2 + "米");
	}

	public static double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {
		// 大地测量计算器，调用计算方法，传入坐标系、经纬度用于计算距离
		GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);
		return geoCurve.getEllipsoidalDistance();
	}
}
