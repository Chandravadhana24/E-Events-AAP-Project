package com.example.login_at1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandlaer extends SQLiteOpenHelper {
    Context context;
    private static String DATABASE_NAME="mydb.db";
    private ByteArrayOutputStream image_byteArray;
    private byte[] imageInBytes;
    private static int DATABASE_VERSION=1;
    private static String createTableQuery="create table if not exists imageInfo (imageName TEXT"+",image BLOB)";

    public DatabaseHandlaer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try
        {
            db.execSQL(createTableQuery);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void storeImage(modelClass objectModelClass)
    {

        try
        {
            SQLiteDatabase objectSqLiteDatabase=this.getWritableDatabase();
            Bitmap imageToStoreBitmap=objectModelClass.getImage();
            image_byteArray=new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,image_byteArray);

            imageInBytes=image_byteArray.toByteArray();
            ContentValues objectContentValues= new ContentValues();

            objectContentValues.put("imageName",objectModelClass.getImageNo());
            objectContentValues.put("image",imageInBytes);

            Cursor c=objectSqLiteDatabase.rawQuery("SELECT * FROM imageInfo WHERE imageName='"+objectModelClass.getImageNo()+"';",null);

            if(c.getCount()!=0)
            {
                objectSqLiteDatabase.execSQL("UPDATE imageInfo SET image='"+imageInBytes+"';");
            }
            else
            {
                long checkQuery=objectSqLiteDatabase.insert("imageInfo",null,objectContentValues);

                if(checkQuery!=0)
                {
                    Toast.makeText(context,"Image added",Toast.LENGTH_SHORT).show();
                    objectSqLiteDatabase.close();
                }
                else
                {
                    Toast.makeText(context,"Failed to load image",Toast.LENGTH_SHORT).show();
                }
            }


        }
        catch(Exception e)
        {

        }
    }

    public ArrayList<modelClass> getAllImageData()
    {
        ArrayList<modelClass> objectModelClassList = new ArrayList<>();
        try
        {
            SQLiteDatabase objectSqLiteDatabase=this.getReadableDatabase();

            Cursor objectCursor = objectSqLiteDatabase.rawQuery("select * from imageInfo",null);
            if(objectCursor.getCount()!=0)
            {
                while(objectCursor.moveToNext())
                {
                    String imageNo=objectCursor.getString(0);
                    byte[] imageBytes=objectCursor.getBlob(1);

                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

                    objectModelClassList.add(new modelClass(imageNo,objectBitmap));
                }

            }
        }
        catch(Exception e)
        {

        }
        return objectModelClassList;
    }

    public void updateImage(modelClass objectModelClass)
    {
        try
        {
            SQLiteDatabase objectSqLiteDatabase=this.getWritableDatabase();
            Bitmap imageToStoreBitmap=objectModelClass.getImage();
            image_byteArray=new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG,100,image_byteArray);

            imageInBytes=image_byteArray.toByteArray();
            ContentValues objectContentValues= new ContentValues();


            objectContentValues.put("image",imageInBytes);

            objectSqLiteDatabase.execSQL("UPDATE imageInfo SET image='"+imageInBytes+"' WHERE imageName='"+objectModelClass.getImageNo()+"'");

        }
        catch (Exception e)
        {

        }
    }
    public void deleteImage(modelClass objectModelClass)
    {
        try
        {
            SQLiteDatabase objectSqLiteDatabase=this.getWritableDatabase();
            objectSqLiteDatabase.execSQL("DELETE FROM imageInfo WHERE imageName='"+objectModelClass.getImageNo()+"';");
            //long checkQuery=objectSqLiteDatabase.delete("imageInfo","imageName="+objectModelClass.getImageNo(),null);
            Cursor c=objectSqLiteDatabase.rawQuery("SELECT imageName FROM imageInfo WHERE imageName>'"+objectModelClass.getImageNo()+"';",null);

            if(c.getCount()!=0)
            {
                int ino=Integer.parseInt(c.getString(0));
                ino=ino-1;
                objectSqLiteDatabase.execSQL("UPDATE imageInfo SET imageName='"+Integer.toString(ino)+"' WHERE imageName='"+c.getString(0)+"';");
                Toast.makeText(context,"image deleted",Toast.LENGTH_SHORT).show();
                while(c.moveToNext())
                {
                    ino=Integer.parseInt(c.getString(0));
                    ino=ino-1;
                    objectSqLiteDatabase.execSQL("UPDATE imageInfo SET imageName='"+Integer.toString(ino)+"' WHERE imageName='"+c.getString(0)+"';");
                    Toast.makeText(context,"image deleted",Toast.LENGTH_SHORT).show();
                }
            }
            objectSqLiteDatabase.close();;
        }
        catch(Exception e)
        {

        }
    }

}
