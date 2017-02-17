package comics.marvel.Utils;

/**
 * Created by albert on 11/02/17.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


public class ImageComicsSincro extends AsyncTask<String, Void, Bitmap> {

    Context context;
    public static final int CONNECTON_TIMEOUT_MILLISECONDS = 60000;
    public OnImageComicsSincroListener onImageComicsSincroListener;
    ImageView imageView;
    ProgressBar progressBar;

    public ImageComicsSincro(Context context, ImageView imageView, ProgressBar progressBar,
                             OnImageComicsSincroListener onImageComicsSincroListener){
        this.context = context;
        this.onImageComicsSincroListener = onImageComicsSincroListener;
        this.imageView = imageView;
        this.progressBar = progressBar;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView.setImageBitmap(null);
        imageView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        CommunicationsUtils comunicationsUtils = new CommunicationsUtils();

        // params[]
        String path_image = params[0];
        if (!path_image.equals("")){
            // image bitmap
            bitmap = comunicationsUtils.getImageComics(path_image);  //getMovies(page);
        }


        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap == null){
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(null);
            onImageComicsSincroListener.OnImageComicsSincroFailed();
        }else{
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);
            onImageComicsSincroListener.OnImageComicsSincroSuccess();
        }

    }


    public interface OnImageComicsSincroListener{
        public void OnImageComicsSincroSuccess();
        public void OnImageComicsSincroFailed();
    }


}