package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
    TextView textViewKeluar;
    TextView textViewMasuk;
    TextView textViewAlasan;
    Button btnScan;
    Button btnIzin;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String ID = "id_student";

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

        textViewId = findViewById(R.id.txt_id);
        textViewName = findViewById(R.id.student_name);
        textViewKeluar = findViewById(R.id.tanggal_keluar);
        textViewMasuk = findViewById(R.id.tanggal_masuk);
        textViewAlasan = findViewById(R.id.alasan);
        btnIzin = findViewById(R.id.btn_izin_scan);

        if (!textViewId.equals(null)) {
            id = getIntent().getStringExtra(ID);
            textViewId.setText(id);
        }

        setUp();

        btnIzin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id = textViewId.getText().toString();
                String Name = textViewName.getText().toString();
                String TimeKeluar = textViewKeluar.getText().toString();
                String TimeMasuk = textViewMasuk.getText().toString();
                String AlasanKeluar = textViewAlasan.getText().toString();

                if (Id.trim().isEmpty() || Name.trim().isEmpty() || TimeKeluar.isEmpty() || TimeMasuk.isEmpty() || AlasanKeluar.isEmpty()) {
                    Toast.makeText(IzinActivity.this, "harap lengkapi data yang masih kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("id", Id);
                objectMap.put("nama", Name);
                objectMap.put("WaktuKeluar", TimeKeluar);
                objectMap.put("WaktuMasuk", TimeMasuk);

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

    }

}

