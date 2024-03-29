package org.testunited.webapi.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.testunited.webapi.*;

public interface TestCaseRepository extends CrudRepository<TestCase, UUID> {

	public List<TestCase> findByTestTargetId(UUID testTargetId);
	public List<TestCase> findByApplicationId(UUID applicationId);
	public List<TestCase> findByTestTargetIdAndTestGroupId(UUID testTargetId, UUID testGroupId);
	public TestCase findByTestSourceId(String testSourceId);
}
