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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class IzinActivity extends AppCompatActivity {

    TextView textViewId;
    TextView textViewName;
    TextView textViewKelas;
    EditText editTextKeluar;
    EditText editTextMasuk;
    EditText editTextAlasan;
    Button btnScan;
    Button btnIzin;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    DocumentReference referenceIDDocument = db.collection("Perizinan").document();

    public static final String ID = "id_student";

    String namaUstad;
    String IDdocument = referenceIDDocument.getId();
    String Document;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

        textViewId = findViewById(R.id.txt_id);
        textViewName = findViewById(R.id.student_name);
        editTextKeluar = findViewById(R.id.tanggal_keluar);
        editTextMasuk = findViewById(R.id.tanggal_masuk);
        editTextAlasan = findViewById(R.id.alasan);
        btnIzin = findViewById(R.id.btn_izin_scan);
        textViewKelas = findViewById(R.id.txt_kelas);

        if (!textViewId.equals(null)) {
            String id = getIntent().getStringExtra(ID);
            textViewId.setText(id);
        }

        setUp();

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
                                editTextKeluar.setText(documentSnapshot.get("waktuKeluar").toString());
                                editTextMasuk.setText(documentSnapshot.get("waktuMasuk").toString());
                                editTextAlasan.setText(documentSnapshot.get("alasan").toString());
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(IzinActivity.this, "santri tidak melakukan perizinan", Toast.LENGTH_SHORT).show();
            }
        });
        final String UserID = user.getUid().trim();
        db.collection("User").whereEqualTo("Id",UserID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot snapshot : task.getResult()){
                            namaUstad = snapshot.get("Nama").toString();
                        }
                    }
                });


        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String Name = textViewName.getText().toString();
                String kelas = textViewKelas.getText().toString();
                String TimeKeluar = editTextKeluar.getText().toString();
                String TimeMasuk = editTextMasuk.getText().toString();
                String AlasanKeluar = editTextAlasan.getText().toString();
                String Ustad = namaUstad;

                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(IzinActivity.this, "harap lengkapi data yang masih kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("idDocument", IDdocument);
                objectMap.put("id", Id);
                objectMap.put("nama", Name);
                objectMap.put("kelas",kelas);
                objectMap.put("waktuKeluar", TimeKeluar);
                objectMap.put("waktuMasuk", TimeMasuk);
                objectMap.put("alasan", AlasanKeluar);
                objectMap.put("ustad", Ustad);
                objectMap.put("WaktuSignOut",null);
                objectMap.put("WaktuSignIn",null);

                db.collection("Perizinan").document(IDdocument)
                        .set(objectMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(IzinActivity.this, "Izin berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IzinActivity.this, BridgeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });



    }


    public void setUp(){
        btnScan = findViewById(R.id.btn_serial_number);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityScanner.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(IzinActivity.this, BridgeActivity.class);
        startActivity(intent);
        finish();
    }
}


