package com.example.dell.asnavigation;

/**
 * Created by dell on 7/31/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdaptera extends BaseAdapter{
    String [] result;
    String [] display={"Preventive care adherencecover baseline, year after year","Swift consumer switches to preferred pharmacies","Game-changing ROI through evidence-based adherence","Improved outcomes through personalized incentives"};
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public CustomAdaptera(Aboutus aboutus, String[] prgmNameList1, int[] prgmImages1) {

        // TODO Auto-generated constructor stub
        result=prgmNameList1;
        context=aboutus;
        imageId=prgmImages1;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
        TextView abouttv;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.aboutprgm, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView11);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView11);
        holder.abouttv=(TextView)rowView.findViewById(R.id.abouttxt);
        holder.tv.setText(result[position]);

        holder.img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
      //  holder.abouttv.setText(display[position]);
                Toast.makeText(context, " "+display[position], Toast.LENGTH_LONG).show();


            }
        });

        return rowView;
    }

}