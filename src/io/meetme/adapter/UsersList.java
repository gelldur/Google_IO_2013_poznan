package io.meetme.adapter;

import io.meetme.database.User;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;

public class UsersList extends BaseAdapterSnippet {

    private ArrayList<User> users = new ArrayList<User>();

    public UsersList(Context context) {
	super(context);
    }

    @Override
    public int getCount() {
	return users.size();
    }

    @Override
    public Object getItem(int position) {
	return users.get(position);
    }

    @Override
    public View getInflatedRow(int position) {
	return null;
    }

    @Override
    protected Object getNewHolder() {
	return null;
    }

    @Override
    protected void initializeHolder(Object yourHolder, View view, int position) {

    }

    @Override
    protected void inflateHolder(Object yourHolder, View view) {

    }

    private static class Holder {

    }

    public void addAll(ArrayList<User> result) {
	users.addAll(result);
	notifyDataSetChanged();
    }

}
