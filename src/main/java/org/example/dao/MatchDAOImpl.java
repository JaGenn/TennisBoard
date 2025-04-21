package org.example.dao;

import org.example.exception.DataBaseOperationException;
import org.example.model.entity.Match;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateError;
import org.hibernate.Session;


import java.util.List;

public class MatchDAOImpl implements MatchDAO {

    @Override
    public Match save(Match match) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(match);
            session.getTransaction().commit();
        } catch (HibernateError e) {
            throw new DataBaseOperationException("Failed to save match to database");
        }
        return match;
    }


    @Override
    public List<Match> getMatches(int page, int size) {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("from Match", Match.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } catch (HibernateError e) {
            throw new DataBaseOperationException("Failed to get matches from database");
        }
    }

    @Override
    public Long getTotalMatches() {
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery("select count(*) from Match", Long.class)
                    .getSingleResult();
        } catch (HibernateError e) {
            throw new DataBaseOperationException("Failed to get total matches from database");
        }
    }

    @Override
    public List<Match> getMatches(int page, int size, String playerName) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "FROM Match m WHERE (player1.name = :playerName OR player2.name = :playerName)";
            return session.createQuery(hql, Match.class)
                    .setParameter("playerName", playerName)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size).getResultList();
        } catch (HibernateError e) {
            throw new DataBaseOperationException("Failed to get matches from database");
        }
    }

    @Override
    public Long getTotalMatches(String playerName) {
        try (Session session = HibernateUtil.getSession()) {
            String hql = "select count(*) from Match where (player1.name = :playerName OR player2.name = :playerName)";
            return session.createQuery(hql, Long.class)
                    .setParameter("playerName", playerName)
                    .getSingleResult();
        } catch (HibernateError e) {
            throw new DataBaseOperationException("Failed to get total matches from database");
        }
    }
}
