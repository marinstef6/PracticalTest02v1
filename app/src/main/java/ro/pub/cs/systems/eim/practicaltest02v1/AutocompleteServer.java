package ro.pub.cs.systems.eim.practicaltest02v1;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AutocompleteServer extends Thread {

    private final int port;
    private boolean running = true;
    private ServerSocket serverSocket;

    public AutocompleteServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Log.d("PT_SERVER", "Trying to start server on port " + port);
            serverSocket = new ServerSocket(port);
            Log.d("PT_SERVER", "Server started on port " + port);

            while (running) {
                Socket s = serverSocket.accept();
                Log.d("PT_SERVER", "Client connected: " + s.getInetAddress());
                new AutocompleteServerThread(s).start();
            }
        } catch (Exception e) {
            Log.e("PT_SERVER", "Server error", e);
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (Exception ignored) {}
    }
}
