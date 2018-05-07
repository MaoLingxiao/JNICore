package com.bean;

public class Result {
	public int cpu_time;
	public int real_time;
	public long memory;
	public int signal;
	public int exit_code;
	public int error;
	public int result;

	public Result() {
		this.cpu_time = 0;
		this.real_time = 0;
		this.memory = 0;
		this.signal = 0;
		this.exit_code = 0;
		this.error = 0;
		this.result = 0;
	}

	public int getCpu_time() {
		return cpu_time;
	}

	public int getReal_time() {
		return real_time;
	}

	public long getMemory() {
		return memory;
	}

	public int getSignal() {
		return signal;
	}

	public int getExit_code() {
		return exit_code;
	}

	public int getError() {
		return error;
	}

	public int getResult() {
		return result;
	}

	public void setCpu_time(int cpu_time) {
		this.cpu_time = cpu_time;
	}

	public void setReal_time(int real_time) {
		this.real_time = real_time;
	}

	public void setMemory(long memory) {
		this.memory = memory;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	public void setExit_code(int exit_code) {
		this.exit_code = exit_code;
	}

	public void setError(int error) {
		this.error = error;
	}

	public void setResult(int result) {
		this.result = result;
	}
}
