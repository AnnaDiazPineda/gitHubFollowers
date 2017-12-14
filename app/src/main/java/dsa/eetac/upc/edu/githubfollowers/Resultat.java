package dsa.eetac.upc.edu.githubfollowers;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import dsa.eetac.upc.edu.githubfollowers.io.ApiAdapter;
import dsa.eetac.upc.edu.githubfollowers.io.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Resultat extends AppCompatActivity {

    //UI references
    private List<Follower> listFollowers;
    private List<String> listNames;
    private ListView listView;
    private ImageView imgUser;
    private ProgressBar progressBar;
    final String tag= "MAPAC";
    private String nomConsultat;
    //Retrofit
    private ApiService mRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        //servei Rest Singleton
        mRest = ApiAdapter.getApiService();

        //rebre el nom del main activity
        nomConsultat = getIntent().getStringExtra("user");

        //**********LLISTA SEGUIDORS**********//
        Call<List<Follower>> callFollowers = mRest.getList(nomConsultat);
        callFollowers.enqueue(new Callback<List<Follower>>() {
            @Override
            public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                switch (response.code()) {
                    case 200:// tot correcte
                        Log.d("callUser","resposta correcta");
                        listFollowers = (List<Follower>) response.body();
                        listView = (ListView) findViewById(R.id.listView);
                        listNames = new ArrayList<>();
                        for (int j=0; j < listFollowers.size(); j++){
                            String item = listFollowers.get(j).getLogin();
                            listNames.add (item);
                        }
                        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>
                                (Resultat.this, android.R.layout.simple_list_item_1, listNames);
                        listView.setAdapter(arrayAdapter);
                        break;
                    case 204://no content
                        Log.d("callUser","buida ");
                        Toast.makeText(Resultat.this, "No funciona: "+response.code(), Toast.LENGTH_SHORT).show();
                        Resultat.this.finish();
                        break;

                }
                progressBar.setVisibility(ListView.GONE);
            }

            @Override
            public void onFailure(Call<List<Follower>> call, Throwable t) {
                Toast.makeText(Resultat.this, "No funciona", Toast.LENGTH_SHORT).show();
                Resultat.this.finish();

            }
        });

        //**********NOM I IMATGE DE USUARI**********//
        Call<Follower> callUser = mRest.getFollower(nomConsultat);
        callUser.enqueue(new Callback<Follower>() {
            @Override
            public void onResponse(Call<Follower> call, Response<Follower> response) {
                switch (response.code()) {
                    case 200:// tot correcte
                        Follower follower = response.body();
                        //dades
                        String nameFollower = follower.getName();
                        int numRepos = follower.getPublic_repos();
                        int numFollows = follower.getFollowing();
                        String urlAvatar =  follower.getAvatar_url();

                        //associant amb la part gràfica
                        TextView nomConsultat = (TextView) findViewById(R.id.consulta);
                        nomConsultat.setText(nameFollower);
                        Log.d(tag, " !!!!!iconName: "+nameFollower);

                        TextView a = (TextView) findViewById(R.id.numRepos);
                        a.setText(numRepos);

                        TextView b = (TextView) findViewById(R.id.numFollowing);
                        b.setText(numFollows);

                        ImageView imageView = (ImageView) findViewById(R.id.imageView);
                        Log.d(tag, "imatge");
                        Picasso.with(Resultat.this).load(urlAvatar).into(imageView);
                        break;
                    case 204://no content

                        break;

                }

            }

            @Override
            public void onFailure(Call<Follower> call, Throwable t) {

            }
        });
    }
}
