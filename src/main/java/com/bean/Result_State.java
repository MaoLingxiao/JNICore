package com.bean;

public enum Result_State {

	WRONG_ANSWER(-1),
	CPU_TIME_LIMIT_EXCEEDED(1),
	REAL_TIME_LIMIT_EXCEEDED(2),
	MEMORY_LIMIT_EXCEEDED(3),
	RUNTIME_ERROR(4),
	SYSTEM_ERROR(5);

	private Result_State(int index) {
		this.code = code;
	}

	private short code;
}
