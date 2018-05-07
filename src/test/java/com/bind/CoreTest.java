package com.bind;

import com.bean.Result;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;//必须是static

import java.io.IOException;

import com.bean.Config;

public class CoreTest extends BaseTest {
	JSONObject configJson;
	Config config = new Config();
	Result result;
	@Before
	public void setUp() throws Exception{
		System.out.println("===========================================================");

		super.initWorkspace("integration_test");

		config.setMax_cpu_time(1000);
		config.setMax_real_time(3000);
		config.setMax_memory(1024*1024*128);
		config.setMax_process_number(10);
		config.setMax_output_size(1024*1024);
		config.setExe_path("/bin/ls");
		config.setInput_path("tmp/null");
		config.setOutput_path("tmp/null");
		config.setError_path("tmp/null");
		String[] arg = {};
		config.setArgs(arg);
		String[] env = {"env=judger_test", "test=judger"};
		config.setEnv(env);
		config.setLog_path("judger_test.log");
		config.setSeccomp_rule_name("");
		config.setUid(0);
		config.setGid(0);

	}

	public void _compile_c(String src_name,String extra_flags){
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
			config.setExe_path(compileCAndCPP("/c/c_test/integration/normal.c",null));
			System.out.println("run:"+config.getExe_path());
			String randomstr = randomStr(8);
			config.setInput_path(make_inputFile("judger_test",randomstr));
			config.setOutput_path(outputFile_path(randomstr));
			config.setError_path(outputFile_path(randomstr));
			Bind bind = new Bind();
			result = bind.c_coreStart(config);

			

			System.out.println("success!~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}