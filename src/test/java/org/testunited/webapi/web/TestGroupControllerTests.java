package org.testunited.webapi.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testunited.webapi.TestGroup;
import org.testunited.webapi.services.TestGroupService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Tag("microservice:booktitle")
public class TestGroupControllerTests {

	private MockMvc mockMvc;

	private final String jsonSingleTestGroupGood = 
			"{\n" + 
			"    \"id\": \"672124b6-9894-11e5-be38-001d42e813fe\",\n" + 
			"    \"name\": \"my_test_group_1\",\n"+ 
			"    \"application\": {\"id\":\"672124b6-9894-11e5-be38-001d42e813fe\"}\n"+ 
			"}";

	private final String jsonSingleTestGroupBad = 
			"{\n" + 
			"    \"id\": \"672124b6-9894-11e5-be38-001d42e813fe\",\n" + 
			"    \"name\"asdfaf \"my_test_group_2\"\n"+ 
			"}";

	private final TestGroup testGroup1 = new TestGroup(UUID.fromString("672124b6-9894-11e5-be38-001d42e813fe"), "my_test_group_1", UUID.fromString("672124b6-9894-11e5-be38-001d42e813f1"));
	private final TestGroup testGroup2 = new TestGroup(UUID.fromString("672124b6-9894-11e5-be38-001d42e813f3"), "my_test_group_2", UUID.fromString("672124b6-9894-11e5-be38-001d42e813f1"));

	private final ArrayList<TestGroup> testTargetList = new ArrayList<TestGroup>();

	@InjectMocks
	private TestGroupController controller;

	@Mock
	private TestGroupService serviceMock;

	@BeforeAll
	public void setUp() throws Exception {
		mockMvc = standaloneSetup(controller).build();

	}


	@Test
	public void testGetById() throws Exception {
		when(serviceMock.getById(UUID.fromString("672124b6-9894-11e5-be38-001d42e813fe"))).thenReturn(this.testGroup1);

		this.mockMvc.perform(get("/testgroups/672124b6-9894-11e5-be38-001d42e813fe").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", Matchers.is("672124b6-9894-11e5-be38-001d42e813fe")))
				.andExpect(jsonPath("$.name", Matchers.is("my_test_group_1")));
	}
	
	@Test
	public void testGetById_nonExistantId() throws Exception {
		when(serviceMock.getById(UUID.fromString("672124b6-9894-11e5-be38-001d42e813fe"))).thenReturn(null);

		this.mockMvc.perform(get("/testgroups/672124b6-9894-11e5-be38-001d42e813fe").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetAll() throws Exception {

		this.testTargetList.add(this.testGroup1);
		this.testTargetList.add(this.testGroup2);

		when(serviceMock.getAll()).thenReturn(this.testTargetList);

		this.mockMvc.perform(get("/testgroups").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*", Matchers.hasSize(2)));
	}

	@Test
	public void testAdd() throws Exception {
				
		this.mockMvc
				.perform(post("/testgroups").content(this.jsonSingleTestGroupGood).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testUpdate() throws Exception {
			
		this.mockMvc
				.perform(put("/testgroups").content(this.jsonSingleTestGroupGood).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

//	@Test
//	@Tag("method:GET")
//	@Tag("route:/booktitles")
//	@Tag(TestTags.GROUP_REQUEST_VALIDATION)
//	@Tag("case:bad_json")
//	@DisplayName("Validate status code when bad JSON is sent")
//	public void testAddBadRequestBadJson() throws Exception {
//		this.mockMvc
//				.perform(post("/booktitles").content(jsonSingleBookTitleBad).contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isBadRequest());
//	}
//
//	@Test
//	public void testAddBadRequestNotJson() throws Exception {
//		this.mockMvc.perform(post("/booktitles").content("NotJason").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isBadRequest());
//	}

}
