package yyl.example.demo.netty.client;

import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringEncoder;

/**
 * _Netty客户端例子
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        // 1、创建客户端启动类
        Bootstrap client = new Bootstrap();

        // 2、定义线程组，处理读写和链接事件，没有了accept事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);

        // 3、绑定客户端通道
        client.channel(NioSocketChannel.class);

        // 4、给NIoSocketChannel初始化handler， 处理读写事件
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                // 字符串编码器，一定要加在SimpleClientHandler 的上面
                ch.pipeline().addLast(new StringEncoder());
                // 基于分隔符的帧解码器
                ch.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                // 通道入站处理器（用来处理服务端返回的数据）
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        if (msg instanceof ByteBuf) {
                            String value = ((ByteBuf) msg).toString(Charset.defaultCharset());
                            System.out.println("Server=>" + value);
                        }
                        // 把客户端的通道关闭
                        ctx.channel().close();
                    }
                });
            }
        });

        // 5、连接服务器
        ChannelFuture future = client.connect("localhost", 8080).sync();

        // 6、推送数据
        for (int i = 0; i < 5; i++) {
            future.channel().writeAndFlush("hello-" + i + "\r\n");
        }
        //
        future.channel().closeFuture().sync();
    }
}