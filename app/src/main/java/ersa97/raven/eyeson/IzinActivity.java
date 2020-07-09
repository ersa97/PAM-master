package ersa97.raven.eyeson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class IzinActivity extends AppCompatActivity {

    TextView textViewId;
    Button btnScan;

    public static final String ID = "id_student";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

        textViewId = findViewById(R.id.txt_id);

        if(!textViewId.equals(null)){
            String id = getIntent().getStringExtra(ID);
            textViewId.setText(id);
        }

        setUp();


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

}

