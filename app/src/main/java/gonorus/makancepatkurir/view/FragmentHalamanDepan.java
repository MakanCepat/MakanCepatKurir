package gonorus.makancepatkurir.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gonorus.makancepatkurir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHalamanDepan extends Fragment {


    public FragmentHalamanDepan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_halaman_depan, container, false);
        return rootView;
    }

}
