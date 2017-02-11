package gonorus.makancepatkurir.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.ImageLoginAdapter;
import gonorus.makancepatkurir.customutil.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMainLogin extends Fragment implements View.OnClickListener {
    private ViewPager _mViewPager;
    private ImageLoginAdapter _adapter;
    private ImageView _btn1, _btn2, _btn3, imgHeader;
    private TextView txtTitle, txtSubTitle;
    private Button btnDaftar, btnMasuk;
    private SessionManager sessionManager;
    private int REQUEST_DAFTAR_ACTIVITY = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_login, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
        setTab();
        onCircleButtonClick();
    }

    private void initView() {
        _btn1 = (ImageView) getView().findViewById(R.id.btn1);
        _btn1.setImageResource(R.drawable.fill_circle);
        _btn2 = (ImageView) getView().findViewById(R.id.btn2);
        _btn3 = (ImageView) getView().findViewById(R.id.btn3);

        txtTitle = (TextView) getView().findViewById(R.id.txtHeaderLogin);
        txtSubTitle = (TextView) getView().findViewById(R.id.txtSubHeaderLogin);
        txtTitle.setText("Pesan Antar");
        txtSubTitle.setText("Pesan Makanan Kesukaanmu \ndan resto favoritmu");

        btnMasuk = (Button) getView().findViewById(R.id.btnMasuk);
        btnMasuk.setOnClickListener(this);
        btnDaftar = (Button) getView().findViewById(R.id.btnActivityDaftar);
        btnDaftar.setOnClickListener(this);

        imgHeader = (ImageView) getView().findViewById(R.id.imgHeaderLogin);
        imgHeader.setImageResource(R.drawable.pesan_antar);

        sessionManager = new SessionManager(getActivity());
    }

    private void setTab() {
        _mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int position) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                _btn1.setImageResource(R.drawable.holo_circle);
                _btn2.setImageResource(R.drawable.holo_circle);
                _btn3.setImageResource(R.drawable.holo_circle);
                btnAction(position);
            }

        });
    }

    private void onCircleButtonClick() {
        _btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _btn1.setImageResource(R.drawable.fill_circle);
                _mViewPager.setCurrentItem(0);
            }
        });

        _btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _btn2.setImageResource(R.drawable.fill_circle);
                _mViewPager.setCurrentItem(1);
            }
        });
        _btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _btn3.setImageResource(R.drawable.fill_circle);
                _mViewPager.setCurrentItem(2);
            }
        });
    }

    private void setUpView() {
        _mViewPager = (ViewPager) getView().findViewById(R.id.imageviewPager);
        _adapter = new ImageLoginAdapter(getActivity(), getFragmentManager());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);
        initView();
    }

    private void btnAction(int action) {
        switch (action) {
            case 0:
                _btn1.setImageResource(R.drawable.fill_circle);
                txtTitle.setText("Pesan Antar");
                txtSubTitle.setText("Pesan Makanan Kesukaanmu \ndan resto favoritmu");
                imgHeader.setImageResource(R.drawable.pesan_antar);
                break;
            case 1:
                _btn2.setImageResource(R.drawable.fill_circle);
                txtTitle.setText("Relasi");
                txtSubTitle.setText("Memiliki ribuan relasi dan \nsaling menguntungkan");
                imgHeader.setImageResource(R.drawable.relasi);
                break;
            case 2:
                _btn3.setImageResource(R.drawable.fill_circle);
                txtTitle.setText("Bonus");
                txtSubTitle.setText("Dapatkan bonus setiap hari \ndan cairkan bonusmu");
                imgHeader.setImageResource(R.drawable.bonus);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnDaftar) {
            Intent intent = new Intent(getActivity(), ActivityRegistration.class);
            getActivity().finish();
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            //startActivityForResult(intent, REQUEST_DAFTAR_ACTIVITY);
        }

        if (view == btnMasuk) {
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(getActivity(), ActivityHome.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getActivity(), ActivityLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().finish();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
            getActivity().finish();
        }
    }
}