package yyl.example.demo.jts;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * 几何实体关系判断
 */
public class JtsGeometryExample2 {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    /**
     * 两个几何对象是否是重叠的
     * @return 是否是重叠的
     * @throws ParseException
     */
    public static boolean equalsGeo() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        LineString geometry1 = (LineString) reader.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) reader.read("LINESTRING(5 0, 0 0)");
        return geometry1.equals(geometry2);// true
    }

    /**
     * 几何对象没有交点(相邻)
     * @return 是否不相交
     * @throws ParseException
     */
    public static boolean disjointGeo() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        LineString geometry1 = (LineString) reader.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) reader.read("LINESTRING(0 1, 0 2)");
        return geometry1.disjoint(geometry2);
    }

    /**
     * 至少一个公共点(相交)
     * @return 是否相交
     * @throws ParseException
     */
    public static boolean intersectsGeo() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        LineString geometry1 = (LineString) reader.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) reader.read("LINESTRING(0 0, 0 2)");
        Geometry interPoint = geometry1.intersection(geometry2);// 相交点
        System.out.println(interPoint.toText());// 输出 POINT (0 0)
        return geometry1.intersects(geometry2);
    }

    /**
     * 判断以x,y为坐标的点point(x,y)是否在geometry表示的Polygon中
     * @param x 坐标
     * @param y 坐标
     * @param 表达式
     * @return 是否包含
     */
    public static boolean withinGeo(double x, double y, String geometry) throws ParseException {
        Coordinate coord = new Coordinate(x, y);
        Point point = GEOMETRY_FACTORY.createPoint(coord);
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        Polygon polygon = (Polygon) reader.read(geometry);
        return point.within(polygon);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(equalsGeo());
        System.out.println(disjointGeo());
        System.out.println(intersectsGeo());
        System.out.println(withinGeo(5, 5, "POLYGON((0 0, 10 0, 10 10, 0 10,0 0))"));
    }
}