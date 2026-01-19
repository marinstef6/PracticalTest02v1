package ro.pub.cs.systems.eim.practicaltest02v1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ro.pub.cs.systems.eim.practicaltest02v1.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02v1.AutocompleteServer;

public class PracticalTest02v1MainActivity extends AppCompatActivity {

    private EditText serverPortEditText;
    private EditText clientAddressEditText;
    private EditText prefixEditText;
    private Button sendButton;

    private AutocompleteServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02v1_main);

        serverPortEditText = findViewById(R.id.serverPortEditText);
        clientAddressEditText = findViewById(R.id.clientAddressEditText);
        prefixEditText = findViewById(R.id.prefixEditText);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            String portStr = serverPortEditText.getText().toString().trim();
            String addr = clientAddressEditText.getText().toString().trim();
            String prefix = prefixEditText.getText().toString().trim();

            if (portStr.isEmpty() || addr.isEmpty() || prefix.isEmpty()) {
                Toast.makeText(this, "Completeaza port, adresa si prefix!", Toast.LENGTH_SHORT).show();
                return;
            }

            int port = Integer.parseInt(portStr);
            if (port < 1024 || port > 65535) {
                Toast.makeText(this, "Port invalid! Alege 1024..65535 (ex: 8000)", Toast.LENGTH_SHORT).show();
                return;
            }
            if (server == null) {
                server = new AutocompleteServer(port);
                server.start();
                Toast.makeText(this, "Server started on port " + port, Toast.LENGTH_SHORT).show();
            }
            new ClientThread(this, addr, port, prefix).start();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (server != null) {
            server.stopServer();
        }
    }
}
