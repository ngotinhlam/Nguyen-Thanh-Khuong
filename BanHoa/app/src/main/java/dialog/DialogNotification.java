package dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

//Dialog thông báo

public class DialogNotification extends AlertDialog.Builder{

    public DialogNotification(@NonNull Context context) {
        super(context);

        initDialog();
    }

    public void initDialog() {
        setTitle("Thông báo");
        setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
    }

    public void showMessage(String message) {
        setMessage(message);
        show();
    }
}
