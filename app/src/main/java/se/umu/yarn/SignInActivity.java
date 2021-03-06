package se.umu.yarn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login_oauth);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken("789873226633-e1pn6ur3fr23ll15kuhmpfs0vtcu48ak.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("onStart", "Inne i onStart");
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //Log.d("robin", account.getEmail());
        //updateUI(account);

        // Update your UI accordingly???that is, hide the sign-in button,
        // launch your main activity, or whatever is appropriate for your app.
    }

    @Override
    public void onClick(View v)  {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //RC_SIGN_IN
        Log.d("robin", mGoogleSignInClient.getApiKey().toString());

        startActivityForResult(signInIntent, 15);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Activity result", "onActivityResult Request code: " + requestCode );

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 15) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        Log.d("Robin", "Inne i handle: " );
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d("Robin",account.getEmail() );

            // Signed in successfully, show authenticated UI.
            updateUI(account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w( "Robin", "n??nting gick fel " + e.getMessage() + e.getCause());
            //updateUI(null);
        }
    }

    /**
     * Start MainActivity and pall along the users account details
     * @param account = the users account details.
     */
    private void updateUI(GoogleSignInAccount account) {
        Log.d("Alice", "Starta main" + account.getEmail());
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("se.umu.yarn.account", account);
        startActivity(intent);
    }
}