package mx.com.noriantec.uberclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputPassword;
    Button mButtonLogin;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AlertDialog mDialog;

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTextInputEmail = findViewById(R.id.textInputEmail);
        mTextInputPassword = findViewById(R.id.textInputPassword);
        mButtonLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setMessage("Espere un momento").build();

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String email = Objects.requireNonNull(mTextInputEmail.getText()).toString();
        String password = Objects.requireNonNull(mTextInputPassword.getText()).toString();

        if(!email.isEmpty() && !password.isEmpty()) {
            if(password.length() >= 6) {
                mDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                            Toast.makeText(LoginActivity.this, "El login se hizo de forma correcta", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this, "El correo o la contraseña son invalidos", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });
            } else
                Toast.makeText(LoginActivity.this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "El correo y la contraseña son necesarios", Toast.LENGTH_SHORT).show();
        }
    }
}