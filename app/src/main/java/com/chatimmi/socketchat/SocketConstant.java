package com.chatimmi.socketchat;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.chatimmi.R;
import com.chatimmi.base.BaseActivitykt;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;


public class SocketConstant {
    public static final String CHAT_SERVER_URL ="https://chatimmi.net:80/";
    private Activity context;
    private Socket mSocket;
    private static SocketConstant instance;


    public SocketConstant(){

    }

    /**
     * Gets instance.
     *
     * @return the instance

     */
    public synchronized static SocketConstant getInstance() {
        if (instance == null) {
            instance = new SocketConstant();
        }
        return instance;
    }


    public SocketConstant(Socket mSocket,Activity context) {
        this.context = context;
        this.mSocket = mSocket;
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
      //  mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
       // mSocket.on(Socket.EVENT_RECONNECT, onReconnect);
        mSocket.connect();
    }

    public void getmSocket(Socket mSocket, Activity context) {
        Log.d("nvkjdnkdnfk", "getmSocket: ");
        if (!mSocket.connected())
            mSocket.connect();

        Log.d("nvkjdnkdnfk", "OUTER: "+mSocket.connected());
            Log.d("nvkjdnkdnfk", "instance: INNER");
            this.context = context;
            this.mSocket = mSocket;
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
          //  mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
           //  mSocket.on(Socket.EVENT_RECONNECT, onReconnect);
            mSocket.connect();

    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("nvkjdnkdnfk", "onConnect: ");

            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
                }
            });

        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("nvkjdnkdnfk", "onDisconnect: ");
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, R.string.disconnect, Toast.LENGTH_LONG).show();                }
            });

        }
    };

    private Emitter.Listener onReconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Reconnect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            Log.d("nvkjdnkdnfk", "onConnectError: ");
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,  R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    public void closeConnection(){
        if (mSocket != null) {
            mSocket.off(Socket.EVENT_CONNECT, onConnect);
            mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
          //  mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            mSocket.disconnect();
            mSocket.close();
        }
    }

    /**
     * Destroy.
     */
    public void destroy(){
        if (mSocket != null) {
            mSocket.off();
            mSocket.disconnect();
            mSocket.close();
            mSocket=null;
        }
    }
}

