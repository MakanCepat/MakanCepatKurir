package gonorus.makancepatkurir.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import gonorus.makancepatkurir.R;
import gonorus.makancepatkurir.adapter.ItemDrawerAdapter;
import gonorus.makancepatkurir.customutil.SessionManager;
import gonorus.makancepatkurir.model.ModelKurir;
import gonorus.makancepatkurir.rest.Communicator;
import gonorus.makancepatkurir.rest.LoginInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHome extends AppCompatActivity {

    private boolean doubleBackToExitPressedOnce = false;

    View drawerView;
    DrawerLayout drawer;
    ItemDrawerAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private int lastExpandedPosition = -1;
    private int lastMenuClickedPosition = -1;
    NavigationView navigationView;
    TextView txtTitleBar;
    CircleImageView profileImage;
    boolean menuClickedOrNot = false;
    SessionManager sessionManager;
    private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView notificationIcon = (ImageView) findViewById(R.id.btnNotifAppBar);
        notificationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(3);
            }
        });

        txtTitleBar = (TextView) findViewById(R.id.txtTitleAppBar);
        setMyTitleBar("Makan Cepat");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        expListView = (ExpandableListView) findViewById(R.id.drawerlist);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        prepareListData();
        selectItem(0);

        listAdapter = new ItemDrawerAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setItemChecked(0, true);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {

                TextView txt = (TextView) view.findViewById(R.id.labelListDrawer);
                if (groupPosition != 11) {
                    int index = expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(groupPosition));
                    expandableListView.setItemChecked(index, true);
                    /*if (groupPosition != lastMenuClickedPosition && lastMenuClickedPosition == -1) {
                        txt.setTextColor(getResources().getColor(R.color.list_text_color));
                    } else txt.setTextColor(getResources().getColor(R.color.colorPrimary));
                    */
                    selectItem(groupPosition);
                    menuClickedOrNot = true;
                } else {
                    //txt.setTextColor(getResources().getColor(R.color.list_text_color));
                    menuClickedOrNot = false;
                }

                lastMenuClickedPosition = groupPosition;
                return menuClickedOrNot;
            }
        });
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);
                return false;
            }
        });
        View headerview = navigationView.getHeaderView(0);
        profileImage = (CircleImageView) headerview.findViewById(R.id.profile_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityHome.this, ActivityDetailProfile.class);
                startActivity(intent);
            }
        });

        sessionManager = new SessionManager(getApplicationContext());
        TextView txtUsername = (TextView) headerview.findViewById(R.id.nav_header_txt_username);
        txtUsername.setText(sessionManager.getKurirDetails().get(SessionManager.KEY_NAME));
        try {
            if (!(sessionManager.getKurirDetails().get(SessionManager.KEY_FOTO)).trim().isEmpty())
                Picasso.with(getApplicationContext()).load(Communicator.BASE_URL + "kurir/images/profile/" + sessionManager.getKurirDetails().get(SessionManager.KEY_FOTO)).skipMemoryCache().into(profileImage);
        } catch (NullPointerException NPE) {
            // Null Pointer Exception did not find any picture
        }

        //FirebaseMessaging.getInstance().subscribeToTopic("MakanCepatKurir");
        //FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (temp == 1 || temp == 3) {
            selectItem(4);
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Checking for fragment count on backstack
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                super.onBackPressed();
                //return;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menuHomeCart) {
            Intent intent = new Intent(getApplicationContext(), ActivitySearchResult.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.menuHomeSearch) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /***********************************************************************************************
     * ACTIVITY METHOD CONTROL
     ***********************************************************************************************/
    /*
    * Set Title Activity
    * */
    public void setMyTitleBar(String title) {
        txtTitleBar.setText(title);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding Header data
        listDataHeader.add("Halaman Depan");
        //listDataHeader.add("Transaksi");
        listDataHeader.add("Log Out");

        // Header, Child data
        listDataChild.put(listDataHeader.get(0), new ArrayList<String>());
        //listDataChild.put(listDataHeader.get(1), new ArrayList<String>());
        listDataChild.put(listDataHeader.get(1), new ArrayList<String>());
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        temp = position;
        // Create a new fragment and specify the planet to show based on position
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new FragmentHalamanDepan();
                break;
            /*
            case 1:
                fragment = new FragmentCourierTransaction();
                break;
                */
            case 3:
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                fragment = new FragmentNotification();
                break;
            case 4:
                fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_to_right);
                fragment = new FragmentHalamanDepan();
                break;
            /*
            case 4:
                fragment = new DompetkuFragment();
                break;
            case 5:
                fragment = new TransaksiFragment();
                break;
            case 6:
                fragment = new LanggananFragment();
                break;
                */
            default:
                fragment = new FragmentHalamanDepan();
                break;
        }

        // Insert the fragment by replacing any existing fragment
        fragmentTransaction
                .replace(R.id.content_frame, fragment)
                .commit();

        if (position == 1) showLogoutDialog();

        drawer.closeDrawer(GravityCompat.START);
    }

    public void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
        // Add the buttons
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                LoginInterface apiService = Communicator.getClient().create(LoginInterface.class);
                Call<ModelKurir> user = apiService.checkLogout(sessionManager.getKurirDetails().get(SessionManager.KEY_EMAIL));
                user.enqueue(new Callback<ModelKurir>() {
                    @Override
                    public void onResponse(Call<ModelKurir> call, Response<ModelKurir> response) {
                        // SUCCEED TO LOGOUT

                        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        finish();
                        sessionManager.logoutUser();
                    }

                    @Override
                    public void onFailure(Call<ModelKurir> call, Throwable t) {
                        // FAILED TO LOGOUT
                    }
                });
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage("Yakin ingin keluar?").setTitle("Konfirmasi");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
