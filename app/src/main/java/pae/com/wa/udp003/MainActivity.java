package pae.com.wa.udp003;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MainActivity extends ActionBarActivity {
    ProgressBar bar,bar2;

Button btn,btn2;

    String msgs="";
    TextView s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        s1=(TextView)findViewById(R.id.txt1);

        btn=(Button)findViewById(R.id.btn1);

    btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread x2= new Thread(new Task());

                x2.start();


            }
    });



    }

   /* class Task implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<=100;i++){

                final int value = i;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                bar.setProgress(value);
            }
        }
    }*/
    class Task2 implements Runnable{
        final Handler handler = new Handler();
        @Override
        public void run() {
            for(int j=0;j<=100;j++){


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                int server_port = 4444;
                DatagramSocket s = null;
                try {
                    s = new DatagramSocket();
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                InetAddress local = null;
                try {
                    local = InetAddress.getByName("128.199.230.75");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                String data="Cod=100";
                int msg_length = data.length();
                byte[] message = data.getBytes();





                DatagramPacket p = new DatagramPacket(message, msg_length, local,server_port);
                try {
                    s.send(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] message2 = new byte[1500];

               p = new DatagramPacket(message2, message2.length);
                try {
                    s.receive(p);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                msgs = new String(message2, 0, p.getLength());
                handler.post(new Runnable() {
                    public void run() {
                        s1.setText(msgs);


                    }
                });

                s.close();


            }
        }

    }



    class  Task implements Runnable{
        final Handler handler = new Handler();

            public void run() {
                try {

                    int server_port = 4444;
                    DatagramSocket s = new DatagramSocket();
                    InetAddress local = InetAddress.getByName("192.168.56.1");
                    String data = "Code=100";
                    int msg_length = data.length();
                    byte[] message = data.getBytes();

                    DatagramPacket p = new DatagramPacket(message, msg_length, local, server_port);
                    s.send(p);
                    
                    for(;;) {
                        byte[] message2 = new byte[1500];
                        p = new DatagramPacket(message2, message2.length);
                        s.receive(p);
                        msgs = new String(message2, 0, p.getLength());
                        handler.post(new Runnable() {
                            public void run() {
                                s1.setText(msgs);


                            }
                        });
                    }




                } catch (Exception e) {

                }


            }
        }


}

