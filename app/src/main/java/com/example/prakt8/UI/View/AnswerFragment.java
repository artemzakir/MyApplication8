package com.example.prakt8.UI.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.TextView;

import com.example.prakt8.R;
import com.example.prakt8.UI.VM.TaskListViewModel;

public class AnswerFragment extends Fragment {
    public AnswerFragment() {
        super(R.layout.fragment_answer);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        TextView tTaskText = view.findViewById(R.id.t_tasktext);
        TextView tAnswer = view.findViewById(R.id.t_taskanswer);
        TaskListViewModel taskListViewModel = new ViewModelProvider(getActivity()).get(TaskListViewModel.class);
        taskListViewModel.getUiState().observe(getViewLifecycleOwner(), uiState -> {
            tTaskText.setText(uiState.
                    get(bundle.getInt("getElement")).getText());
            tAnswer.setText(uiState.
                    get(bundle.getInt("getElement")).getAnswer());
                });
        getParentFragmentManager().setFragmentResult("getElement", bundle);
    }
}