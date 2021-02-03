package com.example.malistedetches;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.malistedetches.db.TodoEntryDBHelper;
import com.example.malistedetches.db.TodoEntrySchema;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TodoEntryDBHelper todoEntryDBHelper;
    private ArrayAdapter<String[]> arrayAdapter;

    private ArrayList<String[]> loadData(){
        String table = TodoEntrySchema.TodoEntry.TABLE;
        String column = TodoEntrySchema.TodoEntry.COL_TODOENTRY;
        String[] columns = new String[]{"_id", column};
        Cursor cursor = todoEntryDBHelper
                .getReadableDatabase()
                .query(table, columns, null, null, null, null, null );

        ArrayList<String[]> taches = new ArrayList<>();
        while (cursor.moveToNext() ){
            String task = cursor.getString(cursor.getColumnIndex(column));
            String _id = cursor.getString(cursor.getColumnIndex("_id"));
            taches.add( new String[]{_id, task});
            Log.d("loadData", _id);
        }

        return taches;
    }

    private void updateTodoEntriesList(){

        List<String[]> taches = loadData();
        arrayAdapter.clear();
        arrayAdapter.addAll(taches);
        arrayAdapter.notifyDataSetChanged();
    }

    public void finishTask(View btn){

        ViewGroup parent = (ViewGroup)btn.getParent();
        TextView _id_txt = parent.findViewById(R.id.entry_id_txt);
        String _id = _id_txt.getText().toString();

        String table = TodoEntrySchema.TodoEntry.TABLE;
        String[] whereArgs = new String[]{_id};
        todoEntryDBHelper.getWritableDatabase().delete(table, "_id = ?", whereArgs);

        updateTodoEntriesList();

        Log.d( "delete" ,_id_txt.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoEntryDBHelper = new TodoEntryDBHelper(this);

        ListView todoEntries = findViewById(R.id.todoEntries);
        arrayAdapter = new TacheAdapter(this, R.layout.todo_entry, loadData());
        todoEntries.setAdapter(arrayAdapter);
        updateTodoEntriesList();

    }

    @SuppressLint("RestrictedApi")
    @ Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate( R.menu.main_menu , menu ) ;
        ((MenuBuilder)menu).setOptionalIconsVisible(true);
        return super.onCreateOptionsMenu( menu ) ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Ajouter tache")){
//            Log.d(TAG, "L'item Ajouter tache a ete selectionee");
            final EditText todoEntryEditText = new EditText ( this ) ;
            AlertDialog dialog = new AlertDialog . Builder ( this )
                    . setTitle ("Ajouter une tache")
                    . setMessage ("Veuillez saisir la description de la tache.")
                    . setView ( todoEntryEditText )
                    . setPositiveButton ("Ajouter", new
                            DialogInterface. OnClickListener () {
                                @ Override
                                public void onClick ( DialogInterface dialog , int which ) {
                                    // Get user entry
                                    String todoentry = String.valueOf( todoEntryEditText.getText() ) ;
                                    // Get table name and column name
                                    String table = TodoEntrySchema.TodoEntry.TABLE;
                                    String column = TodoEntrySchema.TodoEntry.COL_TODOENTRY;
                                    // Prepare values to insert
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put(column, todoentry);
                                    // Insert value in DB
                                    todoEntryDBHelper
                                            .getWritableDatabase()
                                            .insertWithOnConflict(table,
                                                    null,
                                                    contentValues,
                                                    SQLiteDatabase.CONFLICT_REPLACE);

                                    updateTodoEntriesList();
                                    Log.d(TAG, todoentry);
                                }
                            })
                    . setNegativeButton ("Annuler", null )
                    . create () ;
            dialog . show () ;
        }

        return super.onOptionsItemSelected(item);
    }
}