package se62120.fpt.edu.vn.iattendance.views.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import se62120.fpt.edu.vn.iattendance.R;
import se62120.fpt.edu.vn.iattendance.configures.config;
import se62120.fpt.edu.vn.iattendance.views.fragments.AccountTeacherFragment;
import se62120.fpt.edu.vn.iattendance.views.fragments.MessageTeacherFragment;
import se62120.fpt.edu.vn.iattendance.views.fragments.ReportsFragment;
import se62120.fpt.edu.vn.iattendance.views.fragments.ScheduleTeacherFragment;

public class NavigationTeacherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int role;
    // information of user in DrawerNavigation
    @BindView(R.id.tvUserName)
    TextView _tvUserName;
    @BindView(R.id.tvUserEmail)
    TextView _tvUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.share_preference),
                Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "Not found!");
        String username = sharedPreferences.getString("username", "Not found!");
        role = sharedPreferences.getInt("role", -1);

        Log.v(config.AppTag," Navigation Token :" + token + "\n Username:" + username);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = nav_view.getHeaderView(0);

        ButterKnife.bind(this, headerLayout);
        _tvUserName.setText("Sir");
        _tvUserEmail.setText(username);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new ScheduleTeacherFragment());
        ft.commit();

        //getUserInformation();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void getUserInformation() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int role = extras.getInt("role");
            String name = extras.getString("name");
            String email = extras.getString("email");
            String token = extras.getString("token");
            _tvUserName.setText(name);
            _tvUserEmail.setText(email);
        } else {
            Toast.makeText(getApplicationContext(), "No extra", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navTeacherSchedule) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new ScheduleTeacherFragment());
            ft.commit();
            // Handle the camera action
        } else if (id == R.id.navTeacherReport) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new ReportsFragment());
            ft.commit();
        } else if (id == R.id.navTeacherMessage) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new MessageTeacherFragment());
            ft.commit();
        } else if (id == R.id.navTeacherAccount) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new AccountTeacherFragment());
            ft.commit();
        } else if (id == R.id.navLogOut) {
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.I_ATTENDANCE_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", null);
            editor.commit();
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoReportsFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, new ReportsFragment());
        ft.commit();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
