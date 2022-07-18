package com.example.schoolapp.views.siswa.history.extend;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.schoolapp.R;
import com.example.schoolapp.adapter.siswa.AdapterListTerkirim;
import com.example.schoolapp.helper.Server;
import com.example.schoolapp.helper.SessionManager;
import com.example.schoolapp.models.siswa.TerkirimModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SiswaHistoryTerkirimFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SiswaHistoryTerkirimFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AdapterListTerkirim adapterListTerkirim;
    public static ArrayList<TerkirimModels> terkirimModelsArrayList = new ArrayList<>();
    TerkirimModels terkirimModels;
    SessionManager sessionManager;
    SharedPreferences sharedPreferences;
    String getNis;
    ProgressBar progressBar;
    private String getTerkirim = Server.URL_API + "koreksi/get_jawaban_terkirim.php";

    public SiswaHistoryTerkirimFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SiswaHistoryTerkirimFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SiswaHistoryTerkirimFragment newInstance(String param1, String param2) {
        SiswaHistoryTerkirimFragment fragment = new SiswaHistoryTerkirimFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.siswa_history_terkirim_fragment, container, false);
        sessionManager = new SessionManager(getActivity());
//        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getNis = user.get(SessionManager.NIS);
        ListView listView = view.findViewById(R.id.list_terkirim);
        progressBar = view.findViewById(R.id.progress_circular);
        adapterListTerkirim = new AdapterListTerkirim(this.getActivity(), terkirimModelsArrayList);
        listView.setAdapter(adapterListTerkirim);
        CallTerkirim();

        return view;
    }


    public void CallTerkirim(){
        final String txtnis = getNis;
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setMessage("Tunggu Sebentar . . .");
//        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.POST, getTerkirim,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        terkirimModelsArrayList.clear();
                        progressBar.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (sucess.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id_tugas = object.getString("id_tugas");
                                    String mapel = object.getString("mapel");
                                    String modul = object.getString("modul");
                                    String jenis = object.getString("jenis");
                                    String id_siswa = object.getString("id_siswa");
                                    String nama = object.getString("nama");
                                    String benar = object.getString("benar");
                                    String salah = object.getString("salah");
                                    String tanggal = object.getString("tanggal");
                                    String jam = object.getString("jam");
                                    String nilai = object.getString("nilai");
                                    String dikoreksi = object.getString("dikoreksi");

                                    if (jsonArray.length() < 1) {
//                                        progressDialog.dismiss();
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "Belum Ada Data!", Toast.LENGTH_SHORT).show();
                                    } else {
//                                        progressDialog.dismiss();
                                        progressBar.setVisibility(View.GONE);
                                        terkirimModels = new TerkirimModels(id_tugas, mapel, modul, jenis, id_siswa, nama, benar, salah, tanggal, jam, nilai, dikoreksi);
                                        terkirimModelsArrayList.add(terkirimModels);
                                    }
                                }
                            }
                        }
                        catch (JSONException e){
//                            progressDialog.dismiss();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Server Sedang Bermasalah!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        progressDialog.dismiss();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Periksa Internet Kamu!", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_siswa", txtnis);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}