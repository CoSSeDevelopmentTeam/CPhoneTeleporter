package net.comorevi.cpapp.teleporter;

import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;

public class SelectServerActivity extends ListActivity {

    private Bundle bundle;

    public SelectServerActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_select_server"));
        this.setContent(bundle.getString("content_select_server") + "\n" + "現在移動できるサーバーはありません");

        this.addButton(new Button().setText("Teleporterホームへ"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        new MainActivity(getManifest()).start(listResponse.getPlayer(), bundle.getStrings());
        return ReturnType.TYPE_CONTINUE;
    }
}
