package com.example.testwallpaper;

import android.app.WallpaperManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.testwallpaper.DbHelper.COL_PICTURE;

public class MainActivity extends AppCompatActivity {
    int count = 0 ;
    ArrayList<PictureItem> mPictureItemList;
    private DbHelper mHelper;
    private SQLiteDatabase mDb;
    private picListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new DbHelper(this);
        mDb = mHelper.getReadableDatabase();

        mPictureItemList = new ArrayList<>();
        Cursor cursor = mDb.query(mHelper.TABLE_NAME,null,null,null,null,null, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbHelper.COL_ID));
            String type = cursor.getString(cursor.getColumnIndex(DbHelper.COL_TYPE));
            int picture = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.COL_PICTURE)));

            PictureItem item = new PictureItem(id,type,picture);
            mPictureItemList.add(item);
        }
        //adapterList = new AdapterList(this,R.layout.activity_sunset, checkType("SUNSET"));
        mAdapter = new picListAdapter(this,R.layout.activity_item, mPictureItemList);
        ListView iv = (ListView)findViewById(R.id.list_item);
        iv.setAdapter(mAdapter);
        iv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PictureItem item = mPictureItemList.get(i);
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    wallpaperManager.setResource(item.picture);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /*public ArrayList<PictureItem> checkType(String currentType){
        ArrayList<PictureItem> currentPictureItem = new ArrayList<>();

        for(int index = 0 ; index < mPictureItemList.size(); ++index) {
            if(mPictureItemList.get(index).type.equals(currentType)) {
                currentPictureItem.add(mPictureItemList.get(index));
            }
        }

        return currentPictureItem;
    }*/
}
