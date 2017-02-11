package gonorus.makancepatkurir.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import gonorus.makancepatkurir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransaksiKurir extends Fragment {

    private TableLayout tableLayout = null;

    public FragmentTransaksiKurir() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_transaksi_kurir, container, false);
        tableLayout = (TableLayout) rootView.findViewById(R.id.TablePemesanan);

        pemesanan();
        return rootView;
    }

    private void pemesanan() {
        for (int i = 1; i < 4; i++) {
            TextView Restoran = new TextView(this.getContext());
            Restoran.setBackgroundResource(R.drawable.cell_shape);
            Restoran.setText("Restoran "+i);
            tableLayout.addView(Restoran);
            for (int j = 1; j < i + 1; j++) {
                TableRow tableRow = new TableRow(this.getContext());
                TextView Pesanan = new TextView(this.getContext());
                Pesanan.setGravity(Gravity.CENTER);
                Pesanan.setBackgroundResource(R.drawable.cell_shape);
                TextView Jumlah = new TextView(this.getContext());
                Jumlah.setGravity(Gravity.CENTER);
                Jumlah.setBackgroundResource(R.drawable.cell_shape);
                TextView Harga = new TextView(this.getContext());
                Harga.setGravity(Gravity.CENTER);
                Harga.setBackgroundResource(R.drawable.cell_shape);
                TextView Pesan = new TextView(this.getContext());
                Pesan.setGravity(Gravity.CENTER);
                Pesan.setBackgroundResource(R.drawable.cell_shape);

                Pesanan.setText("Menu "+j);
                tableRow.addView(Pesanan);
                Jumlah.setText("Jumlah "+j);
                tableRow.addView(Jumlah);
                Harga.setText("Harga "+j);
                tableRow.addView(Harga);
                Pesan.setText("Pesan "+j);
                tableRow.addView(Pesan);
                tableLayout.addView(tableRow);
            }
        }
    }
}
