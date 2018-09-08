package com.uber.app.activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uber.app.R;
import com.uber.app.Utils.util;
import com.uber.app.rest.RestApi;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import dbEntities.User;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_SINGLE_PICTURE = 101;
    private ProgressDialog progressDialog ;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.imgView)
    ImageView _img;
    @BindView(R.id.input_surname)
    EditText _surnameText;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_mobile)
    EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.input_username)
    EditText _username;
    @BindView(R.id.tenant)
    CheckBox _tenant;
    @BindView(R.id.host)
    CheckBox _host;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    private String path;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(v -> signup());

        _loginLink.setOnClickListener(v -> {
            // Finish the registration screen and return to the Login activity
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        });

        _img.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType(IMAGE_TYPE);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_SINGLE_PICTURE);
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_SINGLE_PICTURE) {
//                Uri selectedImageUri = data.getData();
//
//                OutputStream out;
//                String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
//                File createDir = new File(root+"airbnb-images"+ File.separator);
//                if(!createDir.exists()) {
//                    createDir.mkdir();
//                }
//                String photoPath = selectedImageUri.toString();
//                String filename = photoPath.substring(photoPath.lastIndexOf("/")+1,photoPath.length());
//                File file = new File(root + "airbnb-images" + File.separator + filename);
//                try {
//                    file.createNewFile();
//                    out = new FileOutputStream(file);
//                    out.write(convertImageToByte(selectedImageUri));
//                    out.close();
//
//                    path = root + "airbnb-images" + File.separator + filename;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                Uri uri = Uri.fromFile(new File(path));
//                Picasso.with(this).load(uri)
//                        .transform(new CircleTransform())
//                        .resize(150,150).into(_img);
//
//            }
//        } else {
//            Toast.makeText(getApplicationContext(), "Failed to get intent data", Toast.LENGTH_LONG).show();
//            Log.d(NewResidenceListingActivity.class.getSimpleName(), "Failed to get intent data, result code is " + resultCode);
//        }
    }

    public byte[] convertImageToByte(Uri uri){
        byte[] data = null;
        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String surname = _surnameText.getText().toString();
        String username = _username.getText().toString();
        Boolean host = _host.isChecked();
        Boolean tenant = _tenant.isChecked();

        new SignUp().execute(name,surname,email,mobile,password,username,host,tenant);
    }


    public void onSignupSuccess(User user) {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);


//        Intent intent = new Intent(getApplicationContext(), MainLoggedInActivity.class);
//        Bundle bundle = new Bundle();
//
//        String user_json = new Gson().toJson(user);
//        bundle.putString("user", user_json);
//        intent.putExtras(bundle);
//        startActivity(intent);

        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String surname = _surnameText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String username = _username.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (surname.isEmpty() || surname.length() < 3) {
            _surnameText.setError("at least 3 characters");
            valid = false;
        } else {
            _surnameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        if (username.isEmpty()) {
            _username.setError("Enter Valid Username");
            valid = false;
        } else {
            _username.setError(null);
        }

        return valid;
    }

    private class SignUp extends AsyncTask<Object,Void,User> {

        private RestTemplate restTemplate = new RestApi().getRestTemplate();

        @Override
        protected User doInBackground(Object... params) {
            String uri = "";
            try {
                uri = util.getProperty("baseAddress",getApplicationContext()) +  "/register";
            } catch (IOException e) {
                e.printStackTrace();
            }
            User user = new User();
            user.setFirstname(params[0].toString());
            user.setLastname(params[1].toString());
            user.setEmail(params[2].toString());
            user.setTelephone(params[3].toString());
            user.setPassword(params[4].toString());
            user.setUsername(params[5].toString());
            user.setPhoto(path);

//            List<Role> roles = new ArrayList<>();
//            if (Boolean.getBoolean(params[6].toString()))
//                roles.add(RoleDto.HOST);
//            if (Boolean.getBoolean(params[7].toString()))
//                roles.add(RoleDto.TENANT);
            user.setRole(null);

            User result = null;
            try {
                HttpEntity<User> request = new HttpEntity<>(user);
                result = restTemplate.postForObject(uri,request, User.class);
                return result;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(final User user) {
            if(user != null){
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onSignupSuccess(user);
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }
            else{
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                onSignupFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

        }
    }
}