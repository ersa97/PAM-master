package ersa97.raven.eyeson;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListPerizinanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText editTextFilterDari;
    EditText editTextFilterSampai;
    ImageButton imageButtonSearch;
    Date dateDari;
    Date dateSampai;
    String mDateStart, mDateEnd;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference activityRef = db.collection("Perizinan");
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private ListIzinAdapter adapter;
    private ListSearchIzinAdapter searchAdapter = new ListSearchIzinAdapter();

    private long searchDari, searchSampai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_perizinan);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        editTextFilterDari = findViewById(R.id.tanggaldari);
        editTextFilterSampai = findViewById(R.id.tanggalsampai);
        imageButtonSearch = findViewById(R.id.search);

        recyclerView = findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SetUpRecyclerView();
        editTextFilterDari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDariDateDialog();
            }
        });
        editTextFilterSampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSampaiDateDialog();
            }
        });

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetUpSearchRecyclerView();
            }
        });

    }

    private void showDariDateDialog() {
        SublimePickerFragment pickerFragment = new SublimePickerFragment();
        pickerFragment.setCallback(new SublimePickerFragment.Callback() {
            @Override
            public void onCancelled() {
                Toast.makeText(ListPerizinanActivity.this, "user membatalkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateTimeRecurrenceSet(final SelectedDate selectedDate, int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                mDateStart = formatDate.format(selectedDate.getStartDate().getTime());
                mDateEnd = formatDate.format(selectedDate.getEndDate().getTime());

                editTextFilterDari.setText(mDateStart);
                Date date = selectedDate.getStartDate().getTime();
                searchDari = date.getTime();
            }
        });

        SublimeOptions options = new SublimeOptions();
        options.setCanPickDateRange(true);
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", options);
        pickerFragment.setArguments(bundle);

        pickerFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFragment.show(getSupportFragmentManager(), "SUBLIME_PICKER");
    }

    private void showSampaiDateDialog() {
        SublimePickerFragment pickerFragment = new SublimePickerFragment();
        pickerFragment.setCallback(new SublimePickerFragment.Callback() {
            @Override
            public void onCancelled() {
                Toast.makeText(ListPerizinanActivity.this, "user membatalkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateTimeRecurrenceSet(final SelectedDate selectedDate, int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                mDateStart = formatDate.format(selectedDate.getStartDate().getTime());
                mDateEnd = formatDate.format(selectedDate.getEndDate().getTime());

                editTextFilterSampai.setText(mDateEnd);
                Date date = selectedDate.getStartDate().getTime();
                searchSampai = date.getTime();
            }
        });

        SublimeOptions options = new SublimeOptions();
        options.setCanPickDateRange(true);
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", options);
        pickerFragment.setArguments(bundle);

        pickerFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFragment.show(getSupportFragmentManager(), "SUBLIME_PICKER");
    }


    private void SetUpSearchRecyclerView() {
        // Do Search Logic Here
        final List<Students> students = new ArrayList<>();

        activityRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot snapshots = task.getResult();
                    if (snapshots != null) {
                        for (DocumentSnapshot document : snapshots.getDocuments()) {
                            if (document != null && document.exists()) {
                                Students student = document.toObject(Students.class);
                                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    long waktuKeluar = formatDate.parse(student.getTanggalKeluar()).getTime();
                                    if (waktuKeluar >= searchDari && waktuKeluar <= searchSampai) {
                                        students.add(student);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(ListPerizinanActivity.this, "Error Parsing Waktu Keluar", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ListPerizinanActivity.this, "No such document", Toast.LENGTH_LONG).show();
                            }
                        }

                        searchAdapter.setData(students);
                        recyclerView.setAdapter(searchAdapter);
                    }

                } else {
                    Toast.makeText(ListPerizinanActivity.this, "Search Data Failed", Toast.LENGTH_LONG).show();
                }
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