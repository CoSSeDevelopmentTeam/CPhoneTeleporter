package net.comorevi.cpapp.teleporter;

import cn.nukkit.utils.TextFormat;
import net.comorevi.cosse.warpapi.AdvancedWarpAPI;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Dropdown;
import net.comorevi.cphone.cphone.widget.element.Label;

import java.util.ArrayList;
import java.util.List;

public class SelectOwnPointActivity extends CustomActivity {

    private AdvancedWarpAPI advancedWarpAPI;
    private Bundle bundle;
    private List<String> ownPoints = new ArrayList<>();

    public SelectOwnPointActivity(ApplicationManifest manifest) {
        super(manifest);
        this.advancedWarpAPI = AdvancedWarpAPI.getInstance();
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_select_own_point"));

        this.ownPoints = advancedWarpAPI.getOwnPointNameList(bundle.getCPhone().getPlayer().getName());
        if (ownPoints.isEmpty()) {
            this.addFormElement(new Label().setText(bundle.getString("error_select_own_point")));
        } else {
            this.addFormElement(new Label().setText(bundle.getString("label_select_own_point")));
            this.addFormElement(new Dropdown().setOption(ownPoints).setDefaultOptionIndex(0).setText(bundle.getString("text_select_own_point")));
        }
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse customResponse = (CustomResponse) response;
        if (ownPoints.isEmpty()) {
            new MainActivity(getManifest()).start(customResponse.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }
        customResponse.getPlayer().teleport(advancedWarpAPI.getDestinationPositon(String.valueOf(customResponse.getResult().get(1))));
        bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_select_own_point"));
        return ReturnType.TYPE_END;
    }
}
