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

import com.example.buildmanagement.DataAdatpters.AA_RVOrdersAdapter;
import com.example.buildmanagement.Models.Order;
import com.example.buildmanagement.OrdersDetailsActivity;
import com.example.buildmanagement.R;
import com.example.buildmanagement.RecyclerViewInterface;
import com.example.buildmanagement.Services.OrderServiceImpl;
import com.example.buildmanagement.databinding.FragmentOrdersBinding;

import java.util.ArrayList;

public class OrdersFragment extends Fragment implements RecyclerViewInterface {
    private FragmentOrdersBinding binding;
    public static ArrayList<Order> orders;
    public static AA_RVOrdersAdapter adapter;
    private  OrderServiceImpl orderService;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        orderService = new OrderServiceImpl();

        orders = new ArrayList(orderService.findAll());

        RecyclerView recyclerView = root.findViewById(R.id.mRecyclerViewOrders);
        adapter = new AA_RVOrdersAdapter(getActivity(), orders, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getActivity().getIntent().putExtra("ACTIVE_FRAGMENT", "ORDERS");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), OrdersDetailsActivity.class);

        intent.putExtra("ID", orders.get(position).getId());
        intent.putExtra("POSITION", position);

        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        int orderId = orders.get(position).getId();
        orderService.delete(orderId);
        orders.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
