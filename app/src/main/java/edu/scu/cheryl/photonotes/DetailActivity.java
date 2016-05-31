package edu.scu.cheryl.photonotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        try {
        TextView caption=(TextView)findViewById(R.id.captionDetail);
        ImageView image=(ImageView) findViewById(R.id.imageView2);
        String content= getIntent().getStringExtra("caption");
        String path= getIntent().getStringExtra("filename");
        caption.setText(content);


            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1; // Experiment with different sizes

            Bitmap bitmap= BitmapFactory.decodeFile(path, options);
            image.setImageBitmap(bitmap);

        } catch (Exception e) {
            toast( "Something is wrong, please check");
        }



    }
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(DetailActivity.this, AddPhoto.class);
                startActivity(intent);

                break;
            case R.id.action_delete:
                Intent intent3= new Intent(Intent.ACTION_DELETE);
                intent3.setData(Uri.parse("package:edu.scu.cheryl.photonotes"));
                startActivity(intent3);
                break;
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                toast("unknown action ...");
        }

        return true;
    }
}
