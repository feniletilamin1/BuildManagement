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

import com.example.buildmanagement.DataAdatpters.AA_RVObjectsAdapter;
import com.example.buildmanagement.Models.BuildObject;
import com.example.buildmanagement.ObjectsDetailsActivity;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;
import com.example.buildmanagement.Services.BuildObjectServiceImpl;
import com.example.buildmanagement.databinding.FragmentObjectsBinding;

import java.util.ArrayList;

public class ObjectsFragment extends Fragment implements RecyclerViewInterface {
    public static ArrayList<BuildObject> objects;
    private FragmentObjectsBinding binding;
    public static AA_RVObjectsAdapter adapter;
    private BuildObjectServiceImpl buildObjectService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentObjectsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buildObjectService = new BuildObjectServiceImpl();

        objects = new ArrayList(buildObjectService.findAll());

        RecyclerView recyclerView = root.findViewById(R.id.mRecyclerViewObjects);
        adapter = new AA_RVObjectsAdapter(getActivity(), objects, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getActivity().getIntent().putExtra("ACTIVE_FRAGMENT", "OBJECTS");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), ObjectsDetailsActivity.class);

        intent.putExtra("ID", objects.get(position).getId());
        intent.putExtra("POSITION", position);

        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        int objectId = objects.get(position).getId();
        buildObjectService.delete(objectId);
        objects.remove(position);
        adapter.notifyItemRemoved(position);
    }
}