package tk.qcute.wegrab;


import android.content.Context;
import android.content.pm.PackageManager;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class Version {

    public static void Initialization(String packageName) throws PackageManager.NameNotFoundException{
        //filter
        if((WeChatVersion!=null && QQVersion!=null))return;
        //activity context
        Context context = (Context)callMethod(callStaticMethod(findClass("android.app.ActivityThread", null), "currentActivityThread"), "getSystemContext");
        //get wechat version name
        if (packageName.equals("com.tencent.mm")) {
            WeChatVersion = context.getPackageManager().getPackageInfo("com.tencent.mm", 0).versionName;
            setWeChatArgument(WeChatVersion);
        }
        //get qq version name
        if (packageName.equals("com.tencent.mobileqq")) {
            QQVersion = context.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0).versionName;
            setQQArgument(QQVersion);
            QQ.des = new tencent.com.cftutils.DesEncUtil();
        }
    }


    //WeChat
    //version
    private static String WeChatVersion = null;
    /*
    * the class must be has : com.tencent.mm.network.? object
    * find in class : com.tencent.mm.model.ak$1
    * localObject1 = ak.vy();
    */
    public static String networkFunction = "vy";
    public static String networkClass = "com.tencent.mm.model.ak";
    /*
    * filed : messageClass
    * find in package : com.tencent.mm.e.b
    * find class by string : "msgId".hashCode();
    * */
    public static String attachMessageClass = "com.tencent.mm.plugin.luckymoney.c.ae";
    public static String messageClass = "com.tencent.mm.e.b.by";
    public static String messageMethod = "b";

    public static boolean hasTimingIdentifier = false;
    private static void setWeChatArgument(String version) {
        switch (version) {
            case "6.3.30":
                networkFunction = "vS";
                networkClass = "com.tencent.mm.model.ah";
                messageClass = "com.tencent.mm.e.b.bv";
                break;
            case "6.3.31":
                networkFunction = "vS";
                networkClass = "com.tencent.mm.model.ah";
                messageClass = "com.tencent.mm.e.b.bv";
                break;
            case "6.3.32":
                networkFunction = "vw";
                networkClass = "com.tencent.mm.model.ak";
                messageClass = "com.tencent.mm.e.b.by";
                break;
            case "6.5.3":
                networkFunction = "vy";
                networkClass = "com.tencent.mm.model.ak";
                messageClass = "com.tencent.mm.e.b.bx";
                break;
            case "6.5.4":
                networkFunction = "vy";
                networkClass = "com.tencent.mm.model.ak";
                messageClass = "com.tencent.mm.e.b.by";
                hasTimingIdentifier =true;
                break;
            case "6.5.6":
                networkFunction = "vM";
                networkClass = "com.tencent.mm.model.al";
                messageClass = "com.tencent.mm.e.b.by";
                hasTimingIdentifier =true;
                break;
            default:
                networkFunction = "vM";
                networkClass = "com.tencent.mm.model.al";
                messageClass = "com.tencent.mm.e.b.by";
                hasTimingIdentifier =true;
        }
    }

    public static boolean isSupportWeChat(String version) {
        switch (version) {
            case "6.3.30":
            case "6.3.31":
            case "6.3.32":
            case "6.5.3":
            case "6.5.4":
            case "6.5.6":return true;
            default:return false;
        }
    }


    //QQ
    //version
    public static String QQVersion = null;
    public  static boolean isUpdateLibraryVersion = false;
    /*
    * qwallet_plugin.apk
    * package : com.tenpay.android.qqplugin.a
    * find class contain :  DesDecUtil/Md5EncUtil
    * */
    public static String WalletPluginClass = "com.tenpay.android.qqplugin.a.q";

    private static void setQQArgument(String version) {
        switch (version) {
            case "6.6.0":
            case "6.6.1":
            case "6.6.2":
            case "6.6.5":
            case "6.6.6":
            case "6.6.8":
            case "6.6.9":break;
            case "6.7.0":isUpdateLibraryVersion=true;
            default : WalletPluginClass = "com.tenpay.android.qqplugin.a.q";
        }
    }

    public static boolean isSupportQQ(String version) {
        switch (version) {
            case "6.6.0":
            case "6.6.1":
            case "6.6.2":
            case "6.6.5":
            case "6.6.6":
            case "6.6.7":
            case "6.6.8":
            case "6.6.9":
            case "6.7.0":return true;
            default:return false;
        }
    }
}
