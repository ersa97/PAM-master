package ersa97.raven.eyeson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListSearchIzinAdapter extends RecyclerView.Adapter<ListSearchIzinAdapter.ViewHolder> {

    private List<Students> dataList;

    ListSearchIzinAdapter() {
    }

    void setData(List<Students> data) {
        dataList = data;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListSearchIzinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListSearchIzinAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_perizinan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListSearchIzinAdapter.ViewHolder holder, int position) {
        holder.bind(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView textViewNama;
        private TextView textViewKelas;
        private TextView textViewUstad;
        private TextView textViewWaktuIzinKeluar;
        private TextView textViewWaktuIzinMasuk;
        private TextView textViewTanggalKeluar;
        private TextView textViewTanggalMasuk;
        private TextView textViewWaktuKeluar;
        private TextView textViewWaktuMasuk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
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

        void bind(Students student) {
            this.textViewNama.setText(student.getNama());
            this.textViewUstad.setText(student.getUstad());
            this.textViewKelas.setText(student.getKelas());
            this.textViewWaktuIzinKeluar.setText(student.getWaktuKeluar());
            this.textViewWaktuIzinMasuk.setText(student.getWaktuMasuk());
            this.textViewTanggalKeluar.setText(student.getTanggalKeluar());
            this.textViewTanggalMasuk.setText(student.getTanggalMasuk());

            if (student.getWaktuSignOut() == null) {
                this.textViewWaktuKeluar.setText("santri belum keluar");
            }
            if (student.getWaktuSignIn() == null) {
                this.textViewWaktuMasuk.setText("santri belum kembali");
            } else {
                this.textViewWaktuKeluar.setText(student.getWaktuSignOut().toDate().toString());
                this.textViewWaktuMasuk.setText(student.getWaktuSignIn().toDate().toString());

                Date dateOut = student.getWaktuSignOut().toDate();
                Date dateIn = student.getWaktuSignIn().toDate();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:MM:SS");

                this.textViewWaktuKeluar.setText(formatter.format(dateOut));
                this.textViewWaktuMasuk.setText(formatter.format(dateIn));
            }
        }
    }
}
