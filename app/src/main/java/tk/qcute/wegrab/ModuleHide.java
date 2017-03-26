package tk.qcute.wegrab;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Binder;

import java.util.Iterator;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


public class ModuleHide {

    public static void hookPackage(final XC_LoadPackage.LoadPackageParam Param) throws Throwable {
        //normal hide mode
        //if(Param.packageName.contains("tencent")) hide(Param);

        //new hide mode
        //if (Param.packageName.equals("android")) newHide(Param, "tencent");
        newHide(Param, "tencent");
        /*
        //extreme hide mode
        else if (PreferencesUtils.extremeHide()) {
            extremeHide(loadPackageParam);
        }
        */
    }



    //extreme hide mode
    /**
     * @param Param
     * */
    /*
    private void extremeHide(XC_LoadPackage.LoadPackageParam Param) {
        //filter
        if (!Param.packageName.equals("android")) return;
        //find the package manager service
        Class<?> service = XposedHelpers.findClass("com.android.server.pm.PackageManagerService", Param.classLoader);
        XposedBridge.hookAllMethods(service, "getInstalledApplications", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                if(name.contains("xposed"))return;
                if(name.contains("android.uid.system"))return;
                // this object is android.content.pm.ParceledListSlice
                Object object = param.getResult();
                //convert to application info
                List<ApplicationInfo> list = (List<ApplicationInfo>) XposedHelpers.getObjectField(object, "mList");
                Iterator<ApplicationInfo> iterator = list.iterator();
                //remove query package from ApplicationInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(getInstalledApplications): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                XposedHelpers.setObjectField(object, "mList", list);
            }
        });
        XposedBridge.hookAllMethods(service, "getInstalledPackages", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                if(name.contains("xposed"))return;
                if(name.contains("android.uid.system"))return;
                // this object is android.content.pm.ParceledListSlice
                Object object = param.getResult();
                //convert to application info
                List<PackageInfo> list = (List<PackageInfo>) XposedHelpers.getObjectField(object, "mList");
                Iterator<PackageInfo> iterator = list.iterator();
                //remove query package from PackageInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(getInstalledPackages): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                //set result
                XposedHelpers.setObjectField(object, "mList", list);
            }
        });
        XposedBridge.hookAllMethods(service, "queryIntentActivities", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                if(name.contains("xposed"))return;
                if(name.contains("android.uid.system"))return;
                // android.content.pm.ParceledListSlice
                List<ResolveInfo> list = (List) param.getResult();
                Iterator<ResolveInfo> iterator = list.iterator();
                //remove query package from ResolveInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().activityInfo.packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(queryIntentActivities): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                //set result
                param.setResult(list);
            }
        });
        XposedBridge.hookAllMethods(service, "queryIntentActivityOptions", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                if(name.contains("xposed"))return;
                if(name.contains("android.uid.system"))return;
                // android.content.pm.ParceledListSlice
                List<ResolveInfo> list = (List) param.getResult();
                Iterator<ResolveInfo> iterator = list.iterator();
                //remove query package from ResolveInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().activityInfo.packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(queryIntentActivityOptions): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                //set result
                param.setResult(list);
            }
        });
    }
    */

    //new hide mode
    /**
     * @param Param
     * @param callingName  : not include calling name
     * */
    private static void newHide(XC_LoadPackage.LoadPackageParam Param, final String callingName) {
        //filter
        //if (!loadPackageParam.packageName.equals("android")) return;
        //find the package manager service
        Class<?> service = XposedHelpers.findClass("com.android.server.pm.PackageManagerService", Param.classLoader);
        XposedBridge.hookAllMethods(service, "getInstalledApplications", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //hide control
                //if(!PreferencesUtils.newHide())return;
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                //not include xposed call
                if(!name.contains(callingName))return;
                // this object is android.content.pm.ParceledListSlice
                Object object = param.getResult();
                //convert to application info
                List<ApplicationInfo> list = (List<ApplicationInfo>) XposedHelpers.getObjectField(object, "mList");
                Iterator<ApplicationInfo> iterator = list.iterator();
                //remove query package from ApplicationInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(getInstalledApplications): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                XposedHelpers.setObjectField(object, "mList", list);
            }
        });
        XposedBridge.hookAllMethods(service, "getInstalledPackages", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //hide control
                //if(!PreferencesUtils.newHide())return;
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                //not include xposed call
                if(!name.contains(callingName))return;
                // this object is android.content.pm.ParceledListSlice
                Object object = param.getResult();
                //convert to application info
                List<PackageInfo> list = (List<PackageInfo>) XposedHelpers.getObjectField(object, "mList");
                Iterator<PackageInfo> iterator = list.iterator();
                //remove query package from PackageInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(getInstalledPackages): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                //set result
                XposedHelpers.setObjectField(object, "mList", list);
            }
        });
        XposedBridge.hookAllMethods(service, "queryIntentActivities", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //hide control
                //if(!PreferencesUtils.newHide())return;
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                //not include xposed call
                if(!name.contains(callingName))return;
                // android.content.pm.ParceledListSlice
                List<ResolveInfo> list = (List) param.getResult();
                Iterator<ResolveInfo> iterator = list.iterator();
                //remove query package from ResolveInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().activityInfo.packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(queryIntentActivities): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                //set result
                param.setResult(list);
            }
        });
        XposedBridge.hookAllMethods(service, "queryIntentActivityOptions", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                //hide control
                //if(!PreferencesUtils.newHide())return;
                //compare calling name
                String name = (String) XposedHelpers.callMethod(param.thisObject, "getNameForUid", Binder.getCallingUid());
                //not include xposed call
                if(!name.contains(callingName))return;
                // android.content.pm.ParceledListSlice
                List<ResolveInfo> list = (List) param.getResult();
                Iterator<ResolveInfo> iterator = list.iterator();
                //remove query package from ResolveInfo
                while (iterator.hasNext()) {
                    String queryName = iterator.next().activityInfo.packageName;
                    if (isQueryThis(queryName)) {
                        xLog("ModuleHide Package(queryIntentActivityOptions): " + queryName + "   Calling by: " + name);
                        iterator.remove();
                    }
                }
                //set result
                param.setResult(list);
            }
        });
    }

/*
    //normal hide mode
    private void hide(final XC_LoadPackage.LoadPackageParam Param) {
        //ModuleHide Package From Install Application
        findAndHookMethod("android.app.ApplicationPackageManager", Param.classLoader, "getInstalledApplications", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ApplicationInfo> list = (List) param.getResult();
                Iterator<ApplicationInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next().packageName;
                    if (isQueryThis(name)) {
                        xLog("ModuleHide Package: " + name + "(" + Param.packageName + ")");
                        iterator.remove();
                    }
                }
                param.setResult(list);
            }
        });
        //ModuleHide Package From Install Package
        findAndHookMethod("android.app.ApplicationPackageManager", Param.classLoader, "getInstalledPackages", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<PackageInfo> list = (List) param.getResult();
                Iterator<PackageInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next().packageName;
                    if (isQueryThis(name)) {
                        xLog("ModuleHide Package: " + name + "(" + Param.packageName + ")");
                        iterator.remove();
                    }
                }
                param.setResult(list);
            }
        });
        //Fake Package From Application Info
        findAndHookMethod("android.app.ApplicationPackageManager", Param.classLoader, "getApplicationInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String packageName = (String) param.args[0];
                if (isQueryThis(packageName)) {
                    param.args[0] = Param.packageName;
                    xLog("Fake Package: " + packageName + " as " + Param.packageName);
                }
            }
        });
        //Fake Package From Package Info
        findAndHookMethod("android.app.ApplicationPackageManager", Param.classLoader, "getPackageInfo", String.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String packageName = (String) param.args[0];
                if (isQueryThis(packageName)) {
                    param.args[0] = Param.packageName;
                    xLog("Fake Package: " + packageName + " as " + Param.packageName);
                }
            }
        });
        //ModuleHide Package From Running Services
        findAndHookMethod("android.app.ActivityManager", Param.classLoader, "getRunningServices", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ActivityManager.RunningServiceInfo> list = (List) param.getResult();
                Iterator<ActivityManager.RunningServiceInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next().process;
                    if (isQueryThis(name)) {
                        xLog("ModuleHide Service: " + name);
                        iterator.remove();
                    }
                }
                param.setResult(list);
            }
        });
        //ModuleHide Package From Running Tasks
        findAndHookMethod("android.app.ActivityManager", Param.classLoader, "getRunningTasks", int.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ActivityManager.RunningTaskInfo> list = (List) param.getResult();
                Iterator<ActivityManager.RunningTaskInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next().baseActivity.flattenToString();
                    if (isQueryThis(name)) {
                        xLog("ModuleHide Task: " + name);
                        iterator.remove();
                    }
                }
                param.setResult(list);
            }
        });
        //ModuleHide Package From Running Processes
        findAndHookMethod("android.app.ActivityManager", Param.classLoader, "getRunningAppProcesses", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                List<ActivityManager.RunningAppProcessInfo> list = (List) param.getResult();
                Iterator<ActivityManager.RunningAppProcessInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    String name = iterator.next().processName;
                    if (isQueryThis(name)) {
                        xLog("ModuleHide Process: " + name);
                        iterator.remove();
                    }
                }
                param.setResult(list);
            }
        });
    }
*/
    //query name
    private static boolean isQueryThis(String name) {
        return name.contains("qcute") || name.contains("xposed");
    }

    //log control
    private static void xLog(String msg){
        //if(PreferencesUtils.debug())
        //XposedBridge.log(msg);
    }
}
