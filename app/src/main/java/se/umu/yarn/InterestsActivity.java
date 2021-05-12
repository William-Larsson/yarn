package se.umu.yarn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.util.Log.d;

public class InterestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d("Alice: ", "Hello");
        setContentView(R.layout.interests);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();


        final Button confirmButton = findViewById(R.id.confirmButton);

        final RadioButton radioButton = findViewById(R.id.radioButton2);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d("Alice", "Clicked");
            }
        });



        radioButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d("Alice", "radioButton");

                boolean checked = ((RadioButton) radioButton).isChecked();

                // Check which radio button was clicked
               radioButton.setChecked(!checked);
            }


        });
    }}
