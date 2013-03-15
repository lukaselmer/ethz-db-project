package ch.ethz.inf.dbproject.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import ch.ethz.inf.dbproject.model.DatastoreInterface;
import ch.ethz.inf.dbproject.model.Project;

public class DatastoreInterfaceTest {

	@Test
	public void testGetProjectById() {
		DatastoreInterface ds = new DatastoreInterface();
		Project p = ds.getProjectById(2);
		assertEquals("ETH", p.getName());
		assertEquals(2, p.getId());
	}

	@Test
	public void testGetAllProjects() {
		DatastoreInterface ds = new DatastoreInterface();
		List<Project> projects = ds.getAllProjects();
		assertEquals("ETH", projects.get(1).getName());
		assertEquals(4, projects.size());
	}

}
