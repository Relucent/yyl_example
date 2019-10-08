package yyl.example.basic.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {

    private static final int PORT = 1024;

    public static void main(String[] args) {
        new ServerThread().start();
        new ClientThread().start();
    }

    private static class ServerThread extends Thread {
        @Override
        public void run() {
            // 初始化服务端socket并且绑定端口
            try (ServerSocket server = new ServerSocket(PORT)) {
                while (!Thread.currentThread().isInterrupted()) {
                    // 等待客户端的连接
                    final Socket socket = server.accept();
                    // 每当有一个客户端连接进来后，就启动一个单独的线程进行处理
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 获取输入流
                            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                                // 读取一行数据
                                String line = null;
                                // 输出打印
                                while ((line = reader.readLine()) != null) {
                                    System.out.println("Server#" + ServerThread.this + "<<" + line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static class ClientThread extends Thread {
        @Override
        public void run() {
            long counter = 0L;
            try (Socket socket = new Socket("127.0.0.1", PORT)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while (!Thread.currentThread().isInterrupted() && counter < Integer.MAX_VALUE) {
                    Thread.sleep(1000L);
                    counter++;
                    String msg = String.valueOf(counter);
                    System.out.println("Client#" + ClientThread.this + ">>" + msg);
                    writer.write(msg);
                    writer.write("\n");
                    writer.flush();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
