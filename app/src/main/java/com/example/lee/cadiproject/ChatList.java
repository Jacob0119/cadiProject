package com.example.lee.cadiproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.tubb.smrv.SwipeMenuLayout;
import com.tubb.smrv.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatList extends Fragment {

    private Context mContext;
    private List<Room> rooms;
    private myAdapter mAdapter;
    private SwipeMenuRecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

 //test
    @Override
  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_chat,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



       mContext=getContext();

        getUsers();// 테스트용 데이터 만들기
        swipeRefreshLayout=(SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Toast.makeText(getContext(), "refreshing completed!",Toast.LENGTH_SHORT).show();



                swipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView = (SwipeMenuRecyclerView) getView().findViewById(R.id.listview);
        mRecyclerView.addItemDecoration(new customItemDecoration());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setSwipeDirection(SwipeMenuRecyclerView.DIRECTION_RIGHT);
        // interpolator setting
        mRecyclerView.setOpenInterpolator(new BounceInterpolator());
        mRecyclerView.setCloseInterpolator(new BounceInterpolator());
        mAdapter = new myAdapter(mContext, rooms);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getUsers() {
        rooms = new ArrayList<>();
        for (int i=0; i<100; i++){
            Room room = new Room(i+1,"school"+(i+1));

            rooms.add(room);
        }

    }

    class myAdapter extends RecyclerView.Adapter{

        Context mContext;
        List<Room> rooms;
        myAdapter(Context mContext,List rooms){
            this.mContext=mContext;
            this.rooms=rooms;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_simple,parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Room room=rooms.get(position);
                final MyViewHolder myViewHolder=(MyViewHolder)holder;
            SwipeMenuLayout itemView=(SwipeMenuLayout)myViewHolder.itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Hi " + room.userName, Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.btGood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(myViewHolder.itemView.getContext(), "Good", Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.btOpen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Open " + room.userName, Toast.LENGTH_SHORT).show();
                }
            });
            myViewHolder.btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rooms.remove(myViewHolder.getAdapterPosition());
                    mAdapter.notifyItemRemoved(myViewHolder.getAdapterPosition());
                }
            });
            myViewHolder.tvName.setText("ChatRoom:"+room.userId);
            //boolean swipeEnable = swipeEnableByViewType(getItemViewType(position));
            myViewHolder.tvSwipeEnable.setText(room.userName );

            /**
             * optional
             */
            itemView.setSwipeEnable(true);
            itemView.setOpenInterpolator(mRecyclerView.getOpenInterpolator());
            itemView.setCloseInterpolator(mRecyclerView.getCloseInterpolator());

        }

        @Override
        public int getItemCount() {
            return rooms.size();
        }

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvSwipeEnable;
        View btGood;
        View btOpen;
        View btDelete;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSwipeEnable = (TextView) itemView.findViewById(R.id.tvSwipeEnable);
            btGood = itemView.findViewById(R.id.btGood);
            btOpen = itemView.findViewById(R.id.btOpen);
            btDelete = itemView.findViewById(R.id.btDelete);
        }
    }

    class Room{
        private int userId;
        private String userName;

        Room(int userId,String userName){

            this.userId=userId;
            this.userName=userName;
        }
    }
}
