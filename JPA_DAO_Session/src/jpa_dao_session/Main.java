package jpa_dao_session;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Must create the scheme1 and table student at the DB before executing the test code below! Unless xml is changed.

public class Main {
	private static SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
	public static void main(String[] args) {
		try {
			testTruncateTable(); System.out.println();
			testDAOInsert(); System.out.println();
			testDAOSearch1(); System.out.println();
			testDAOSearch2(); System.out.println();
			testDAOSearch3(); System.out.println();
			testDAOSearch4(); System.out.println();
			testDAOUpdate1(); System.out.println();
			testDAOUpdate2(); System.out.println();
			testDAOUpdate3(); testDAOSearch4(); System.out.println();
			testDAODelete1(); System.out.println();
			testDAODelete2(); System.out.println();
			testDAODelete3(); System.out.println();
			testDAODelete4(); System.out.println();
			testDAODelete5(); System.out.println();
			testDAOSearch4(); System.out.println();
			testTruncateTable();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			factory.close();
		}
	}
	private static void testDAOInsert() {
		Student student = new Student("student1");
		System.out.println("Inserting unmanaged "+student);
		boolean result = DAO.insertStudent(student, factory);
		if(result) {
			System.out.print("Insertion successful! Student now in managed state: ");
			System.out.println(student);
		}
		
		Student student2 = new Student("student2");
		System.out.println("Inserting unmanaged "+student2);
		boolean result2 = DAO.insertStudent(student2, factory);
		if(result2) {
			System.out.print("Insertion successfully! Student now in managed state: ");
			System.out.println(student2);
		}
		
		Student student3 = new Student("student3");
		System.out.println("Inserting unmanaged "+student3);
		boolean result3 = DAO.insertStudent(student3, factory);
		if(result3) {
			System.out.print("Insertion successfully! Student now in managed state: ");
			System.out.println(student3);
		}
	}
	private static void testDAOSearch1() {
		Long studentId = 1L;
		System.out.println("Searching unmanaged existent student. Id="+studentId+".");
		Student student = DAO.searchStudent(studentId, factory);
		if(student!=null) {
			System.out.print("Student found! And is now in managed state: ");
			System.out.println(student);
		} else {
			System.out.println("Student not found!");
		}
		
		Long studentId2 = 2L;
		System.out.println("Searching unmanaged existent student. Id="+studentId2+".");
		Student student2 = DAO.searchStudent(studentId2, factory);
		if(student2!=null) {
			System.out.print("Student found! And is now in managed state: ");
			System.out.println(student2);
		} else {
			System.out.println("Student not found!");
		}
	}
	private static void testDAOSearch2() {
		Long studentId = 1000L;
		System.out.println("Searching unmanaged inexistent student. Id="+studentId+".");
		Student student = DAO.searchStudent(studentId, factory);
		if(student!=null) {
			System.out.print("Student found! And is now in managed state: ");
			System.out.println(student);
		} else {
			System.out.println("Student not found!");
		}
	}
	private static void testDAOSearch3() {
		String studentName = "ude";
		System.out.println("Searching ALL students with partial or total names = '"+studentName+"'.");
		List<Student> studentList = DAO.searchStudents(studentName, factory);
		if(studentList!=null&&studentList.size()>0) {
			System.out.println("Student(s) found ("+studentList.size()+")!");
			studentList.forEach(System.out::println);
		} else {
			System.out.println("No Student found for that name!");
		}
	}
	private static void testDAOSearch4() {
		System.out.println("Searching ALL students in the DB.");
		List<Student> studentList = DAO.searchAllStudents(factory);
		if(studentList!=null&&studentList.size()>0) {
			System.out.println("Student(s) found ("+studentList.size()+")!");
			studentList.forEach(System.out::println);
		} else {
			System.out.println("No Student(s) found in the DB!");
		}
	}
	private static void testDAOUpdate1() {
		Student mockOldStudent = new Student("doesn't matter the other attributes, only the id!"); mockOldStudent.setId(3L);
		Student mockNewStudent = new Student("student300");
		System.out.println("Updating existing student registry from reference="+mockOldStudent+" to "+mockNewStudent+".");
		boolean result = DAO.updateStudent(mockOldStudent, mockNewStudent,factory);
		if(result) {
			System.out.println("Student found and updated!");
		} else {
			System.out.println("Target Student entity does not exist in the DB! Get a valid Id, and try again!");
		}
	}
	private static void testDAOUpdate2() {
		Student mockOldStudent = new Student("doesn't matter the other attributes, only the id!"); mockOldStudent.setId(30000000L);
		Student mockNewStudent = new Student("student600");
		System.out.println("Updating non-existing student registry from reference="+mockOldStudent+" to "+mockNewStudent+".");
		boolean result = DAO.updateStudent(mockOldStudent, mockNewStudent,factory);
		if(result) {
			System.out.println("Student found and updated!");
		} else {
			System.out.println("Target Student entity does not exist in the DB! Get a valid Id, and try again!");
		}
	}
	private static void testDAOUpdate3() {
		String oldName = "t1";
		String newName = "noOne";
		System.out.println("Updating existing student registries of partial or total name '"+oldName+"' to '"+newName+"'.");
		int affectedRows = DAO.updateStudentBulk(oldName, newName, factory);
		if(affectedRows>0) {
			System.out.println("Student(s) found and updated ("+affectedRows+")!");
		} else {
			System.out.println("Target Student entity(ies) does(do) not exist in the DB! Get a valid name, and try again!");
		}
	}
	private static void testDAODelete1() {
		Long studentId = 1L;
		System.out.println("Deleting existing student registry from Id="+studentId+".");
		boolean result = DAO.deleteStudent(studentId, factory);
		if(result) {
			System.out.println("Student found and deleted!");
		} else {
			System.out.println("Student entity does not exist in the DB! Get a valid Id, and try again!");
		}
	}
	private static void testDAODelete2() {
		Long studentId = 1000000L;
		System.out.println("Deleting inexisting student registry from Id="+studentId+".");
		boolean result = DAO.deleteStudent(studentId, factory);
		if(result) {
			System.out.println("Student found and deleted!");
		} else {
			System.out.println("Student entity does not exist in the DB! Get a valid Id, and try again!");
		}
	}
	private static void testDAODelete3() {
		Student student = new Student("doesn't matter the other attributes, only the id!"); student.setId(20000000L);
		System.out.println("Deleting non-existing student registry from reference="+student+".");
		boolean result = DAO.deleteStudent(student, factory);
		if(result) {
			System.out.println("Student found and deleted!");
		} else {
			System.out.println("Student entity does not exist in the DB! Get a valid Id, and try again!");
		}
	}
	private static void testDAODelete4() {
		Student student = new Student("doesn't matter the other attributes, only the id!"); student.setId(2L);
		System.out.println("Deleting existing student registry from reference="+student+".");
		boolean result = DAO.deleteStudent(student, factory);
		if(result) {
			System.out.println("Student found and deleted!");
		} else {
			System.out.println("Student entity does not exist in the DB! Get a valid Id, and try again!");
		}
	}
	private static void testDAODelete5() {
		String studentName = "";
		System.out.println("Deleting existing student registries with name='"+studentName+"'.");
		boolean result = DAO.deleteStudentBulk(studentName, factory);
		if(result) {
			System.out.println("Student(s) found and deleted!");
		} else {
			System.out.println("No Student(s) found for that name!");
		}
	}
	private static void testTruncateTable() {
		String fullyQualifiedTableName="scheme1.student";
		boolean result = DAO.truncateDBTable(fullyQualifiedTableName, factory);
		if(result) {
			System.out.println("Table "+fullyQualifiedTableName+" truncated!");
		} else {
			System.out.println("Table "+fullyQualifiedTableName+" NOT truncated!");
		}
	}
}
