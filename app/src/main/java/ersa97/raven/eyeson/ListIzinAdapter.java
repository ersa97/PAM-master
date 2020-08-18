package ersa97.raven.eyeson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListIzinAdapter extends FirestoreRecyclerAdapter <Students, ListIzinAdapter.ListIzinHolder>{

    private Context context1;
    private ArrayList<Students> studentsArrayList;
    private Context context;


    public ListIzinAdapter(@NonNull FirestoreRecyclerOptions<Students> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull ListIzinHolder listIzinHolder, int i, @NonNull Students students) {


        listIzinHolder.textViewNama.setText(students.getNama());
        listIzinHolder.textViewUstad.setText(students.getUstad());
        listIzinHolder.textViewKelas.setText(students.getKelas());
        listIzinHolder.textViewWaktuIzinKeluar.setText(students.getWaktuKeluar());
        listIzinHolder.textViewWaktuIzinMasuk.setText(students.getWaktuMasuk());
        listIzinHolder.textViewTanggalKeluar.setText(students.getTanggalKeluar());
        listIzinHolder.textViewTanggalMasuk.setText(students.getTanggalMasuk());

        if (students.getWaktuSignOut() == null) {
            listIzinHolder.textViewWaktuKeluar.setText("santri belum keluar");
        }
        if (students.getWaktuSignIn() == null) {
            listIzinHolder.textViewWaktuMasuk.setText("santri belum kembali");
        } else {
            listIzinHolder.textViewWaktuKeluar.setText(students.getWaktuSignOut().toDate().toString());
            listIzinHolder.textViewWaktuMasuk.setText(students.getWaktuSignIn().toDate().toString());

            Date dateOut = students.getWaktuSignOut().toDate();
            Date dateIn = students.getWaktuSignIn().toDate();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");

            listIzinHolder.textViewWaktuKeluar.setText(formatter.format(dateOut));
            listIzinHolder.textViewWaktuMasuk.setText(formatter.format(dateIn));
        }
    }

    @NonNull
    @Override
    public ListIzinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_perizinan,
                parent, false);
        return new ListIzinHolder(view);
    }

    class ListIzinHolder extends RecyclerView.ViewHolder{

        TextView textViewNama;
        TextView textViewKelas;
        TextView textViewUstad;
        TextView textViewWaktuIzinKeluar;
        TextView textViewWaktuIzinMasuk;
        TextView textViewTanggalKeluar;
        TextView textViewTanggalMasuk;
        TextView textViewWaktuKeluar;
        TextView textViewWaktuMasuk;

        public ListIzinHolder(@NonNull View itemView) {
            super(itemView);

            textViewNama = itemView.findViewById(R.id.show_nama);
            textViewKelas = itemView.findViewById(R.id.show_alamat);
            textViewUstad = itemView.findViewById(R.id.ustad_pengizin);
            textViewWaktuIzinKeluar = itemView.findViewById(R.id.waktu_keluar_izin);
            textViewWaktuIzinMasuk = itemView.findViewById(R.id.waktu_masuk_izin);
            textViewTanggalKeluar = itemView.findViewById(R.id.tanggal_keluar_izin);
            textViewTanggalMasuk = itemView.findViewById(R.id.tanggal_masuk_izin);
            textViewWaktuKeluar = itemView.findViewById(R.id.waktu_keluar);
            textViewWaktuMasuk = itemView.findViewById(R.id.waktu_masuk);
        }
    }
}
