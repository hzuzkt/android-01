package last;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yds.android_01.R;

import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;

/**
 *                 课堂点名系统
 上课时，任课教师打开应用，界面上直接呈现出学生的照片、姓名、学号等信息，
 以及提供迟到、旷课 、请假等选择项，方便教师点击，
 每页可只呈现一名或两名学生信息，点击一个学生后界面自动调入下 一个学生的信息。
 还需要实现Excel文档的自动导入和导出功能
 *
 * Created by YDS on 2017/11/14.
 */

public class Main_student extends AppCompatActivity {
    Excle excle;
    Sheet sheet;
    Boolean isnext=true;
    Boolean isclear=true;
    ImageView iv1, iv2;
    TextView tvna1,tvna2,tvnum1,tvnum2;
    Button btbefor,btafter,btsave;
    RadioGroup rg1,rg2;
    /// mMap 存的是 被选中的（学号，单选按钮编号）
    Map<String,Object> mMap = new HashMap<String,Object>();

    int rows,clos;                            //row 代表excle中的行数  clo代表列数
    int row=1,clo=0;
    String name="姓名：",number="学号：";
    String filePath = Environment.getExternalStorageDirectory().getPath()
            + "/android-last/android_student.xls";
    String picFilPath = Environment.getExternalStorageDirectory().getPath()
            + "/android-last/student-picture/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        tvna1=(TextView)findViewById(R.id.tvna1);
        tvna2=(TextView)findViewById(R.id.tvna2);
        tvnum1=(TextView)findViewById(R.id.tvnum1);
        tvnum2=(TextView)findViewById(R.id.tvnum2);
        btbefor=(Button)findViewById(R.id.btbefor);
        btafter=(Button)findViewById(R.id.btafter);
        btsave=(Button)findViewById(R.id.btsave);
        rg1=(RadioGroup)findViewById(R.id.radiogroup1);
        rg2=(RadioGroup)findViewById(R.id.radiogroup2);

        final Matrix matrix=getmatrix();
         excle = new Excle(getApplicationContext(), filePath);
        sheet = excle.readExcle();

        rows=sheet.getRows();    //总行数
        clos=sheet.getColumns(); //总列数
        updatenanum(tvna1,tvnum1);
        updatepicture(iv1,matrix);
        updatenanum(tvna2,tvnum2);
        updatepicture(iv2,matrix);

        btbefor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isnext=false;
                btafter.setEnabled(true);
                row=row-4;
                if(tvna2.getVisibility()==View.INVISIBLE) {
                    isclear = false;
                    clearsecond(isclear);
                }
                if(row==1) {
                    btbefor.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"这已经是第一页啦！",Toast.LENGTH_SHORT).show();
                }
                if (1<=row|| row <= rows-1) {
                    updatenanum(tvna1, tvnum1);
                    updatepicture(iv1, matrix);
                }

                if (row>=1||row <= rows-1) {
                    updatenanum(tvna2, tvnum2);
                    updatepicture(iv2, matrix);
                }
            }


        });
        btafter.setOnClickListener(new View.OnClickListener() {
           // 完成下一页的替换，和获取单选按钮组的选中情况
            @Override
            public void onClick(View view) {

                ////////////////////获取单选按钮的选中情况
                getchecked();
                //////////////////////////
                    isnext=true;
                    btbefor.setEnabled(true);
                    if (row <= rows-1) {

                        updatenanum(tvna1, tvnum1);
                        updatepicture(iv1, matrix);
                        //Toast.makeText(getApplicationContext(),"row:"+row+"rows:"+rows,Toast.LENGTH_SHORT).show();
                    }
                    else {
                       // Toast.makeText(getApplicationContext(), "row:" + row + "rows:" + rows, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "这已经是最后一页啦\n请注意保存哦！", Toast.LENGTH_SHORT).show();
                        btafter.setEnabled(false);
                    }
                     if (row <= rows-1) {
                        //Toast.makeText(getApplicationContext(),"row:"+row+"rows:"+rows,Toast.LENGTH_SHORT).show();
                         updatenanum(tvna2, tvnum2);
                         updatepicture(iv2, matrix);
                     }
                    else
                    {

                        Toast.makeText(getApplicationContext(),"这已经是最后一页啦\n请注意保存哦！",Toast.LENGTH_SHORT).show();
                        btafter.setEnabled(false);
                        row++;
                       // Toast.makeText(getApplicationContext(),"row:"+row+"rows:"+rows,Toast.LENGTH_SHORT).show();
                        if(rows%2==0) {
                            isclear=true;
                            clearsecond(isclear);
                        }
                    }
                    if(row > rows-1)
                    {
                        Toast.makeText(getApplicationContext(),"这已经是最后一页啦\n请注意保存哦！",Toast.LENGTH_SHORT).show();
                        btafter.setEnabled(false);
                    }
            }
        });
        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getchecked();
                excle.writeToExcel(mMap);

            }
        });
    }

    public Matrix getmatrix()
    {
        String picpath = picFilPath + "martin" + ".jpg";
        //////////////
        //对图片进行放大处理 固定图片大小
        Bitmap bmp = BitmapFactory.decodeFile(picpath);
        // 获得图片的宽高
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        // 设置想要的大小
        int newWidth = 600;
        int newHeight = 600;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return matrix;
    }
    public void updatepicture(ImageView iv,Matrix matrix) {
        String studname="";
        studname = sheet.getCell(0, row).getContents();
        row++;
        String picpath = picFilPath + studname + ".jpg";
        Bitmap bmp=BitmapFactory.decodeFile(picpath);
        // 得到新的图片

        Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0,200,200, matrix,
                true);
        iv.setImageBitmap(newbm);

    }

    public void updatenanum(TextView tvname,TextView tvnumber)
    {

            tvname.setText(name + sheet.getCell(0, row).getContents());
            tvnumber.setText(number + sheet.getCell(1, row).getContents());


    }
    public void clearsecond(Boolean isclear)
    {
        if(isclear) {
            RadioGroup rg2 = (RadioGroup) findViewById(R.id.radiogroup2);
            iv2.setVisibility(View.INVISIBLE);
            tvna2.setVisibility(View.INVISIBLE);
            tvnum2.setVisibility(View.INVISIBLE);
            iv2.setVisibility(View.INVISIBLE);
            rg2.setVisibility(View.INVISIBLE);
        }
        else
        {
            RadioGroup rg2 = (RadioGroup) findViewById(R.id.radiogroup2);
            iv2.setVisibility(View.VISIBLE);
            tvna2.setVisibility(View.VISIBLE);
            tvnum2.setVisibility(View.VISIBLE);
            iv2.setVisibility(View.VISIBLE);
            rg2.setVisibility(View.VISIBLE);
        }
    }
    public void getchecked()//将当前页的单选按钮选中情况记录下来
    {
        RadioButton rb1,rb2;
        for(int i=0;i<rg1.getChildCount();i++) {
            rb1 = (RadioButton) rg1.getChildAt(i);
            if(rb1.isChecked()) {
                mMap.put(tvnum1.getText().subSequence(3,tvnum2.getText().length()).toString(), i);
                //Toast.makeText(getApplicationContext(),tvnum1.getText().subSequence(3,tvnum1.getText().length()).toString()+";"+i,Toast.LENGTH_SHORT).show();
                rb1.setChecked(false);
            }
        }
        for(int i=0;i<rg2.getChildCount();i++) {
            rb2 = (RadioButton) rg2.getChildAt(i);
            if(rb2.isChecked()) {
                mMap.put(tvnum2.getText().subSequence(3,tvnum2.getText().length()).toString(), i);
                //Toast.makeText(getApplicationContext(),tvnum2.getText().subSequence(3,tvnum2.getText().length()).toString()+";"+i,Toast.LENGTH_SHORT).show();
                rb2.setChecked(false);
            }
        }
    }
}

