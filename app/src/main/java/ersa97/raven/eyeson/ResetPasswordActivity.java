package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    ConstraintLayout constraintProgress;
    TextInputEditText editText_email;
    Button buttonLanjut;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editText_email = findViewById(R.id.verif_email);

        buttonLanjut = findViewById(R.id.lanjut_button);

        constraintProgress = findViewById(R.id.constraintloading1);

        auth = FirebaseAuth.getInstance();

        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText_email.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplication(), "Masukan email anda yang terdaftar", Toast.LENGTH_SHORT).show();
                    return;
                }

                constraintProgress.setVisibility(View.VISIBLE);

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ResetPasswordActivity.this,
                                            "arahan untuk reset password telah dikirim ke email anda",
                                            Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ResetPasswordActivity.this, "gagal untuk menreset password", Toast.LENGTH_SHORT).show();
                                }
                                constraintProgress.setVisibility(View.GONE);
                            }
                        });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
