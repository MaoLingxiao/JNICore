package com.bean;

public class Config {
	public int max_cpu_time;
	public int max_real_time;
	public long max_memory;
	public int max_process_number;
	public long max_output_size;
	public String exe_path;
	public String input_path;
	public String output_path;
	public String error_path;
	public String[] args;
	public String[] env;
	public String log_path;
	public String seccomp_rule_name;
	public int uid;
	public int gid;

	public int getMax_cpu_time() {
		return max_cpu_time;
	}

	public int getMax_real_time() {
		return max_real_time;
	}

	public long getMax_memory() {
		return max_memory;
	}

	public int getMax_process_number() {
		return max_process_number;
	}

	public long getMax_output_size() {
		return max_output_size;
	}

	public String getExe_path() {
		return exe_path;
	}

	public String getInput_path() {
		return input_path;
	}

	public String getOutput_path() {
		return output_path;
	}

	public String getError_path() {
		return error_path;
	}

	public String[] getArgs() {
		return args;
	}

	public String[] getEnv() {
		return env;
	}

	public String getLog_path() {
		return log_path;
	}

	public String getSeccomp_rule_name() {
		return seccomp_rule_name;
	}

	public int getUid() {
		return uid;
	}

	public int getGid() {
		return gid;
	}

	public void setMax_cpu_time(int max_cpu_time) {
		this.max_cpu_time = max_cpu_time;
	}

	public void setMax_real_time(int max_real_time) {
		this.max_real_time = max_real_time;
	}

	public void setMax_memory(long max_memory) {
		this.max_memory = max_memory;
	}

	public void setMax_process_number(int max_process_number) {
		this.max_process_number = max_process_number;
	}

	public void setMax_output_size(long max_output_size) {
		this.max_output_size = max_output_size;
	}

	public void setExe_path(String exe_path) {
		this.exe_path = exe_path;
	}

	public void setInput_path(String input_path) {
		this.input_path = input_path;
	}

	public void setOutput_path(String output_path) {
		this.output_path = output_path;
	}

	public void setError_path(String error_path) {
		this.error_path = error_path;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public void setEnv(String[] env) {
		this.env = env;
	}

	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}

	public void setSeccomp_rule_name(String seccomp_rule_name) {
		this.seccomp_rule_name = seccomp_rule_name;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}
}
