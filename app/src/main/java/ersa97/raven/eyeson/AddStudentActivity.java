package ersa97.raven.eyeson;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

public class AddStudentActivity extends AppCompatActivity {

    EditText editTextNama;
    EditText editTextAlamat;
    EditText editTextDeskripsi;
    ImageView imageAdd;
    Button buttonImage;
    Uri photoUri;
    String photoUrl;
    Uri image;
    private Toolbar mToolbar;

    private final int REQUEST_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);


        editTextNama = findViewById(R.id.nama_Student);
        editTextAlamat = findViewById(R.id.alamat);
        editTextDeskripsi = findViewById(R.id.deskripsi);
        buttonImage = findViewById(R.id.chooseImage);
        imageAdd = findViewById(R.id.ImageAdd);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(AddStudentActivity.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(REQUEST_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            List<Uri> photo = Matisse.obtainResult(data);
            image = photo.get(0);
            photoUri = image;
            imageAdd.setImageURI(image);
        }
    }

    private void upload(final String nama, final String alamat, final String deskripsi, final String izin, Uri image) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        String filename = image.getLastPathSegment();

        final StorageReference ImageRef = storage.getReference().child("profile_image/" + filename);
        UploadTask task = ImageRef.putFile(image);
        Task<Uri> urlTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL

                return ImageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    CollectionReference reference = FirebaseFirestore.getInstance().collection("Student");
                    reference.add(new Students(nama, alamat, deskripsi, izin, downloadUri.toString()));

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_save:
                save_student();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void save_student() {
        String nama = editTextNama.getText().toString();
        String alamat = editTextAlamat.getText().toString();
        String deskripsi = editTextDeskripsi.getText().toString();
        String izin = "tidak izin";

        if (nama.trim().isEmpty() || alamat.trim().isEmpty() || deskripsi.trim().isEmpty() || photoUri == null) {
            Toast.makeText(this, "Mohon masukan nama, alamat dan usia", Toast.LENGTH_SHORT).show();
            return;
        }

        upload(nama, alamat, deskripsi, izin, photoUri);
        Toast.makeText(this, "Murid ditambahkan", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
