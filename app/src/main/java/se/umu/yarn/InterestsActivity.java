package se.umu.yarn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.d;

public class InterestsActivity extends AppCompatActivity {

    RadioButton one, two, three, four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d("Alice: ", "Hello");
        setContentView(R.layout.fragment_interests);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_interests, R.id.navigation_conversations, R.id.navigation_settings)
                .build();


        List<RadioButton> radioButtonsSelected = new ArrayList<>();

        final Button confirmButton = findViewById(R.id.confirmButton);

        one = findViewById(R.id.radioButton1);
        two = findViewById(R.id.radioButton2);
        three = findViewById(R.id.radioButton3);
        four = findViewById(R.id.radioButton4);


        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d("Alice", "Clicked");
            }

        });

        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean one_b) {
                d("Alice", "Clicked radio");
            }
        });



    }}
