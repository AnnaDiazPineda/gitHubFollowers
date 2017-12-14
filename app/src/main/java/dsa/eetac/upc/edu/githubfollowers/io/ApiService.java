package dsa.eetac.upc.edu.githubfollowers.io;


import java.util.List;

import dsa.eetac.upc.edu.githubfollowers.Follower;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by anita on 03/12/2017.
 */

public interface ApiService {

    @GET("/users/{name}/followers")
    Call<List<Follower>> getList(@Path("name") String name);

    @GET("/users/{name}")
    Call<Follower> getFollower(@Path("name") String name);

    @GET("/users/{name}/following")
    Call<List<Follower>> getListrepos(@Path("name") String name);





}
