package gonorus.makancepatkurir.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.model.ModelPesananDetail;

/**
 * Created by USER on 2/10/2017.
 */

public class ItemPesananDetailAdapter  extends RecyclerView.Adapter<ItemPesananDetailAdapter.itemViewHolder>{

    List<ModelPesananDetail> items ;

    public ItemPesananDetailAdapter(List<ModelPesananDetail> items) {
        this.items = items;
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesanan_detail, parent, false);
        return new itemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, int position) {
        holder.namaitemTV.setText(items.get(position).getNamaItem());
        holder.totalitemTV.setText(items.get(position).getTotal()+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class itemViewHolder extends RecyclerView.ViewHolder{
        TextView namaitemTV;
        TextView totalitemTV;

        public itemViewHolder(View itemView) {
            super(itemView);
            namaitemTV = (TextView) itemView.findViewById(R.id.txtNamaItem);
            totalitemTV = (TextView) itemView.findViewById(R.id.txtJumlahItem);
        }
    }
}
