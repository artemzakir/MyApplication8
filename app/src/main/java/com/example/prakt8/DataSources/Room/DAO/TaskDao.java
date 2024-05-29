package com.example.prakt8.DataSources.Room.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.prakt8.DataSources.Room.Entities.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task")
    public List<Task> getTasks();
    @Query("SELECT * FROM Task WHERE id == (:index)")
    public Task get(int index);
    @Insert
    public void add(Task task);
    @Query("DELETE FROM Task")
    public void clearAll();
    @Query("SELECT COUNT(*) FROM Task")
    public int getSize();
}
