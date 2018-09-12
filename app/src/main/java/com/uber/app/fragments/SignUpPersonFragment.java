package com.uber.app.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uber.app.R;
import com.uber.app.Utils.util;
import com.uber.app.activities.LoginActivity;
import com.uber.app.activities.MainLoggedInActivity;
import com.uber.app.rest.RestApi;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import dbEntities.Role;
import dbEntities.User;

public class SignUpPersonFragment extends Fragment{

    private static final String TAG = "SignUpActivity";
    public static final String IMAGE_TYPE = "image/*";
    private static final int SELECT_SINGLE_PICTURE = 101;
    private ProgressDialog progressDialog ;
    private String path;
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
    @BindView(R.id.driver)
    CheckBox _driver;
    @BindView(R.id.user)
    CheckBox _user;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;


    public interface FragmentChangeInterface {
        void changeChildFragment();
    }


    FragmentChangeInterface callback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View retView = inflater.inflate(R.layout.activity_signup_person, container,false);

        final FragmentActivity fragmentBelongActivity = (FragmentActivity) getActivity();

        if(retView!=null) {
            ButterKnife.bind(this,retView);

            _signupButton.setOnClickListener(v -> signup());

            _loginLink.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
            });

            _img.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setType(IMAGE_TYPE);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_SINGLE_PICTURE);
            });
        }
        return retView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (FragmentChangeInterface) context;
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
            ContentResolver cr = getActivity().getBaseContext().getContentResolver();
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

        onSignupSuccess(null);

//        Log.d(TAG, "Signup");
//
//        if (!validate()) {
//            onSignupFailed();
//            return;
//        }
//
//        _signupButton.setEnabled(false);
//
//        progressDialog = new ProgressDialog(getContext(),
//                R.style.AppTheme_Dark_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Creating Account...");
//        progressDialog.show();
//
//        String name = _nameText.getText().toString();
//        String email = _emailText.getText().toString();
//        String mobile = _mobileText.getText().toString();
//        String password = _passwordText.getText().toString();
//        String surname = _surnameText.getText().toString();
//        String username = _username.getText().toString();
//        Boolean driver = _driver.isChecked();
//        Boolean user = _user.isChecked();
//
//        new SignUp().execute(name,surname,email,mobile,password,username,driver,user);
    }


    public void onSignupSuccess(User user) {
        _signupButton.setEnabled(true);
        //setResult(RESULT_OK, null);

        if(_driver.isChecked()){
            callback.changeChildFragment();
        }else{
            Intent intent = new Intent(getActivity(), MainLoggedInActivity.class);
            Bundle bundle = new Bundle();
            String user_json = new Gson().toJson(user);
            bundle.putString("user", user_json);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity().getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
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
                uri = util.getProperty("baseAddress",getActivity().getApplicationContext()) +  "/register";
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
//            user.setPhoto(path);

            Role role = new Role();
            if (Boolean.getBoolean(params[6].toString()))
                role.setRole("driver");
            else
                role.setRole("user");
            user.setRole(role);

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
