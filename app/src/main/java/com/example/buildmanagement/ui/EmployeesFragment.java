package com.example.buildmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.DataAdatpters.AA_RVWorkersAdapter;
import com.example.buildmanagement.Helpers.ImageWorker;
import com.example.buildmanagement.Models.Employee;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;
import com.example.buildmanagement.Services.EmployeeServiceImpl;
import com.example.buildmanagement.EmployeesDetailsActivity;
import com.example.buildmanagement.databinding.FragmentWorkersBinding;

import java.io.File;
import java.util.ArrayList;


public class EmployeesFragment extends Fragment implements RecyclerViewInterface {
    public static  ArrayList<Employee> employees;
    private FragmentWorkersBinding binding;
    private EmployeeServiceImpl employeeService;
    public static AA_RVWorkersAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWorkersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        employeeService = new EmployeeServiceImpl();

        employees = new ArrayList(employeeService.findAll());

        RecyclerView recyclerView = root.findViewById(R.id.mRecyclerViewWorkers);
        adapter = new AA_RVWorkersAdapter(getActivity(), employees, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getActivity().getIntent().putExtra("ACTIVE_FRAGMENT", "EMPLOYEES");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), EmployeesDetailsActivity.class);

        intent.putExtra("ID", employees.get(position).getId());
        intent.putExtra("POSITION", position);

        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        int employeeId = employees.get(position).getId();
        ImageWorker.filedDelete(getContext().getFilesDir() + File.separator + employees.get(position).getPhoto());
        employeeService.delete(employeeId);
        employees.remove(position);
        adapter.notifyItemRemoved(position);
    }
}