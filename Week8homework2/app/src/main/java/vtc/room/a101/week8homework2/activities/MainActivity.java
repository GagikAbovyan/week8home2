package vtc.room.a101.week8homework2.activities;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vtc.room.a101.week8homework2.R;
import vtc.room.a101.week8homework2.adapters.PeopleAdapter;
import vtc.room.a101.week8homework2.dialogs.AddDialog;
import vtc.room.a101.week8homework2.models.People;

public class MainActivity extends AppCompatActivity {

    private List<People> list = new ArrayList<>();
    private PeopleAdapter peopleAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPeople();
        if (getArrayList()!= null){
            list = getArrayList();
        }
        startPeoplesList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveArrayList(list);
    }

    private void startPeoplesList() {
        final RecyclerView recyclerPeoples = (RecyclerView) findViewById(R.id.recycler_peoples);
        recyclerPeoples.setHasFixedSize(true);
        recyclerPeoples.setLayoutManager(new LinearLayoutManager(this));
        peopleAdapter = new PeopleAdapter(this, list);
        recyclerPeoples.setAdapter(peopleAdapter);
        swipeToDeleteLeft(recyclerPeoples);

    }

    private void addPeople() {
        final FloatingActionButton add = (FloatingActionButton) findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDialog add = new AddDialog();
                add.show(getSupportFragmentManager().beginTransaction(), "tag");
            }
        });
    }

    public void addElement(final String name, final String surname){
        list.add(new People(name, surname));
        peopleAdapter.notifyDataSetChanged();
    }

    private void saveArrayList(final List<People> list) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final Gson gson = new Gson();
        final String json = gson.toJson(list);
        editor.putString(getString(R.string.my_list), json);
        editor.apply();
    }

    private ArrayList<People> getArrayList() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final Gson gson = new Gson();
        final String json = prefs.getString(getString(R.string.my_list), null);
        final Type type = new TypeToken<ArrayList<People>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    private void swipeToDeleteLeft(final RecyclerView recyclerView) {
        final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    removeElementByPosition(position);
                }
            }
        };
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removeElementByPosition(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.message_del));

        builder.setPositiveButton(getString(R.string.remove), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                peopleAdapter.notifyItemRemoved(position);
                list.remove(position);
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                peopleAdapter.notifyItemRemoved(position + 1);
                peopleAdapter.notifyItemRangeChanged(position, peopleAdapter.getItemCount());
            }
        }).show();
    }

}
