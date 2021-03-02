package com.chatimmi.socketchat;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketConnection {
    private Socket mSocket;

    public void establishSocketConnection(Activity context) {
        try {
            mSocket = IO.socket("https://chatimmi.net:80/");
        }catch (Exception e){
            Log.d("fnanflas", "getSocketInstance: "+e.getMessage());
        }

        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.connect();
        Log.d("fnanflas", "CHECK: "+mSocket.connected());

    }

    public Socket getSocketInstance() {
        return mSocket;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("fnanflas", "onConnect: ");
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("fnanflas", "onDisconnect: " + args);

        }
    };

    private final Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.d("fnanflas", "onConnectError: " + args);

        }
    };

}
