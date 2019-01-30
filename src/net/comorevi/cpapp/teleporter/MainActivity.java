package net.comorevi.cpapp.teleporter;

import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;

public class MainActivity extends ListActivity {

    private Bundle bundle;
    private CPhone cPhone;

    public MainActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();
        this.setTitle(bundle.getString("title_home"));
        this.setContent(bundle.getString("content_home"));

        this.addButton(new Button().setText(bundle.getString("button_home1")));
        this.addButton(new Button().setText(bundle.getString("button_home2")));
        this.addButton(new Button().setText(bundle.getString("button_home3")));
        this.addButton(new Button().setText(bundle.getString("button_home4")));
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case 0:
                new SelectLevelActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 1:
                new SelectPointActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 2:
                new SelectServerActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 3:
                new EditPointActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 4:
                new SettingsActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
        }

        return ReturnType.TYPE_END;
    }
}
