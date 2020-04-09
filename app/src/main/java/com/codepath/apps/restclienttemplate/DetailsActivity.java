package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailsActivity extends AppCompatActivity {

    String TAG = "DetailsActivity";

    TextView tvName;
    TextView tvTextBody;
    ImageView ivProfile;
    Tweet tweet;
    TwitterClient twitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        int radius = 60, margin = 10;

        tvName = findViewById(R.id.tvName);
        tvTextBody = findViewById(R.id.tvTextBody);
        ivProfile = findViewById(R.id.ivProfile);

        tvName.setText(tweet.user.screenName);
        tvTextBody.setText(tweet.body);
        Glide.with(this).load(tweet.user.profileImageUrl)
                .apply(new RequestOptions().transform(new RoundedCornersTransformation(radius, margin))).into(ivProfile);

    }

    private void populateHomeTimeline() {
        twitterClient.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG, "onSuscess!" + json.toString());

                try {
                    JSONArray jsonArray = json.jsonArray;
                    if(jsonArray.length() == 0){
                        return;
                    }
                    Tweet.fromJsonArray(jsonArray);
                } catch (JSONException e) {
                    Log.e(TAG, "Json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.i(TAG, "onFailure!", throwable);
            }
        });
    }
}
