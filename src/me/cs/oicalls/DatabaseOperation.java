package me.cs.oicalls;


import me.cs.oicalls.TableData.TableInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOperation extends SQLiteOpenHelper {
	public static final int database_versions = 1;
	public String CREATE_TABLE = "CREATE TABLE "+TableInfo.TABLE_NAME+"("+TableInfo.USER_NAME +"  TEXT ,"+TableInfo.USER_PASS+"  TEXT);";
	public DatabaseOperation(Context context) {
		super(context, TableInfo.DATABASE_NAME, null, database_versions);		
		Log.d("database operation","   Database created");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);	
		Log.d("database operation","   Table created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void putInformation(DatabaseOperation dop, String name, String pass){
		SQLiteDatabase SQ = dop.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TableInfo.USER_NAME, name);
		cv.put(TableInfo.USER_PASS, pass);
		long k = SQ.insert(TableInfo.TABLE_NAME, null, cv);
		Log.d("database operation", "   one row inserted");
	
	}
	
	public Cursor getInformation(DatabaseOperation dop){
		SQLiteDatabase SQ = dop.getReadableDatabase();
		String[] columns = {TableInfo.USER_NAME,TableInfo.USER_PASS};
		Cursor CR = SQ.query(TableInfo.TABLE_NAME, columns, null, null, null, null, null);
		return CR;
	}

}
