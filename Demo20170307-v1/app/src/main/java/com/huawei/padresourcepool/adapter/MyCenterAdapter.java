package com.huawei.padresourcepool.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.huawei.padresourcepool.R;
import com.huawei.padresourcepool.activity.DetailListActivityBackup1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mWX435313 on 2017/2/6.
 */

public class MyCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<String> datas;

    //保存拉出item的集合
    private List<SwipeLayout> openItems;

    //item默认关闭
    boolean isOpen = false;


    public MyCenterAdapter(Context context, List<String> datas){
        this.context = context;
        this.datas = datas;
        openItems = new ArrayList<>();
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            ItemViewHolder holder = new ItemViewHolder(LayoutInflater.from(
                    context).inflate(R.layout.item_mycenter_recycleview, parent,
                    false));
            return holder;

        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_foot_detaillist_recycleview, parent,
                    false);
            return new MyCenterAdapter.FootViewHolder(view);
        }
        return null;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof ItemViewHolder) {

            ((ItemViewHolder) holder).tv_title.setText(datas.get(position));

            initEvent((ItemViewHolder) holder);

        }
    }


    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
       // return datas==null?0:datas.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        /*if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }*/

        return TYPE_ITEM;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_time;
        TextView tv_edit;
        TextView tv_copy;
        TextView tv_delete;

        SwipeLayout swipeLayout;
        View v;


        public ItemViewHolder(View view) {
            super(view);


            v = view;
            swipeLayout = (SwipeLayout) view.findViewById(R.id.swipeLayout);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_edit = (TextView) view.findViewById(R.id.tv_edit);
            tv_copy = (TextView) view.findViewById(R.id.tv_copy);
            tv_delete = (TextView) view.findViewById(R.id.tv_delete);

        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }


    /**
     * 关闭所有的layout
     */
    public void closeAllLayout() {
        if (openItems.size() == 0) {
            return;
        }
        for (SwipeLayout l : openItems) {
            l.close();
        }
        openItems.clear();
    }

    //获取拉开item的数目
    public int getOpenItems() {
        return openItems.size();
    }


    private void initEvent(final ItemViewHolder holder) {



        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getOpenItems()>0){
                    closeAllLayout();
                }else{
                    //Toast.makeText(context, "itemClick" + holder.tv_title.getText().toString(), Toast.LENGTH_SHORT).show(); //条目的点击事件

                    //获取当前holder位置
                    int position = holder.getLayoutPosition();

                    Intent intent = new Intent(context, DetailListActivityBackup1.class);
                    context.startActivity(intent);
                }

            }
        });

        final SwipeLayout swipeLayout = holder.swipeLayout;

        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout swipeLayout) {
                //先去关闭打开的（如果没有打开的直接返回）
                closeAllLayout();
                openItems.add(swipeLayout); //保留

            }

            @Override
            public void onOpen(SwipeLayout swipeLayout) {
                //添加
                openItems.add(swipeLayout);

            }

            @Override
            public void onStartClose(SwipeLayout swipeLayout) {

            }

            @Override
            public void onClose(SwipeLayout swipeLayout) {
                //移除
                openItems.remove(swipeLayout);


            }

            @Override
            public void onUpdate(SwipeLayout swipeLayout, int i, int i1) {

            }

            @Override
            public void onHandRelease(SwipeLayout swipeLayout, float v, float v1) {

            }
        });

        //item菜单的点击事件
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getLayoutPosition();
                Toast.makeText(context, "编辑", Toast.LENGTH_SHORT).show();
                swipeLayout.close();

            }
        });


        holder.tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getLayoutPosition();
                Toast.makeText(context, "复制", Toast.LENGTH_SHORT).show();
                swipeLayout.close();
            }
        });


       holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "删除"+holder.tv_title.getText(), Toast.LENGTH_SHORT).show();
                swipeLayout.close();

                int position = holder.getLayoutPosition();
                datas.remove(position);
                notifyItemRemoved(position);

                notifyItemRangeChanged(position, datas.size() - position);

            }
        });


    }

}