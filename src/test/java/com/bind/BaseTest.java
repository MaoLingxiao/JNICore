package com.bind;

import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

public class BaseTest {
	enum result{
		cpu_time_limited,
		real_time_limit_exceeded,
		memory_limit_exceeded,
		runtime_error,
		system_error
	}
	String base_workspace;
	String workspace;
	String systemdir;
	String realpath;

	public String initWorkspace(String language) throws Exception {
		System.out.println("Running:Before-"+Thread.currentThread().getStackTrace()[1].getMethodName());
		base_workspace = "/tmp";
		workspace = base_workspace+"/"+language;
		systemdir = System.getProperty("user.dir");
		realpath = systemdir+workspace;
		System.out.println("realworkspace:"+realpath);
		if(deleteFolder(realpath)){
			System.out.println("clean Dir and File");
		} else {
			System.out.println("available Dir");
		}
		File dir = new File(realpath);
		if (dir.mkdirs()) {
			System.out.println("mkdir:" + realpath + " success");
			return realpath;
		} else {
			System.out.println("mkdir:" + realpath + " fail");
		}
		return null;
	}

	public boolean deleteFolder(String url) {
		File file = new File(url);
		if (!file.exists()) {
			return false;
		}
		if (file.isFile()) {
			file.delete();
			return true;
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				//得到子文件或文件夹的绝对路径
				String root = files[i].getAbsolutePath();
				//System.out.println(root);
				deleteFolder(root);
			}
			file.delete();
			return true;
		}
	}

	public String randomStr(int length) {
		final String SOURCES = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++){
			stringBuffer.append(SOURCES.charAt((int)(Math.random()*(SOURCES.length()-1))));
		}
		return stringBuffer.toString();
	}

	public String compileCAndCPP(String src_name,String extra_flags){
		try {
			String name = systemdir+src_name.substring(0,src_name .lastIndexOf("."));
			String cmd = "gcc "+systemdir+src_name+" -o "+name;
			final Process process = Runtime.getRuntime().exec(cmd); // 执行编译指令
			return name;
		} catch (IOException e) {
			System.out.println("compile:"+src_name+" can't run");
			e.printStackTrace();
		}
		return null;
	}

	public String make_inputFile(String content,String name) throws Exception{
		if (content==null && !content.equals("")){
			System.out.println("empty or null:in method-"+Thread.currentThread().getStackTrace()[1].getMethodName());
		}
		String filename = name+".in";
		FileWriter inputFile = new FileWriter(realpath+"/"+name+".in");
		inputFile.write(content);
		inputFile.close();
		return realpath+"/"+filename;
	}

	public String outputFile_path(String name) throws Exception{
		String filename = name+".out";
		return realpath+"/"+filename;
	}

	public FileReader outputFile_content(String path) throws Exception{
		FileReader outpuFile = new FileReader(path);
		return outpuFile;
	}

//	@Test
	public void test(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("basic method definition");
	}
}