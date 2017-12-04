package com.example.yds.android_01;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by YDS on 2017/9/21.
 */

public class button2 implements View.OnClickListener {

    public Button b2;
    public ListView list2;
    public Window winmain;
    public  String strname [] ={"宫保鸡丁","水煮肉片","香辣酥肉","酸菜鱼"};
    public  String strtext [] = {"宫保鸡丁色泽赤红诱人，酸辣口，鸡肉滑嫩，花生米爽脆，大葱也好吃。",
                            "水煮肉片是的一种传统四川菜式，几乎四川家家户户都会做。水煮的特色是“麻、辣、鲜、烫”"
                            , "酥肉，是一道特色传统名菜，陕西省长武县的一种地方特色小吃。",
                              "酸菜鱼也称为酸汤鱼，是一道源自重庆的经典菜品，以其特有的调味和独特的烹调技法而著称。"};
    @Override
    public void onClick(View view) {
        winmain.setContentView(R.layout.button2);
        list2=winmain.findViewById(R.id.listview);  //之前一直停止运行，原来list2没有与界面的id绑定。

        ArrayAdapter<String> listadapter=new ArrayAdapter<String>(winmain.getContext(),android.R.layout.simple_expandable_list_item_1);
        for(int i=0;i<strname.length;i++)
            listadapter.add(strname[i]);
        list2.setAdapter(listadapter);

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(winmain.getContext(),strtext[i],Toast.LENGTH_SHORT).show();
            }
        });

    }

    public  button2 (Button b,Window w)
    {
        b2=b;
        winmain=w;
    }

}
