package ersa97.raven.eyeson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ListIzinAdapter extends FirestoreRecyclerAdapter <Students, ListIzinAdapter.ListIzinHolder>{
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ListIzinAdapter(@NonNull FirestoreRecyclerOptions<Students> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ListIzinHolder listIzinHolder, int i, @NonNull Students students) {


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
        TextView textViewWaktuKeluar;
        TextView textViewWaktuMasuk;

        public ListIzinHolder(@NonNull View itemView) {
            super(itemView);

            textViewNama = itemView.findViewById(R.id.show_nama);
            textViewKelas = itemView.findViewById(R.id.show_alamat);
            textViewUstad = itemView.findViewById(R.id.ustad_pengizin);
            textViewWaktuIzinKeluar = itemView.findViewById(R.id.waktu_keluar_izin);
            textViewWaktuIzinMasuk = itemView.findViewById(R.id.waktu_masuk_izin);
            textViewWaktuKeluar = itemView.findViewById(R.id.waktu_keluar);
            textViewWaktuMasuk = itemView.findViewById(R.id.waktu_masuk);
        }
    }
}
