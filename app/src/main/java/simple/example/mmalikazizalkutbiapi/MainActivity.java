package simple.example.mmalikazizalkutbiapi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnJava,btnC,btnJS,btnPy, btnDart, btnGo;
    TextView txtBahasaPemrograman,txtHelloWorld,txtReadMore, txtDescription;
    ImageView logo;
    FloatingActionButton btnRefresh;
    View lyCurrency;
    ProgressBar loadingIndicator;
    private String bahasaPemrograman = "Java";
    JSONObject dataBahasaPemrograman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        getDataBahasaPemrograman();
    }

    private void inisialisasiView() {
        txtBahasaPemrograman = findViewById(R.id.txtBahasaPemrograman);
        txtBahasaPemrograman = findViewById(R.id.txtBahasaPemrograman);
        lyCurrency = findViewById(R.id.lyCurrency);
        txtHelloWorld = findViewById(R.id.txtHelloWorld);
        txtHelloWorld = findViewById(R.id.txtHelloWorld);
        txtReadMore = findViewById(R.id.txtReadMore);
        txtDescription = findViewById(R.id.txtDescription);
        loadingIndicator = findViewById(R.id.loadingIndicator);
        logo = findViewById(R.id.logo);

        btnJava = findViewById(R.id.btnJava);
        btnJava.setOnClickListener(view -> showDataBahasaPemrograman("Java"));

        btnC = findViewById(R.id.btnC);
        btnC.setOnClickListener(view -> showDataBahasaPemrograman("C"));

        btnJS = findViewById(R.id.btnJS);
        btnJS.setOnClickListener(view -> showDataBahasaPemrograman("Javascript"));

        btnPy = findViewById(R.id.btnPy);
        btnPy.setOnClickListener(view -> showDataBahasaPemrograman("Python"));

        btnDart = findViewById(R.id.btnDart);
        btnDart.setOnClickListener(view -> showDataBahasaPemrograman("Dart"));

        btnGo = findViewById(R.id.btnGo);
        btnGo.setOnClickListener(view -> showDataBahasaPemrograman("Go"));

        btnRefresh = findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(view -> getDataBahasaPemrograman());
    }

    private void getDataBahasaPemrograman() {
        loadingIndicator.setVisibility(View.VISIBLE);
        String baseURL = "https://ewinsutriandi.github.io/mockapi/bahasa_pemrograman.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("MAIN",response.toString());
                        dataBahasaPemrograman = response;
                        showDataBahasaPemrograman(bahasaPemrograman);
                        loadingIndicator.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingIndicator.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"Gagal mengambil data",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    private void showDataBahasaPemrograman(String bahasaPemrograman) {
        this.bahasaPemrograman = bahasaPemrograman;
        // tampilkan nama framework terpilih
        txtBahasaPemrograman.setText(bahasaPemrograman);
        try { // try catch untuk antisipasi error saat parsing JSON
            // tampilkan data framework
            JSONObject data = dataBahasaPemrograman.getJSONObject(bahasaPemrograman);
            txtHelloWorld.setText(data.getString("hello_world"));
            String link = data.getString("read_more");
            txtReadMore.setLinksClickable(true);
            txtReadMore.setMovementMethod(LinkMovementMethod.getInstance());
            txtReadMore.setText(Html.fromHtml(link));
            txtDescription.setText(data.getString("description"));

            String imgUrl = data.getString("logo");
            Glide.with(this)
                    .load(imgUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(logo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}