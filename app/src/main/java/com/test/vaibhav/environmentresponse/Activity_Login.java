package com.test.vaibhav.environmentresponse;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.auth.core.AuthProviderType;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;

//import com.facebook.FacebookSdk;

import java.util.Map;

public class Activity_Login extends FirebaseLoginBaseActivity {

    static Firebase firebaseRef;
    EditText userNameET;
    EditText passwordET;
    String mName;

    /* String Constants */
    private static final String FIREBASEREF = "https://moviedatadb.firebaseio.com/";
    private static final String FIREBASE_ERROR = "Firebase Error";
    private static final String USER_ERROR = "User Error";
    private static final String LOGIN_SUCCESS = "Login Success";
    private static final String LOGOUT_SUCCESS = "Logout Success";
    private static final String USER_CREATION_SUCCESS =  "Successfully created user";
    private static final String USER_CREATION_ERROR =  "User creation error";
    private static final String EMAIL_INVALID =  "email is invalid :";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(FIREBASEREF);
        super.onCreate(savedInstanceState);
 //       FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);
        userNameET = (EditText)findViewById(R.id.edit_text_email);
        passwordET = (EditText)findViewById(R.id.edit_text_password);
        //Activity_Login.this.showFirebaseLoginPrompt();

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Login.this.showFirebaseLoginPrompt();
            }
        });
        //Button createButton = (Button) findViewById(R.id.button);
        //createButton.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View v) {
        //        createUser();
        //    }
        //});
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        Snackbar snackbar = Snackbar.
                make(userNameET, FIREBASE_ERROR + firebaseLoginError.message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        Log.d("login provider error", FIREBASE_ERROR + firebaseLoginError.message);
        resetFirebaseLoginPrompt();
    }



    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        Snackbar snackbar = Snackbar
                .make(userNameET, USER_ERROR + firebaseLoginError.message, Snackbar.LENGTH_SHORT);
        snackbar.show();
        Log.d("login user error", FIREBASE_ERROR + firebaseLoginError.message);
        resetFirebaseLoginPrompt();
    }

    @Override
    public Firebase getFirebaseRef() {
        return firebaseRef;
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        switch (authData.getProvider()) {
            case "password":
                mName = (String) authData.getProviderData().get("email");
                break;
           default:
                mName = (String) authData.getProviderData().get("displayName");
                break;
        }
        Toast.makeText(getApplicationContext(), LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(Activity_Login.this, MapsActivity.class);
        Activity_Login.this.startActivity(myIntent);
        UserContext.setDisplayName((String) authData.getProviderData().get("displayName"));
        UserContext.setEmail((String) authData.getProviderData().get("email"));
        UserContext.setId((String) authData.getProviderData().get("id"));
        UserContext.setProfileImageURL((String) authData.getProviderData().get("profileImageURL"));
        UserContext.downloadImageusingHTTPGetRequest(UserContext.getProfileImageURL());
    }


    @Override
    public void onFirebaseLoggedOut() {
        Toast.makeText(getApplicationContext(), LOGOUT_SUCCESS, Toast.LENGTH_SHORT).show();
        //firebaseRef.unauth();
        //finish();
        //System.exit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // All providers are optional! Remove any you don't want.
        //setEnabledAuthProvider(AuthProviderType.PASSWORD);
        setEnabledAuthProvider(AuthProviderType.GOOGLE);
        //FacebookSdk.sdkInitialize(this);
        //setEnabledAuthProvider(AuthProviderType.FACEBOOK);
        //  setEnabledAuthProvider(AuthProviderType.TWITTER);
    }

    // Validate email address for new accounts.
    private boolean isEmailValid(String email) {
        boolean isGoodEmail = (email != null && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if (!isGoodEmail) {
            userNameET.setError(EMAIL_INVALID + email);
            return false;
        }
        return true;
    }

    // create a new user in Firebase
    public void createUser() {
        if(userNameET.getText() == null ||  !isEmailValid(userNameET.getText().toString())) {
            return;
        }
        firebaseRef.createUser(userNameET.getText().toString(), passwordET.getText().toString(),
                new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_SUCCESS, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Snackbar snackbar = Snackbar.make(userNameET, USER_CREATION_ERROR, Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                });
    }
}
