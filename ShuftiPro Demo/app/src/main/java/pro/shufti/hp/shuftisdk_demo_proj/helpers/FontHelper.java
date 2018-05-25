package pro.shufti.hp.shuftisdk_demo_proj.helpers;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by HP on 4/22/2018.
 */

public class FontHelper {

    private static final String FONT_NAME = "futura_medium.otf";
    private static Typeface typeface = null;

    public static Typeface getFuturaFont(Context context) {

        if(typeface == null){
            typeface = Typeface.createFromAsset(context.getAssets(), FONT_NAME);
        }

        return typeface;

    }

}
