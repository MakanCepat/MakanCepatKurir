package gonorus.makancepatkurir.adapter;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.model.ItemTransactionModel;

/**
 * Created by USER on 1/16/2017.
 */

public class ItemRowTransactionAdapter extends RecyclerView.Adapter<ItemRowTransactionAdapter.RecyclerViewHolder> {
    List<ItemTransactionModel> listTransaction;
    Intent intent, chooser;

    public ItemRowTransactionAdapter(List<ItemTransactionModel> listTransaction) {
        this.listTransaction = listTransaction;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_transaction, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final ItemTransactionModel model = listTransaction.get(position);
        holder.textView1.setText(model.getNama());
        holder.textView2.setText(model.getAlamat());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String label = model.getNama();
                String uriBegin = "geo:"+model.getLat()+","+model.getLon();
                String query = model.getLat()+","+model.getLon()+"(" + label + ")";
                String encodedQuery = Uri.encode( query  );
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );
                intent = new Intent(android.content.Intent.ACTION_VIEW, uri );
                chooser = Intent.createChooser(intent, "Launch Maps");
                view.getContext().startActivity(chooser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTransaction.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1, textView2;
        Button button;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgProses);
            textView1 = (TextView) itemView.findViewById(R.id.txtNama);
            textView2 = (TextView) itemView.findViewById(R.id.txtAlamat);
            button= (Button) itemView.findViewById(R.id.btnMap);
        }
    }
}
