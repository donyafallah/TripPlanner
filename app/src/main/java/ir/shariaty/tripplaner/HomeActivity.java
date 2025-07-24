package ir.shariaty.tripplaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW_TRIP = 100;

    // متغیرها
    private FloatingActionButton btnNewTrip;
    private CardView cardTripSummary;
    private TextView tvTripTitle, tvTripDate, tvTripCompanions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // اتصال ویجت‌ها از XML به جاوا
        btnNewTrip = findViewById(R.id.btnNewTrip);
        cardTripSummary = findViewById(R.id.cardTripSummary);
        tvTripTitle = findViewById(R.id.tvTripTitle);
        tvTripDate = findViewById(R.id.tvTripDate);
        tvTripCompanions = findViewById(R.id.tvTripCompanions);


        cardTripSummary.setVisibility(View.GONE);

        // تنظیم عملکرد کلیک برای دکمه "New Trip"
        btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, SubmitNewTripActivity.class);

                startActivityForResult(intent, REQUEST_CODE_NEW_TRIP);
            }
        });
    }

    /**
     * این متد زمانی فراخوانی می شود که از یک اکتیویتی دیگر (که با startActivityForResult شروع شده)
     * نتیجه ای به این اکتیویتی برگردد.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // بررسی می کنیم که آیا نتیجه از صفحه ثبت سفر جدید برگشته و موفقیت آمیز بوده است
        if (requestCode == REQUEST_CODE_NEW_TRIP && resultCode == Activity.RESULT_OK && data != null) {




            String tripName = data.getStringExtra("trip_name");
            String startDate = data.getStringExtra("trip_start_date");
            String endDate = data.getStringExtra("trip_end_date");
            String companions = data.getStringExtra("trip_companions");

            // بررسی اینکه آیا کاربر نامی برای سفر وارد کرده است یا نه
            if (tripName != null && !tripName.isEmpty()) {
                tvTripTitle.setText(tripName); // اگر نامی وارد شده بود، آن را نمایش بده
            } else {
                tvTripTitle.setText("My Awesome Trip"); // در غیر این صورت، یک عنوان پیش فرض نمایش بده
            }


            tvTripDate.setText("From: " + startDate + " to " + endDate);
            tvTripCompanions.setText("With: " + companions);


            cardTripSummary.setVisibility(View.VISIBLE);


        }
    }
}