package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListPerizinanActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference activityRef = db.collection("Perizinan");
    RecyclerView recyclerView;

    EditText editTextFilterDari;
    EditText editTextFilterSampai;

    ImageButton imageButtonSearch;

    private ListIzinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_perizinan);

        editTextFilterDari = findViewById(R.id.tanggaldari);
        editTextFilterSampai = findViewById(R.id.tanggalsampai);
        imageButtonSearch = findViewById(R.id.search);

        final String filterDari = editTextFilterDari.getText().toString();
        final String filterSampai = editTextFilterSampai.getText().toString();



        recyclerView = findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SetUpRecyclerView();

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

               Query query = activityRef.orderBy("nama", Query.Direction.DESCENDING)
                       .startAt(filterDari).endAt(filterSampai);

               FirestoreRecyclerOptions<Students> options = new FirestoreRecyclerOptions.Builder<Students>()
                       .setQuery(query, Students.class)
                       .build();

               adapter = new ListIzinAdapter(options);
               recyclerView.setAdapter(adapter);
            }
        });

    }
    private void SetUpRecyclerView() {
        Query query = activityRef.orderBy("WaktuSignOut", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Students> recyclerOptions = new FirestoreRecyclerOptions.Builder<Students>()
                .setQuery(query, Students.class)
                .build();

        adapter = new ListIzinAdapter(recyclerOptions);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListPerizinanActivity.this, BridgeActivity.class);
        startActivity(intent);
        finish();
    }
}