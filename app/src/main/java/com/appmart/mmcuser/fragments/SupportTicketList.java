package com.appmart.mmcuser.fragments;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appmart.mmcuser.R;
import com.appmart.mmcuser.Validation;
import com.appmart.mmcuser.activities.Home;
import com.appmart.mmcuser.adapters.SupportTicketListAdapter;
import com.appmart.mmcuser.models.SupportTicketListData;
import com.appmart.mmcuser.sharedPreference.Profile_SharedPref;
import com.appmart.mmcuser.utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.layout.simple_spinner_item;
import static com.appmart.mmcuser.utils.ServerAddress.ADD_NEW_SUPPORT_TICKET;
import static com.appmart.mmcuser.utils.ServerAddress.GET_SUPPORT_TICKET;

public class SupportTicketList extends Fragment {
    private RecyclerView recyclerView;
    private SupportTicketListAdapter adapter;
    private List<SupportTicketListData> data_list = null;
    private Fragment menu_Frag;
    private Dialog addNewTicket;
    private Button btnSubmit, btnCancel;
    private String success;
    private Button btnAddSupportTicket;
    private SearchView searchView;
    private EditText edtMessege;
    private Spinner spnSelectSubject;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_ticket_list, container, false);
        ((Home) getActivity()).setActionBarTitle(getString(R.string.support));

        setHasOptionsMenu(true);
        btnAddSupportTicket = view.findViewById(R.id.btnAddSupportTicket);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        data_list = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new SupportTicketListAdapter(getContext(), data_list, SupportTicketList.this);
        recyclerView.setAdapter(adapter);


        btnAddSupportTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewServicePArtner();
            }
        });

        load_data_from_server();

        return view;
    }

    private void load_data_from_server() {
        data_list.clear();

        ConstantMethods.loaderDialog(getContext());
        final String customer_id = Profile_SharedPref.getInstance(getContext()).getUserId();
        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("customer_id", customer_id)
                        .build();

                Request request = new Request.Builder()
                        .url(GET_SUPPORT_TICKET)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject object = array.getJSONObject(i);


                        SupportTicketListData data = new SupportTicketListData(object.getString("support_id"),
                                object.getString("customer_id"),
                                object.getString("ticket_subject"),
                                object.getString("ticket_msg")
                        );
                        data_list.add(data);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(getContext());
                adapter.notifyDataSetChanged();

            }
        };

        task.execute();

    }

    private void addNewServicePArtner() {

        String subjectSelect[] = {"Select Subject", "Sponsor Approval", "Company Joining", "Sponsor Support", "MMC Training & Support", "Self Development Education", "Other"};
        addNewTicket = new Dialog(getContext());
        addNewTicket.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addNewTicket.setContentView(R.layout.add_new_support_ticket);
        addNewTicket.show();
        btnSubmit = addNewTicket.findViewById(R.id.btnSubmit);
        btnCancel = addNewTicket.findViewById(R.id.btnCancel);

        spnSelectSubject = addNewTicket.findViewById(R.id.spnSelectSubject);

        edtMessege = addNewTicket.findViewById(R.id.edtMessege);


        ArrayAdapter aaa = new ArrayAdapter(getContext(), simple_spinner_item, subjectSelect);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner

        spnSelectSubject.setAdapter(aaa);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (spnSelectSubject.getSelectedItemId() == 0) {
                    ConstantMethods.showAlertMessege(getContext(), "", "Please select Subject");
                    return;
                }
                if (checkValidation()) {
                    addNewTicketToServer();
                    addNewTicket.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTicket.dismiss();
            }
        });
    }

    private void addNewTicketToServer() {
        ConstantMethods.loaderDialog(getContext());
        final String subject = spnSelectSubject.getSelectedItem().toString();
        final String messege = edtMessege.getText().toString();
        final String user_id = Profile_SharedPref.getInstance(getContext()).getUserId();

        AsyncTask<Integer, Void, Void> task = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {

                OkHttpClient client = new OkHttpClient();

                RequestBody requestBody = new FormBody.Builder()
                        .add("user_id", user_id)
                        .add("subject", subject)
                        .add("messege", messege)
                        .build();

                Request request = new Request.Builder()
                        .url(ADD_NEW_SUPPORT_TICKET)
                        .post(requestBody)
                        .build();
                try {
                    Response response = client.newCall(request).execute();

                    JSONObject object = new JSONObject(response.body().string());
                    success = object.getString("success");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ConstantMethods.hideLoaderDialog(getContext());
                if (success.equalsIgnoreCase("true")) {
                    Toast.makeText(getContext(), "Support Ticket placed Successfully..!", Toast.LENGTH_SHORT).show();
                    load_data_from_server();
                } else
                    Toast.makeText(getContext(), "Unable to place ticket", Toast.LENGTH_SHORT).show();
            }
        };

        task.execute();
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edtMessege)) ret = false;
        return ret;
    }

    @Override
    public void onDetach() {

        ((Home) getActivity()).setActionBarTitle("Home");
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openFullChat(Bundle bundle) {
        menu_Frag = new ReplyToComplaints();
        menu_Frag.setArguments(bundle);
        loadFragment();
    }

    private void loadFragment() {
        if (menu_Frag != null) {
            FragmentManager FM = getFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.main_Container, menu_Frag)
                    .addToBackStack(String.valueOf(FM))
                    .commit();
        }
    }
}
