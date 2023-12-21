package uk.ac.aston.cs3mdd.busapp.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import uk.ac.aston.cs3mdd.busapp.MainActivity;
import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.model.StopPoint.StopPoint;
import uk.ac.aston.cs3mdd.busapp.ui.home.HomeFragmentDirections;


public class StopListAdapter extends RecyclerView.Adapter<StopListAdapter.StopViewHolder> {

    private List<StopPoint> mStopList;
    private final LayoutInflater mInflater;

    private final Context context;

    public StopListAdapter(Context context, List<StopPoint> stopList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        if (stopList != null) {
            this.mStopList = stopList;
        }
    }

    @NonNull
    @Override
    public StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.stop_item, parent, false);
        return new StopViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull StopViewHolder holder, int position) {

        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        StopPoint stopPoint = mStopList.get(position);
        String buses = stopPoint.toStringBuses();
        holder.stopPoint = stopPoint;

        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(stopPoint.getLat(), stopPoint.getLon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

            holder.usernameView.setText("Bus Lines: " + "\n" + address);


        } catch (IOException e) {
            Log.e(MainActivity.TAG, "Error getting address\n" + e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return mStopList.size();
    }


    public void updateData(List<StopPoint> list) {
        this.mStopList = list;
        notifyDataSetChanged();
    }

    class StopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView usernameView;
        final StopListAdapter mAdapter;
        public StopPoint stopPoint;

        public StopViewHolder(@NonNull View itemView, StopListAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            usernameView = itemView.findViewById(R.id.username);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            HomeFragmentDirections.ActionNavigationHomeToStopPointFragment action =
                    HomeFragmentDirections.actionNavigationHomeToStopPointFragment(stopPoint);
            Navigation.findNavController(view).navigate(action);
        }
    }
}