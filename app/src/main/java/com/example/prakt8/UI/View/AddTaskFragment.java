package com.example.prakt8.UI.View;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.prakt8.DataSources.Files.AppSpDS;
import com.example.prakt8.DataSources.Room.Core;
import com.example.prakt8.DataSources.Room.DAO.TaskDao;
import com.example.prakt8.DataSources.Room.Entities.Task;
import com.example.prakt8.R;
import com.example.prakt8.UI.VM.TaskListViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AddTaskFragment extends Fragment {
    public AddTaskFragment() {
        super(R.layout.fragment_add_task);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button bAccept = view.findViewById(R.id.b_accept);
        EditText eTaskText = view.findViewById(R.id.e_tasktext);
        EditText eTaskAnswer = view.findViewById(R.id.e_taskanswer);
        Core db = Room.databaseBuilder(getContext(),
                        Core.class, "UserTasks")
                .allowMainThreadQueries()
                .build();
        TaskDao taskDao = db.taskDao();
        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSpDS.createFile(getContext(),"UserTasks.txt");
                AppSpDS.writeToFile(getContext(),"UserTasks.txt", eTaskText.getText().toString()+": "+eTaskAnswer.getText().toString()+'\n');
                Log.d("CheckingFile", AppSpDS.readFile(getContext(), "UserTasks.txt"));
                taskDao.add(new Task(eTaskText.getText().toString(), R.drawable.un_task, eTaskAnswer.getText().toString()));
            }
        });
    }
}