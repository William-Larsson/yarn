package se.umu.yarn;

import android.content.Intent;
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
                R.id.navigation_interests, R.id.navigation_conversations, R.id.navigation_settings).build();

    }

    void newActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("se.umu.yarn.account", "account");
        startActivity(intent);
    }
}