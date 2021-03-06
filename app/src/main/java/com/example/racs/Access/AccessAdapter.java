package com.example.racs.Access;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.MainActivity;
import com.example.racs.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessAdapter extends RecyclerView.Adapter<AccessAdapter.ViewHolder> {

    private final ArrayList<AccessPOJO.AccPOJO> accesses = new ArrayList<>();

    private AppCompatActivity activity;
    private int layoutResource;
    private OnDeleteListener onDeleteListener;

    public AccessAdapter(AppCompatActivity activity, int layoutResource, OnDeleteListener onDeleteListener) {
        this.activity = activity;
        this.layoutResource = layoutResource;
        this.onDeleteListener = onDeleteListener;

    }

    public void replaceAccesses(ArrayList<AccessPOJO.AccPOJO> a) {
        this.accesses.clear();
        this.accesses.addAll(a);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.access_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (accesses.size()>=50 && accesses.size()%50 == 0 && position == accesses.size()-20){
            Access.getAccesses(accesses.size()+50);
            Log.i("Надо", "Ну прост");
        }
        AccessPOJO.AccPOJO access = accesses.get(position);
        holder.bindViews(access);

        /*ServerDate begin = new ServerDate(access.getAccessStart());
        begin.parseDate();
        SimpleDateFormat applFormat = new SimpleDateFormat("dd.MM.yyyy'\n'HH:mm");
        String s_begin = applFormat.format(begin.getServerDate());
        holder.begin.setText(s_begin);

        ServerDate end = new ServerDate(access.getAccessStop());
        end.parseDate();
        String s_end = applFormat.format(end.getServerDate());
        holder.finish.setText(s_end);*/



        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                Log.d("item", String.valueOf(position));
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return accesses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fio;
        TextView aud;
        ImageView del_acc;

        private ViewHolder (View itemview) {
            super(itemview);
            fio = itemview.findViewById(R.id.famNamPat);
            aud = itemview.findViewById(R.id.aud);
            del_acc = itemview.findViewById(R.id.del_acc);
        }

        private void bindViews(AccessPOJO.AccPOJO access){
            fio.setText(AccessPOJO.searchUserById(MainActivity.users.getUsers(), access.getUser()));
            String auditoria = AccessPOJO.searchLockById(MainActivity.locks.getResults(), access.getLock()).substring(0,3);
            aud.setText(auditoria);
            del_acc.setImageResource(R.drawable.ic_delete_black_24dp);
            del_acc.setTag(access.getA_id());
            itemView.setTag(access.getA_id());

            del_acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    onDeleteListener.onDelete(position);

                }
            });
        }
    }

    interface OnDeleteListener {
        void onDelete(int position);
    }
}
