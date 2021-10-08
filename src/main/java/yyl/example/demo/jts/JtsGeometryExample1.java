package yyl.example.demo.jts;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

/**
 * 几何实体的创建，读取操作
 */
public class JtsGeometryExample1 {

    private static final GeometryFactory GEOMETRY_FACTORY = new GeometryFactory();

    /**
     * create a point
     * @return 点
     */
    public static Point createPoint() {
        Coordinate coord = new Coordinate(109.013388, 32.715519);
        Point point = GEOMETRY_FACTORY.createPoint(coord);
        return point;
    }

    /**
     * create a point by WKT
     * @return 点
     * @throws ParseException
     */
    public static Point createPointByWKT() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        Point point = (Point) reader.read("POINT (109.013388 32.715519)");
        return point;
    }

    /**
     * create multiPoint by wkt
     * @return 多点
     */
    public static MultiPoint createMulPointByWKT() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        MultiPoint mpoint = (MultiPoint) reader.read("MULTIPOINT(109.013388 32.715519,119.32488 31.435678)");
        return mpoint;
    }

    /**
     * create a line
     * @return 线
     */
    public static LineString createLine() {
        Coordinate[] coords = new Coordinate[] { new Coordinate(2, 2), new Coordinate(2, 2) };
        LineString line = GEOMETRY_FACTORY.createLineString(coords);
        return line;
    }

    /**
     * create a line by WKT
     * @return 线
     * @throws ParseException
     */
    public static LineString createLineByWKT() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        LineString line = (LineString) reader.read("LINESTRING(0 0, 2 0)");
        return line;
    }

    /**
     * create multiLine
     * @return 多线
     */
    public static MultiLineString createMLine() {
        Coordinate[] coords1 = new Coordinate[] { new Coordinate(2, 2), new Coordinate(2, 2) };
        LineString line1 = GEOMETRY_FACTORY.createLineString(coords1);
        Coordinate[] coords2 = new Coordinate[] { new Coordinate(2, 2), new Coordinate(2, 2) };
        LineString line2 = GEOMETRY_FACTORY.createLineString(coords2);
        LineString[] lineStrings = new LineString[2];
        lineStrings[0] = line1;
        lineStrings[1] = line2;
        MultiLineString ms = GEOMETRY_FACTORY.createMultiLineString(lineStrings);
        return ms;
    }

    /**
     * create multiLine by WKT
     * @return 多线
     * @throws ParseException
     */
    public static MultiLineString createMLineByWKT() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        MultiLineString line = (MultiLineString) reader.read("MULTILINESTRING((0 0, 2 0),(1 1,2 2))");
        return line;
    }

    /**
     * create a polygon(多边形) by WKT
     * @return 多边形
     * @throws ParseException
     */
    public static Polygon createPolygonByWKT() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        Polygon polygon = (Polygon) reader.read("POLYGON((20 10, 30 0, 40 10, 30 20, 20 10))");
        return polygon;
    }

    /**
     * create multi polygon by wkt
     * @return 多个多边形
     * @throws ParseException
     */
    public static MultiPolygon createMulPolygonByWKT() throws ParseException {
        WKTReader reader = new WKTReader(GEOMETRY_FACTORY);
        MultiPolygon mpolygon = (MultiPolygon) reader.read("MULTIPOLYGON(((40 10, 30 0, 40 10, 30 20, 40 10),(30 10, 30 0, 40 10, 30 20, 30 10)))");
        return mpolygon;
    }

    /**
     * create GeometryCollection contain point or multiPoint or line or multiLine or polygon or multiPolygon
     * @return 图形集
     * @throws ParseException
     */
    public static GeometryCollection createGeoCollect() throws ParseException {
        LineString line = createLine();
        Polygon poly = createPolygonByWKT();
        Geometry g1 = GEOMETRY_FACTORY.createGeometry(line);
        Geometry g2 = GEOMETRY_FACTORY.createGeometry(poly);
        Geometry[] garray = new Geometry[] { g1, g2 };
        GeometryCollection gc = GEOMETRY_FACTORY.createGeometryCollection(garray);
        return gc;
    }

    /**
     * create a Circle 创建一个圆，圆心(x,y) 半径RADIUS
     * @param x 圆心X坐标
     * @param y 圆心X坐标
     * @param RADIUS 半径
     * @return 圆
     */
    public static Polygon createCircle(double x, double y, final double RADIUS) {
        final int SIDES = 32;// 圆上面的点个数
        Coordinate coords[] = new Coordinate[SIDES + 1];
        for (int i = 0; i < SIDES; i++) {
            double angle = ((double) i / (double) SIDES) * Math.PI * 2.0;
            double dx = Math.cos(angle) * RADIUS;
            double dy = Math.sin(angle) * RADIUS;
            coords[i] = new Coordinate((double) x + dx, (double) y + dy);
        }
        coords[SIDES] = coords[0];
        LinearRing ring = GEOMETRY_FACTORY.createLinearRing(coords);
        Polygon polygon = GEOMETRY_FACTORY.createPolygon(ring, null);
        return polygon;
    }

    public static void main(String[] args) throws ParseException {
        Polygon p = createCircle(0, 1, 2);
        // 圆上所有的坐标(32个)
        Coordinate coords[] = p.getCoordinates();
        for (Coordinate coord : coords) {
            System.out.println(coord.x + "," + coord.y);
        }
    }
}