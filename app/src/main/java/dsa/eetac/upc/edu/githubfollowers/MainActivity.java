package dsa.eetac.upc.edu.githubfollowers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button exploreUser;
    private EditText nomUser;
    private String nom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nomUser = (EditText) findViewById(R.id.etUser);
        exploreUser = (Button) findViewById(R.id.btExplore);

        nom = nomUser.toString();

        exploreUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Resultat.class );
                intent.putExtra("user", nom);
                startActivity(intent);
            }
        });

    }
}
