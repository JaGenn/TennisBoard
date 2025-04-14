package org.example.dao;

import org.example.model.entity.Player;
import org.example.exception.DataBaseOperationException;
import org.example.exception.NotFoundException;
import org.example.exception.PlayerAlreadyExistsException;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Optional;


public class PlayerDaoImpl implements PlyerDAO {

    @Override
    public Player save(Player player) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            throw new PlayerAlreadyExistsException("Player with id " + player.getId() + " already exists in database");
        } catch (HibernateError e) {
            throw new DataBaseOperationException("Failed to save player with name " + player.getName() + " to database");
        }
        return player;
    }

    @Override
    public Optional<Player> findByName(String name) {
        String query = "FROM Player WHERE name = :name";
        try (Session session = HibernateUtil.getSession()) {
            return session.createQuery(query, Player.class)
                    .setParameter("name", name).uniqueResultOptional();

        } catch (HibernateException e) {
            throw new NotFoundException("There is no player with name " + name + " in database");
        }
    }


}
