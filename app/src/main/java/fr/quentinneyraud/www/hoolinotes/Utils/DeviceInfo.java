package fr.quentinneyraud.www.hoolinotes.Utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.util.Patterns;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by quentin on 14/02/16.
 */
public class DeviceInfo {

    public static ArrayList<String> getAccounts(Context context){
        final Account[] accounts = AccountManager.get(context).getAccounts();
        final Set<String> emailSet = new HashSet<String>();
        for (Account account : accounts) {
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                emailSet.add(account.name);
            }
        }
        return new ArrayList<String>(emailSet);

    }
}
