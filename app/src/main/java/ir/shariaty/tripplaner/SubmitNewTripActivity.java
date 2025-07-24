package ir.shariaty.tripplaner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SubmitNewTripActivity extends AppCompatActivity {



    EditText etTripName, etStartDate, etEndDate, etCompanions, etAlarmNote, etNewItem;
    Button btnAddItem, btnSaveTrip;
    Switch switchAlarm;
    RecyclerView recyclerView;
    PackingListAdapter adapter;
    Calendar startDateCal = Calendar.getInstance();
    Calendar endDateCal = Calendar.getInstance();
    List<String> packingItems = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_new_trip);

        etTripName = findViewById(R.id.etTripName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etCompanions = findViewById(R.id.etCompanions);
        etAlarmNote = findViewById(R.id.etAlarmNote);
        etNewItem = findViewById(R.id.etNewItem);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSaveTrip = findViewById(R.id.btnSaveTrip);
        switchAlarm = findViewById(R.id.switchAlarm);
        recyclerView = findViewById(R.id.recyclerViewItems);

        requestNotificationPermission();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PackingListAdapter(packingItems);
        recyclerView.setAdapter(adapter);
        etStartDate.setOnClickListener(v -> showDatePicker(startDateCal, etStartDate));
        etEndDate.setOnClickListener(v -> showDatePicker(endDateCal, etEndDate));
        btnAddItem.setOnClickListener(v -> addNewItem());
        btnSaveTrip.setOnClickListener(v -> saveTrip());
    }

    private void showDatePicker(Calendar calendar, EditText targetEditText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    targetEditText.setText(sdf.format(calendar.getTime()));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void addNewItem() {
        String item = etNewItem.getText().toString().trim();
        if (!item.isEmpty()) {
            packingItems.add(item);
            adapter.notifyItemInserted(packingItems.size() - 1);
            etNewItem.setText("");
        }
    }

    private void saveTrip() {
        String tripName = etTripName.getText().toString().trim();
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String companions = etCompanions.getText().toString();
        String alarmNote = etAlarmNote.getText().toString();
        boolean isAlarmOn = switchAlarm.isChecked();

        if (isAlarmOn) {
            scheduleAlarm(startDate, alarmNote.isEmpty() ? tripName : alarmNote);
        }

        Toast.makeText(this, "Trip saved successfully!", Toast.LENGTH_SHORT).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("trip_name", tripName);
        resultIntent.putExtra("trip_start_date", startDate);
        resultIntent.putExtra("trip_end_date", endDate);
        resultIntent.putExtra("trip_companions", companions);

        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void scheduleAlarm(String startDateStr, String note) {
        if (startDateStr.isEmpty()) return;
        try {
            Date tripDate = sdf.parse(startDateStr);
            Calendar alarmTime = Calendar.getInstance();
            alarmTime.setTime(tripDate);
            alarmTime.add(Calendar.DAY_OF_MONTH, -1);

            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("note", note);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not set alarm.", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1001);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}