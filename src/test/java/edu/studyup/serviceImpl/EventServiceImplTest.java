package edu.studyup.serviceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.studyup.entity.Event;
import edu.studyup.entity.Location;
import edu.studyup.entity.Student;
import edu.studyup.util.DataStorage;
import edu.studyup.util.StudyUpException;

class EventServiceImplTest {

	EventServiceImpl eventServiceImpl;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventServiceImpl = new EventServiceImpl();
		// Create Student
		Student student = new Student();
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setEmail("JohnDoe@email.com");
		student.setId(1);

		// Create Event1
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Event 1");
		Location location = new Location(-122, 37);
		event.setLocation(location);
		List<Student> eventStudents = new ArrayList<>();
		eventStudents.add(student);
		event.setStudents(eventStudents);

		DataStorage.eventData.put(event.getEventID(), event);
	}

	@AfterEach
	void tearDown() throws Exception {
		DataStorage.eventData.clear();
	}

	@Test
	void testUpdateEventName_GoodCase() throws StudyUpException {
		int eventID = 1;
		eventServiceImpl.updateEventName(eventID, "Renamed Event 1");
		assertEquals("Renamed Event 1", DataStorage.eventData.get(eventID).getName());
	}

	@Test
	void testUpdateEvent_WrongEventID_badCase() {
		int eventID = 3;
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(eventID, "Renamed Event 3");
		});
	}

	@Test
	void eventNameValidityCheck() {
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("A string obviously longer than twenty characters");

		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.updateEventName(1, "A string obviously longer than twenty characters");
		});
	}

	@Test
	void secondEventNameValidityCheck() {
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		event.setName("Doesn't matter");
		try {
			eventServiceImpl.updateEventName(1, "Exactly 20characters");
		} catch (StudyUpException e) {
			assert (false);
		}

	}

	@Test
	void eventStudentLengthValidityCheck() {
		Event event = new Event();
		event.setEventID(1);

		List<Student> students = new ArrayList<Student>();
		Student student = new Student();
		student.setFirstName("Jason");
		student.setLastName("Lee");
		student.setEmail("jsnlee@ucdavis.edu");

		Student student2 = new Student();
		student2.setFirstName("Jason");
		student2.setLastName("Lee");
		student2.setEmail("jsnlee@ucdavis.edu");

		Student student3 = new Student();
		student3.setFirstName("Jason");
		student3.setLastName("Lee");
		student3.setEmail("jsnlee@ucdavis.edu");

		students.add(student);
		students.add(student2);
		students.add(student3);

		event.setStudents(students);
		DataStorage.eventData.put(event.getEventID(), event);

//		assert(event.getStudents().size() <= 2);
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student3, 1);
		});

	}
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> cff6ceea8a4446e65ec671fe98dfae6a2ae6e130
	@Test
	void methodName_event_null_badcases() {
		Event event = null;
		Assertions.assertThrows(StudyUpException.class, () -> {
		eventServiceImpl.updateEvent(event);
	});
	}
	@Test
	void testBadCase() {
		assertEquals(DataStore.eventData.size(),1);
	}

	@Test
	void eventStudentAddValidityCheck() {
		Event event = new Event();
		event.setEventID(1);

		List<Student> students = new ArrayList<Student>();
		Student student = new Student();
		student.setFirstName("Jason");
		student.setLastName("Lee");
		student.setEmail("jsnlee@ucdavis.edu");

		Student student2 = new Student();
		student2.setFirstName("Jason");
		student2.setLastName("Lee");
		student2.setEmail("jsnlee@ucdavis.edu");

		Student student3 = new Student();
		student3.setFirstName("Jason");
		student3.setLastName("Lee");
		student3.setEmail("jsnlee@ucdavis.edu");

		students.add(student);
		students.add(student2);
		students.add(student3);

		event.setStudents(students);
		DataStorage.eventData.put(event.getEventID(), event);

		List<Student> receivedStudents = DataStorage.eventData.get(event.getEventID()).getStudents();
		assert (receivedStudents.size() == 3);
		assert (receivedStudents.get(0).getFirstName().equals("Jason"));

	}

	@Test
	void getActiveEventsConsistencyCheck() {
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		Event event2 = new Event();
		event2.setDate(new Date());
		event2.setEventID(2);
		DataStorage.eventData.put(event.getEventID(), event);
		DataStorage.eventData.put(event2.getEventID(), event2);
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		assert (activeEvents.size() == 2);
	}

	@Test
	void getActiveEventsTimeConsistencyCheck() {
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		Event event2 = new Event();
		event2.setDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
		event2.setEventID(2);
		DataStorage.eventData.put(event.getEventID(), event);
		DataStorage.eventData.put(event2.getEventID(), event2);
		List<Event> activeEvents = eventServiceImpl.getActiveEvents();
		assert (activeEvents.size() == 1);
	}

	@Test
	void getPastEventsConsistencyCheck() {
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000));
		DataStorage.eventData.put(event.getEventID(), event);
		List<Event> pastEvents = eventServiceImpl.getPastEvents();
		assert (pastEvents.size() == 1);
	}

	@Test
	void testEventDeletion() {
		Event event = new Event();
		event.setEventID(1);
		DataStorage.eventData.put(event.getEventID(), event);
		eventServiceImpl.deleteEvent(1);
		assert (DataStorage.eventData.isEmpty());
		
	}

	@Test
	void nullEventCheckForAddStudentToEvent() {
		Student student = new Student();
		student.setFirstName("Jason");
		student.setLastName("Lee");
		student.setEmail("jsnlee@ucdavis.edu");
		
		Assertions.assertThrows(StudyUpException.class, () -> {
			eventServiceImpl.addStudentToEvent(student, 100);
		});
	}
	
	@Test
	void nullStudentListCheckForAddStudentToEvent() {
		Event event = new Event();
		event.setEventID(1);
		event.setDate(new Date());
		
		Student student = new Student();
		student.setFirstName("Jason");
		student.setLastName("Lee");
		student.setEmail("jsnlee@ucdavis.edu");
		
		DataStorage.eventData.put(1, event);
		try {
			eventServiceImpl.addStudentToEvent(student, event.getEventID());
		} catch (StudyUpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert(DataStorage.eventData.get(event.getEventID()).getStudents().size() == 1);
	}
}
