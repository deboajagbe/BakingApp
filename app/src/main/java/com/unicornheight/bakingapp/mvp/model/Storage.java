
package com.unicornheight.bakingapp.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class Storage extends SQLiteOpenHelper {

    private static final String TAG = Storage.class.getSimpleName();

    @Inject
    public Storage(Context context) {
        super(context, "cakes_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addCake(Cake cake) {
        List<CakesResponseIngredients> cakesResponseIngredients = cake.getIngredients();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, cake.getId());
        values.put(TITLE, cake.getName());
        values.put(SERVINGS, cake.getServings());
        values.put(IMAGE_URL, cake.getImage());
        for (CakesResponseIngredients cakes : cakesResponseIngredients) {
            values.put(MEASURE, cakes.getMeasure());
            values.put(QUANTITY, cakes.getQuantity());
            values.put(INGREDIENT, cakes.getIngredient());
        }

        try {
            db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
        }

        db.close();
    }

    public List<Cake> getSavedCakes() {
        List<Cake> cakeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(SELECT_QUERY, null);
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            Cake cake = new Cake();
                            CakesResponseIngredients cakesResponseIngredients = new CakesResponseIngredients();
                            cake.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
                            cake.setServings(cursor.getInt(cursor.getColumnIndex(SERVINGS)));
                            cakesResponseIngredients.setMeasure(cursor.getString(cursor.getColumnIndex(MEASURE)));
                            cakesResponseIngredients.setQuantity(cursor.getInt(cursor.getColumnIndex(QUANTITY)));
                            cakesResponseIngredients.setIngredient(cursor.getString(cursor.getColumnIndex(INGREDIENT)));
                            List<CakesResponseIngredients> cakeIngredientList = new ArrayList<>();
                            cakeIngredientList.add(cakesResponseIngredients);
                            cake.setIngredients(cakeIngredientList);
//                            cake.setIngredients(cursor.getString(cursor.getColumnIndex(INGREDIENT)));
//                            cake.setSteps(cursor.getString(cursor.getColumnIndex(STEPS)));
//                            cakesResponseIngredients.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(QUANTITY))));
//                            cakesResponseSteps.setVideoURL(cursor.getString(cursor.getColumnIndex(VIDEO_URL)));
//                            cakesResponseSteps.setThumbnailURL(cursor.getString(cursor.getColumnIndex(THUMBNAIL_URL)));
//                            cakesResponseSteps.setShortDescription(cursor.getString(cursor.getColumnIndex(SHORT_DESCRIPTION)));
//                            cakesResponseSteps.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                            cake.setImage(cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
                            cakeList.add(cake);

                        } while (cursor.moveToNext());
                    }
                }
            }
            cursor.close();
        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
        return cakeList;
    }

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String SERVINGS = "servings";
    private static final String MEASURE = "measure";
    private static final String QUANTITY = "description";
    private static final String INGREDIENT = "ingredient";
    private static final String IMAGE_URL = "imageUrl";
    private static final String TABLE_NAME = "cakes";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            ID + " integer primary key autoincrement not null," +
            TITLE + " text not null," +
            SERVINGS + " text not null," +
            MEASURE + " text not null," +
            INGREDIENT + " text not null," +
            QUANTITY + " text not null," +
            IMAGE_URL + " text not null)";
}
