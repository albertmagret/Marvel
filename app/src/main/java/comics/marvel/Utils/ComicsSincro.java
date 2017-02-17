package comics.marvel.Utils;

/**
 * Created by albert on 09/02/17.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import comics.marvel.dummy.DummyContent;


public class ComicsSincro extends AsyncTask <Integer, Void, String>{

    Context context;
    public static final int CONNECTON_TIMEOUT_MILLISECONDS = 60000;
    public OnComicsSincroListener onComicsSincroListener;

    String TAG = "ComicsSincro";

    public ComicsSincro(Context context, OnComicsSincroListener onComicsSincroListener){
        this.context = context;
        this.onComicsSincroListener = onComicsSincroListener;
    }


    @Override
    protected String doInBackground(Integer... params) {
        String messageResult = null;
        String finalUrl = null;

        try {
            String ts = CommunicationsUtils.ts();
            String api_key = CommunicationsUtils.md5(ts+""+CommunicationsUtils.PRIVATE_KEY+""+CommunicationsUtils.PUBLIC_KEY);
            finalUrl = CommunicationsUtils.URL_COMIC_LIST+"ts="+ts+"&apikey="+CommunicationsUtils.PUBLIC_KEY+"&hash="+ URLEncoder.encode(api_key, "utf-8");

            Log.w(TAG,"getCapitanComics finalUrl: "+finalUrl);
            CommunicationsUtils comm = new CommunicationsUtils();
            String response = comm.getCapitanComics(finalUrl);

            if (response!= null && !response.isEmpty())
                messageResult = fillComicList(response);
            else messageResult = null;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return messageResult;
    }

    @Override
    protected void onPostExecute(String message) {
        super.onPostExecute(message);

        if (message == null){
            onComicsSincroListener.OnComicsSincroListenerFailed(message);
        }else{
            onComicsSincroListener.OnComicsSincroListenerSuccess(message);
        }


    }


    public String fillComicList(String JSONstring){

        String result = null;
        int code = 0;
        String status = null;
        int total = 0;

        try{
            JSONObject jsonObject = new JSONObject(JSONstring);
            if (!jsonObject.isNull("code")){
                code = jsonObject.getInt("code");
            }
            if (jsonObject.has("status")){
                status = jsonObject.getString("status");
            }

            if (code == 200 && status != null && status.equals("Ok")){
                if (!jsonObject.isNull("total"))
                    total = jsonObject.getInt("total");

                JSONObject jsonObject_data = jsonObject.getJSONObject("data");
                JSONArray jsonArray = jsonObject_data.getJSONArray("results");
                for (int pos = 0; pos < jsonArray.length(); pos++) {

                    int id = 0;
                    String title = "", desc = "", resourceURI = "", thumbnail_path = "", image_path = "";
                    List<String> image_list = new ArrayList<>();

                    JSONObject jsonObject_comics = jsonArray.getJSONObject(pos);
                    if (!jsonObject_comics.isNull("id"))
                        id = jsonObject_comics.getInt("id");

                    if (jsonObject_comics.has("title"))
                        title = jsonObject_comics.getString("title");

                    if (!jsonObject_comics.isNull("description"))
                        desc = jsonObject_comics.getString("description");

                    if (jsonObject_comics.has("resourceURI"))
                        resourceURI = jsonObject_comics.getString("resourceURI");

                    if (jsonObject_comics.has("thumbnail")){
                        JSONObject json_thumbnail =  jsonObject_comics.getJSONObject("thumbnail");

                        if (json_thumbnail.has("path") && json_thumbnail.has("extension"))
                            thumbnail_path = json_thumbnail.getString("path")+"."+json_thumbnail.getString("extension");

                    }

                    if (jsonObject_comics.has("images")){
                        JSONArray jsonArray_images = jsonObject_comics.getJSONArray("images");
                        for (int k = 0; k<jsonArray_images.length(); k++){
                            JSONObject json_image = jsonArray_images.getJSONObject(k);
                            if (json_image.has("path") && json_image.has("extension")){
                                image_path = json_image.getString("path")+"."+json_image.getString("extension");
                                image_list.add(k, image_path);
                            }
                        }
                    }

                    DummyContent dummyContent = new DummyContent();
                    dummyContent.createCommics(String.valueOf(id), title, desc, resourceURI, thumbnail_path, image_list);

                }

                result = "OK";

            }else{
                result = null;
            }


        }catch (JSONException e){
            e.printStackTrace();
            result = null;
        }

        Log.w(TAG, "fillComicList result: "+result);
        return result;
    }


    public interface OnComicsSincroListener{
        public void OnComicsSincroListenerSuccess(String result);
        public void OnComicsSincroListenerFailed(String result);
    }


}
/*
    {
  "code": 200,
  "status": "Ok",
  "copyright": "© 2017 MARVEL",
  "attributionText": "Data provided by Marvel. © 2017 MARVEL",
  "attributionHTML": "<a href=\"http://marvel.com\">Data provided by Marvel. © 2017 MARVEL</a>",
  "etag": "97dea642236bd6494c60a24e427422c19de8939f",
  "data": {
    "offset": 0,
    "limit": 20,
    "total": 1471,
    "count": 20,
    "results": [
      {
        "id": 43508,
        "digitalId": 30238,
        "title": "A+X (2012) #9",
        "issueNumber": 9,
        "variantDescription": "",
        "description": "Captain America and Wolverine fight a villain unlike any you've ever seen!\nDeadpool and Hawkeye do not see eye-to-eye!\n",
        "modified": "2014-01-15T15:14:20-0500",
        "isbn": "",
        "upc": "75960607899800911",
        "diamondCode": "APR130666",
        "ean": "",
        "issn": "",
        "format": "Comic",
        "pageCount": 32,
        "textObjects": [
          {
            "type": "issue_solicit_text",
            "language": "en-us",
            "text": "Captain America and Wolverine fight a villain unlike any you've ever seen!\nDeadpool and Hawkeye do not see eye-to-eye!\n"
          }
        ],
        "resourceURI": "http://gateway.marvel.com/v1/public/comics/43508",
        "urls": [
          {
            "type": "detail",
            "url": "http://marvel.com/comics/issue/43508/ax_2012_9?utm_campaign=apiRef&utm_source=6a7ed890b4b941a925202a5630d5b162"
          },
          {
            "type": "purchase",
            "url": "http://comicstore.marvel.com/A-X-9/digital-comic/30238?utm_campaign=apiRef&utm_source=6a7ed890b4b941a925202a5630d5b162"
          },
          {
            "type": "reader",
            "url": "http://marvel.com/digitalcomics/view.htm?iid=30238&utm_campaign=apiRef&utm_source=6a7ed890b4b941a925202a5630d5b162"
          },
          {
            "type": "inAppLink",
            "url": "https://applink.marvel.com/issue/30238?utm_campaign=apiRef&utm_source=6a7ed890b4b941a925202a5630d5b162"
          }
        ],
        "series": {
          "resourceURI": "http://gateway.marvel.com/v1/public/series/16450",
          "name": "A+X (2012 - Present)"
        },
        "variants": [],
        "collections": [],
        "collectedIssues": [],
        "dates": [
          {
            "type": "onsaleDate",
            "date": "2013-06-19T00:00:00-0400"
          },
          {
            "type": "focDate",
            "date": "2013-06-05T00:00:00-0400"
          },
          {
            "type": "unlimitedDate",
            "date": "2013-12-16T00:00:00-0500"
          },
          {
            "type": "digitalPurchaseDate",
            "date": "2013-06-19T00:00:00-0400"
          }
        ],
        "prices": [
          {
            "type": "printPrice",
            "price": 3.99
          },
          {
            "type": "digitalPurchasePrice",
            "price": 1.99
          }
        ],
        "thumbnail": {
          "path": "http://i.annihil.us/u/prod/marvel/i/mg/9/40/584f15cfa53d0",
          "extension": "jpg"
        },
        "images": [
          {
            "path": "http://i.annihil.us/u/prod/marvel/i/mg/9/40/584f15cfa53d0",
            "extension": "jpg"
          }
        ],
        "creators": {
          "available": 13,
          "collectionURI": "http://gateway.marvel.com/v1/public/comics/43508/creators",
          "items": [
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/4014",
              "name": "Axel Alonso",
              "role": "editor"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/4300",
              "name": "Nick Lowe",
              "role": "editor"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/8822",
              "name": "Jordan White",
              "role": "editor"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/10172",
              "name": "Clayton Cowles",
              "role": "letterer"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/430",
              "name": "Edgar Delgado",
              "role": "colorist"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/593",
              "name": "Lee Loughridge",
              "role": "colorist"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/11680",
              "name": "Gerry Duggan",
              "role": "writer"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/11799",
              "name": "Nathan Edmondson",
              "role": "writer"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/1227",
              "name": "David Lapham",
              "role": "writer"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/11757",
              "name": "Salvador Larroca",
              "role": "penciller (cover)"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/447",
              "name": "Victor Olazaba",
              "role": "inker"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/72",
              "name": "Humberto Ramos",
              "role": "penciller"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/creators/840",
              "name": "Adam Warren",
              "role": "artist"
            }
          ],
          "returned": 13
        },
        "characters": {
          "available": 5,
          "collectionURI": "http://gateway.marvel.com/v1/public/comics/43508/characters",
          "items": [
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/characters/1009220",
              "name": "Captain America"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/characters/1009282",
              "name": "Doctor Strange"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/characters/1011179",
              "name": "Pixie"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/characters/1009387",
              "name": "Quentin Quire"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/characters/1009718",
              "name": "Wolverine"
            }
          ],
          "returned": 5
        },
        "stories": {
          "available": 2,
          "collectionURI": "http://gateway.marvel.com/v1/public/comics/43508/stories",
          "items": [
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/stories/97202",
              "name": "A+X (2012) #9",
              "type": "cover"
            },
            {
              "resourceURI": "http://gateway.marvel.com/v1/public/stories/97203",
              "name": "story from All-New X-Men (2012) #12",
              "type": "interiorStory"
            }
          ],
          "returned": 2
        },
        "events": {
          "available": 0,
          "collectionURI": "http://gateway.marvel.com/v1/public/comics/43508/events",
          "items": [],
          "returned": 0
        }
      },..{}
  ]
}
*/