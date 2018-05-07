package com.bind;

import com.bean.Config;
import com.bean.Result;
import com.google.gson.JsonObject;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;

public class Bind {

    private Config config;
    private Result result;

    //native methonds
    //void run(struct config *_config, struct result *_result)
    native Result c_coreStart(Config config);

    static private String libname = null;
    static {
        try {
            libname = "judgebindroot";
            System.setProperty("java.library.path", System.getProperty("java.library.path") + ":/home/valhalla/WorkSpace/JNICore_root/src/main/java");
			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
            //C loading Dynamic link library
            //C++动态链接库 JNI C
            System.out.println(System.getProperty("java.library.path"));
            System.loadLibrary(libname);
        } catch (Exception e) {
            System.out.println("no "+libname+" in java.library.path");
        }
    }
}