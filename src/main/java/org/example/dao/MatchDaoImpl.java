package org.example.dao;

import org.example.exception.DataBaseOperationException;
import org.example.exception.PlayerAlreadyExistsException;
import org.example.model.entity.Match;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

public class MatchDaoImpl implements MatchDAO {

    @Override
    public void save(Match match) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(match);
            System.out.println("Матч с id " + match.getId() + " сохранен в бд - дао");
            session.getTransaction().commit();
        } catch (HibernateError e) {
            throw new DataBaseOperationException(e.getMessage());
        }
    }
}
