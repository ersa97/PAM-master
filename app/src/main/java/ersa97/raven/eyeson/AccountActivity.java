package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    TextView textViewEmail;
    Button buttonLogOut;
    TextView TextReset;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        user = firebaseAuth.getInstance().getCurrentUser();

        textViewEmail = findViewById(R.id.emailSekarang);
        buttonLogOut = findViewById(R.id.Button_Logout);
        TextReset = findViewById(R.id.Button_reset_password);

        email = user.getEmail();
        textViewEmail.setText(email);



        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        TextReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.sendPasswordResetEmail(String.valueOf(textViewEmail))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AccountActivity.this,
                                            "arahan untuk reset password telah dikirim ke email anda",
                                            Toast.LENGTH_SHORT).show();
                                    firebaseAuth.signOut();
                                    finish();
                                    System.exit(0);
                                }
                                else{
                                    Toast.makeText(AccountActivity.this, "gagal untuk menreset password", Toast.LENGTH_SHORT).show();
                                }
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