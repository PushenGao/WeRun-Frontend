package ws.remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.BasicAccount;

/**
 * Created by Michael-Gao on 2015/4/11.
 */
public interface RecommendFriend {
    public List<BasicAccount> getRecommend(String userId);
}
