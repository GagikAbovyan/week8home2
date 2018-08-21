package vtc.room.a101.week8homework2.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import vtc.room.a101.week8homework2.R;
import vtc.room.a101.week8homework2.activities.MainActivity;

public class AddDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_add_dialog, container, false);
        okButtonOnClick(view);
        return view;
    }

    private void okButtonOnClick(final View view){
        final EditText editName = (EditText) view.findViewById(R.id.edit_name);
        final EditText editSurname = (EditText) view.findViewById(R.id.edit_surname);
        final Button okButton = (Button) view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).addElement(editName.getText().toString(),
                        editSurname.getText().toString());
                dismiss();
            }
        });
    }
}
