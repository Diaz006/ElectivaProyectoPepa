package com.my.first.taller_sharedpreference.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.first.taller_sharedpreference.Articles;
import com.my.first.taller_sharedpreference.NewsModal;
import com.my.first.taller_sharedpreference.NewsRVAdapter;
import com.my.first.taller_sharedpreference.R;
import com.my.first.taller_sharedpreference.RetrofitAPI;
import com.my.first.taller_sharedpreference.databinding.FragmentGalleryBinding;
import com.my.first.taller_sharedpreference.ui.home.HomeFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;

    //Variables que se usaran para agregar las noticias
    private RecyclerView newsRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private NewsRVAdapter newsRVAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        //Codigo para agregar las noticias
        newsRV = root.findViewById(R.id.idRVNews1);
        loadingPB = root.findViewById(R.id.idPBLoading1);
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
        String url = "https://newsapi.org/v2/everything?q=noticias falsas covid&sortBy=popularity&language=es&apiKey=8df9385425ca463f9ddaf899a09aa2f2";
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
                Toast.makeText(GalleryFragment.this.getContext(), "Error al presentar las noticias", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}