package uk.ac.aston.cs3mdd.busapp.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import uk.ac.aston.cs3mdd.busapp.MainActivity;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.model.SearchResponse.SearchMatch;
import uk.ac.aston.cs3mdd.busapp.ui.search.SearchFragmentDirections;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> {
    private List<SearchMatch> mSearchList;
    private final LayoutInflater mInflater;
    private Context context;

    public SearchListAdapter(Context context, List<SearchMatch> searchList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mSearchList = searchList;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.searched_item, parent, false);
        return new SearchViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        SearchMatch searchMatch = mSearchList.get(position);

        holder.searchMatch = searchMatch;

        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(searchMatch.getLat(), searchMatch.getLon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            holder.usernameView.setText("Common Name: " + searchMatch.getName() + "\n" + address);


        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error getting address\n" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return this.mSearchList.size();
    }

    public void updateData(List<SearchMatch> list) {
        this.mSearchList = list;
        notifyDataSetChanged();
    }




    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView usernameView;
        final SearchListAdapter mAdapter;
        public SearchMatch searchMatch;

        public SearchViewHolder(@NonNull View itemView, SearchListAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            usernameView = itemView.findViewById(R.id.username);
            this.mAdapter = adapter;
        }
        @Override
        public void onClick(View view) {
            Log.i("MDI", "You clicked " + searchMatch.toString());
            SearchFragmentDirections.ActionNavigationSearchToStopPointFragment action =
                    SearchFragmentDirections.actionNavigationSearchToStopPointFragment(null, searchMatch);
            Navigation.findNavController(view)
                    .navigate(action);
        }
    }
}