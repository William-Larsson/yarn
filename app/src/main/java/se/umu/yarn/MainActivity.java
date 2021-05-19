package se.umu.yarn;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

/**
 * Main activity for the application. Handles the main interface
 * and associated fragments.
 */
public class MainActivity extends AppCompatActivity {

    // Get user name from intent from SignInActivity.
    // Intent intent = getIntent();
    // String message = intent.getStringExtra("se.umu.yarn.fullName");

    private final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation();

        if (!isPermissionsGranted()){
            askForPermissions();
        }

        FirebaseApp.initializeApp(this);
        // NOTE: If Firebase database URL is null, try to "Build -> Clean Project, + Rebuild project.
        // Otherwise, make sure to use the correct google-services.json file.
        Log.d("Firebase connection", "App Firebase URL: " +
                FirebaseApp.getInstance().getOptions().getDatabaseUrl());
    }

    /**
     * Set up the bottom navigation bar for the Main-screens of
     * the system (Interests, Conversations, Settings)
     */
    private void setupBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_interests,
                R.id.navigation_conversations,
                R.id.navigation_settings
        ).build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        NavController navController = Objects.requireNonNull(navHostFragment).getNavController();
        NavigationUI.setupWithNavController(navView, navController);
    }

    /**
     * Loop through all the permissions and check if they have been granted
     * by the user.
     * @return = true if has access to permissions.
     */
    private boolean isPermissionsGranted() {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    /**
     * Asks the user to grant access to device functions that require permissions.
     */
    private void askForPermissions() {
        int requestCode = 1;
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }
}