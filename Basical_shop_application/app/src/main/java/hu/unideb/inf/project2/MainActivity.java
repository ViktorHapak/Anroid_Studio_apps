package hu.unideb.inf.project2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String username;
    public static String name;

    private EditText usernameField;
    private EditText nameField;

    private TextView messageText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameField = findViewById(R.id.usernameField);
        nameField = findViewById(R.id.nameField);
        messageText = findViewById(R.id.errorText);
        usernameField.setText(username);
        nameField.setText(name);

    }



    public void signUp(View view) {
        if ((usernameField.getText().toString().trim().equals(""))
        || nameField.getText().toString().equals("")) messageText.setText("Üres mező!");
        else if (usernameField.getText().toString().trim().length()>15)
            messageText.setText("A felhasználónév legfeljebb 15 karakterből állhat!");
        else if (nameField.getText().toString().length()>25)
            messageText.setText("A név nem lehet több, mint 25 karakternyi hosszúságú!");
        else {
            messageText.setText("");
            username = usernameField.getText().toString().trim();
            name = setSpace(nameField.getText().toString());
            Intent intent = new Intent(this, SellActivity.class);
            startActivity(intent);

        }

        }

        public String setSpace(String string){

           string = string.replace(" ","_");

          return string;
        }
    }
