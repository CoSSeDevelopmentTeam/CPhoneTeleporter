package net.comorevi.cpapp.teleporter;

import cn.nukkit.Player;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
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

public class SelectLevelActivity extends ListActivity {

    private Bundle bundle;
    private Config config;

    public SelectLevelActivity(ApplicationManifest manifest) {
        super(manifest);
        File file = new File(SharingData.server.getDataPath() + "plugins/CPhone/AppData/Teleporter");
        file.mkdirs();
        ConfigSection cs = new ConfigSection(){{
            put("WORLDNAME_CENTRAL", "central2020-01");
            put("WORLDNAME_LIFE", "life2020-01");
            put("WORLDNAME_RESOURCE", "resource");
        }};
        config = new Config(new File(file, "config.yml"), Config.YAML, cs);
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
        //if (bundle.getCPhone().getPlayer().isOp()) this.addButton(new Button().setText("セントラル(建築中"));
    }

    @Override
    public ReturnType onStop(Response response) {
        ListResponse listResponse = (ListResponse) response;
        Player player = listResponse.getPlayer();
        switch (listResponse.getButtonIndex()) {
            case 0:
                tpPlayer(player, config.getString("WORLDNAME_CENTRAL"));
                break;
            case 1:
                tpPlayer(player, config.getString("WORLDNAME_LIFE"));
                break;
            case 2:
                tpPlayer(player, config.getString("WORLDNAME_RESOURCE"));
                break;
            /*
            case 3:
                tpPlayer(player, "central2020-01");
                break;
             */
        }
        return ReturnType.TYPE_IGNORE;
    }

    private void tpPlayer(Player player, String levelname) {
        //注意...ネザーを追加する際にはdimensionの値に関する処理を変更すること。
        player.sendMessage(TextFormat.AQUA + bundle.getString("message_select_level"));
        player.teleport(SharingData.server.getLevelByName(levelname).getSafeSpawn());
        /*
        ChangeDimensionPacket pk = new ChangeDimensionPacket();
        pk.dimension = 1; //0...over world, 1...nether, 2...end
        pk.x = 0;
        pk.y = 0;
        pk.z = 0;
        player.dataPacket(pk);
        PlayStatusPacket pk2 = new PlayStatusPacket();
        pk2.status = PlayStatusPacket.PLAYER_SPAWN;
        player.dataPacket(pk2);

        player.teleport(pos);
        pk.dimension = 0;
        pk.x = (float) pos.x;
        pk.y = (float) pos.y;
        pk.z = (float) pos.z;
        pk.respawn = true;
        player.dataPacket(pk);
        new Timer().schedule(new updatePlayStatusTask(player), 5000);

       */
    }

    /*
    class updatePlayStatusTask extends TimerTask {
        Player player;

        updatePlayStatusTask(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            PlayStatusPacket pk2 = new PlayStatusPacket();
            pk2.status = PlayStatusPacket.PLAYER_SPAWN;
            player.dataPacket(pk2);
        }
    }
     */
}
