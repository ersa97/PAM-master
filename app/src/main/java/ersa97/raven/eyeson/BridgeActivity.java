package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BridgeActivity extends AppCompatActivity {

    Button btnList;
    Button btnIzin;
    Button btnKeamanan;
    TextView textViewNama;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference referenceUser = db.collection("User");

    DocumentSnapshot snapshot;

    public static final String ID = "id_user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);

        btnList = findViewById(R.id.daftar_button);
        btnIzin = findViewById(R.id.izin_button);
        textViewNama = findViewById(R.id.nama_user);
        btnKeamanan = findViewById(R.id.keamanan_button);

        btnKeamanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainKeamananActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final String UserID = user.getUid().trim();
        db.collection("User").whereEqualTo("Id",UserID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot snapshot : task.getResult()){
                            textViewNama.setText(snapshot.get("Nama").toString());
                        }
                    }
                });



        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), List_Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IzinActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);
    }
}

