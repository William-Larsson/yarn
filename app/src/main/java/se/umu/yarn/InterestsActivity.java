package se.umu.yarn;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import se.umu.yarn.EntityClass.InterestModel;
import se.umu.yarn.EntityClass.UserModel;

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

        List<String> interestList = new ArrayList<>();


        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                d("Alice", "Clicked");

                //Get all the checked boxes and insert into database

                //"SELECT id FROM interests WHERE Name ='".$interests."'";
            }

        });

        one.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean one_b) {
                d("Alice", "Clicked radio");
                TextView interests = findViewById(R.id.textView2);
                String interest = (String) interests.getText();
                d("Alice", "Clicked radio +"  + interests);
                interestList.add(interest);

            }
        });
        two.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean two_b) {
                d("Alice", "Clicked radio");
                TextView interests = findViewById(R.id.textView3);
                String interest = (String) interests.getText();
                d("Alice", "Clicked radio +"  + interest);
                interestList.add(interest);

            }
        });




    }

    private void saveInterests(){
        String interests = one.getText().toString().trim();

        UserModel user = new UserModel();
        InterestModel interest = new InterestModel();

        user.setName("Alice");

        DatabaseClass.getDatabase(getApplicationContext()).getDao().insert(interest);
        Toast.makeText(this, "Data success", Toast.LENGTH_SHORT).show();

    }

}
