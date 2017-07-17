package ltd.pvt.tagore_6.navigationapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import ltd.pvt.tagore_6.navigationapp.fragments.AboutFragment;
import ltd.pvt.tagore_6.navigationapp.fragments.HomeFragment;
import ltd.pvt.tagore_6.navigationapp.fragments.CandidatesFragment;
import ltd.pvt.tagore_6.navigationapp.fragments.LiveElectionsFragment;
import ltd.pvt.tagore_6.navigationapp.fragments.LoginFragment;
import ltd.pvt.tagore_6.navigationapp.fragments.RegisterFragment;
import ltd.pvt.tagore_6.navigationapp.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity{
    public static NavigationView navigationView;
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    public static Toolbar mtoolbar;
    public static String sheet_uid;
    public static String ind_elec,uid;
    public static String upd_votes;
    public static String upd_party;
    public static Editable uuid=null;
    public static Editable pass;
    public static boolean voted=false;

    TextView headerUid;
    Bundle state;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtoolbar=(Toolbar)findViewById(R.id.nav_action);
        setSupportActionBar(mtoolbar);

        android.app.FragmentManager fm=getFragmentManager();
        android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new HomeFragment());
        //transaction.addToBackStack("Home");
        transaction.commit();
        

        state=savedInstanceState;
        DrawerLayout mDrawerLayout;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView=(NavigationView)findViewById(R.id.design_navigation_view);
        navigationView.getMenu().getItem(0).setCheckable(true);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.getMenu().getItem(0).setEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        android.app.FragmentManager fm=getFragmentManager();
                        android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new HomeFragment());

                        transaction.addToBackStack("Home");
                        transaction.commit();


                        mdrawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.nav_login: {
                        android.app.FragmentManager fm=getFragmentManager();
                        android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new LoginFragment());

                        transaction.addToBackStack("Login");
                        transaction.commit();


                        mdrawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.nav_register: {
                        android.app.FragmentManager fm=getFragmentManager();
                        android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new RegisterFragment());

                        transaction.addToBackStack("Register");
                        transaction.commit();


                        mdrawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.nav_setting: {
                        android.app.FragmentManager fm=getFragmentManager();
                        android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new SettingFragment());

                        transaction.addToBackStack("Setting");

                        transaction.commit();


                        mdrawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.nav_election: {
                        android.app.FragmentManager fm=getFragmentManager();
                        android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new LiveElectionsFragment());

                        transaction.addToBackStack("Live");

                        transaction.commit();


                        mdrawerLayout.closeDrawers();
                        break;
                    }
                    // case blocks for other MenuItems (if any)
                }
               return mtoggle.onOptionsItemSelected(item);
            }
        });
        mdrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        mtoggle=new ActionBarDrawerToggle(this,mdrawerLayout,R.string.open,R.string.close);
        mdrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()) {
            case R.id.tool_setting: {
                android.app.FragmentManager fm=getFragmentManager();
                android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new SettingFragment());
                transaction.addToBackStack("Setting");
                transaction.commit();

                break;
            }
            case R.id.tool_about: {
                android.app.FragmentManager fm=getFragmentManager();
                android.app.FragmentTransaction transaction=fm.beginTransaction().replace(R.id.content_frame,new AboutFragment());
                transaction.addToBackStack("About");
                transaction.commit();

                break;
            }
            case R.id.tool_exit: {
                ActivityCompat.finishAffinity(this);
                break;
            }
            // case blocks for other MenuItems (if any)
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause(){
        super.onPause();

    }
}
