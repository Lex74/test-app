package ru.lex.alexander.testapp.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import ru.lex.alexander.testapp.R;
import ru.lex.alexander.testapp.ui.base.BaseActivity;
import ru.lex.alexander.testapp.util.DialogFactory;
import ru.lex.alexander.testapp.util.EntryChecker;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements LoginMvpView {
    private final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;

    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.root_view)
    View rootView;
    @BindView(R.id.login_tool_bar)
    Toolbar toolbar;
    @BindView(R.id.email_edit_text)
    EditText emailEditText;
    @BindView(R.id.password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.email_input_layout)
    TextInputLayout emailInputLayout;
    @BindView(R.id.password_input_layout)
    TextInputLayout passwordInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loginPresenter.attachView(this);
    }

    @OnClick(R.id.enter_button)
    public void showWeather() {
        boolean isEmailValid = checkEmail();
        boolean isPasswordValid = checkPassword();

        if (isEmailValid && isPasswordValid && checkLocationPermission()) {
            hideSoftKeyboard();
            loginPresenter.getWeather();
        }
    }

    private boolean checkEmail() {
        String email = emailEditText.getText().toString();
        boolean isEmailValid = EntryChecker.checkEmail(email);
        if (!isEmailValid) {
            String emailErrorMessage = getResources().getString(R.string.wrong_email);
            emailInputLayout.setErrorEnabled(true);
            emailInputLayout.setError(emailErrorMessage);
        }
        return isEmailValid;
    }

    private boolean checkPassword() {
        String password = passwordEditText.getText().toString();
        boolean isPasswordValid = EntryChecker.checkPassword(password);
        if (!isPasswordValid) {
            String emailErrorMessage = getResources().getString(R.string.wrong_password);
            passwordInputLayout.setErrorEnabled(true);
            passwordInputLayout.setError(emailErrorMessage);
        }
        return isPasswordValid;
    }

    @OnEditorAction(R.id.password_edit_text)
    boolean onPasswordEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            showWeather();
            return true;
        }
        return false;
    }

    @OnFocusChange(R.id.email_edit_text)
    void checkEmailField(boolean hasFocus) {
        if (hasFocus) {
            emailInputLayout.setErrorEnabled(false);
        }
    }

    @OnFocusChange(R.id.password_edit_text)
    void checkPasswordField(boolean hasFocus) {
        if (hasFocus) {
            passwordInputLayout.setErrorEnabled(false);
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
    }

    /***** MVP View methods implementation *****/

    @Override
    protected void onDestroy() {
        super.onDestroy();

        loginPresenter.detachView();
    }

    @Override
    public void showError(String message) {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots) + "\n" + message)
                .show();
    }

    @Override
    public void showWeatherMessage(String message) {
        Snackbar.make(rootView, message, BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {

                Toast.makeText(this, getResources().getString(R.string.permission_description), Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showWeather();
                } else {
                    Timber.e("PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION permission rejected");
                }
                return;
            }
        }
    }
}
