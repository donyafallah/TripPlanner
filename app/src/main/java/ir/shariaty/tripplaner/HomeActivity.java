package ir.shariaty.tripplaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton; // تغییر مهم: ایمپورت صحیح برای دکمه شناور

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW_TRIP = 100;

    // متغیرها
    private FloatingActionButton btnNewTrip; // تغییر مهم: نوع دکمه به FloatingActionButton تغییر کرد
    private CardView cardTripSummary;
    private TextView tvTripTitle, tvTripDate, tvTripCompanions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // اتصال ویجت‌ها از XML به جاوا
        btnNewTrip = findViewById(R.id.btnNewTrip); // این آیدی باید با آیدی دکمه در XML یکی باشد
        cardTripSummary = findViewById(R.id.cardTripSummary);
        tvTripTitle = findViewById(R.id.tvTripTitle);
        tvTripDate = findViewById(R.id.tvTripDate);
        tvTripCompanions = findViewById(R.id.tvTripCompanions);

        // در ابتدا کارت خلاصه سفر را مخفی می کنیم
        cardTripSummary.setVisibility(View.GONE);

        // تنظیم عملکرد کلیک برای دکمه "New Trip"
        btnNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ایجاد یک اینتنت برای رفتن به صفحه ثبت سفر جدید
                Intent intent = new Intent(HomeActivity.this, SubmitNewTripActivity.class);
                // شروع اکتیویتی و منتظر ماندن برای دریافت نتیجه از آن
                startActivityForResult(intent, REQUEST_CODE_NEW_TRIP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // بررسی می کنیم که آیا نتیجه از صفحه ثبت سفر جدید برگشته و موفقیت آمیز بوده است
        if (requestCode == REQUEST_CODE_NEW_TRIP && resultCode == Activity.RESULT_OK && data != null) {
            // دریافت اطلاعات سفر از اینتنت
            String startDate = data.getStringExtra("trip_start_date");
            String endDate = data.getStringExtra("trip_end_date");
            String companions = data.getStringExtra("trip_companions");

            // تنظیم اطلاعات در کارت خلاصه سفر
            tvTripTitle.setText("Your Upcoming Trip"); // یک عنوان بهتر
            tvTripDate.setText(startDate + " to " + endDate);
            tvTripCompanions.setText("Companions: " + companions);

            // نمایش کارت خلاصه سفر
            cardTripSummary.setVisibility(View.VISIBLE);
        }
    }
}