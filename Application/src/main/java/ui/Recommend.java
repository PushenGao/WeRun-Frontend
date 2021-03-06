package ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.actionbarcompat.styled.R;

import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.BasicAccount;
import ws.remote.RemoteServerProxy;


public class Recommend extends ActionBarActivity {
    private Button bt;
    private ListView recommend_listview;
    private RecommendationAdapter mAdapter;
    private ShakeListener mShakeListener = null;
    private Vibrator mVibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_recommend);

        //set up the recommen list view
        recommend_listview = (ListView) findViewById(R.id.recommend_listview);
        //mAdapter = new RecommendationAdapter(this, getData());
        //will be used in real time
        //try to get the recommend friend from server
        RemoteServerProxy remoteServerProxy = new RemoteServerProxy();
        List<BasicAccount> basicAccountList = remoteServerProxy.getRecommend(LogIn.loginAccount.getBasicAccount().getName());
        mAdapter = new RecommendationAdapter(this, basicAccountList);

        recommend_listview.setAdapter(mAdapter);

        //set up the shake listener to listen the shake
        mVibrator = (Vibrator) getApplication().getSystemService(
                VIBRATOR_SERVICE);
        mShakeListener = new ShakeListener(Recommend.this);

        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {

            public void onShake() {
//when the shake occurs, get new recommend friend from server
                mShakeListener.stop();
                startVibrato();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RemoteServerProxy remoteServerProxy = new RemoteServerProxy();
                        List<BasicAccount> basicAccountList = remoteServerProxy.getRecommend(LogIn.loginAccount.getBasicAccount().getName());
                        mAdapter = new RecommendationAdapter(Recommend.this, basicAccountList);
                        Toast.makeText(getApplicationContext(), "Recommend new users for you!",
                                Toast.LENGTH_LONG).show();
                        recommend_listview.setAdapter(mAdapter);
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
    }

    //test method to set up the list view
    private List<Account> getData()
    {
        List<Account> list = new ArrayList<Account>();

        BasicAccount basicAccount1 = new BasicAccount();
        Account friend1 = new Account();
        friend1.setBasicAccount(basicAccount1);
        friend1.getBasicAccount().setName("Nancy");

        BasicAccount basicAccount2 = new BasicAccount();
        Account friend2 = new Account();
        friend2.setBasicAccount(basicAccount2);
        friend2.getBasicAccount().setName("Joe");

        BasicAccount basicAccount3 = new BasicAccount();
        Account friend3 = new Account();
        friend3.setBasicAccount(basicAccount3);
        friend3.getBasicAccount().setName("Annie");

        list.add(friend1);
        list.add(friend2);
        list.add(friend3);

        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recommend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void viewProfile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }


    public void startVibrato() {
        mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
    }

}
