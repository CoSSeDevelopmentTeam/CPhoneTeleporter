package net.comorevi.cpapp.teleporter;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.network.protocol.ChangeDimensionPacket;
import cn.nukkit.network.protocol.PlayStatusPacket;
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
        if (bundle.getCPhone().getPlayer().isOp()) this.addButton(new Button().setText("セントラル(建築中"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        Player player = listResponse.getPlayer();
        switch (listResponse.getButtonIndex()) {
            case 0:
                player.teleport(SharingData.server.getLevelByName(config.getString("WORLDNAME_CENTRAL")).getSafeSpawn());
                player.sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
            case 1:
                player.teleport(SharingData.server.getLevelByName(config.getString("WORLDNAME_LIFE")).getSafeSpawn());
                player.sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
            case 2:
                player.teleport(SharingData.server.getLevelByName(config.getString("WORLDNAME_RESOURCE")).getSafeSpawn());
                player.sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
            case 3:
                player.teleport(SharingData.server.getLevelByName("central2020-01").getSafeSpawn());
                player.sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
                break;
        }
        return ReturnType.TYPE_IGNORE;
    }

    private void tpPlayer(Player player, String levelname) {
        player.teleport(SharingData.server.getLevelByName(levelname).getSafeSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
        ChangeDimensionPacket pk = new ChangeDimensionPacket();
        pk.dimension = 0; //0...over world, 1...nether, 2...end
        pk.x = player.getFloorX();
        pk.y = player.getFloorY();
        pk.z = player.getFloorZ();
        pk.respawn = true;
        player.dataPacket(pk);
        PlayStatusPacket pk2 = new PlayStatusPacket();
        pk2.status = PlayStatusPacket.PLAYER_SPAWN;
        player.dataPacket(pk2);
    }
}
