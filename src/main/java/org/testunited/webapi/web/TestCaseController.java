package org.testunited.webapi.web;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.testunited.webapi.TestCase;
import org.testunited.webapi.TestGroup;
import org.testunited.webapi.TestTarget;
import org.testunited.webapi.services.TestCaseService;

@RestController
@CrossOrigin
public class TestCaseController {

	@Autowired
	TestCaseService testCaseService;

	@GetMapping("/applications/{applicationId}/testcases")
	public List<TestCase> getByApplicationId(@PathVariable UUID applicationId){
		return this.testCaseService.getByApplicationId(applicationId);
	}
	
	@GetMapping("/testtargets/{testTargetId}/testcases")
	public List<TestCase> getByTestTargetId(@PathVariable UUID testTargetId){
		return this.testCaseService.getByTestTargetId(testTargetId);
	}

	@GetMapping("/testtargets/{testTargetId}/testgroups/{testGroupId}/testcases")
	public List<TestCase> getByTestTargetIdAndTesGroupId(@PathVariable UUID testTargetId,
			@PathVariable UUID testGroupId){
		return this.testCaseService.getByTestTargetIdTestGroupId(testTargetId, testGroupId);
	}
	
	@GetMapping("/testcases/{id}")
	public ResponseEntity<TestCase> getById(@PathVariable UUID id){
		var target = this.testCaseService.getById(id);
		if (target == null)
			return new ResponseEntity<TestCase>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<TestCase>(target, HttpStatus.OK);
	}

	@GetMapping("/testcases")
	public List<TestCase> getAll(){
		return this.testCaseService.getAll();
	}
	
	@PostMapping("/testcases")
	@ResponseStatus(HttpStatus.CREATED)
	public TestCase save(@Valid @RequestBody TestCase testCase) {
		this.testCaseService.save(testCase);
		return testCase;
	}
	
	@PostMapping("/testtargets/{testTargetId}/testgroups/{testGroupId}/testcases")
	@ResponseStatus(HttpStatus.CREATED)
	public TestCase add(@PathVariable UUID testTargetId, 
			@PathVariable UUID testGroupId, 
			@Valid @RequestBody TestCase testCase) {
		testCase.setTestGroup(new TestGroup(testGroupId));
		testCase.setTestTarget(new TestTarget(testTargetId));
		this.testCaseService.save(testCase);
		return testCase;
	}
	
	@PutMapping("/testtargets/{testTargetId}/testgroups/{testGroupId}/testcases")
	public TestCase update(@PathVariable UUID testTargetId, 
			@PathVariable UUID testGroupId, 
			@Valid @RequestBody TestCase testCase) {
		testCase.setTestGroup(new TestGroup(testGroupId));
		testCase.setTestTarget(new TestTarget(testTargetId));
		this.testCaseService.save(testCase);
		return testCase;
	}
	
	@PostMapping("/testcases/bulk")
	@ResponseStatus(HttpStatus.CREATED)
	public List<TestCase> save(@RequestBody List<TestCase> testCases) {
		for(TestCase testCase: testCases)
			this.testCaseService.save(testCase);
		return testCases;
	}
}
