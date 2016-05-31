package edu.scu.cheryl.photonotes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;

public class AddPhoto extends AppCompatActivity implements View.OnClickListener {
    final String albumName = "Photo Notes";
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        Button take= (Button)findViewById(R.id.TakePhoto);
        Button save=(Button) findViewById(R.id.Save);
        take.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.TakePhoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null) {
                    toast("Cannot take pictures on this device!");
                    return;
                }

                imageFile = createImageFile();

                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));

                startActivityForResult(intent, 1234);
                break;
            case R.id.Save:
                NoteRepo repo= new NoteRepo(this);
                EditText caption= (EditText)findViewById(R.id.captionNote);
                String content=caption.getText().toString();
                if (imageFile!=null) {
                    String path = imageFile.getAbsolutePath().toString();

                    String query = "INSERT INTO Photo_Notes(caption,filename) values ('"
                            + content + "','" + path + "')";
                    repo.executeQuery(query);

                }else{
                    toast("please take a photo before saving");
                }
                finish();


                break;
            default:
                toast("unknown button clicked");



        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != 1234) return;

        if (resultCode != Activity.RESULT_OK) {
            imageFile.delete();
            return;
        }

        try {
            InputStream is = new FileInputStream(imageFile);

            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageDrawable(Drawable.createFromStream(is, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private File createImageFile() {
        File image = null;
        try {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getAlbumStorageDir();
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (Exception e) {
            Log.e("Cheryl", "something went wrong in creating image file");
        }
        return image;
    }

    public File getAlbumStorageDir() {
        // Same as Environment.getExternalStorageDirectory() + "/Pictures/" + albumName
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (file.exists()) {
            Log.d("Cheryl", "Album directory exists");
        } else if (file.mkdirs()) {
            Log.i("Cheryl", "Album directory is created");
        } else {
            Log.e("Cheryl", "Failed to create album directory.  Check permissions and storage.");
        }
        return file;
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
                Intent intent = new Intent(AddPhoto.this, AddPhoto.class);
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
    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
