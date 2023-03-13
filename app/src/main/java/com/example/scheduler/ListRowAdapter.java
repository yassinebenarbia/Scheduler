package com.example.scheduler;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scheduler.databinding.FragmentListRowBinding;

import java.util.Arrays;
import java.util.List;

public class ListRowAdapter extends RecyclerView.Adapter<ListRowAdapter.ViewHolder>{
    private List<ListRow> items;
    private Context context;

    private Integer mExpandedPosition = -1;

    // This list is constituted of ListRow organized on the times parameter
    ListRowAdapter(Context context, List<ListRow> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FragmentListRowBinding binding =
                FragmentListRowBinding.inflate(layoutInflater, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Loading data from String file
        bind(holder,position);

        final boolean isExpanded = position==mExpandedPosition;
        holder.binding.detailsSection.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        holder.itemView.setActivated(isExpanded);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;

                // transition (expansion) of a row in the RecyclerView
                Transition transition = new ChangeBounds();
                transition.setDuration(400);
                ViewGroup parent = (ViewGroup) v.getParent();
                TransitionManager.beginDelayedTransition(parent , transition);
                notifyDataSetChanged();
            }
        });
    }
    private void bind(@NonNull ViewHolder holder, int position){
        // loading data
        String separator  = ":";
        int[] minutes =  context.getResources().getIntArray(R.array.minutes);
        int[] hours =  context.getResources().getIntArray(R.array.hours);
        String[] minutesString = Arrays.stream(minutes)
                                         .mapToObj(String::valueOf)
                                         .toArray(String[]::new);
        String[] hoursString = Arrays.stream(hours)
                                       .mapToObj(String::valueOf)
                                       .toArray(String[]::new);
        // bind data
        holder.binding.hoursText.setText(hoursString[position]);
        holder.binding.minutesText.setText(minutesString[position]);
        holder.binding.separatorText.setText(separator);
        holder.binding.labelLabel.setText("Label");
        holder.binding.labelNote.setText("Note");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentListRowBinding binding;
        public ViewHolder(@NonNull FragmentListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ListRow listRow, @Nullable Integer position){}
    }
}
