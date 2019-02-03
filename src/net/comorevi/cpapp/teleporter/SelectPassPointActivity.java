package net.comorevi.cpapp.teleporter;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.comorevi.cosse.warpapi.AdvancedWarpAPI;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Input;
import net.comorevi.cphone.cphone.widget.element.Label;

import java.util.Map;

public class SelectPassPointActivity extends CustomActivity {

    private AdvancedWarpAPI advancedWarpAPI;
    private Bundle bundle;

    public SelectPassPointActivity(ApplicationManifest manifest) {
        super(manifest);
        this.advancedWarpAPI = AdvancedWarpAPI.getInstance();
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_select_pass_point"));

        this.addFormElement(new Label().setText(bundle.getString("label_select_pass_point")));
        this.addFormElement(new Input().setPlaceHolder("必ず入力...").setText(bundle.getString("text_select_pass_point1")));
        this.addFormElement(new Input().setPlaceHolder("必ず入力...").setText(bundle.getString("text_select_pass_point2")));
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse customResponse = (CustomResponse) response;
        Player player = customResponse.getPlayer();
        String inputPoint = String.valueOf(customResponse.getResult().get(1));
        String inputPassword = String.valueOf(customResponse.getResult().get(2));

        if (inputPoint.equals("") || inputPassword.equals("")) {
            new ErrorActivity(getManifest(), "ポイント名とパスワードを入力してください", this).start(customResponse.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        } else if (!advancedWarpAPI.exsistsPoint(inputPoint)) {
            new ErrorActivity(getManifest(), "入力されたポイントは存在しません", this).start(customResponse.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }

        Map<String, Object> pointData = advancedWarpAPI.getPointData(inputPoint);
        if (pointData.get("pass").equals(inputPassword)) {
            player.teleport(advancedWarpAPI.getDestinationPositon(String.valueOf(customResponse.getResult().get(1))));
            bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_select_pass_point"));
            return ReturnType.TYPE_END;
        } else {
            new ErrorActivity(getManifest(), "入力されたパスワードが違います", this).start(customResponse.getPlayer(), bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }
    }
}
