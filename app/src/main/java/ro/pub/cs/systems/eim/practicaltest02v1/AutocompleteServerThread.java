package ro.pub.cs.systems.eim.practicaltest02v1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class AutocompleteServerThread extends Thread {

    private final Socket socket;

    public AutocompleteServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            String prefix = in.readLine();
            if (prefix == null) {
                socket.close();
                return;
            }


            String result = WebAutocompleteService.getSuggestions(prefix);

            out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception ignored) {}
        }
    }
}
