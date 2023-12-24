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
import uk.ac.aston.cs3mdd.busapp.ui.saved.SavedStopsFragmentDirections;


public class SavedStopListAdapter extends RecyclerView.Adapter<SavedStopListAdapter.SavedStopViewHolder> {

    private List<StopPoint> mStopList;
    private final LayoutInflater mInflater;

    private final Context context;

    public SavedStopListAdapter(Context context, List<StopPoint> stopList) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        if (stopList != null) {
            this.mStopList = stopList;
        }
    }

    @NonNull
    @Override
    public SavedStopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.stop_item, parent, false);
        return new SavedStopViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedStopViewHolder holder, int position) {

        Geocoder geocoder;
        geocoder = new Geocoder(context, Locale.getDefault());
        StopPoint stopPoint = mStopList.get(position);
        holder.stopPoint = stopPoint;

        List<Address> addresses;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Common Name: " + stopPoint.getCommonName() + "\n");
            addresses = geocoder.getFromLocation(stopPoint.getLat(), stopPoint.getLon(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            if(stopPoint.getLines() != null) {
                String buses = stopPoint.toStringBuses();
                sb.append("Bus Lines: " + buses + "\n" + address);

            }else{
                sb.append(address);
            }
            holder.usernameView.setText(sb);


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

    class SavedStopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView usernameView;
        final SavedStopListAdapter mAdapter;
        public StopPoint stopPoint;

        public SavedStopViewHolder(@NonNull View itemView, SavedStopListAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            usernameView = itemView.findViewById(R.id.username);
            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View view) {
            SavedStopsFragmentDirections.ActionNavigationNotificationsToStopPointFragment action =
                    SavedStopsFragmentDirections.actionNavigationNotificationsToStopPointFragment(stopPoint, null);
            Navigation.findNavController(view).navigate(action);
        }
    }
}