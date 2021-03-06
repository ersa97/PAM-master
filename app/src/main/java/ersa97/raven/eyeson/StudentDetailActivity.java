package ersa97.raven.eyeson;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentDetailActivity extends AppCompatActivity {


    String nama, deskripsi, photo, izin;
    String namaDoc;
    TextView textViewNama;
    TextView textViewDeskripsi;
    ImageView imageViewPhoto;
    TextView textViewIzin;
    TextView textViewKelas;
    Bundle extra = new Bundle();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    //DocumentReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        extra = getIntent().getExtras();

        final String id = extra.getString("id");
        nama = extra.getString("nama");
        deskripsi = extra.getString("usia");
        photo = extra.getString("photo");
        izin = extra.getString("izin");

        db.collection("Student").whereEqualTo("id", id).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            textViewKelas.setText(snapshot.get("kelas").toString());
                        }
                    }
                });


        textViewIzin = findViewById(R.id.textView_Izin);
        textViewNama = findViewById(R.id.textView_Name);
        textViewDeskripsi = findViewById(R.id.textView_Deskripsi);
        textViewKelas = findViewById(R.id.textView_class);
        imageViewPhoto = findViewById(R.id.img_photo);


        textViewNama.setText(nama);
        textViewDeskripsi.setText(deskripsi);
        textViewIzin.setText(izin);


        Bundle bundle;
        bundle = getIntent().getExtras();

        //reference.update("izin",izin);
        //textViewIzin.setText(izin);


        Glide.with(this)
                .load(photo)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .into(imageViewPhoto);
        /*textView.setText(TempName);
        imageView.setImageResource(TempImage);*/


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), List_Dashboard.class);
        startActivity(intent);
        finish();
    }


}
