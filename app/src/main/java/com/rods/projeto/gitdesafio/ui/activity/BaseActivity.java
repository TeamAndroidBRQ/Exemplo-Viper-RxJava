package com.rods.projeto.gitdesafio.ui.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity{

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this); //Just to save one line on parent Activity
    }

    protected void showSnackbakMsg(View view, int msgId) {
        Snackbar.make(view, msgId, Snackbar.LENGTH_LONG).show();
    }

}
