package com.example.prakt8.DataSources.Files;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class AppSpDS {
    public static void createFile(Context context, String name) {
        for (String check : checkFiles(context)) {
            if (check.compareTo(name) == 0) {
                return;
            }
        }
        File file = new File(context.getFilesDir(), name);
    }
    public static void writeToFile(Context context, String name, String data) {
        try (FileOutputStream fos = context.openFileOutput(name, context.MODE_APPEND)) {
            fos.write(data.getBytes());
        } catch (IOException e) {
            Toast.makeText(context,"File didn't created", Toast.LENGTH_LONG).show();
        }
    }
    public static String readFile(Context context, String filename) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fis = context.openFileInput(filename)) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            }
        } catch (IOException e) {
            Toast.makeText(context, "FailedToRead", Toast.LENGTH_LONG).show();
        }
        return stringBuilder.toString();
    }
    public static String[] checkFiles(Context context) {
        String[] files = context.fileList();
        for (String file : files) {
            Log.d("AppSpDS", file);
        }
        return files;
    }
    public static void deleteFile(Context context, String name) {
        context.deleteFile(name);
    }
}
