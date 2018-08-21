package vtc.room.a101.week8homework2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vtc.room.a101.week8homework2.R;
import vtc.room.a101.week8homework2.models.People;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    private final Context context;
    private final List<People> list;

    public PeopleAdapter(final Context context, final List<People> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public PeopleViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.layout_people, parent, false);
        return new PeopleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PeopleViewHolder holder, final int position) {
        final People people = list.get(position);
        holder.nameText.setText(people.getName());
        holder.surnameText.setText(people.getSurname());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class PeopleViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText;
        private TextView surnameText;
        private PeopleViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.name_people);
            surnameText = (TextView) itemView.findViewById(R.id.surname_people);
        }
    }
}
