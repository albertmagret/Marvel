package comics.marvel.dummy;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {


    public static final List<Comics> ITEMS = new ArrayList<Comics>();
    public static List<Comics> comicsList;
    public static final Map<String, Comics> ITEM_MAP = new HashMap<String, Comics>();

    private static final int COUNT = 25;


    private static void addItem(Comics item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }


    public void createCommics (String id, String title, String description, String resourceURI, String thumbnail_path, List<String> images_for_comic){
        DummyContent.Comics comics = new DummyContent.Comics(String.valueOf(id), title, description, resourceURI, thumbnail_path, images_for_comic);
        addItem(comics);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

        public class Comics {


        public String id, title, description, resourceURI, thumbnail_path;
        public Bitmap thumbnail, image;
        public List<String> images_for_comic;

        public Comics(){}

        public Comics(String id, String title, String description, String resourceURI, String thumbnail_path, List<String> images_for_comic) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.resourceURI = resourceURI;
            this.thumbnail_path = thumbnail_path;
            this.images_for_comic = images_for_comic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Bitmap getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Bitmap thumbnail) {
            this.thumbnail = thumbnail;
        }

        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public List<String> getImages_for_comic() {
            return images_for_comic;
        }

        public void setImages_for_comic(List<String> images_for_comic) {
            this.images_for_comic = images_for_comic;
        }

        public String getResourceURI() {
            return resourceURI;
        }

        public void setResourceURI(String resourceURI) {
            this.resourceURI = resourceURI;
        }

        public String getThumbnail_path() {
            return thumbnail_path;
        }

        public void setThumbnail_path(String thumbnail_path) {
            this.thumbnail_path = thumbnail_path;
        }
    }

}
