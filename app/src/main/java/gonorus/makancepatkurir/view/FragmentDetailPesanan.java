package gonorus.makancepatkurir.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.ItemPesananDetailAdapter;
import gonorus.makancepatkurir.adapter.SimpleDividerItemDecoration;
import gonorus.makancepatkurir.model.ModelPesananDetail;
import gonorus.makancepatkurir.model.ModelResponsePesanan;

public class FragmentDetailPesanan extends DialogFragment {

    private List<ModelResponsePesanan> responsePesanan;
    private TextView textView, catatanpesananTV;
    private Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_pesanan, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        textView = (TextView) v.findViewById(R.id.txtDialogTitle);
        catatanpesananTV = (TextView) v.findViewById(R.id.catatanpesananTV);
        button = (Button) v.findViewById(R.id.btnRoute);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String label = responsePesanan.get(0).getWarung_nama();
                String uriBegin = "geo:" + responsePesanan.get(0).getWarung_lattitude() + "," + responsePesanan.get(0).getWarung_longitude();
                String query = responsePesanan.get(0).getWarung_lattitude() + "," + responsePesanan.get(0).getWarung_longitude() + "(" + label + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                Intent chooser = Intent.createChooser(intent, "Launch Maps");
                v.getContext().startActivity(chooser);
            }
        });
        textView.setText("Detail Pesanan");

        String catatan = "\n";
        for (ModelResponsePesanan temp : responsePesanan) {
            catatan = catatan + temp.getDetail_note() + ",\n";
        }
        catatan = replaceLast(catatan, ",", "");
        catatanpesananTV.setText("Catatan : " + catatan.trim());

        List<ModelPesananDetail> items = new ArrayList<ModelPesananDetail>();
        int sum = 0;
        for (int i = 0; i < responsePesanan.size(); i++) {
            items.add(new ModelPesananDetail(responsePesanan.get(i).getMenu_nama(), responsePesanan.get(i).getDetail_qty()));
            sum += sum + (responsePesanan.get(i).getMenu_harga() * responsePesanan.get(i).getDetail_qty());
        }
        items.add(new ModelPesananDetail("Total : ", sum));

        ItemPesananDetailAdapter itemRvAdapter = new ItemPesananDetailAdapter(items);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(v.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemRvAdapter);


        return v;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    public void setResponsePesanan(List<ModelResponsePesanan> responsePesanan) {
        this.responsePesanan = responsePesanan;
    }

    private String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1)
            return string;
        return string.substring(0, index) + replacement
                + string.substring(index + substring.length());
    }
}
