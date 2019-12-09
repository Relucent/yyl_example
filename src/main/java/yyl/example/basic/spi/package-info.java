/**
 * <h3>SPI机制</h3><br>
 * SPI（Service Provider Interface）是JDK内置的服务发现机制，用在不同模块间通过接口调用服务，避免对具体服务服务接口具体实现类的耦合。<br>
 * 比如JDBC的数据库驱动模块，不同数据库连接驱动接口相同但实现类不同，在使用SPI机制以前调用驱动代码需要直接在类里采用Class.forName(具体实现类全名）的方式调用，这样调用方依赖了具体的驱动实现，在替换驱动实现时要修改代码。<br>
 * 而采用SPI机制后，在驱动jar包的META-INF/services下面新建一个驱动接口全名的UTF-8编码的文件，里面写上具体实现类的全名，这样调用方通过Java的ServiceLoad接口动态的去加载接口的实现类，从而达到替换驱动实现不用修改代码的效果。<br>
 * 使用步骤：<br>
 * 1、服务调用方通过ServiceLoader.load加载服务接口的实现类实例；<br>
 * 2、服务提供方实现服务接口后，在自己Jar包的META-INF/services目录下新建一个接口名全名的文件，并将具体实现类全名写入。<br>
 */
package yyl.example.basic.spi;
