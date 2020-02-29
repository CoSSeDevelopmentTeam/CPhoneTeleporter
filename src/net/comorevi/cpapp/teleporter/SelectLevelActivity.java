package net.comorevi.cpapp.teleporter;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.ListResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.ListActivity;
import net.comorevi.cphone.cphone.widget.element.Button;
import net.comorevi.cphone.presenter.SharingData;

import java.io.File;
import java.util.LinkedHashMap;

public class SelectLevelActivity extends ListActivity {

    private Bundle bundle;
    private Config config;

    public SelectLevelActivity(ApplicationManifest manifest) {
        super(manifest);
        File file = new File(SharingData.server.getDataPath() + "plugins/CPhone/AppData/Teleporter");
        file.mkdirs();
        config = new Config(
                new File(file, "config.yml"),
                Config.YAML,
                new LinkedHashMap<String, Object>() {
                    {
                        put("WORLDNAME_CENTRAL", "central2020-01");
                        put("WORLDNAME_LIFE", "life2020-01");
                        put("WORLDNAME_RESOURCE", "resource");
                    }
                });
        config.save();
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
                player.teleport(SharingData.server.getLevelByName(config.getString("WORLDNAME_CENTRAL")).getSpawnLocation());
                break;
            case 1:
                player.teleport(SharingData.server.getLevelByName(config.getString("WORLDNAME_LIFE")).getSpawnLocation());
                break;
            case 2:
                player.teleport(SharingData.server.getLevelByName(config.getString("WORLDNAME_RESOURCE")).getSpawnLocation());
                break;
        }
        ((ListResponse) response).getPlayer().sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
        return ReturnType.TYPE_IGNORE;
    }
}
