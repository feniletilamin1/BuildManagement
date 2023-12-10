package com.example.buildmanagement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buildmanagement.ClientsDetailsActivity;
import com.example.buildmanagement.DataAdatpters.AA_RVClientsAdapter;
import com.example.buildmanagement.Helpers.ImageWorker;
import com.example.buildmanagement.Models.Client;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;
import com.example.buildmanagement.Services.ClientServiceImpl;
import com.example.buildmanagement.databinding.FragmentClientsBinding;

import java.io.File;
import java.util.ArrayList;

public class ClientsFragment extends Fragment implements RecyclerViewInterface {
    public static ArrayList<Client> clients;
    private FragmentClientsBinding binding;
    public static AA_RVClientsAdapter adapter;
    private ClientServiceImpl clientService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentClientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        clientService = new ClientServiceImpl();

        clients = new ArrayList(clientService.findAll());

        RecyclerView recyclerView = root.findViewById(R.id.mRecyclerViewClients);
        adapter = new AA_RVClientsAdapter(getActivity(), clients, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getActivity().getIntent().putExtra("ACTIVE_FRAGMENT", "CLIENTS");

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), ClientsDetailsActivity.class);

        intent.putExtra("ID", clients.get(position).getId());
        intent.putExtra("POSITION", position);

        startActivity(intent);

    }

    @Override
    public void onItemLongClick(int position) {
        int clientId = clients.get(position).getId();
        ImageWorker.filedDelete(getContext().getFilesDir() + File.separator + clients.get(position).getPhoto());
        clientService.delete(clientId);
        clients.remove(position);
        adapter.notifyItemRemoved(position);}
}