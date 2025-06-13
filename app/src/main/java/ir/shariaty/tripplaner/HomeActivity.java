package ir.shariaty.tripplaner;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_NEW_TRIP = 100;
    private Button btnNewTrip;
    private CardView cardTripSummary;
    private TextView tvTripTitle, tvTripDate, tvTripCompanions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnNewTrip = findViewById(R.id.btnNewTrip);
        cardTripSummary = findViewById(R.id.cardTripSummary);
        tvTripTitle = findViewById(R.id.tvTripTitle);
        tvTripDate = findViewById(R.id.tvTripDate);
        tvTripCompanions = findViewById(R.id.tvTripCompanions);

        btnNewTrip.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubmitNewTripActivity.class);
            startActivityForResult(intent, REQUEST_CODE_NEW_TRIP);
        });

        cardTripSummary.setVisibility(View.GONE);

        btnNewTrip.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SubmitNewTripActivity.class);
            startActivityForResult(intent, REQUEST_CODE_NEW_TRIP);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_NEW_TRIP && resultCode == Activity.RESULT_OK && data != null) {
            String startDate = data.getStringExtra("trip_start_date");
            String endDate = data.getStringExtra("trip_end_date");
            String companions = data.getStringExtra("trip_companions");

            tvTripTitle.setText("Trip");
            tvTripDate.setText(startDate + " to " + endDate);
            tvTripCompanions.setText("With: " + companions);

            cardTripSummary.setVisibility(View.VISIBLE);
        }
    }
}
