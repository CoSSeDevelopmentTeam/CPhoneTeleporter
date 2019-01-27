package net.comorevi.cpapp.teleporter;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;

public class SelectPointActivity extends ListActivity {
    public SelectPointActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {

    }

    @Override
    public ReturnType onStop(Response response) {
        return null;
    }
}
