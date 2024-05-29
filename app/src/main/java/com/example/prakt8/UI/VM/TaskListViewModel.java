package com.example.prakt8.UI.VM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prakt8.DataSources.Room.Entities.Task;
import com.example.prakt8.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TaskListViewModel extends ViewModel {
    private MutableLiveData<List<Task>> uiState =
            new MutableLiveData<>(new ArrayList<Task>());
    private MutableLiveData<List<Task>> uiStateUser =
            new MutableLiveData<>(new ArrayList<Task>());
    public LiveData<List<Task>> getUiState() {
        return uiState;
    }
    public LiveData<List<Task>> getUiStateUser() {
        return uiStateUser;
    }
    public Task getTask(int index) {
        return uiState.getValue().get(index);
    }
    public void addToList(Task task) {
        uiState.getValue().add(task);
    }
    public void clearTaskList() {
        uiState.getValue().clear();
    }
    public Task createRandomMathematicalTask() {
        Random random = new Random();
        Task task;
        int r = random.nextInt(2);
        if (r == 0) {
            task = setRandomArithmeticTask();
        }
        else {
            task = setRandomGeometricTask();
        }
        return task;
    }
    public void addUserTask(Task task) {
        uiStateUser.getValue().add(task);
    }
    public Task setRandomUserTask() {
        if (uiStateUser.getValue().isEmpty()) {
            return createRandomMathematicalTask();
        }
        Random random = new Random();
        Task task = uiStateUser.getValue().get(
                random.nextInt(uiStateUser.getValue().size())
        );
        return task;
    }
    private Task setRandomArithmeticTask() {
        Random random = new Random();
        int image = R.drawable.un_task;
        int first = random.nextInt(100);
        int second = random.nextInt(100);
        int coperator = random.nextInt(3);
        Integer answer = 0;
        String operator = null;
        switch (coperator) {
            case 0:
                operator=" + ";
                answer = first + second;
                break;
            case 1:
                operator=" - ";
                answer = first - second;
                break;
            case 2:
                operator=" * ";
                answer = first * second;
                break;
        }
        String text = "Решить: " + first + operator + second;
        return new Task(text, image, answer.toString());
    }
    private Task setRandomGeometricTask() {
        Random random = new Random();
        int image = R.drawable.gpt;
        String text = "Выбор универа: ";
        int angles = random.nextInt(5);
        String answer = "нет";
        switch (angles) {
            case 0:
                text+="МИРЭА?";
                answer = "Сложно";
                break;
            case 1:
                text+="МГУ?";
                answer = "Сложно";
                break;
            case 2:
                text+="РАНХИГС?";
                answer = "Легко";
                break;
        }
        return new Task(text, image, answer.toString());
    }
}
