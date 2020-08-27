package com.benmohammad.yoyo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.benmohammad.yoyo.R;
import com.benmohammad.yoyo.snippets.Snip;

import java.util.ArrayList;

public class CommAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Snip> list = new ArrayList<>();
    private Context context;
    private Communicator comm;

    public CommAdapter(ArrayList<Snip> arr, Context context, Communicator comm) {
        list = arr;
        this.context = context;
        this.comm = comm;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Snip getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.snippets, null);
        }

        final TextView content = view.findViewById(R.id.snipname);
        content.setText(list.get(position).getTitle());

        Button btnPaste;

        btnPaste = view.findViewById(R.id.pastesnipicon);

        btnPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.customSetResult(list.get(position).getContent());
            }
        });

        return view;
    }
}
