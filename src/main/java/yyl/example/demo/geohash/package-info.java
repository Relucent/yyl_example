/**
 * GeoHash是一种地址编码方法 。<br>
 * 基本原理是将地球理解为一个二维平面，将平面递归分解成更小的子块，每个子块在一定经纬度范围内拥有相同的编码。<br>
 * 这种方式简单粗暴，可以满足对小规模的数据进行经纬度的检索。<br>
 * 
 * <pre>
 * [GEOHASH 精度对照]
 * length  width       height
 *  1      5,009.4km   4,992.6km
 *  2      1,252.3km   624.1km
 *  3      156.5km     156km
 *  4      39.1km      19.5km
 *  5      4.9km       4.9km
 *  6      1.2km       609.4m
 *  7      152.9m      152.4m
 *  8      38.2m       19m
 *  9      4.8m        4.8m
 * 10      1.2m        59.5cm
 * 11      14.9cm      14.9cm
 * 12      3.7cm       1.9cm
 * </pre>
 * 
 * Geohash-Java 是一个基于JAVA实现的 GeoHash工具类，提供了许多Geohash的算法。<br>
 * <a href="https://github.com/kungfoo/geohash-java">Geohash-Java</a><br>
 */
package yyl.example.demo.geohash;
