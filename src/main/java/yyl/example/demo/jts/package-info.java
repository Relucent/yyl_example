package yyl.example.demo.jts;
/**
 * JTS是加拿大的 Vivid Solutions公司做的一套开放源码的 Java API。<br>
 * 它提供了一套空间数据操作的核心算法。为在兼容OGC标准的空间对象模型中进行基础的几何操作提供2D空间谓词API。<br>
 * 支持的空间操作包括：<br>
 * 相等(Equals)： 几何形状拓扑上相等。<br>
 * 脱节(Disjoint)： 几何形状没有共有的点。<br>
 * 相交(Intersects)： 几何形状至少有一个共有点（区别于脱节）<br>
 * 接触(Touches)： 几何形状有至少一个公共的边界点，但是没有内部点。<br>
 * 交叉(Crosses)： 几何形状共享一些但不是所有的内部点。<br>
 * 内含(Within)： 几何形状A的线都在几何形状B内部。<br>
 * 包含(Contains)： 几何形状B的线都在几何形状A内部（区别于内含）<br>
 * 重叠(Overlaps)： 几何形状共享一部分但不是所有的公共点，而且相交处有他们自己相同的区域。<br>
 * <br>
 * WKT(Well-known text)是一种文本标记语言，用于表示矢量几何对象、空间参照系统及空间参照系统之间的转换。<br>
 * 它的二进制表示方式，亦即WKB(well-known binary)则胜于在传输和在数据库中存储相同的信息。<br>
 * 该格式由开放地理空间联盟(OGC)制定。<br>
 */