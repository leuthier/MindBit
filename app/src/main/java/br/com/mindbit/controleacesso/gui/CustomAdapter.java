package br.com.mindbit.controleacesso.gui;

import android.app.Activity;
import android.content.ClipData.Item;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Item>{

    private Activity context;
    private int id;
    ArrayList<Item> array;
    public CustomAdapter(Activity context,int resource, ArrayList<Item> objects){
        super(context,resource,objects);
        this.context = context;
        this.id = resource;
        this.array = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
        }


        return super.getView(position, convertView, parent);
    }
}
