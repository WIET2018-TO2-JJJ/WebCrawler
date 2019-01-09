package WebCrawlerApp.session;

import WebCrawlerApp.model.Result;
import WebCrawlerApp.model.Search;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class SessionService {

    private static SessionFactory sessionFactory = null;
    Session session;
    Transaction tx;

    public SessionService(){
        sessionFactory = getSessionFactory();
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public void save(Search search){
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        search.addResultsToSet();
        for (Result result : search.getResultSet()){
            session.save(result);
        }
        session.save(search.getQuery());
        session.save(search);
        tx.commit();
        session.close();
    }

    public List<Search> getAllSearches(){
        session = sessionFactory.openSession();
        tx = session.beginTransaction();
        List<Search> searches = session.createCriteria(Search.class).list();
        for (Search search : searches){
            search.addResultsToObservableList();
        }
        tx.commit();
        session.close();
        return searches;
    }

}
