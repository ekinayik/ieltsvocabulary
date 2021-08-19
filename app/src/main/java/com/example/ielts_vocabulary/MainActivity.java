package com.example.ielts_vocabulary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<VocabularyModel> vocabularyModelList=new ArrayList<>();
    Button bt1,bt2,bt3;
    Intent fileintent;
    String path;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    boolean confirmation;
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
                startActivity(new Intent(MainActivity.this,MainActivity.class));
                break;
            case R.id.questionsit:
                startActivity(new Intent(MainActivity.this,Questions.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1=findViewById(R.id.button4);
        bt2=findViewById(R.id.button2);
        bt3=findViewById(R.id.button3);
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Are you confirm to reset app?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmation=true;
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmation=false;
            }
        });
        SQLiteDatabase sqLiteDatabase=openOrCreateDatabase("Vocabulary",MODE_PRIVATE,null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Vocabulary (PK INTEGER PRIMARY KEY, sentence VARCHAR, answer VARCHAR, type VARCHAR, status INT)");
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog=builder.create();
                dialog.show();
                if(confirmation)
                {
                sqLiteDatabase.execSQL("DELETE FROM Vocabulary");
                AssetManager assetManager=getAssets();
                try {
                    InputStream inputStream=assetManager.open("test.xls");
                    POIFSFileSystem fileSystem=new POIFSFileSystem(inputStream);
                    HSSFWorkbook wb=new HSSFWorkbook(fileSystem);
                    for(int i=0;wb.getSheetAt(0).getRow(i)!=null;i++)
                    {
                        sqLiteDatabase.execSQL("INSERT INTO Vocabulary (sentence,answer,type,status) VALUES ('"+wb.getSheetAt(0).getRow(i).getCell(0).getStringCellValue()
                                +"','"+wb.getSheetAt(0).getRow(i).getCell(2).getStringCellValue()+"','"
                                +wb.getSheetAt(0).getRow(i).getCell(1).getStringCellValue()+"','0')");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Questions.class));
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileintent=new Intent(Intent.ACTION_GET_CONTENT);
                fileintent.setType("*/*");
                startActivityForResult(fileintent,10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        switch (requestCode)
        {
            case 10:
                if(requestCode==RESULT_OK)
                {
                    path=data.getData().getPath();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}