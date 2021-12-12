package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.asra.R;
import com.example.asra.Services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ServicesRecyclerAdapter extends RecyclerView.Adapter<ServicesRecyclerAdapter.UserViewHolder> implements Filterable {

    private List<Services> listService;
    private List<Services> listServiceFull;
    private Context context;

    public ServicesRecyclerAdapter(Context context, List<Services> listService) {
        this.context = context;
        this.listService = listService;
        listServiceFull = new ArrayList<>(listService);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_recycler, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.textViewName.setText(listService.get(position).getName());
        holder.textViewEmail.setText(listService.get(position).getDescription());
        holder.textViewPassword.setText(listService.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        Log.v(ServicesRecyclerAdapter.class.getSimpleName(), "" + listService.size());
        return listService.size();
    }

    @Override
    public Filter getFilter() {
        return serviceFilter;
    }

    private Filter serviceFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Services> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listServiceFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Services item : listServiceFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            listService.clear();
            listService.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewPassword;

        public UserViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewEmail = (TextView) view.findViewById(R.id.textViewDescription);
            textViewPassword = (TextView) view.findViewById(R.id.textViewPrice);
        }
    }
}

