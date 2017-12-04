package last;


import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellType;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by YDS on 2017/11/15.
 */

public class Excle  {

    Context context;
    String filepath;

    public Excle (Context con,String path)
    {
        context=con;
        filepath=path;
    }

    public Sheet readExcle()
    {
        try {
            /**
             * 后续考虑问题,比如Excel里面的图片以及其他数据类型的读取
             **/
            InputStream is = new FileInputStream(filepath);
            Workbook book = null;
            book = Workbook
                    .getWorkbook(new File(filepath));

            book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();

            return sheet;
        }catch (Exception e)
        {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return null;
    }

//    public static void updateExcel(File excelFile,int lie,int rows,String content){
//    // new File("D://test.xls")
//            try {
//    //获得Excel文件
//                Workbook rw= Workbook.getWorkbook(excelFile);
//    //打开一个文件副本，并且指定数据写回到原文件
//                WritableWorkbook book = Workbook.createWorkbook(excelFile, rw);
//                // 这里两处file需一直，表明写回原文件中
//                //得到对应的单元格（列，行）
//                WritableCell writeCL = sheet.getWritableCell(lie, rows);
//                if(writeCL.getType() == CellType.LABEL){
//                    Label l = (Label)writeCL;
//                    l.setString(content);//对单元格里面的内容进行设置
//                }
//                book.write();
//                book.close();
//                rw.close();
//            } catch (BiffException e) {
//    // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//    // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (RowsExceededException e) {
//    // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (WriteException e) {
//    // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//
//        }

    //将数据存入到Excel表中
    // 通过map中的学号与表中的匹配，再通过 map中对应的 单选按钮标号确定是  请假|迟到|旷课，
    // 再取对应的值加 1。次数加1
    public void writeToExcel(Map map) {

        try {
            Workbook oldWwb = Workbook.getWorkbook(new File(filepath));
            WritableWorkbook wwb = Workbook.createWorkbook(new File(filepath), oldWwb);
            WritableSheet ws = wwb.getSheet(0);
            // 从内存中写入文件中,只能刷一次.
            Label lab1;

            for (Object key : map.keySet()) {
                for(int i=1;i<ws.getRows();i++)
                {
                    if(key.equals(ws.getCell(1,i).getContents())) {
                        int j=Integer.parseInt(ws.getCell(2+Integer.parseInt(map.get(key).toString()),i).getContents());
                        //Toast.makeText(context, "key= " + key + " and value= " + map.get(key), Toast.LENGTH_SHORT).show();
                        lab1 = new Label(2+Integer.parseInt(map.get(key).toString()), i,j+1+"");
                        ws.addCell(lab1);
                    }
                }
            }

            wwb.write();
            wwb.close();
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
