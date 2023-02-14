package ren.icraft.boatinstaller;

import android.os.Handler;
import android.widget.TextView;
import java.io.*;
import java.net.*;

public class OnlineAnnouncement{
    private Handler handler;
    private TextView TV;
    private String allStr = "";
    public OnlineAnnouncement(TextView TV){
        this.TV = TV;
    }
    public void run(){
        handler = new Handler();
        new Thread(() -> {
            //在这里写耗时操作例如网络请求之类的
            String url = "https://icraft.ren:90/titles/Boat_Announcement.txt";
            String inputLine = null;
            try {
                URL oracle = new URL(url);
                URLConnection conn = oracle.openConnection();//或HttpURLConnection connect = (HttpURLConnection) connection.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((inputLine = br.readLine()) != null){
                    allStr = allStr + inputLine + "\n";
                }
            }catch (IOException e) {
                allStr = "网络异常[若长期看见此消息说明服务器挂了]" + e;
            }
            handler.post(runnableUi);
        }).start();
    }
    Runnable runnableUi = new Runnable(){
        @Override
        public void run() {
            //在这里写更新UI的操作
            TV.setText(allStr);
        }

    };
}
