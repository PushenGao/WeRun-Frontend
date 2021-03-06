package ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.android.actionbarcompat.styled.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.BuildRecentChatRecord;
import dblayout.ChatRecordDAO;
import model.Account;
import model.BasicAccount;
import model.ChatRecord;
import ws.remote.RemoteServerProxy;


public class Chathistory extends ActionBarActivity {
    private RelativeLayout layout;
    private ListView chat_listview;
    private RecentChatAdapter mAdpter;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chat_history);

        chat_listview = (ListView) findViewById(R.id.chat_listview);
        mAdpter = new RecentChatAdapter(this, getData());

        chat_listview.setAdapter(mAdpter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.removeCallbacks(mUpdateClockTask);
        mHandler.postDelayed(mUpdateClockTask, 100);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mUpdateClockTask);
    }

    private Runnable mUpdateClockTask = new Runnable() {
        public void run() {
            updateClock();
            mHandler.postDelayed(mUpdateClockTask, 1000);
        }
    };

    public void updateClock(){
        chat_listview = (ListView) findViewById(R.id.chat_listview);
        mAdpter = new RecentChatAdapter(this, getData());

        chat_listview.setAdapter(mAdpter);

    }

    private List<Account> getData()
    {
        String myName = LogIn.loginAccount.getBasicAccount().getName();

        ChatRecordDAO chatRecordDAO = new ChatRecordDAO(getApplicationContext());

        List<Account> list=new ArrayList<Account>();

        List<BasicAccount> basicList = LogIn.loginAccount.getActiveFriends();
        for (BasicAccount account : basicList) {
            List<ChatRecord> recordList1 = chatRecordDAO.getAllRecord(myName, account.getName());
            List<ChatRecord> recordList2 = chatRecordDAO.getAllRecord(account.getName(), myName);
            List<ChatRecord> recordList = new ArrayList<ChatRecord>();
            recordList.addAll(recordList1);
            recordList.addAll(recordList2);
            if (recordList.size() != 0) {
                Account validAccount = new Account();
                validAccount.setBasicAccount(account);
                list.add(validAccount);
            }
        }

        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_chathistory, menu);
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
}
