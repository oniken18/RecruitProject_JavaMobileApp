package com.example.testapp.ArrayAdapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.testapp.ClassObjects.Client;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class ClientLines extends ArrayAdapter<Client> {

    private boolean IsActive;
    private boolean IsFlag;
    String FilterPattern;
    private List<Client> FullClientsList;

    public ClientLines(Activity context, ArrayList<Client> clients) {
        super(context,0, clients);
        FullClientsList = new ArrayList<>(clients);
    }

    @NonNull
    @Override
    public Filter getFilter()  {
        return ClientsFilter;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Client CurrClient = getItem(position);

        View listClients = convertView;
        if (listClients== null){
            listClients = LayoutInflater.from(getContext()).inflate(R.layout.client_lines,parent,false);
        }

        TextView Name =  listClients.findViewById(R.id.txtName);
        Name.setText(CurrClient.getFirstName() + " " + CurrClient.getLastName());

        View Flag =  listClients.findViewById(R.id.viewFlag);
        if  (CurrClient.getIsFlag()) {
            Flag.setBackgroundColor(Color.parseColor("#FF0000") );
        }
        return listClients;
    }

    public boolean isActive() {
        return IsActive;
    }

    public boolean isFlag() {
        return IsFlag;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public void setFlag(boolean flag) {
        IsFlag = flag;
    }

    private Filter ClientsFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Client> suggestions = new ArrayList<>();

            if (constraint==null || constraint.length()==0) {
                FilterPattern = "";
            }else {
                FilterPattern = constraint.toString().toLowerCase().trim();
            }

            for (Client item : FullClientsList) {
                if ((!IsActive || (IsActive && item.getIsActive())) && (!IsFlag || (IsFlag && item.getIsFlag()))){
                    if (constraint!=null || constraint.length()!=0) {
                        if ((item.getFirstName() + item.getLastName()).toLowerCase().contains(FilterPattern)) {
                            suggestions.add(item);
                        }
                    }else{
                        suggestions.add(item);
                    }
                }
            }

            results.values=suggestions;
            results.count=suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
        }
    };
}
