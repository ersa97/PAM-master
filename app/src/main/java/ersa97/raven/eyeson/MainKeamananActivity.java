package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class MainKeamananActivity extends AppCompatActivity {

    TextView textViewId;
    TextView textViewIdDoc;
    TextView textViewName;
    TextView textViewKeluar;
    TextView textViewMasuk;
    TextView textViewAlasan;
    TextView textViewKelas;
    Button btnScan;
    Button btnKeluar;
    Button btnMasuk;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DocumentSnapshot snapshot;

    public static final String ID = "id_student";

    DocumentReference referenceIDDocument = db.collection("Perizinan").document();

    String idDocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_keamanan);

        textViewId = findViewById(R.id.txt_id);
        textViewIdDoc = findViewById(R.id.txt_id_doc);
        textViewName = findViewById(R.id.student_name);
        textViewKeluar = findViewById(R.id.tanggal_keluar);
        textViewMasuk = findViewById(R.id.tanggal_masuk);
        textViewAlasan = findViewById(R.id.alasan);
        btnScan = findViewById(R.id.btn_serial_number);
        btnKeluar = findViewById(R.id.btn_izin_verif_keluar);
        btnMasuk = findViewById(R.id.btn_izin_verif_masuk);
        textViewKelas = findViewById(R.id.txt_kelas);

        if (!textViewId.equals(null)) {
            String id = getIntent().getStringExtra(ID);
            textViewId.setText(id);
        }


        btnScan = findViewById(R.id.btn_serial_number);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityScannerKeamanan.class);
                startActivity(intent);
                finish();
            }
        });

        final String id = textViewId.getText().toString();
        db.collection("Student").whereEqualTo("id", id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot snapshot : task.getResult()){
                                textViewName.setText(snapshot.get("nama").toString());
                                textViewKelas.setText(snapshot.get("kelas").toString());
                            }
                        }
                    }
                });
        db.collection("Perizinan").whereEqualTo("id",id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot : task.getResult()){
                                textViewIdDoc.setText(documentSnapshot.get("idDocument").toString());
                                textViewKeluar.setText(documentSnapshot.get("waktuKeluar").toString());
                                textViewMasuk.setText(documentSnapshot.get("waktuMasuk").toString());
                                textViewAlasan.setText(documentSnapshot.get("alasan").toString());
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainKeamananActivity.this, "santri tidak melakukan perizinan", Toast.LENGTH_SHORT).show();
            }
        });


        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String IdDoc = textViewIdDoc.getText().toString();
                String Name = textViewName.getText().toString();
                String TimeKeluar = textViewKeluar.getText().toString();
                String TimeMasuk = textViewMasuk.getText().toString();
                String AlasanKeluar = textViewAlasan.getText().toString();
                SimpleDateFormat dateFormatKeluar = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssz");
                dateFormatKeluar.setTimeZone(TimeZone.getTimeZone("GMT"));


                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(MainKeamananActivity.this, "santri belum melakukan perizinan", Toast.LENGTH_SHORT).show();
                    return;
                }


                Map<String,Object> map = new HashMap<>();
                map.put("izin","izin");


                db.collection("Student").document(id)
                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });

                Map<String,Object> map1 = new HashMap<>();
                map1.put("WaktuSignOut", new Timestamp(new Date()));

                db.collection("Perizinan").document(IdDoc).update(map1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getApplicationContext(),MainKeamananActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(MainKeamananActivity.this, "update berhasil", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String IdDoc = textViewIdDoc.getText().toString();
                String Name = textViewName.getText().toString();
                String TimeKeluar = textViewKeluar.getText().toString();
                String TimeMasuk = textViewMasuk.getText().toString();
                String AlasanKeluar = textViewAlasan.getText().toString();

                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(MainKeamananActivity.this, "ID dan Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("izin","tidak izin");

                db.collection("Student").document(id)
                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainKeamananActivity.this, "verifikasi berhasil", Toast.LENGTH_SHORT).show();
                    }
                });

                Map<String,Object> map1 = new HashMap<>();
                map1.put("WaktuSignIn", new Timestamp(new Date()));

                db.collection("Perizinan").document(IdDoc).update(map1)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getApplicationContext(),MainKeamananActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(MainKeamananActivity.this, "update berhasil", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),BridgeActivity.class);
        startActivity(intent);
        finish();
    }
}