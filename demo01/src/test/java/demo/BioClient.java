package demo;

import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * @author v_xiangbluo
 * @date 2018/9/26 10:35
 */
public class BioClient {
    public static void main(String[] args) {
        try (Socket s = new Socket("127.0.0.1", 8000);){
            while (true) {
                OutputStream out = s.getOutputStream();
                out.write((LocalDateTime.now().toString()+" hello").getBytes("utf-8"));

                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
