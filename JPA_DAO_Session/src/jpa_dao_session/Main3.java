package jpa_dao_session;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Just for showing type conversions of EntityManager to Session and vice-versa.

public class Main3 {
	public static void main(String[] args) {
		testEntityManagerToSession();
		testSessionToEntityManager();
		System.out.println("\nEND OF TEST SCRIPT!!");
	}
	private static void testEntityManagerToSession() {
		EntityManagerFactory emFactory=null;
		EntityManager em=null;
		Session sessionFromEntityManager=null;
		try {
			System.out.println("Creating EntityManagerFactory...");
			emFactory = Persistence.createEntityManagerFactory("JPA_DAO_SessionPU");
			System.out.println("EntityManagerFactory created!");
			System.out.println("Creating EntityManager...");
			em = emFactory.createEntityManager();
			System.out.println("EntityManager created!");
			/*as when commiting from a EntityManager Transaction the API does NOT close the EntityManager (or Session),
			only one EntityManager or Session are needed at this method. The Transaction, though, must be begun after
			each commit.*/ 
			
			System.out.println("Beginning a Transaction through EntityManager...");
			em.getTransaction().begin();
			System.out.println("Transaction started!");
			
	//		for(int i=1;i<=10;i++) {
	//			em.persist(new Student("student"+i));
	//		}
	//		System.out.println(em.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
	//		em.getTransaction().commit();
			
			System.out.println("Unwrapping Session from EntityManager...");
			sessionFromEntityManager = em.unwrap(Session.class);
			System.out.println("Session unwrapped!");
			System.out.println("Saving entity objects with Session...");
			for(int i=1;i<=10;i++) {
				sessionFromEntityManager.save(new Student("student"+i));
			}
			System.out.println("Entity objects saved!");
			
			System.out.println(em.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
			System.out.println("Committing transaction...");
			em.getTransaction().commit();
			//After getting the Session, this EntityManager Transaction can be reused by the Session to save its entity objects to the DB. If an EntityManager was got from a Session, on the contrary, there couldn't be any reuse, and the Session Transaction would have to be finished and another, from the EntityManager itself, obtained for persisting on the DB.
			System.out.println("Committed! Closing current Transaction and NOT the Session or EntityManager...");
			
			System.out.println(em.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
			System.out.println(sessionFromEntityManager.isOpen()?"Session open!":"Session closed!");
			System.out.println(em.isOpen()?"EntityManager open!":"EntityManager closed!");
			System.out.println("Searching ALL Students:");
			em.createQuery("from Student", Student.class).getResultList().forEach(System.out::println);
			//Queries with EntityManager can be done after transaction closes.
			
			System.out.println("Test SUCCESSFULL!");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			System.out.println("Closing all open resources...");
			if(em!=null&&em.isOpen()) {
				em.close();
				System.out.println("EntityManager and associated Session closed!");
			}
			if(sessionFromEntityManager!=null&&sessionFromEntityManager.isOpen()) {
				sessionFromEntityManager.close();
				System.out.println("Session closed!");
			}
			//if EntityManager is closed first, it also closes the associated Session, and vice-versa. 
			if(emFactory!=null&&emFactory.isOpen()) {
				emFactory.close();
				System.out.println("EntityManagerFactory closed!");
			}
			System.out.println("All resources Closed!");
		}
	}
	
	private static void testSessionToEntityManager() {
		SessionFactory sessionFactory=null;
		Session session=null;
		EntityManager emFromSession=null;
		try {
			System.out.println("Creating SessionFactory...");
			sessionFactory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Student.class).buildSessionFactory();
			System.out.println("SessionFactory created!");
			
			System.out.println("Creating new Session...");
			session = sessionFactory.getCurrentSession();
			System.out.println("Session created!");
			/*as when commiting from a Session Transaction the API DOES close the Session (although not the associated EntityManager),
			multiple Sessions are needed at this method, after each commit, as well as Transactions.*/
			
			System.out.println("Beginning a Transaction through Session...");
			session.getTransaction().begin();
			System.out.println("Transaction started!");
			
//			System.out.println("Saving entity objects with Session...");
//			for(int i=11;i<=20;i++) {
//				session.save(new Student("student"+i));
//			}
//			System.out.println("Entity objects saved!");
//			
//			System.out.println(session.isOpen()?"Session open!":"Session closed!");
//			System.out.println(session.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
//			System.out.println("Committing transaction...");
//			session.getTransaction().commit();
//			System.out.println("Committed! Closing current Transaction and Session...");
//			System.out.println(session.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
//			System.out.println(session.isOpen()?"Session open!":"Session closed!");
//			
			System.out.println("Creating EntityManager from Session...");
			emFromSession = session.getEntityManagerFactory().createEntityManager();
			System.out.println("EntityManager created!");
			session.getTransaction().commit(); 
			//After getting the EntityManager, this Session Transaction has to be closed (committed) so that the EntityManager Transaction can begin a connection to the DB, in order to persist its entity objects. Session Transaction would not work to do that, only a Transaction obtained from the EntityManager itself.
			System.out.println("Beginning a Transaction through EntityManager...");
			emFromSession.getTransaction().begin();
			System.out.println("Transaction started!");
			System.out.println("Persisting entity objects with EntityManager...");
			for(int i=11;i<=20;i++) {
				emFromSession.persist(new Student("student"+i));
			}
			System.out.println("Entity objects persisted!");
			
			System.out.println(emFromSession.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
			System.out.println(session.isOpen()?"Session open!":"Session closed!");
			System.out.println((emFromSession!=null&&emFromSession.isOpen())?"EntityManager open!":"EntityManager null or closed!");
			System.out.println("Committing transaction...");
			emFromSession.getTransaction().commit();
			System.out.println("Committed! Closing current Transaction and Session, NOT the associated EntityManager...");
			System.out.println(emFromSession.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
			System.out.println(session.isOpen()?"Session open!":"Session closed!");
			System.out.println((emFromSession!=null&&emFromSession.isOpen())?"EntityManager open!":"EntityManager null or closed!");
			
			System.out.println("Creating new Session...");
			session = sessionFactory.getCurrentSession();
			System.out.println("Session created!");
			
			System.out.println("Beginning a Transaction through Session...");
			session.getTransaction().begin();
			System.out.println("Transaction started!");
			
			System.out.println(session.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
			System.out.println(session.isOpen()?"Session open!":"Session closed!");
			System.out.println((emFromSession!=null&&emFromSession.isOpen())?"EntityManager open!":"EntityManager null or closed!");
			System.out.println("Searching ALL Students:");
			session.createQuery("from Student", Student.class).getResultList().forEach(System.out::println);
			System.out.println("Committing transaction...");
			session.getTransaction().commit();
			System.out.println("Committed! Closing current Transaction and Session...");
			//Queries with Session must be done with an open transaction and with a commit at the end.
			System.out.println(session.getTransaction().isActive()?"Transaction active!":"Transaction NOT active!");
			System.out.println(session.isOpen()?"Session open!":"Session closed!");
			System.out.println((emFromSession!=null&&emFromSession.isOpen())?"EntityManager open!":"EntityManager null or closed!");
			
			System.out.println("Test SUCCESSFULL!");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} finally {
			System.out.println("Closing all open resources...");
			if(session!=null&&session.isOpen()) {
				session.close();
				System.out.println("session closed!");
			}
			if(emFromSession!=null&&emFromSession.isOpen()) {
				emFromSession.close();
				System.out.println("emFromSession closed!");
			}
			if(sessionFactory!=null&&sessionFactory.isOpen()) {
				sessionFactory.close();
				System.out.println("sessionFactory closed!");
			}
			System.out.println("All resources Closed!");
		}
	}
}


/*
 In a Nutshell, both EntityManager as Session can be used, having in mind the differences below (and probably many others):
 
 - Session has more features and is an extension of the JPA specification, though it is made wrapped with the EntityManager Hibernate implementation (and, because of that, can be unwrapped);
 - Session can be got from EntityManager through the unwrap(Session.class) method. Method getDelegate() would also be an old option for that,
 	but is is not generically and may have issues regarding casting, with different demands for each DBMS;
 - EntityManager, on the other hand, can be got from Session through getEntityManagerFactory().createEntityManager() methods;
 - Regarding the Committing to the DB, two things must be pretty clear for avoiding Exceptions:
 	1 - Either using EntityManager or Session as the original source for managing the entities, EACH SHOULD HAVE ITS OWN TRANSACTION when commiting
 	to the DB, while the other's Transaction should be finished. If a Session is unwrapped from an EntityManager, and as the Session is an extension of
 	the EntityManager (IS A), it can reuse the same active Transaction started by the EntityManager to commit. Differently, if an EntityManager (which IS
 	NOT A Session) is created from an EntityManagerFactory got from a Session, any active Transaction, started by the Session, CAN NOT be
 	resused by the EntityManager for it to commit. In this case, after the EntityManager is got from the Session, the Session Transaction, if active, should
 	be closed (committed), in order to the EntityManager can open its own Transaction and Connection to the DB.
 	2 - Other than that, it must be noted that when the commit occurs, in the case of an unwrapped Session reusing an EntityManager Transaction, it closes 
 	only the Transaction, not the Session or EntityManager. On the other hand, when a Session commits using its own Transaction, it closes not only the 
 	Transaction, but also the Session, leaving, in both cases, the EntityManager open. When the EntityManager commits using its own Transaction, it also
 	closes only the Transaction but not itself. These three possibilities may turn pretty annoying to debug and find what the problem is. When the Session
 	happens to close itself after commiting, other Sessions and Transactions should be got after each commit. So it should always be in hand to check 
 	before and after each commit the state of each main object dealing with the entities.
 - Regarding the Queries, it must be clear that Queries executed by an EntityManager do not need an open Transaction, nor any commiting right after. 
 	Differently from Queries executed by a Session, which needs both.
 - XML config files, in the case of JPA EntityManagerFactory or Hibernate SessionFactory, are obviously different, as well as the place where to put them,
    so that the API can find them. For console apps, the Persistence.xml should be put at the source package of the project, while the Hibernate.cfg.xml
    should be put at the META-INF folder.
 - Annotations must be imported correctly, as most of them are available both in the JPA native spec, implemented by the Hibernate (javax.persistence.*), 
 	as also in the Hibernate customized and extended spec, and the same Annotation might have, for example, different attributes or features. 
 */
