package yyl.example.demo.netty.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Netty服务端例子
 */
public class NettyServer {

	public static void main(String[] args) {
		ServerBootstrap bootstrap = new ServerBootstrap(//
				new NioServerSocketChannelFactory(//
						Executors.newCachedThreadPool(), // boss
						Executors.newCachedThreadPool()// worker
				));

		// Set up the default event pipeline.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(new StringDecoder(), new StringEncoder(), new ServerHandler());
			}
		});

		// Bind and start to accept incoming connections.
		Channel bind = bootstrap.bind(new InetSocketAddress(8000));
		System.out.println("Server started, listening port: " + bind.getLocalAddress() + ", Waiting for client register...");
	}

	private static class ServerHandler extends SimpleChannelHandler {
		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			if (e.getMessage() instanceof String) {
				String message = (String) e.getMessage();
				System.out.println("Client -> :" + message);
				e.getChannel().write("" + System.currentTimeMillis());// 此处不支持Long类型,只能传递字符串
				System.out.println("\nWaiting Client Input...");
			}

			super.messageReceived(ctx, e);
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
			super.exceptionCaught(ctx, e);
		}

		@Override
		public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
			System.out.println("Client Regist!");
			System.out.println("Client:" + e.getChannel().getRemoteAddress());
			System.out.println("Server:" + e.getChannel().getLocalAddress());
			System.out.println("\nWaiting Client Input...");
			super.channelConnected(ctx, e);
		}
	}
}