package com.chatimmi.socketchat;

import android.app.Activity;
import android.widget.Toast;
import com.chatimmi.base.BaseActivitykt;
import java.net.URISyntaxException;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketCont {
    public static final String CHAT_SERVER_URL ="https://chatimmi.net:3000/";
   // public static final String CHAT_SERVER_URL ="https://www.apoim.com:5000/";
    private Activity context;
    public static Socket mSocket;
    private static SocketCont instance;

    public SocketCont(){

    }

    /**
     * Gets instance.
     *
     * @return the instance

     */
    public synchronized static SocketCont getInstance() {
        if (instance == null) {
            instance = new SocketCont();
        }

        try {
//            mSocket = IO.socket("https://www.apoim.com:5000/");
            mSocket = IO.socket(CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return instance;

    }



    public SocketCont(Socket mSocket, BaseActivitykt context) {
        this.context = context;
        this.mSocket = mSocket;
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_RECONNECT, onReconnect);
        mSocket.connect();
    }

    public void getmSocket(Socket mSocket, Activity context) {
        if (!mSocket.connected())
            mSocket.connect();

        // if (instance == null) {
        this.context = context;
        this.mSocket = mSocket;
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Socket.EVENT_RECONNECT, onReconnect);
        mSocket.connect();
        // }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Connect", Toast.LENGTH_SHORT).show();
                }

            });


        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Disconnect", Toast.LENGTH_LONG).show();                }
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
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    public void closeConnection(){
        if (mSocket != null) {
            mSocket.off(Socket.EVENT_CONNECT, onConnect);
            mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
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

