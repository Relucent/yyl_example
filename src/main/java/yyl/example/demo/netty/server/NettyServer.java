package yyl.example.demo.netty.server;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * _Netty服务端例子
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        // 1、通过ServerBootstrap启动服务端
        ServerBootstrap server = new ServerBootstrap();

        // 2、步定义两个线程组，用来处理客户端通道的accept和读写事件
        // parentGroup用来处理accept事件，childgroup用来处理通道的读写事件
        // parentGroup获取客户端连接，连接接收到之后再将连接转发给childgroup去处理
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        server.group(parentGroup, childGroup);

        // 3、配置积压队列尺寸（可选）；服务端处理客户端连接请求是按顺序处理的，所以同一时间只能处理一个客户端连接，多个客户端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog参数指定了队列的大小。
        server.option(ChannelOption.SO_BACKLOG, 128);

        // 4、绑定服务端通道
        server.channel(NioServerSocketChannel.class);

        // 5、绑定日志处理级别（可选）
        server.handler(new LoggingHandler(LogLevel.DEBUG));

        // 6、绑定handler，处理读写事件，ChannelInitializer 用于初始化通道
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline ph = ch.pipeline();
                // 基于分隔符的帧解码器； maxFrameLength表示这一帧最大的大小 ，delimiter表示分隔符
                // _Netty并没有提供一个DelimiterBasedFrameDecoder对应的编码器实现，因此在发送端需要自行编码添加分隔符，如 \r \n分隔符
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                // 处理客户端返回的数据
                ph.addLast(new SimpleServerHandler());
            }
        });
        // 7、绑定8080端口
        ChannelFuture future = server.bind(8080).sync();

        future.channel().closeFuture().sync();
    }

    private static class SimpleServerHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof ByteBuf) {
                System.out.println("Client=>" + ((ByteBuf) msg).toString(StandardCharsets.UTF_8));
            }
            ctx.channel().writeAndFlush(Unpooled.buffer().writeBytes("hello client\r\n".getBytes()));
        }
    }
}