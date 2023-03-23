package com.example.scheduler;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TimePicker;

import com.example.scheduler.ui.main.SectionsPagerAdapter;
import com.example.scheduler.databinding.ActivityMainBinding;

import java.sql.SQLException;
import java.util.Calendar;

/**
 *
 * setting the ViewPager with the adapter and
 * the TabLayout with the ViewPager
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        // the floating button stuff
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
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

    // this method creates a notificatino channel
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

        storeChosenAlarm();

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }
    // storing the chosen Alarm goes here
    public void storeChosenAlarm(){
        AlarmDBManager alarmDBManager = new AlarmDBManager(getBaseContext());
        try {
            alarmDBManager.open();

        } catch (SQLException e) {
            System.out.println("Could not open DB");
            throw new RuntimeException(e);
        }

        alarmDBManager.close();

    }
}