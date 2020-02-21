package yyl.example.demo.mockito;
/**
 * Mock 测试就是在测试过程中，对于某些不容易构造（如 HttpServletRequest 必须在Servlet 容器中才能构造出来）或者不容易获取比较复杂的对象（如 JDBC 中的ResultSet 对象），用一个虚拟的对象（Mock 对象）来创建以便测试的测试方法。<br>
 * Mock 最大的功能是将单元测试的耦合分解开，如果在代码对另一个类或者接口有依赖，它能够帮模拟这些依赖，并帮验证所调用的依赖的行为。 <br>
 */
