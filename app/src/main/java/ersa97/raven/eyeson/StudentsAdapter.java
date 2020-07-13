package ersa97.raven.eyeson;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class StudentsAdapter extends FirestoreRecyclerAdapter<Students, StudentsAdapter.StudentHolder>{
    private OnItemClickListener listener;

    public StudentsAdapter(@NonNull FirestoreRecyclerOptions<Students> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull StudentHolder holder, int position, final @NonNull Students model) {
        holder.bind(model);

    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new StudentHolder(view);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();

    }

    class StudentHolder extends RecyclerView.ViewHolder{
        TextView textViewNama;
        TextView textViewAlamat;
        TextView textViewIzin;
        CardView card_item;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            textViewNama = itemView.findViewById(R.id.show_nama);
            textViewAlamat = itemView.findViewById(R.id.show_alamat);
            textViewIzin = itemView.findViewById(R.id.show_izin);
            card_item = itemView.findViewById(R.id.card_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener !=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }

        public void bind(final Students students){
            textViewNama.setText(students.getNama());
            textViewAlamat.setText(students.getAlamat());
            textViewIzin.setText(students.getIzin());
            card_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nama = students.getNama();
                    String deskripsi = students.getDeskripsi();
                    String photo = students.getPhotoUrl();
                    String izin = students.getIzin();
                    Intent intent = new Intent(v.getContext(),StudentDetailActivity.class);
                    intent.putExtra("nama", nama);
                    intent.putExtra("usia", deskripsi);
                    intent.putExtra("photo", photo);
                    intent.putExtra("izin", izin);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickLlistener(OnItemClickListener listener){
        this.listener = listener;
    }

}
