package com.example.shafaet.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arif.myapplication.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private Button buttonOn, buttonOff;
    private EditText editText;
    Socket myAppSocket = null;
    public  static String wifiModuleIp = "";
    public  static int wifiModulePort = 0;
    public static String CMD = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonOn = findViewById(R.id.id_button_on);
        buttonOff = findViewById(R.id.id_button_off);
        editText = findViewById(R.id.id_editText);
        buttonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIpandPort();
                CMD = "Up";
                Socket_AsyncTask cmd_light_on = new Socket_AsyncTask();
                cmd_light_on.execute();

            }
        });
        buttonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIpandPort();
                CMD = "Down";
                Socket_AsyncTask cmd_light_off = new Socket_AsyncTask();
                cmd_light_off.execute();
            }
        });
    }



    public void getIpandPort(){
        String ipandPort = editText.getText().toString();
        Log.d("MYTEST","IP String: "+ipandPort);
        String temp[] = ipandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST", "IP:" +wifiModuleIp);
        Log.d("MY TEST", "PORT:" +wifiModulePort);
    }

    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void> {
        Socket socket;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIp);
                socket = new java.net.Socket(inetAddress, MainActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
