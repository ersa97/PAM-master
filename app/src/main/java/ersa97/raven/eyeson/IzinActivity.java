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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class IzinActivity extends AppCompatActivity {

    TextView textViewId;
    TextView textViewName;
    EditText editTextKeluar;
    EditText editTextMasuk;
    EditText editTextAlasan;
    Button btnScan;
    Button btnIzin;
    Button btnVerifMasuk;
    Button btnVerifKeluar;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    DocumentReference referenceStudent = db.collection("Student").document("izin");

    public static final String ID = "id_student";



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
        btnVerifKeluar = findViewById(R.id.btn_izin_verif_keluar);
        btnVerifMasuk = findViewById(R.id.btn_izin_verif_masuk);

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
                                editTextKeluar.setText(documentSnapshot.get("WaktuKeluar").toString());
                                editTextMasuk.setText(documentSnapshot.get("WaktuMasuk").toString());
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


        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String Name = textViewName.getText().toString();
                String TimeKeluar = editTextKeluar.getText().toString();
                String TimeMasuk = editTextMasuk.getText().toString();
                String AlasanKeluar = editTextAlasan.getText().toString();

                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(IzinActivity.this, "harap lengkapi data yang masih kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("id", Id);
                objectMap.put("nama", Name);
                objectMap.put("WaktuKeluar", TimeKeluar);
                objectMap.put("WaktuMasuk", TimeMasuk);
                objectMap.put("alasan", AlasanKeluar);

                db.collection("Perizinan")
                        .add(objectMap)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(IzinActivity.this, "Izin berhasil", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IzinActivity.this, BridgeActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });

        btnVerifKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String Name = textViewName.getText().toString();
                String TimeKeluar = editTextKeluar.getText().toString();
                String TimeMasuk = editTextMasuk.getText().toString();
                String AlasanKeluar = editTextAlasan.getText().toString();

                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(IzinActivity.this, "ID dan Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("izin","izin");

                db.collection("Student").document(id)
                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(IzinActivity.this, "verifikasi berhasil", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnVerifMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String Name = textViewName.getText().toString();
                String TimeKeluar = editTextKeluar.getText().toString();
                String TimeMasuk = editTextMasuk.getText().toString();
                String AlasanKeluar = editTextAlasan.getText().toString();

                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(IzinActivity.this, "ID dan Nama tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,Object> map = new HashMap<>();
                map.put("izin","tidak izin");

                db.collection("Student").document(id)
                        .update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(IzinActivity.this, "verifikasi berhasil", Toast.LENGTH_SHORT).show();
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


