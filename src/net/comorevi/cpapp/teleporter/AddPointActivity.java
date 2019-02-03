package net.comorevi.cpapp.teleporter;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.comorevi.cosse.warpapi.AdvancedWarpAPI;
import net.comorevi.cosse.warpapi.PointType;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.model.Bundle;
import net.comorevi.cphone.cphone.model.CustomResponse;
import net.comorevi.cphone.cphone.model.Response;
import net.comorevi.cphone.cphone.widget.activity.ReturnType;
import net.comorevi.cphone.cphone.widget.activity.base.CustomActivity;
import net.comorevi.cphone.cphone.widget.element.Dropdown;
import net.comorevi.cphone.cphone.widget.element.Input;
import net.comorevi.cphone.cphone.widget.element.Label;

import java.util.ArrayList;
import java.util.List;

public class AddPointActivity extends CustomActivity {

    private Bundle bundle;
    private AdvancedWarpAPI advancedWarpAPI;

    public AddPointActivity(ApplicationManifest manifest) {
        super(manifest);
        this.advancedWarpAPI = AdvancedWarpAPI.getInstance();
    }

    @Override
    public void onCreate(Bundle bundle) {
        this.bundle = bundle;
        this.setTitle(bundle.getString("title_add_point"));

        this.addFormElement(new Label().setText(bundle.getString("label_add_point1")));
        this.addFormElement(new Input().setPlaceHolder(bundle.getString("placeHolder_add_point1")).setText(bundle.getString("text_add_point1")));
        List<String> option = new ArrayList<>();
        option.add("ノーマル");
        option.add("パスワード");
        option.add("共有");
        this.addFormElement(new Label().setText(
                        bundle.getString("label_add_point2-line1") + "\n" +
                                bundle.getString("label_add_point2-line2") + "\n" +
                                bundle.getString("label_add_point2-line3")
        ));
        this.addFormElement(new Dropdown().setOption(option).setText(bundle.getString("text_add_point2")));
        this.addFormElement(new Input().setPlaceHolder(bundle.getString("placeHolder_add_point2")).setText(bundle.getString("text_add_point3")));
    }

    @Override
    public ReturnType onStop(Response response) {
        CustomResponse customResponse = (CustomResponse) response;
        Player player = customResponse.getPlayer();

        if (player.getLevel().getFolderName().equals("resource")) {
            new ErrorActivity(getManifest(), "資源ワールドではポイントを指定できません（ワールドが毎日変更されるため）", this).start(player, bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }

        if (String.valueOf(customResponse.getResult().get(1)).equals("")) {
            new ErrorActivity(getManifest(), "設定するポイントの名前を入力してください", this).start(player, bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }

        if (advancedWarpAPI.exsistsPoint(String.valueOf(customResponse.getResult().get(1)))) {
            new ErrorActivity(getManifest(), "ポイントの名前が既に使用されています", this).start(player, bundle.getStrings());
            return ReturnType.TYPE_CONTINUE;
        }

        switch (String.valueOf(customResponse.getResult().get(3))) {
            case "ノーマル": //type private
                advancedWarpAPI.addPoint(player.getName(), String.valueOf(customResponse.getResult().get(1)), player.getPosition(), PointType.POINT_TYPE_PRIVATE, null);
                bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_add_point"));
                bundle.getCPhone().home();
                break;
            case "パスワード": //type pass
                if (String.valueOf(customResponse.getResult().get(4)).equals("")) {
                    new ErrorActivity(getManifest(), "パスワードを入力してください", this).start(player, bundle.getStrings());
                    return ReturnType.TYPE_CONTINUE;
                }

                advancedWarpAPI.addPoint(player.getName(), String.valueOf(customResponse.getResult().get(1)), player.getPosition(), PointType.POINT_TYPE_PASS_LIMITED, String.valueOf(customResponse.getResult().get(4)));
                bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_add_point"));
                bundle.getCPhone().home();
                break;
            case "共有": //type public
                advancedWarpAPI.addPoint(player.getName(), String.valueOf(customResponse.getResult().get(1)), player.getPosition(), PointType.POINT_TYPE_PUBLIC, null);
                bundle.getCPhone().setHomeMessage(TextFormat.AQUA + bundle.getString("message_add_point"));
                bundle.getCPhone().home();
                break;
        }
        return ReturnType.TYPE_END;
    }
}
