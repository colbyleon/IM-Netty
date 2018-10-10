package demo;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 10:30
 */
public class BioServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            while (true) {
                Socket s = serverSocket.accept();

                new Thread(()->{
                    while (true) {
                        try {
                            int n = 0;
                            byte[] data = new byte[1024];
                            InputStream in = s.getInputStream();

                            while ((n = in.read(data)) != -1) {
                                String msg = new String(data, 0, n);
                                System.out.println(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
