package no.hvl.dat250.jpa.polls;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo.Poll;
import com.example.demo.User;
import com.example.demo.VoteOption;
import com.example.demo.Vote;


public class PollsTest {

    private EntityManagerFactory emf;


    private void populate(EntityManager em) {
        User alice = new User("alice", "alice@online.com");
        User bob = new User("bob", "bob@bob.home");
        User eve = new User("eve", "eve@mail.org");
        em.persist(alice);
        em.persist(bob);
        em.persist(eve);

        List<VoteOption> voteOptions1 = new ArrayList();
        VoteOption voteOption = new VoteOption("Vim", 1);
        voteOptions1.add(voteOption);
        VoteOption voteOption2 = new VoteOption("Emacs", 2);
        voteOptions1.add(voteOption2);
        Poll poll = new Poll(1,"Vim or Emacs?", voteOptions1, "alice");
        voteOption.setPoll(poll);
        voteOption2.setPoll(poll);

        List<VoteOption> voteOptions2 = new ArrayList();
        VoteOption voteOption3 = new VoteOption("Yes! Yammy!", 1);
        voteOptions2.add(voteOption3);
        VoteOption voteOption4 = new VoteOption("Mamma mia: Nooooo!", 2);
        voteOptions2.add(voteOption4);
        Poll poll2 = new Poll(2,"Pineapple on Pizza", voteOptions2, "bob");
        voteOption3.setPoll(poll2);
        voteOption4.setPoll(poll2);

        em.persist(poll);
        em.persist(poll2);

        Vote vote = new Vote(voteOption,"alice", 1);
        em.persist(vote);

        Vote vote2 = new Vote(voteOption,"bob", 1);
        em.persist(vote2);

        Vote vote3 = new Vote(voteOption2,"eve", 1);
        em.persist(vote3);

        Vote vote4 = new Vote(voteOption4,"eve", 1);
        em.persist(vote4);
    }

    @BeforeEach
    public void setUp() {
        EntityManagerFactory emf = new PersistenceConfiguration("polls")
                .managedClass(Poll.class)
                .managedClass(User.class)
                .managedClass(Vote.class)
                .managedClass(VoteOption.class)
                .property(PersistenceConfiguration.JDBC_URL, "jdbc:h2:mem:polls")
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, "drop-and-create")
                .property(PersistenceConfiguration.JDBC_USER, "sa")
                .property(PersistenceConfiguration.JDBC_PASSWORD, "")
                .createEntityManagerFactory();
        emf.runInTransaction(em -> {
            populate(em);
        });
        this.emf = emf;
    }

    @Test
    public void testUsers() {
        emf.runInTransaction(em -> {
            Integer actual = (Integer) em.createNativeQuery("select count(username) from users", Integer.class).getSingleResult();
            assertEquals(3, actual);
            System.out.println(actual + " FOUND USERS");

            User maybeBob = em.createQuery("select u from User u where u.username like 'bob'", User.class).getSingleResultOrNull();
            assertNotNull(maybeBob);
            System.out.println(maybeBob.getUsername() + " FOUND BOB?");

        });
    }

    @Test
    public void testVotes() {
        emf.runInTransaction(em -> {
            Long vimVotes = em.createQuery("select count(v) from Vote v where v.username =:username", Long.class).setParameter("username", "alice").getSingleResult();
            System.out.println(vimVotes + " FOUND VOTES");
            assertEquals(1, vimVotes);
        });
    }

    @Test
    public void testOptions() {
        emf.runInTransaction(em -> {
            List<String> poll2Options = em.createQuery("select o.caption from Poll p join p.options o ", String.class).getResultList();
            System.out.println(poll2Options + " FOUND CAPTIONS");
            List<String> expected = Arrays.asList("Vim", "Emacs", "Yes! Yammy!", "Mamma mia: Nooooo!");
            assertEquals(expected, poll2Options);

        });
    }
}