package com.m.rabbit.actionprovider;

import android.content.Context;
import android.support.v4.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.m.rabbit.R;

public class MyActionProvider extends ActionProvider{  
  
    private Context context;  
    private LayoutInflater inflater;  
    private View view;  
    private ImageView button;  
    public MyActionProvider(Context context) {  
        super(context);  
        // TODO Auto-generated constructor stub  
        this.context = context;  
        inflater = LayoutInflater.from(context);  
        view = inflater.inflate(R.layout.myactionprovider, null);  
    }  
  
      
    @Override  
    public View onCreateActionView() {  
        // TODO Auto-generated method stub  
        button = (ImageView) view.findViewById(R.id.button);  
        button.setOnClickListener(new View.OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                Toast.makeText(context, " «Œ“£¨√ª¥Ì", Toast.LENGTH_SHORT).show();  
            }  
        });  
        return view;  
    }  
  
}  