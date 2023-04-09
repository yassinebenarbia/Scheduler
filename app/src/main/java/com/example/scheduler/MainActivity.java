package com.example.scheduler;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import com.example.scheduler.ui.main.SectionsPagerAdapter;
import com.example.scheduler.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

/**
 * main activity stuff 4n
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MaterialToolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    private Menu menu;
    ViewPager viewPager;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private  Menu bodyMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        syncDrawerAndToolbar();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);

        // Listening to the current page the user is on, on the PageAdapter
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            // The position is a number represent the order of the current page
            // with respect to all the pages putted in the viewPager
            @Override
            public void onPageSelected(int position) {
                Drawable navigationIcon = toolbar.getNavigationIcon();
                switch (position) {
                    case 0:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        break;
                    default:
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        // the floating button stuff
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            // requesting the current hour and minute of the day
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(calendar.HOUR_OF_DAY);
            int minute= calendar.get(calendar.MINUTE);
            @Override
            public void onClick(View view) {
                // TODO: Add the alarm to the local storage
                // TODO: refreshing the UI such that the new alarm will geta displayed
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // TODO: Store the Alarm in order to show it later
                                onTimeChosen(hourOfDay,minute);
                            }
                        },hour,minute,false);
                timePickerDialog.show();
            }
        });

        // setting the menu
        navigationView = binding.navigationView;

        menu = navigationView.getMenu();

        MenuItem menuItem =  menu.getItem(0);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                navigationView.inflateMenu(R.menu.body);
                // show an interface for the user to custimize the
                // cycle, store it then show it
                menu.add("helo");
                ItemListDialogFragment.newInstance(1).show(getSupportFragmentManager(),"dialog");
                Log.i("menu size",String.valueOf(menu.size()));
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.body,menu);
        bodyMenu=menu;
        System.out.println(bodyMenu.size());
        System.out.println("hello there");
        return super.onCreateOptionsMenu(menu);
    }

    public void addItem(){}
    // this method creates a notification channel for that the AlarmReceiver to handle
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O ){
            String channelName = "scheduleChannelName";
            String description = "aDescriptionOfTheSchedulerNotificationChannel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("ScheduledAlarm",channelName,importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager  = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    // Scheduling an alarm after the user chose the time
    public void onTimeChosen (int hour,int minute){
        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.setAction("com.example.scheduler");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),25242221, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // getting the selected time and appending them in a Calendar object
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,1);
        long alarmTime = calendar.getTimeInMillis();

        Alarm alarm = new Alarm(UUID.randomUUID().toString(),
                true,
                alarmTime,
                "1,2,3,4");

        // storing the chosen alarm on DB
        storeChosenAlarm(alarm);

        //setting timer section "may be discussed later"
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    //  after the user chooses the time he want to use this function will be executed
    //  storing the chosen Alarm goes here
    public void storeChosenAlarm(Alarm alarm){
        AlarmDBManager alarmDBManager = new AlarmDBManager(getBaseContext());
        try {
            alarmDBManager.open();
            // need to be able to retrieve the Cycle that the alarm belongs to
//            alarmDBManager.addAlarm(alarm,);

        } catch (SQLException e) {
            System.out.println("Could not open DB");
            throw new RuntimeException(e);
        }
        alarmDBManager.close();
    }

    // synchronizing between the DrawerLayout and the Toolbar
    public void syncDrawerAndToolbar(){
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        drawerLayout = binding.layoutDrawer;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    // handling the selection of the cycle on the sidebar

    // ToDo: what should the app do wen the user destroys it ?
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}