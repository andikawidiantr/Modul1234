package id.progmob.modul34;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {

    private Context context;
    private Cursor cursor;
    private OnClickListenerBiodata listenerBiodata;
//    long id;
private BiodataDatabase dbcenter;

    public DataAdapter(Context mcontext, Cursor mcursor) {
        this.context = mcontext;
        this.cursor = mcursor;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.content,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)){
            return;
        }
        String name = cursor.getString(cursor.getColumnIndex(BiodataDatabase.nama));
        holder.tNama.setText(name);

        String nim1 = cursor.getString(cursor.getColumnIndex(BiodataDatabase.nim));
        holder.tNim.setText(nim1);

        long id = cursor.getLong(cursor.getColumnIndex(BiodataDatabase.nim));
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder  {

        private TextView tNim, tNama, tJK, tHobby, tUmur;
        ImageButton imageButton;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            tNama = itemView.findViewById(R.id.nama_nya);
            tNim = itemView.findViewById(R.id.nim_nya);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long position = (long) itemView.getTag();
                    listenerBiodata.onItemClickBiodata(position);
                }
            });
        }
    }

    public interface OnClickListenerBiodata{
        void onItemClickBiodata(long id);
    }

    public void setOnItemClickListenerBiodata(OnClickListenerBiodata listenerBiodata){
        this.listenerBiodata = listenerBiodata;
    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null){
            cursor.close();
        }
        cursor = newCursor;

        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }

}



