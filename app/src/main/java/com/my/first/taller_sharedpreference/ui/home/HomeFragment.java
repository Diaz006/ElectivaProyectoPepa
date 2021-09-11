package com.my.first.taller_sharedpreference.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.first.taller_sharedpreference.ActivityPrincipal;
import com.my.first.taller_sharedpreference.Articles;
import com.my.first.taller_sharedpreference.NewsModal;
import com.my.first.taller_sharedpreference.NewsRVAdapter;
import com.my.first.taller_sharedpreference.R;
import com.my.first.taller_sharedpreference.RetrofitAPI;
import com.my.first.taller_sharedpreference.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    //Variables que se usaran para agregar las noticias
    private RecyclerView newsRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private NewsRVAdapter newsRVAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        final String Urlv= "https://lugarvacunacion.cne.gob.ec";
        final String UrlC= "https://sgrdacaa-admision.msp.gob.ec/hcue/paciente/certificadovacuna/public/index";

        final Button btnLugarV = root.findViewById(R.id.btnLugarVacunacion);
        final Button btnCertificadoV = root.findViewById(R.id.btnCertificadoVacunacion);

        btnLugarV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link = Uri.parse(Urlv);
                Intent i = new Intent(Intent.ACTION_VIEW, _link);
                startActivity(i);

            }
        });

        btnCertificadoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link = Uri.parse(UrlC);
                Intent i = new Intent(Intent.ACTION_VIEW, _link);
                startActivity(i);

            }
        });

        final ActivityPrincipal ma = (ActivityPrincipal) getActivity();

        SharedPreferences sp = ma.getSharedPreferences("SP", ma.MODE_PRIVATE);
        ma.setDayNight();
        SharedPreferences.Editor editor = sp.edit();

        final Switch swi = root.findViewById(R.id.switema);

        int theme = sp.getInt("Theme", 1);
        if (theme==1){
            swi.setChecked(false);
        }else{
            swi.setChecked(true);
        }
        swi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swi.isChecked()){
                    //((MainActivity)getActivity()).setDayNight(0);
                    editor.putInt("Theme",0);
                }else{
                    //((MainActivity)getActivity()).setDayNight(1);
                    editor.putInt("Theme", 1);
                }
                editor.commit();
                ma.setDayNight();
            }
        });

        //Codigo para agregar las noticias
        newsRV = root.findViewById(R.id.idRVNews);
        loadingPB = root.findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this.getContext());
        newsRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
        newsRV.setAdapter(newsRVAdapter);
        getNews();
        newsRVAdapter.notifyDataSetChanged();
        //Fin de codigo para agregar las noticias

        return root;
    }
    //Wilson
    private void getNews()
    {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String url = "https://newsapi.org/v2/top-headlines?country=mx&category=science&language=es&q=covid&apikey=8df9385425ca463f9ddaf899a09aa2f2";
        String BASEurl = "https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        call = retrofitAPI.getAllNews(url);

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),
                            articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(HomeFragment.this.getContext(), "Error al presentar las noticias", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}