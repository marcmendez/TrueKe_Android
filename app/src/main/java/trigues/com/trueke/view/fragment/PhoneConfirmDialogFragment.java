package trigues.com.trueke.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import trigues.com.trueke.R;

/**
 * Created by Albert on 29/03/2017.
 */

public class PhoneConfirmDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_telefono, null))
                .setNeutralButton("Confirma", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //tal pascual crida a acitivity per fer intent a pantalla principal
                    }
                });
       return builder.create();
    }
}
