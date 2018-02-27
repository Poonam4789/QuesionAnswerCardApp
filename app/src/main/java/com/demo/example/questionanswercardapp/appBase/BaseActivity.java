package com.demo.example.questionanswercardapp.appBase;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.example.questionanswercardapp.R;
import com.demo.example.questionanswercardapp.network.NetworkChangeReceiver;
import com.demo.example.questionanswercardapp.network.NetworkStatus;

/**
 * Created by poonampatel on 22/02/18.
 */

public abstract class BaseActivity extends AppCompatActivity
{
    private NetworkChangeReceiver _networkReceiver;
    protected ProgressBar progressDailog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d("Called", "Base Called");
        _networkReceiver = new NetworkChangeReceiver(networkChangeHandler);
    }

    protected void initToolbar(Toolbar toolbar)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void settoolbarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerNetworkChangeReceiver();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterNetworkChangeReciver();
    }

    public void showHideProgress(NetworkStatus networkStatus)
    {
        switch (networkStatus)
        {
            case STATUS_LOADING:

                showProgressBar(true);
                break;

            case STATUS_SUCCESS:

                showProgressBar(false);
                break;

        }
    }

    protected void showProgressBar(boolean toShow)
    {
        if (progressDailog != null)
        {
            if (toShow)
            {
                progressDailog.setVisibility(View.VISIBLE);
            }
            else
            {
                progressDailog.setVisibility(View.GONE);
            }
        }
    }

    /*******************
     * Network change receiver implementation
     **************/

    /*************
     * Register Network Change Receiver
     *********/
    private void registerNetworkChangeReceiver()
    {
        registerReceiver(_networkReceiver, NetworkChangeReceiver.getNetworkChangeFilter());
    }

    /*********
     * Unregister Network Change Receiver
     ************/
    private void unregisterNetworkChangeReciver()
    {
        unregisterReceiver(_networkReceiver);
    }

    /**
     * @networkChangeHandler will get message
     * msg.what = NETWORK_NOT_CONNECTED; if network connected
     * msg.what = NETWORK_CONNECTED;     if network not connected
     */
    Handler networkChangeHandler = new Handler()
    {
        @Override
        public void handleMessage(Message networkMessage)
        {
            onNetworkChange(NetworkChangeReceiver.isOnline());
        }
    };

    protected abstract void onNetworkChange(boolean isNetworkConnected);

    protected void showNoNetworkBar(boolean isNetworkConnected)
    {
        if (!isNetworkConnected)
        {
            Snackbar.make(findViewById(android.R.id.content), "Please check network connection", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    public void toggleDrawerIcon(boolean showDrawer)
    {
        if (showDrawer)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        else
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void invalidateMenu(){
        invalidateOptionsMenu();
    }
}
