package ru.lex.alexander.testapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.lex.alexander.testapp.R;
import ru.lex.alexander.testapp.ui.base.BaseActivity;
import ru.lex.alexander.testapp.ui.login.LoginActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tool_bar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.auth_button)
    void onAuthButtonClick() {
        Intent authIntent = new Intent(this, LoginActivity.class);
        startActivity(authIntent);
    }
}
