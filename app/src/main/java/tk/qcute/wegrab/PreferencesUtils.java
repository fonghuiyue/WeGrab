package tk.qcute.wegrab;


import de.robv.android.xposed.XSharedPreferences;

public class PreferencesUtils {
    //Preferences Instance
    private static XSharedPreferences preferences = null;
    //Load on Zygote
    public static void loadPreferencesOnZygote(){
        if (preferences == null) {
            preferences = new XSharedPreferences(PreferencesUtils.class.getPackage().getName());
            preferences.makeWorldReadable();
        }
    }
    //Get Instance (and Refresh)
    private static XSharedPreferences getInstance() {
        if (preferences == null) {
            preferences = new XSharedPreferences(PreferencesUtils.class.getPackage().getName());
            preferences.makeWorldReadable();
        } else {
            preferences.reload();
        }
        return preferences;
    }


    //public static boolean debug(){return getInstance().getBoolean("debug",false);}
    //public static boolean open(){return getInstance().getBoolean("open",false);}
    //public static boolean close(){return getInstance().getBoolean("close",false);}

    //wechat
    public static boolean wechatOpen() {return getInstance().getBoolean("wechat_open", false);}

    public static boolean wechatSelf() {return getInstance().getBoolean("wechat_self", false);}

    public static boolean wechatMute() {return getInstance().getBoolean("wechat_mute", false);}

    //qq
    public static boolean qqOpen() {return getInstance().getBoolean("qq_open", false);}

    public static boolean qqWord() {return getInstance().getBoolean("qq_word", false);}

    public static boolean qqSelf() {return getInstance().getBoolean("qq_self", false);}

    //public static boolean qqCompatible() {return getInstance().getBoolean("qq_compatible", false);}

    //hide
    //public static boolean newHide(){return getInstance().getBoolean("newHide",false);}

    //public static boolean extremeHide(){return getInstance().getBoolean("extremeHide",false);}

}


