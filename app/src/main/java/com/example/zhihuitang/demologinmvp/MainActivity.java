package com.example.zhihuitang.demologinmvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.zhihuitang.demologinmvp.Presenter.ILoginPresenter;
import com.example.zhihuitang.demologinmvp.Presenter.LoginPresenterCompl;
import com.example.zhihuitang.demologinmvp.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    @BindView(R.id.et_login_username) EditText editUser;
    @BindView(R.id.et_login_password) EditText editPass;
    @BindView(R.id.btn_login_login) Button btnLogin;
    @BindView(R.id.btn_login_clear) Button btnClear;
    ILoginPresenter loginPresenter;
    @BindView(R.id.progress_login) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        btnClear.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        loginPresenter = new LoginPresenterCompl(this);
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_clear:
                loginPresenter.clear();
                break;
            case R.id.btn_login_login:
                loginPresenter.setProgressBarVisiblity(View.VISIBLE);
                btnLogin.setEnabled(false);
                btnClear.setEnabled(false);
                loginPresenter.doLogin(editUser.getText().toString(), editPass.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onClearText() {
        editPass.setText("");
        editUser.setText("");
    }

    @Override
    public void onLoginResult(Boolean result, int code) {
        loginPresenter.setProgressBarVisiblity(View.INVISIBLE);
        btnLogin.setEnabled(true);
        btnClear.setEnabled(true);
        if (result){
            Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Login Fail, code = " + code,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        progressBar.setVisibility(visibility);
    }
}
