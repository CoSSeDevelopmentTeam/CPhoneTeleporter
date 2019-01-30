package net.comorevi.cpapp.teleporter;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ModalResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.Activity;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ModalActivity;

public class ErrorActivity extends ModalActivity {

    private String eMessage;
    private Activity activity;
    private Bundle bundle;

    public ErrorActivity(ApplicationManifest manifest, String errorMessage, Activity lastActivity) {
        super(manifest);
        this.eMessage = errorMessage;
        this.activity = lastActivity;
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_error"));
        this.setContent("message:\n - " + eMessage);
        this.setButton1Text(bundle.getString("button_error1"));
        this.setButton2Text(bundle.getString("button_error2"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ModalResponse modalResponse = (ModalResponse) response;
        if (modalResponse.isButton1Clicked()) {
            switch (activity.getClass().getName()) {
                case "net.comorevi.cpapp.teleporter.AddPointActivity":
                    new AddPointActivity(getManifest()).start(modalResponse.getPlayer(), bundle.getStrings());
                    break;
            }
            return ReturnType.TYPE_CONTINUE;
        } else if (modalResponse.isButton2Clicked()) {
            return ReturnType.TYPE_END;
        }
        return ReturnType.TYPE_END;
    }
}
