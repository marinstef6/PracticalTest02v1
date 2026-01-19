package ro.pub.cs.systems.eim.practicaltest02v1;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {

    private final Context context;
    private final String address;
    private final int port;
    private final String prefix;

    public ClientThread(Context context, String address, int port, String prefix) {
        this.context = context;
        this.address = address;
        this.port = port;
        this.prefix = prefix;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(address, port);

            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out.println(prefix);

            final String response = in.readLine();

            android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
            mainHandler.post(() ->
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            );

        } catch (Exception e) {
            e.printStackTrace();
            android.os.Handler mainHandler = new android.os.Handler(context.getMainLooper());
            mainHandler.post(() ->
                    Toast.makeText(context, "Client error: " + e.getMessage(), Toast.LENGTH_LONG).show()
            );
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (Exception ignored) {}
        }
    }
}
