package com.example.prakt8.DataSources.SP;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.prakt8.DataSources.Room.Entities.Task;

import java.util.List;

public class SP {
    public static void saveList(Activity activity, int size, List<Task> listData) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for (int i = 0; i < size; i++) {
            editor.putString(Integer.toString(i), (listData.get(i).toString()));
        }
        editor.apply();
    }
    public static void checkList(Activity activity) {
        String data = "";
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        for (String k : sharedPref.getAll().keySet()) {
            data = sharedPref.getString(k,"Nope");
            Log.d("SPDS", k + ": " + data);
        }
    }
    public static void removeAll(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }
}
