package dblayout;

import java.util.ArrayList;
import java.util.HashMap;

import model.Account;
import model.ChatRecord;

/**
 * Created by JiateLi on 15/4/11.
 */
public interface IChatRecordDAO {

    public void insertRecord(ChatRecord chatRecord);

    public void deleteRecord(ChatRecord chatRecord);

    public void updateRecord(ChatRecord chatRecord);

    public ArrayList<ChatRecord> getAllRecord(String userid, String withUserid);

    public ChatRecord getRecord(String userid, String withUserid, String time);

    public ArrayList<ChatRecord> getAllRecentRecord(String userid);
}
