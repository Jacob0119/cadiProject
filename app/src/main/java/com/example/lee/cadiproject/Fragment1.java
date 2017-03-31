package com.example.lee.cadiproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    private Context mContext;
    private List<Room> rooms;
    private myAdapter mAdapter;
    private RecyclerView mRecyclerView;


 //test
    @Override
  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




//
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return   inflater.inflate(R.layout.fragment_chat,container,false);


    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



       mContext=getContext();

        getChatroomList();// 테스트용 데이터 만들기


        mRecyclerView = (RecyclerView) getView().findViewById(R.id.listview);
        mRecyclerView.addItemDecoration(new customItemDecoration());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // interpolator setting

        mAdapter = new myAdapter(mContext, rooms);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getChatroomList() {
        rooms = new ArrayList<>();
        for (int i=0; i<100; i++){
            Room room = new Room(""+(i+1),"school"+(i+1));

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

            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_simple_content,parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Room room=rooms.get(position);
                final MyViewHolder myViewHolder=(MyViewHolder)holder;
            LinearLayout itemView=(LinearLayout)myViewHolder.itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Snackbar.make(mRecyclerView, "Hi " + room.roomName, Snackbar.LENGTH_LONG).show();



                }
            });



            myViewHolder.roomName.setText("ChatRoom:"+room.roomName);
            myViewHolder.schoolName.setText(room.schoolName );

            /**
             * optional
             */



        }

        @Override
        public int getItemCount() {
            return rooms.size();
        }

    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView roomName;
        TextView schoolName;
        View image;

        public MyViewHolder(View itemView) {
            super(itemView);
            roomName = (TextView) itemView.findViewById(R.id.item_Name);
            schoolName = (TextView) itemView.findViewById(R.id.item_school);
            image = itemView.findViewById(R.id.item_image);

        }
    }

    class Room{
        private String roomName;
        private String schoolName;

        Room(String roomName,String schoolName){

            this.roomName=roomName;
            this.schoolName=schoolName;
        }
    }





}
