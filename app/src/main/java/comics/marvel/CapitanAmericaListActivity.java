package comics.marvel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import comics.marvel.Utils.ComicsSincro;
import comics.marvel.Utils.ImageComicsSincro;
import comics.marvel.dummy.DummyContent;

import java.util.List;


public class CapitanAmericaListActivity extends AppCompatActivity {

    Context context;
    String TAG = "CapitanListActivity";
    ComicsSincro comicsSincro;


    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capitanamerica_list);
        context = this;

        final View recyclerView = findViewById(R.id.capitanamerica_list);
        assert recyclerView != null;

        Log.w(TAG,"onCreate ");
        if (DummyContent.ITEMS.isEmpty()){
            setupRecyclerView((RecyclerView) recyclerView);
            comicsSincro = new ComicsSincro(context, new ComicsSincro.OnComicsSincroListener() {
                @Override
                public void OnComicsSincroListenerSuccess(String result) {
                    setupRecyclerView((RecyclerView) recyclerView);
                }

                @Override
                public void OnComicsSincroListenerFailed(String result) {

                }
            });
            comicsSincro.execute(0);

        }else{
            setupRecyclerView((RecyclerView) recyclerView);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Data provided by Marvel. © 2017 MARVEL", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.clockwise);
                fab.startAnimation(animation1);
            }
        });




        if (findViewById(R.id.capitanamerica_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        ImageComicsSincro imageComicsSincro;
        private final List<DummyContent.Comics> mValues;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.Comics> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.capitanamerica_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).title);


            getImage(holder, mValues.get(position));


            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(CapitanAmericaDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        CapitanAmericaDetailFragment fragment = new CapitanAmericaDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.capitanamerica_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, CapitanAmericaDetailActivity.class);
                        intent.putExtra(CapitanAmericaDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public final ImageView mImageView;
            public final ProgressBar mProgressBar;
            public DummyContent.Comics mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
                mImageView = (ImageView) view.findViewById(R.id.image_row_id);
                mProgressBar = (ProgressBar) view.findViewById(R.id.progress_id);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }

        public void getImage(ViewHolder viewHolder, DummyContent.Comics comics){

            if (!comics.getThumbnail_path().equals("null")
                    && !comics.getThumbnail_path().isEmpty()){
                imageComicsSincro = new ImageComicsSincro(context, viewHolder.mImageView, viewHolder.mProgressBar,
                    new ImageComicsSincro.OnImageComicsSincroListener() {
                        @Override
                        public void OnImageComicsSincroSuccess() {

                        }

                        @Override
                        public void OnImageComicsSincroFailed() {

                        }
                    });
                imageComicsSincro.execute(comics.getThumbnail_path());
            }

         }

    }


}
