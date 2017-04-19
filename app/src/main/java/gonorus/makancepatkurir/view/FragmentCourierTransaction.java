package gonorus.makancepatkurir.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.ItemRowTransactionAdapter;
import gonorus.makancepatkurir.model.ItemTransactionModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCourierTransaction extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public FragmentCourierTransaction() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<ItemTransactionModel> list = new ArrayList<>();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_courier_transaction, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.listTransaction);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        list.add(new ItemTransactionModel("gonorus1", "Salatiga", -7.331019, 110.505839, false));
        list.add(new ItemTransactionModel("gonorus2", "Salatiga", -7.319101, 110.498801, false));
        list.add(new ItemTransactionModel("gonorus3", "Salatiga", -7.329317, 110.499831, false));
        list.add(new ItemTransactionModel("gonorus4", "Salatiga", -7.339703, 110.508586, false));
        adapter = new ItemRowTransactionAdapter(list);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}