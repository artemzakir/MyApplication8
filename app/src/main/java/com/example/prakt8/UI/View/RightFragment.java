package com.example.prakt8.UI.View;

import static com.example.prakt8.DataSources.Files.SharedDS.requestPermissionWrite;
import static com.example.prakt8.DataSources.Files.SharedDS.writeToFile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.prakt8.DataSources.Files.AppSpDS;
import com.example.prakt8.DataSources.Files.SharedDS;
import com.example.prakt8.DataSources.Room.Core;
import com.example.prakt8.DataSources.Room.DAO.TaskDao;
import com.example.prakt8.DataSources.Room.Entities.Task;
import com.example.prakt8.R;
import com.example.prakt8.UI.VM.TaskListViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class RightFragment extends Fragment {
    private int count;
    private String stringTaskList = "";
    private RecyclerView recyclerView;
    public RightFragment() {
        super(R.layout.fragment_right);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        TaskListViewModel list = new ViewModelProvider(getActivity()).get(TaskListViewModel.class);
        Bundle bundle = getArguments();
        if (bundle.getBoolean("CreateList")) {
            list.getUiState().observe(getViewLifecycleOwner(), uiState -> {
                list.clearTaskList();
            });
            if (bundle.containsKey("qCount")) {
                count = bundle.getInt("qCount");
            } else {
                count = 200;
            }
            Core db = Room.databaseBuilder(getContext(),
                    Core.class, "UserTasks")
                    .allowMainThreadQueries()
                    .build();
            TaskDao taskDao = db.taskDao();
            List<Task> taskList = taskDao.getTasks();
            if (taskList.isEmpty()) {
                for (int i = 0; i < count; i++) {
                    list.getUiState().observe(getViewLifecycleOwner(), uiState -> {
                        list.addToList(
                                list.createRandomMathematicalTask());
                    });
                }
            } else {
                Random random = new Random();
                for (int i = 0; i < count; i++) {
                    list.getUiState().observe(getViewLifecycleOwner(), uiState -> {
                        list.addToList(
                                taskList.get(random.nextInt(taskDao.getSize())));
                    });
                }
            }
        }
        bundle.putBoolean("CreateList", false);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(
                getContext(),
                list.getUiState().getValue()
        );
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()
                .getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Button bDownload = view.findViewById(R.id.b_download_tasks);
        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                    for (int i = 0; i < list.getUiState().getValue().size(); i++) {
                        stringTaskList += list.getUiState().getValue().get(i).toString() + "\n";
                    }
                    SharedDS.writeToFile("UserTasks.txt", stringTaskList);
                    stringTaskList = "";
                } else {
                    SharedDS.requestPermissionWrite(getActivity());
                }
            }
        });
        Button bOpen = view.findViewById(R.id.b_open_tasks);
        bOpen.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                String check = SharedDS.readFromFile("UserTasks.txt");
                Log.d("RightFragment", SharedDS.readFromFile("UserTasks.txt"));
            } else {
                SharedDS.requestPermissionRead(getActivity());
            }
        });
    }
}