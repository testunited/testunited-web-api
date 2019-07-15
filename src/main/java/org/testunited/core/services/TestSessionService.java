package org.testunited.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testunited.core.TestCase;
import org.testunited.core.TestSession;
import org.testunited.core.data.TestCaseRepository;
import org.testunited.core.data.TestSessionRepository;

@Service
public class TestSessionService {

	@Autowired
	private TestSessionRepository testSessionRepo;
	
	public void save(TestSession testSession) {
		this.testSessionRepo.save(testSession);
	}
	
	public TestSession getById(UUID id) {
		return this.testSessionRepo.findById(id).get();
	}
	
	public TestSession getByName(String name) {
		return testSessionRepo.findByName(name);
	}
	
	public List<TestSession> getAll(){
		return (List<TestSession>)this.testSessionRepo.findAll();
	}
	
}
