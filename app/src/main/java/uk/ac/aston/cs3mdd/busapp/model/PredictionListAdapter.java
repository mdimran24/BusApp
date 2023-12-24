package uk.ac.aston.cs3mdd.busapp.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import uk.ac.aston.cs3mdd.busapp.R;
import uk.ac.aston.cs3mdd.busapp.model.Prediction.Prediction;

public class PredictionListAdapter extends
        RecyclerView.Adapter<PredictionListAdapter.PredictionViewHolder> {

    private List<Prediction> mPredictionList;
    private final LayoutInflater mInflater;

    public PredictionListAdapter(Context context, List<Prediction> predictionList) {
        mInflater = LayoutInflater.from(context);
        this.mPredictionList = predictionList;

    }


    @NonNull
    @Override
    public PredictionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.prediction_item, parent, false);

        return new PredictionViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PredictionViewHolder holder, int position) {
        Prediction prediction = mPredictionList.get(position);
        holder.prediction = prediction;
        String arrival = "";
        String displayName = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        if (prediction.getExpectedArrival() == null && prediction.getScheduledArrival() != null) {
            LocalDateTime busTime = LocalDateTime.parse(prediction.getScheduledArrival(), formatter);
            Duration d = Duration.between(LocalDateTime.now(), busTime);
            arrival = "Scheduled Arrival: " + busTime.toLocalTime();
        }else {
            if(prediction.getExpectedArrival() != null) {
                LocalDateTime busTime = LocalDateTime.parse(prediction.getExpectedArrival(), formatter);
                Duration d = Duration.between(LocalDateTime.now(), busTime);
                StringBuilder sb = new StringBuilder();
                sb.append("Expected Arrival: ");
                Log.i("TIME", d.toString());
                sb.append(d.toMinutes()  + " minutes");
                sb.append(" ");

                arrival = sb.toString();
                if(d.toHours() == 0 && d.toMinutes() < 2){
                    arrival = "Expected Arrival: Approaching Stop";
                }
            }
        }
        if(prediction.getId() == null){
             displayName = prediction.getDestinationName();
        }else {
             displayName = "Line: " + prediction.getLineName() + "\n" + "Destination: " + prediction.getDestinationName() + "\n" + arrival;
        }
//        Log.i("MDI", "" + displayName );
        holder.usernameView.setText(displayName);
    }

    @Override
    public int getItemCount() {
        return this.mPredictionList.size();
    }

    public void updateData(List<Prediction> list) {
        this.mPredictionList = list;
        notifyDataSetChanged();
    }

    class PredictionViewHolder extends RecyclerView.ViewHolder {
        public final TextView usernameView;
        final PredictionListAdapter mAdapter;
        public Prediction prediction;

        public PredictionViewHolder(@NonNull View itemView, PredictionListAdapter adapter) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.username);
            this.mAdapter = adapter;
        }
    }
}

