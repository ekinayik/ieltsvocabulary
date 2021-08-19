package com.example.ielts_vocabulary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Random;

public class Questions extends AppCompatActivity {
    RadioButton rb1,rb2,rb3,rb4;
    RadioGroup rg;
    Button bt1,bt31;
    TextView tw1,tw_answer;
    Workbook vocabularylist;
    Cursor cursor;
    int checked;
    int crt;
    Random rn=new Random();
    int ans,rad1,rad2,rad3;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.homeit:
                startActivity(new Intent(Questions.this,MainActivity.class));
                break;
            case R.id.questionsit:
                startActivity(new Intent(Questions.this,Questions.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vocabularylist=new HSSFWorkbook();
        vocabularylist.createSheet();
        SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("Vocabulary",MODE_PRIVATE,null);
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM Vocabulary",null);
        int sentenceix=cursor.getColumnIndex("sentence");
        int idx=cursor.getColumnIndex("PK");
        int answerx=cursor.getColumnIndex("answer");
        int typex=cursor.getColumnIndex("type");
        int statusx=cursor.getColumnIndex("status");
        int size=cursor.getCount();
        int i=0;
        while (cursor.moveToNext())
        {
                String sentence=cursor.getString(sentenceix);
                String answer=cursor.getString(answerx);
                String type=cursor.getString(typex);
                int status=cursor.getInt(statusx);
                int id=cursor.getInt(idx);
                vocabularylist.getSheetAt(0).createRow(i);
                vocabularylist.getSheetAt(0).getRow(i).createCell(0);
                vocabularylist.getSheetAt(0).getRow(i).createCell(1);
                vocabularylist.getSheetAt(0).getRow(i).createCell(2);
                vocabularylist.getSheetAt(0).getRow(i).createCell(3);
                vocabularylist.getSheetAt(0).getRow(i).createCell(4);
                vocabularylist.getSheetAt(0).getRow(i).getCell(0).setCellValue(id);
                vocabularylist.getSheetAt(0).getRow(i).getCell(1).setCellValue(sentence);
                vocabularylist.getSheetAt(0).getRow(i).getCell(2).setCellValue(answer);
                vocabularylist.getSheetAt(0).getRow(i).getCell(3).setCellValue(type);
                vocabularylist.getSheetAt(0).getRow(i).getCell(4).setCellValue(status);
                i++;
        }
        vocabularylist.createSheet();
        setContentView(R.layout.activity_questions);
        bt1=findViewById(R.id.button);
        bt31=findViewById(R.id.button5);
        rg=findViewById(R.id.radioGroup);
        rb1=findViewById(R.id.radioButton);
        rb2=findViewById(R.id.radioButton2);
        rb3=findViewById(R.id.radioButton3);
        rb4=findViewById(R.id.radioButton4);
        tw1=findViewById(R.id.textView);
        tw_answer=findViewById(R.id.textView2);
        tw_answer.setText("Answer");
        tw_answer.setTextColor(0xff000000);
        ans=rn.nextInt(325);
        rad1=rn.nextInt(325);
        while(rad1==ans)
        {
            rad1=rn.nextInt(325);
        }
        rad2=rn.nextInt(325);
        while(rad2==rad1||rad2==ans)
        {
            rad2=rn.nextInt(325);
        }
        rad3=rn.nextInt(325);
        while(rad3==rad1||rad3==rad2||rad3==ans)
        {
            rad3=rn.nextInt(325);
        }
        tw1.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(1).getStringCellValue());
        crt=rn.nextInt(4);
        switch (crt)
        {
            case 0:
                rb1.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                rb2.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                rb3.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                rb4.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                break;
            case 1:
                rb1.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                rb2.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                rb3.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                rb4.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                break;
            case 2:
                rb1.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                rb2.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                rb3.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                rb4.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                break;
            case 3:
                rb1.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                rb2.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                rb3.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                rb4.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                break;
            default:
                break;
        }
        bt31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked=rg.getCheckedRadioButtonId();
                if(crt==0&&checked==R.id.radioButton)
                {
                    tw_answer.setText("True");
                    tw_answer.setTextColor(0xff0000ff);
                }
                else
                if(crt==1&&checked==R.id.radioButton2)
                {
                    tw_answer.setText("True");
                    tw_answer.setTextColor(0xff0000ff);
                }
                else
                if(crt==2&&checked==R.id.radioButton3)
                {
                    tw_answer.setText("True");
                    tw_answer.setTextColor(0xff0000ff);
                }
                else
                if(crt==3&&checked==R.id.radioButton4)
                {
                    tw_answer.setText("True");
                    tw_answer.setTextColor(0xff0000ff);
                }
                else
                {
                    tw_answer.setText("Wrong");
                    tw_answer.setTextColor(0xffff0000);
                }
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tw_answer.setText("Answer");
                tw_answer.setTextColor(0xff000000);
                ans=rn.nextInt(325);
                rad1=rn.nextInt(325);
                while(rad1==ans)
                {
                    rad1=rn.nextInt(325);
                }
                rad2=rn.nextInt(325);
                while(rad2==rad1||rad2==ans)
                {
                    rad2=rn.nextInt(325);
                }
                rad3=rn.nextInt(325);
                while(rad3==rad1||rad3==rad2||rad3==ans)
                {
                    rad3=rn.nextInt(325);
                }
                tw1.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(1).getStringCellValue());
                crt=rn.nextInt(4);
                switch (crt)
                {
                    case 0:
                        rb1.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                        rb2.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                        rb3.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                        rb4.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                        break;
                    case 1:
                        rb1.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                        rb2.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                        rb3.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                        rb4.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                        break;
                    case 2:
                        rb1.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                        rb2.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                        rb3.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                        rb4.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                        break;
                    case 3:
                        rb1.setText(vocabularylist.getSheetAt(0).getRow(rad3).getCell(2).getStringCellValue());
                        rb2.setText(vocabularylist.getSheetAt(0).getRow(rad1).getCell(2).getStringCellValue());
                        rb3.setText(vocabularylist.getSheetAt(0).getRow(rad2).getCell(2).getStringCellValue());
                        rb4.setText(vocabularylist.getSheetAt(0).getRow(ans).getCell(2).getStringCellValue());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}