package com.example.grocerieslist.init;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.grocerieslist.R;
import com.example.grocerieslist.background.ProcessCustAsync;
import com.example.grocerieslist.background.ProcessDataAsync;
import com.example.grocerieslist.background.ProcessStockAsync;
import com.example.grocerieslist.db.company.CompanyAccess;
import com.example.grocerieslist.db.company.CompanyClass;
import com.example.grocerieslist.db.stockdetails.StockDetailsAccess;
import com.example.grocerieslist.db.stockdetails.StockDetailsClass;
import com.example.grocerieslist.fragment.CompanyFragment;
import com.example.grocerieslist.fragment.CustomerFragment;
import com.example.grocerieslist.fragment.DashboardFragment;
import com.example.grocerieslist.fragment.LocationFragment;
import com.example.grocerieslist.fragment.PickUpPersonFragment;
import com.example.grocerieslist.fragment.product.PriceListFragment;
import com.example.grocerieslist.fragment.product.ProductFragment;
import com.example.grocerieslist.fragment.SettingsFragment;
import com.example.grocerieslist.fragment.product.ProductTabFragment;
import com.example.grocerieslist.fragment.stock.StockTabFragment;
import com.example.grocerieslist.utilities.AppGlobal;
import com.example.grocerieslist.utilities.CircleTransform;
import com.example.grocerieslist.utilities.Constant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Tejaswi on 22/07/21.
 */
public class SlidingMenu extends AppCompatActivity {
    private static final int PICKFILE_REQUEST_CODE = 1;
    private static final int PICKFILE_REQUEST_CODE_CUST = 2;
    private static final int PICKFILE_REQUEST_CODE_STOCK = 3;

    String TAG = SlidingMenu.class.getSimpleName();
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    AppGlobal global;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_DASHBOARD = "dashboard";
    private static final String TAG_PRICELIST = "pricelist";
    private static final String TAG_FILTER = "filter";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_STOCK = "stock";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_CUSTOMER = "customer";
    private static final String TAG_STORAGELOCATION = "storagelocation";
    private static final String TAG_PICKUP_PERSON = "pickupperson";
    private static final String TAG_COMPANY = "company";
    private static final String TAG_PRODUCT = "products";
    public static String CURRENT_TAG = TAG_DASHBOARD;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();
        global = new AppGlobal(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(CURRENT_TAG.equals(TAG_PRODUCT)){
                    importData(Constant.PRODUCT);
                }else if(CURRENT_TAG.equals(TAG_COMPANY)){
                    Snackbar.make(view, "Replace with your own action "+CURRENT_TAG, Snackbar.LENGTH_LONG).show();
                }else if(CURRENT_TAG.equals(TAG_STORAGELOCATION)){
                    dialogCreate(Constant.PLACE);
                }else if(CURRENT_TAG.equals(TAG_PICKUP_PERSON)){
                    dialogCreate(Constant.PERSON);
                }else if(CURRENT_TAG.equals(TAG_CUSTOMER)){
                    importData(Constant.CUSTOMER);
                }else if(CURRENT_TAG.equals(TAG_STOCK)){
                    Intent newSales = new Intent(SlidingMenu.this,ProductStock.class);
                    startActivity(newSales);
                }else{
                    Snackbar.make(view, "Replace with your own action "+CURRENT_TAG, Snackbar.LENGTH_LONG)
                            .setAction("Action", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Calendar c = Calendar.getInstance();
                                    String msg = "Eashwar Traders goods details of "+c.get(Calendar.DATE)+"/"+(c.get(Calendar.MONTH)+1)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
                                    msg = msg +"\n"+"--------------------------------------------------------------------------------";
                                    msg = msg +"\nName"+"          "+"Type"+"          "+"Quantity"+"          "+"          "+"Remark";
                                    msg = msg +String.format("%20s %20s \r\n", "column 1", "column 2");
                                    msg = msg +String.format("%20s %20s \r\n", "comn 1", "clmn 2");
                                    msg = msg +"\n"+"--------------------------------------------------------------------------------";
                                    global.sendWhatsApp(msg);
                                }
                            }).show();
                }
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASHBOARD;
            loadHomeFragment();
        }
    }

    public void dialogCreate(final String type){
        final Dialog dialog = new Dialog(SlidingMenu.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.createstockdetails);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView title = dialog.findViewById(R.id.csd_title);
        final TextInputEditText name = dialog.findViewById(R.id.csd_name);
        final TextInputEditText address = dialog.findViewById(R.id.csd_address);
        final TextInputEditText state = dialog.findViewById(R.id.csd_state);
        final TextInputEditText number = dialog.findViewById(R.id.csd_number);
        final TextInputEditText pincode = dialog.findViewById(R.id.csd_pincode);
        final TextInputEditText desc = dialog.findViewById(R.id.csd_desc);
        final Spinner status = dialog.findViewById(R.id.csd_status);
        CardView save = dialog.findViewById(R.id.csd_save);
        CardView cancel = dialog.findViewById(R.id.csd_cancel);

        title.setText("Create "+type);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameStr = name.getText().toString().trim();
                String descStr = desc.getText().toString().trim();
                String addStr = address.getText().toString().trim();
                String stateStr = state.getText().toString().trim();
                String pincodeStr = pincode.getText().toString().trim();
                String numStr = number.getText().toString().trim();
                String statusStr = String.valueOf(status.getSelectedItem());

                if(!nameStr.isEmpty()){
                    StockDetailsClass sdc = new StockDetailsClass(nameStr,addStr,stateStr,pincodeStr,numStr,descStr,type,statusStr,"");
                    StockDetailsAccess sda = new StockDetailsAccess(SlidingMenu.this);
                    sda.open();
                    sda.addStockDetails(sdc);
                    sda.close();

                    dialog.dismiss();

                    Toast.makeText(getApplicationContext(),"Successfully added...",Toast.LENGTH_SHORT).show();
                    loadHomeFragment();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void importData(final String cmd){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(SlidingMenu.this);
        builderSingle.setIcon(R.mipmap.logo);
        builderSingle.setTitle("Select new "+cmd+" of create");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SlidingMenu.this, android.R.layout.select_dialog_item);
        arrayAdapter.add(Constant.IMPORT+" "+cmd);
        arrayAdapter.add(Constant.CREATE+" "+cmd);

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                if(strName.equals(Constant.IMPORT+" "+cmd)){
                    Intent mRequestFileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    mRequestFileIntent.setType("text/*");
                    if(cmd.equals(Constant.CUSTOMER))
                        startActivityForResult(mRequestFileIntent, PICKFILE_REQUEST_CODE_CUST);
                    else if(cmd.equals(Constant.PRODUCT))
                        startActivityForResult(mRequestFileIntent, PICKFILE_REQUEST_CODE);
                    else if(cmd.equals(Constant.STOCK))
                        startActivityForResult(mRequestFileIntent, PICKFILE_REQUEST_CODE_STOCK);
                }

            }
        });
        builderSingle.show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICKFILE_REQUEST_CODE) {
            String path = data.getData().getPath();
            Log.i(TAG, "PATH IS " +path);
            ProcessDataAsync pda = new ProcessDataAsync(path,SlidingMenu.this,data);
            pda.execute();
            global.setPreference(global.App_Product_Path,path, Constant.Pref_String,null);
        }else if (requestCode == PICKFILE_REQUEST_CODE_CUST) {
            String path = data.getData().getPath();
            Log.i(TAG, "PATH IS " +path);
            ProcessCustAsync pda = new ProcessCustAsync(path,SlidingMenu.this,data);
            pda.execute();
            global.setPreference(global.App_Customer_Path,path, Constant.Pref_String,null);
        }else if (requestCode == PICKFILE_REQUEST_CODE_STOCK) {
            String path = data.getData().getPath();
            Log.i(TAG, "PATH IS " +path);
            ProcessStockAsync pda = new ProcessStockAsync(path,SlidingMenu.this,data);
            pda.execute();
            global.setPreference(global.App_Stock_Path,path, Constant.Pref_String,null);
        }
    }

    AlertDialog.Builder builderSingle;
    public void selectCompany(){
        builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.mipmap.logo);
        builderSingle.setTitle("Select Company....");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        for(int i=0;i<ccs.size();i++)
            arrayAdapter.add(ccs.get(i).getCompanyName());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                global.setPreference(global.App_Selected_Company,ccs.get(which).getId(),Constant.Pref_String,null);
                loadNavHeader();
            }
        });
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                builderSingle.show();
            }
        });
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    CompanyClass companyClass;
    CompanyAccess ca;
    List<CompanyClass> ccs;
    private void loadNavHeader() {
        // name, website
        String name = "",gst="";
        CompanyAccess ca = new CompanyAccess(SlidingMenu.this);
        if(global.getPreference(global.App_Selected_Company) != null){
            String ccid = String.valueOf(global.getPreference(global.App_Selected_Company));
            ca.open();
            companyClass = ca.getCompanyDetails(ccid);
            ca.close();

            Log.i(TAG,"company details "+companyClass.toString());
            name = companyClass.getCompanyName();
            gst = companyClass.getCompanyOwner();

        }else{
            ca.open();
            ccs = ca.getCompaniesDetails();
            ca.close();
            selectCompany();
        }
        txtName.setText(name);
        txtWebsite.setText(gst);

        // loading header background image
        Glide.with(this).load(R.drawable.sales_background)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(R.mipmap.logo)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        selectNavMenu();
        setToolbarTitle();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            toggleFab();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                DashboardFragment homeFragment = new DashboardFragment();
                return homeFragment;
            case 1:
                ProductTabFragment productTabFragment = new ProductTabFragment();
                return productTabFragment;
            case 2:
                CustomerFragment customerFragment = new CustomerFragment();
                return customerFragment;
            case 3:
                CompanyFragment companyFragment = new CompanyFragment();
                return companyFragment;
            case 4:
                LocationFragment locationFragment = new LocationFragment();
                return locationFragment;
                /*FilterFragment photosFragment = new FilterFragment();
                return photosFragment;*/
            case 5:
                StockTabFragment stockTabFragment = new StockTabFragment();
                return stockTabFragment;
                /*NotificationsFragment notificationsFragment = new NotificationsFragment();
                return notificationsFragment;*/
            case 6:
                PriceListFragment moviesFragment = new PriceListFragment();
                return moviesFragment;
            case 7:
                PickUpPersonFragment pickUpPersonFragment = new PickUpPersonFragment();
                return pickUpPersonFragment;
            case 8:
                SettingsFragment settingsFragment = new SettingsFragment();
                return settingsFragment;
            default:
                return new DashboardFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                /**
                 * 0-- DashBoard                4-- Storage Location        8-- Settings
                 * 1-- Products                 5-- Product Stock           9--
                 * 2-- Customer                 6-- Price List              10-
                 * 3-- Company                  7-- Pick Up Person          11-
                 */
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        break;
                    case R.id.nav_product:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PRODUCT;
                        break;
                    case R.id.nav_customer:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CUSTOMER;
                        break;
                    case R.id.nav_company:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_COMPANY;
                        break;
                    case R.id.nav_storage_location:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_STORAGELOCATION;
                        break;
                    case R.id.nav_produt_stock:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_STOCK;
                        break;
                    case R.id.nav_pricelist:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_PRICELIST;
                        break;
                    case R.id.nav_pickup_person:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_PICKUP_PERSON;
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(SlidingMenu.this, Dashboard.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(SlidingMenu.this, MainActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASHBOARD;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent dash = new Intent(SlidingMenu.this,Dashboard.class);
            startActivity(dash);
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0 || navItemIndex == 1 || navItemIndex == 2 ||
                navItemIndex == 3 || navItemIndex == 4 || navItemIndex == 7 || navItemIndex == 5)
            fab.show();
        else
            fab.hide();
    }

}
