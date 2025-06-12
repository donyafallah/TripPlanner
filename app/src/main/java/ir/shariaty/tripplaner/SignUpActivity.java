package ir.shariaty.tripplaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button signUpBtn;
    private TextView goToLoginText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        signUpBtn = findViewById(R.id.signUpBtn);
        goToLoginText = findViewById(R.id.goToLoginText);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                } else {
                    // Code for sign up or sending request to server goes here
                    Toast.makeText(SignUpActivity.this, "Signing up...", Toast.LENGTH_SHORT).show();
                    // if sign up was success:
                    // Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    // startActivity(intent);
                    // finish();
                }
            }
        });

        goToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
