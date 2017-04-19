package gonorus.makancepatkurir.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.model.ModelItemPesanan;
import gonorus.makancepatkurir.model.ModelResponsePesanan;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.view.FragmentDetailPesanan;

/**
 * Created by USER on 2/10/2017.
 */

public class ItemPesananAdapter extends RecyclerView.Adapter<ItemPesananAdapter.MyViewHolder> {

    private List<ModelResponsePesanan> transactions;
    private List<ModelItemPesanan> pesananList;
    private AppCompatActivity appCompatActivity;

    public ItemPesananAdapter(List<ModelResponsePesanan> transactions, List<ModelItemPesanan> pesananList, AppCompatActivity appCompatActivity) {
        this.transactions = transactions;
        this.pesananList = pesananList;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.namarestoTV.setText(pesananList.get(position).getNama_warung());
        holder.notelprestoTV.setText(pesananList.get(position).getAlamat_warung());
        holder.detailPesananTV.setText("Detail Pesanan");
        try {
            if (!pesananList.get(position).getLogo_warung().trim().isEmpty())
                Picasso.with(holder.itemView.getContext()).load(Communicator.BASE_URL + pesananList.get(position).getLogo_warung()).skipMemoryCache().into(holder.imageView);
        } catch (NullPointerException NPE) {
            // Null Pointer Exception did not find any picture
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ModelResponsePesanan> responsePesanan = new ArrayList<ModelResponsePesanan>();
                for (ModelResponsePesanan temp : transactions) {
                    if (temp.getWarung_nama().equals(pesananList.get(position).getNama_warung()))
                        responsePesanan.add(temp);
                }
                FragmentManager manager = appCompatActivity.getSupportFragmentManager();
                FragmentDetailPesanan detailOrder = new FragmentDetailPesanan();
                detailOrder.setResponsePesanan(responsePesanan);
                detailOrder.show(manager, "asu");
            }
        });
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView namarestoTV;
        TextView notelprestoTV;
        TextView detailPesananTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            namarestoTV = (TextView) itemView.findViewById(R.id.namaresto);
            notelprestoTV = (TextView) itemView.findViewById(R.id.notelpresto);
            detailPesananTV = (TextView) itemView.findViewById(R.id.detailpesananTV);
        }
    }
}