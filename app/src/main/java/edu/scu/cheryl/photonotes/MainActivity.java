package edu.scu.cheryl.photonotes;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    ArrayList<Notes> noteList;
    NoteRepo repo;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new NoteRepo(this);


        lv=(ListView) findViewById(R.id.listView);
        showList();
        setListViewHeightBasedOnChildren(lv);
        lv.setOnItemClickListener(this);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Photo Notes");
        actionBar.setIcon(R.drawable.ic_action_title);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);


    }
    @Override
    public void onResume() {
        super.onResume();
        showList();
        setListViewHeightBasedOnChildren(lv);

        noteAdapter.notifyDataSetChanged();


    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void showList() {

        noteList = new ArrayList<Notes>();
        noteList.clear();
        String query = "SELECT * FROM Photo_Notes";


        Cursor c1 = NoteRepo.selectQuery(query);
        if (c1 != null && c1.getCount() != 0) {
            if (c1.moveToFirst()) {
                do {
                    Notes noteItems = new Notes(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))), c1.getString(c1
                            .getColumnIndex("caption")), c1.getString(c1
                            .getColumnIndex("filename")) );


                    noteList.add(noteItems);

                } while (c1.moveToNext());
            }
            c1.close();
        }else{
            toast("database is empty");
        }


        noteAdapter = new NoteAdapter(this, R.layout.my_list, noteList);
        lv.setAdapter(noteAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, AddPhoto.class);
                startActivity(intent);

                break;
            case R.id.action_delete:
                Intent intent3= new Intent(Intent.ACTION_DELETE);
                intent3.setData(Uri.parse("package:edu.scu.cheryl.photonotes"));
                startActivity(intent3);
                break;
            case R.id.home:
                finish();
                return true;
            default:
                toast("unknown action ...");
        }

        return true;
    }
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Notes note =noteList.get(position);
        Intent intent= new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("caption", note.getCaption());
        intent.putExtra("filename", note.getFilename());
        startActivity(intent);


    }
}
