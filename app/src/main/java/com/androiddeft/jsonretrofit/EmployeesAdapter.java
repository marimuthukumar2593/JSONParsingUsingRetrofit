package com.androiddeft.jsonretrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abhi on 23 Sep 2017 023.
 */

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.CustomViewHolder> {
    private List<UserList.UserDatum> employees;
    Context mCon;
    int[] mDrawable = new int[] { R.drawable.user1,
            R.drawable.user2, R.drawable.user3,
            R.drawable.user4, R.drawable.user5, R.drawable.user6,
            R.drawable.user7, R.drawable.user8,
            R.drawable.user9, R.drawable.user10,
            R.drawable.user11,R.drawable.user12 };
    boolean isOffline=true;
    public EmployeesAdapter(ArrayList<UserList.UserDatum> employees,Context context,boolean isoffine) {
        this.employees = employees;
        this.mCon=context;
        this.isOffline=isoffine;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        UserList.UserDatum u=  employees.get(position);
        holder.employeeId.setText("Id : "+String.valueOf(u.id));
        holder.lastname.setText("First Name : " +u.first_name);
        holder.firstName.setText("Last Name : "+u.last_name);

        if(!isOffline) {
            int id =mDrawable[position];
            holder.mUserImage.setBackgroundResource(id);
        }
        else{
            String url =u.avatar;
        Glide.with(mCon).load(url).into(holder.mUserImage);
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView employeeId, firstName, lastname, salary, dob,contactNumber;
public ImageView mUserImage;
        public CustomViewHolder(View view) {
            super(view);
            employeeId = (TextView) view.findViewById(R.id.userId);
            lastname = (TextView) view.findViewById(R.id.lastName);
            firstName = (TextView) view.findViewById(R.id.firstname);
            mUserImage = (ImageView) view.findViewById(R.id.userimage);
        }
    }
}
