package net.comorevi.cpapp.teleporter;

import net.comorevi.cphone.cphone.CPhone;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;

public class EditPointActivity extends ListActivity {

    private Bundle bundle;
    private CPhone cPhone;

    public EditPointActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.cPhone = bundle.getCPhone();
        this.setTitle(bundle.getString("title_edit_point"));
        this.setContent(bundle.getString("content_edit_point"));

        this.addButton(new Button().setText(bundle.getString("button_edit_point1")));
        //this.addButton(new Button().setText(bundle.getString("button_edit_point2")));
        this.addButton(new Button().setText(bundle.getString("button_edit_point3")));
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        switch (listResponse.getButtonIndex()) {
            case 0:
                new AddPointActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 1:
                new ChangePointActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
            case 2:
                new RemovePointActivity(getManifest()).start(cPhone.getPlayer(), bundle.getStrings());
                return ReturnType.TYPE_CONTINUE;
        }
        return ReturnType.TYPE_END;
    }
}
