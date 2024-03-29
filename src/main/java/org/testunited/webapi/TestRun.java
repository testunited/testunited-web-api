package org.testunited.webapi;

import java.util.Date;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class TestRun {

	@Id
//	@GeneratedValue
	@org.hibernate.annotations.Type(type="uuid-char")
	private UUID id;
	
	@ManyToOne
	@NotNull
	private TestCase testCase;
	@NotNull
	private Date timeStamp;
	@NotNull
	private boolean result;
	private String reason;
	
	@ManyToOne
	@NotNull
	private TestSession testSession;

	public TestRun() {
	}

	public TestRun(TestCase testCase, Date timeStamp, boolean result, String reason, TestSession testSession) {
		super();
		this.testCase = testCase;
		this.timeStamp = timeStamp;
		this.result = result;
		this.reason = reason;
		this.testSession = testSession;
	}

	public TestRun(UUID id, TestCase testCase, Date timeStamp, boolean result, String reason, TestSession testSession) {
		super();
		this.testCase = testCase;
		this.timeStamp = timeStamp;
		this.result = result;
		this.id = id;
		this.reason = reason;
		this.testSession = testSession;
	}

	public TestSession getTestSession() {
		return testSession;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public void setTestSession(TestSession testSession) {
		this.testSession = testSession;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public UUID getId() {
		return id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}
