package com.example.racs.Locks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.racs.Locks.LocksPOJO;
import com.example.racs.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private final List<LocksPOJO.Lock> locks;
    private final Context context;
    private final LayoutInflater inflater;

    //конструктор для класса listadapter
    public ListAdapter(Context context, List<LocksPOJO.Lock> locks){
        this.context = context;
        this.locks = locks;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //возвращаем размер списка
    @Override
    public int getCount() {
        return locks.size();
    }
    //возвращаем конкретный элемент
    @Override
    public Object getItem(int i) {
        return locks.get(i);
    }
    //возвращаем идентификатор конкретного элемента
    @Override
    public long getItemId(int i) {
        return i;
    }
    //возвращаем view, т.е. задаем правила инициализации каждого listItem
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowItem = inflater.inflate(R.layout.list_item, viewGroup, false);
        TextView t2 = rowItem.findViewById(R.id.t2);
        TextView t3 = rowItem.findViewById(R.id.t3);
        TextView t4 = rowItem.findViewById(R.id.t4);

        LocksPOJO.Lock lock = locks.get(i);
        t2.setText(Integer.toString(lock.getLId()));
        t3.setText(lock.getDescription());
        t4.setText(lock.getVersion());
        //t5.setText(lock.getLastEcho());
        return rowItem;
    }
}
