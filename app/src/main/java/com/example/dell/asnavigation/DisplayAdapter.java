package com.example.dell.asnavigation;

/**
 * Created by dell on 7/31/2016.
 */
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dell.asnavigation.R;
import java.util.ArrayList;
import java.util.HashMap;


public class DisplayAdapter extends BaseAdapter {
    Context context;
    int resourseid;
    int colorFlag;

    ArrayList<HashMap<String,Object>> arrayList;
    DisplayAdapter(Context context,int resourseid,ArrayList<HashMap<String,Object>> arrayList){
        this.context=context;
        this.resourseid=resourseid;
        this.arrayList=arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class Holder
    {
        TextView t1,t2,t3,t4;
        LinearLayout ll;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View convertView1 = null;
        Holder h = new Holder();

        if(convertView1==null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView1 = inflater.inflate(resourseid, parent, false);
            h.ll=(LinearLayout)convertView1.findViewById(com.example.dell.asnavigation.R.id.parentq);
            h.t1 = (TextView)convertView1.findViewById(R.id.hospnamee);
            h.t2 = (TextView)convertView1.findViewById(R.id.fordcc);
            h.t3 = (TextView)convertView1.findViewById(R.id.doctornamee);
            h.t4=(TextView)convertView1.findViewById(R.id.resultt);

            convertView1.setTag(h);
        }
        else
        {
            h = (Holder) convertView1.getTag();
        }

        h.t1.setText((String)arrayList.get(position).get("Hospitalname"));
        h.t2.setText((String)arrayList.get(position).get("Visitfor"));
        h.t3.setText((String)arrayList.get(position).get("Doctorname"));
        h.t4.setText((String)arrayList.get(position).get("Result"));
        colorFlag = (int)arrayList.get(position).get("flag");

        if(colorFlag == 0)
        {
            String bgcolor="#FFDFE5";
              h.ll.setBackgroundColor(Color.parseColor(bgcolor));
            h.t1.setTextColor(Color.BLUE);
            h.t2.setTextColor(Color.BLUE);
            h.t3.setTextColor(Color.BLUE);
            h.t4.setTextColor(Color.BLUE);
        }
        else if(colorFlag == 1)
        {
            String bgcolor1="#E5FFFF";
            h.ll.setBackgroundColor(Color.parseColor(bgcolor1));
            h.t1.setTextColor(Color.RED);
            h.t2.setTextColor(Color.RED);
            h.t3.setTextColor(Color.RED);
            h.t4.setTextColor(Color.RED);
        }
        return convertView1 ;
    }
}
