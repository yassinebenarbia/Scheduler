package com.example.scheduler;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toolbar;

import com.example.scheduler.ui.main.SectionsPagerAdapter;
import com.example.scheduler.databinding.ActivityMainBinding;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

/**
 * setting the ViewPager with the adapter and
 * the TabLayout with the ViewPager
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MaterialToolbar toolbar;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        drawerLayout = binding.layoutDrawer;
        // Todo : add the AppBar to the ActionBarDrawerToggle, see it's constructor to know more
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // Assumption: the position is a number that represent the order of the current page
            // with respect to all the pages putted in the viewPager
            @Override
            public void onPageSelected(int position) {
                Log.i("onPageSelected","Page Number: "+String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        TabLayout tabs = layoutBinding.tabs;
//        tabs.setupWithViewPager(viewPager);

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
    }

    // this method creates a notification channel
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
    // handling the selection of the cycle on the sidebar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // toDo: what should the app do wen the user destroys it
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}