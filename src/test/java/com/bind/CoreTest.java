package com.bind;

import com.bean.Result;
import com.bean.Result_State;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;//必须是static

import java.io.IOException;

import com.bean.Config;


import sun.misc.Signal;
import sun.misc.SignalHandler;
public class CoreTest extends BaseTest {
	JSONObject configJson;
	Config config = new Config();
	Result result;
	Bind bind = new Bind();

	@BeforeClass
	public static void BeforeClass(){
		try {
			initWorkspace("integration_test");
		} catch (Exception e){
			System.out.println(e);
		}
	}

	@Before
	public void setUp() throws Exception{
		System.out.println("===========================================================");

		config.setMax_cpu_time(1000);
		config.setMax_real_time(3000);
		config.setMax_memory(1024*1024*128);
		config.setMax_process_number(10);
		config.setMax_output_size(1024*1024);
		config.setExe_path("/bin/ls");
		config.setInput_path("/dev/null");
		config.setOutput_path("/dev/null");
		config.setError_path("/dev/null");
		String[] arg = {};
		config.setArgs(arg);
		String[] env = {"env=judger_test", "test=judger"};
		config.setEnv(env);
		config.setLog_path("judger_test.log");
		config.setSeccomp_rule_name("");
		config.setUid(0);
		config.setGid(0);

	}

	public void _compile_c(String src_name,String extra_flags) throws Exception{
		if (extra_flags == null){
			extra_flags = "None";
		}
		super.compileCAndCPP("../../../../../c/c_test/integration/" + src_name,extra_flags);
	}

	@Test
	public void test_normal(){
		try {
			System.out.println("-----------------------------------------------------------");
			System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());
			String randomstr;

			randomstr = "normal"+randomStr(8);
			config.setExe_path(compileCAndCPP("/c/c_test/integration/normal.c",null));
			System.out.println("run:"+config.getExe_path());
			config.setInput_path(make_inputFile("judger_test",randomstr));
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			result = bind.c_coreStart(config);
			assertEquals(result.result,0);
			assertEquals(super.outputFile_content(config.output_path), "judger_test\nHello world");

			randomstr = "math"+randomStr(8);
			config.setExe_path(compileCAndCPP("/c/c_test/integration/math.c",null));
			System.out.println("run:"+config.getExe_path());
			config.setInput_path("/dev/null");
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			result = bind.c_coreStart(config);
			assertEquals(result.result,Result_State.SUCCESS);
			assertEquals(super.outputFile_content(config.output_path), "abs 1024");

			System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void test_args(){
		try {
			System.out.println("-----------------------------------------------------------");
			System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());
			String randomstr;

			randomstr = "args"+randomStr(8);
			config.setExe_path(compileCAndCPP("/c/c_test/integration/args.c",null));
			System.out.println("run:"+config.getExe_path());
			String[] strings = {"test", "hehe", "000"};
			config.setArgs(strings);
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			result = bind.c_coreStart(config);
			assertEquals(result.result,Result_State.SUCCESS);
			assertEquals(super.outputFile_content(config.output_path), "argv[0]: test\nargv[1]: hehe\nargv[2]: 000\n");

			System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void test_env(){
		try {
			System.out.println("-----------------------------------------------------------");
			System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());
			String randomstr;

			randomstr = "env"+randomStr(8);
			config.setExe_path(compileCAndCPP("/c/c_test/integration/env.c",null));
			System.out.println("run:"+config.getExe_path());
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			result = bind.c_coreStart(config);
			assertEquals(result.result,Result_State.SUCCESS);
			assertEquals(super.outputFile_content(config.output_path), "judger_test\njudger\n");

			System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void test_real_time(){
		try {
			System.out.println("-----------------------------------------------------------");
			System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

			config.setExe_path(compileCAndCPP("/c/c_test/integration/sleep.c",null));
			System.out.println("run:"+config.getExe_path());
			result = bind.c_coreStart(config);
			assertEquals(result.result, Result_State.REAL_TIME_LIMIT_EXCEEDED);
			assertEquals(result.signal, new Signal("KILL").getNumber());
			assertTrue(result.real_time >= config.max_real_time);

			System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void test_cpu_time(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/while1.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		result = bind.c_coreStart(config);
		assertEquals(result.result, Result_State.CPU_TIME_LIMIT_EXCEEDED);
		assertEquals(result.signal, new Signal("KILL").getNumber());
		assertTrue(result.cpu_time >= config.max_cpu_time);

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_memory1(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/memory1.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		config.setMax_memory(64 * 1024 * 1024);
		result = bind.c_coreStart(config);
		assertEquals(result.result, Result_State.MEMORY_LIMIT_EXCEEDED);
		assertTrue(result.memory > 80 * 1024 * 1024);

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_memory2(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/memory2.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		config.setMax_memory(64 * 1024 * 1024);
		result = bind.c_coreStart(config);
		assertEquals(result.exit_code, 1);
		//存在争议的最小程序大小
		assertTrue(result.memory < 32 * 1024 * 1024);
		assertEquals(result.result, Result_State.RUNTIME_ERROR);
		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_memory3(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/memory3.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		config.setMax_memory(512 * 1024 * 1024);
		result = bind.c_coreStart(config);
		assertEquals(result.result, Result_State.SUCCESS);
		assertTrue(result.memory >= 102400000 * 4);

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_re1(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/re1.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		result = bind.c_coreStart(config);
		assertEquals(result.exit_code, 25);

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_re2(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/re2.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		result = bind.c_coreStart(config);
		assertEquals(result.result, Result_State.RUNTIME_ERROR);
		assertEquals(result.signal, new Signal("SEGV").getNumber());

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_child_proc_cpu_time_limit(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/child_proc_cpu_time_limit.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		result = bind.c_coreStart(config);
		assertEquals(result.result, Result_State.CPU_TIME_LIMIT_EXCEEDED);

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_child_proc_real_time_limit(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/child_proc_real_time_limit.c",null));
		} catch (Exception e){
			System.out.println(e);
		}
		result = bind.c_coreStart(config);
		assertEquals(result.result, Result_State.REAL_TIME_LIMIT_EXCEEDED);
		assertEquals(result.signal, new Signal("KILL").getNumber());

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_stdout_and_stderr(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String randomstr;

		try {
			randomstr = "stdout_stderr"+randomStr(8);
			config.setExe_path(compileCAndCPP("/c/c_test/integration/stdout_stderr.c",null));
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			result = bind.c_coreStart(config);
			assertEquals(super.outputFile_content(config.output_path), "stderr\n+++++++++++++++\n--------------\nstdout\n");
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_uid_and_gid(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());
		String randomstr;

		try {
			randomstr = "uid_gid"+randomStr(8);
			config.setExe_path(compileCAndCPP("/c/c_test/integration/uid_gid.c",null));
			config.setUid(65534);
			config.setGid(65534);
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			result = bind.c_coreStart(config);
			assertEquals(result.result, Result_State.SUCCESS);
			assertEquals(super.outputFile_content(config.output_path), "uid=65534(nobody) gid=65534(nogroup) groups=65534(nogroup)\nuid 65534\ngid 65534\n");
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_gcc_random(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path("/usr/bin/gcc");
			String[] strings = {"/usr/bin/gcc","/home/valhalla/WorkSpace/JNICore_root/c/c_test/integration/gcc_random.c","-o","/home/valhalla/WorkSpace/JNICore_root/c/c_test/integration/gcc_random"};
			config.setArgs(strings);
			//该程序命令占用内存时间和大小与cpu性能有关
			config.setMax_memory(1024*1024*512);
			System.out.println(strings[0]);
			result = bind.c_coreStart(config);
			assertEquals(result.result, Result_State.CPU_TIME_LIMIT_EXCEEDED);
			assertTrue(result.cpu_time >= 1950);
			assertTrue(result.real_time >= 1950);
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_cpp_meta(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());

		try {
			config.setExe_path("/usr/bin/g++");
			String[] strings = {"/usr/bin/gcc","/home/valhalla/WorkSpace/JNICore_root/c/c_test/integration/cpp_meta.cpp","-o","/home/valhalla/WorkSpace/JNICore_root/c/c_test/integration/cpp_meta"};
			config.setArgs(strings);
			//该程序命令占用内存时间和大小与cpu性能有关
			config.setMax_memory(1024*1024*512);
			System.out.println(strings[0]);
			result = bind.c_coreStart(config);
			assertEquals(result.result, Result_State.CPU_TIME_LIMIT_EXCEEDED);
			assertTrue(result.cpu_time >= 1950);
			assertTrue(result.real_time >= 1950);
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	@Test
	public void test_output_size(){
		System.out.println("-----------------------------------------------------------");
		System.out.println("test:CoreTest:~"+Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			config.setExe_path(compileCAndCPP("/c/c_test/integration/output_size.c",null));
			config.setMax_output_size(1000 * 10);
			System.out.println("run:"+config.getExe_path());
			result = bind.c_coreStart(config);
			//超出后返回0 截断return
			assertEquals(result.exit_code, 0);
			assertEquals(result.signal,new Signal("SGIXFSZ").getNumber());
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
}
