package com.zbars.kappaMessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private static final String TAG = "ContactAdapter";

    private int viewResourceId;
    private ArrayList<Contact> originalItems;
    private ArrayList<Contact> results;

    public ContactAdapter(Context context, int viewResourceId, ArrayList<Contact> contacts) {
        super(context, viewResourceId, contacts);
        this.viewResourceId = viewResourceId;
        this.originalItems = new ArrayList<>(contacts);
        this.results = new ArrayList<>();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(viewResourceId, parent, false);
        }

        TextView tvName = (TextView) convertView.findViewById(R.id.contactName);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.contactPhone);
        TextView tvType = (TextView) convertView.findViewById(R.id.contactType);

        tvName.setText(contact.name);
        tvPhone.setText(contact.phone);
        tvType.setText(contact.type);

        return convertView;
    }

    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        public FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                results.clear();
                for(Contact contact : originalItems) {
                    if(contact.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        results.add(contact);
                    } else if(contact.phone.contains(constraint.toString())) {
                        results.add(contact);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = results;
                filterResults.count = results.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Contact> filteredList = (ArrayList<Contact>) results.values;
            if(results.count > 0) {
                clear();
                for(Contact c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
