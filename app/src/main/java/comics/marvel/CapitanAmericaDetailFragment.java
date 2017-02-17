package comics.marvel;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import comics.marvel.Utils.ImageComicsSincro;
import comics.marvel.dummy.DummyContent;


public class CapitanAmericaDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";
    ImageComicsSincro imageComicsSincro;
    Context context;

    private DummyContent.Comics mItem;


    public CapitanAmericaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            ImageView imageView = (ImageView) activity.findViewById(R.id.image_toolbar);
            ProgressBar progressBar = (ProgressBar) activity.findViewById(R.id.progress_toolbar_id);
            if (imageView != null
                    && progressBar != null) {
                getImage(activity.getBaseContext(), imageView, progressBar , mItem);
            }

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.title);
            }


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.capitanamerica_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.capitanamerica_detail_progress);
            progressBar.setVisibility(View.GONE);
            final ImageView imageView = (ImageView) rootView.findViewById(R.id.capitanamerica_detail_image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation1 = AnimationUtils.loadAnimation(rootView.getContext(),
                            R.anim.zoom);
                    imageView.startAnimation(animation1);
                }
            });
            getImage(rootView.getContext(), imageView, progressBar, mItem);
            ((TextView) rootView.findViewById(R.id.capitanamerica_detail)).setText(mItem.description);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void getImage(Context context, ImageView imageView, ProgressBar progressBar, DummyContent.Comics comics){

        if (!comics.getImages_for_comic().isEmpty()){
            Random rnd = new Random();
            int rndIndex = rnd.nextInt(comics.getImages_for_comic().size());
            imageComicsSincro = new ImageComicsSincro(context, imageView, progressBar, new ImageComicsSincro.OnImageComicsSincroListener() {
                @Override
                public void OnImageComicsSincroSuccess() {

                }

                @Override
                public void OnImageComicsSincroFailed() {

                }
            });
            imageComicsSincro.execute(comics.getImages_for_comic().get(rndIndex));
        }
    }



}
