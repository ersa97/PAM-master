package ersa97.raven.eyeson;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;

public class ListPerizinanActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference activityRef = db.collection("Perizinan");
    RecyclerView recyclerView;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    EditText editTextFilterDari;
    EditText editTextFilterSampai;

    ImageButton imageButtonSearch;

    Button buttonDatePicker;

    Date dateDari;
    Date dateSampai;

    String mDateStart, mDateEnd;


    private ListIzinAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_perizinan);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);


        editTextFilterDari = findViewById(R.id.tanggaldari);
        editTextFilterSampai = findViewById(R.id.tanggalsampai);
        imageButtonSearch = findViewById(R.id.search);
        buttonDatePicker = findViewById(R.id.btn_datepicker);

        recyclerView = findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SetUpRecyclerView();


        buttonDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*              String filterDari = editTextFilterDari.getText().toString();
                String filterSampai = editTextFilterSampai.getText().toString();

                Date dateDari = null;
                Date dateSampai = null;
                try {
                    dateDari = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(filterDari);
                    dateSampai = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).parse(filterSampai);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Timestamp timestampDari = new Timestamp(dateDari);
                Timestamp timestampSampai = new Timestamp(dateSampai);

                Query query = activityRef.orderBy("WaktuSignOut", Query.Direction.DESCENDING);

                FirestoreRecyclerOptions<Students> recyclerOptions = new FirestoreRecyclerOptions.Builder<Students>()
                        .setQuery(query, Students.class)
                        .build();

                adapter = new ListIzinAdapter(recyclerOptions);
                recyclerView.setAdapter(adapter);

*/
            }
        });

    }

    private void showDateDialog() {
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
                editTextFilterSampai.setText(mDateEnd);
            }
        });

        SublimeOptions options = new SublimeOptions();
        options.setCanPickDateRange(true);
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", options);
        pickerFragment.setArguments(bundle);

        pickerFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        pickerFragment.show(getSupportFragmentManager(),"SUBLIME_PICKER");
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