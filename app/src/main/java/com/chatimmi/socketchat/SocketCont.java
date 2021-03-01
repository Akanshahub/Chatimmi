package com.chatimmi.socketchat;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
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


public class SocketCont {
    public static final String CHAT_SERVER_URL ="https://chatimmi.net:80/";
   // public static final String CHAT_SERVER_URL ="https://www.apoim.com:5000/";
    private Activity context;
    public static Socket mSocket;
    private static SocketCont instance;



    /**
     * Gets instance.
     *
     * @return the instance

     */

    public static Socket getSocketInstance(){
          try {
              mSocket = IO.socket(CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d("ohioijoinonoi", "getInstance: "+e.getMessage());
        }
          return mSocket;
    }


    public synchronized static SocketCont getInstance() {
        if (instance == null) {
            instance = new SocketCont();
        }

        try {
//            mSocket = IO.socket("https://www.apoim.com:5000/");
            mSocket = IO.socket(CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.d("ohioijoinonoi", "getInstance: "+e.getMessage());
        }
        return instance;

    }



//    public SocketCont(Socket mSocket, BaseActivitykt context) {
//        this.context = context;
//        this.mSocket = mSocket;
//        mSocket.on(Socket.EVENT_CONNECT, onConnect);
//        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
//        mSocket.on(Socket.EVENT_RECONNECT, onReconnect);
//        mSocket.connect();
//    }

    public void getmSocket(Socket mSocket, Activity context) {

        mSocket.connect();
        Log.d("ohioijoinonoi", "getmSocket: "+mSocket.connected());

        this.context = context;
        SocketCont.mSocket = mSocket;
        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);





   /*     mSocket.io().on(Manager.EVENT_TRANSPORT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Transport transport = (Transport) args[0];
            // Adding headers when EVENT_REQUEST_HEADERS is called
                transport.on(Transport.EVENT_REQUEST_HEADERS, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.v("TAG", "TAG");
                        Map<String, List<String>> mHeaders = (Map<String, List<String>>)args[0];
                        mHeaders.put("Authorization", Arrays.asList("Basic bXl1c2VyOm15cGFzczEyMw=="));
                    }
                });
            }
        });*/


    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Connect", Toast.LENGTH_SHORT).show();
                    Log.d("cccccc", "onConnect: " +mSocket.id());

                }

            });


        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("ohioijoinonoi", "onDisconnect: "+args);
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Disconnect", Toast.LENGTH_LONG).show();                }
            });

        }
    };

    private Emitter.Listener onReconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("ohioijoinonoi", "onReconnect: "+args);
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
            Log.d("ohioijoinonoi", "onConnectError: "+args);
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, "Failed to connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    public void closeConnection(Socket mSocket, Activity context){
        if (mSocket != null) {
            mSocket.off(Socket.EVENT_CONNECT, onConnect);
            mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);

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

