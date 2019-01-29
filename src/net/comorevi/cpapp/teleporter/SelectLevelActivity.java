package net.comorevi.cpapp.teleporter;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.cphone.presenter.SharingData;

public class SelectLevelActivity extends ListActivity {

    private Bundle bundle;

    public SelectLevelActivity(ApplicationManifest manifest) {
        super(manifest);
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_home"));
        String replacedString = bundle.getString("content_select_level").replace("%1", bundle.getCPhone().getPlayer().getLevel().getFolderName());
        this.setContent(replacedString);

        this.addButton(new Button().setText(bundle.getString("button_select_level1")));
        this.addButton(new Button().setText(bundle.getString("button_select_level2")));
        this.addButton(new Button().setText(bundle.getString("button_select_level3")));
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        Player player = listResponse.getPlayer();
        switch (listResponse.getButtonIndex()) {
            case 0:
                player.teleport(SharingData.server.getLevelByName("central").getSpawnLocation());
                bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
            case 1:
                player.teleport(SharingData.server.getLevelByName("life").getSpawnLocation());
                bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
            case 2:
                player.teleport(SharingData.server.getLevelByName("resource").getSpawnLocation());
                bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
        }
        return ReturnType.TYPE_END;
    }
}
