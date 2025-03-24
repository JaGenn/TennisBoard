package org.example.dao;

import org.example.entity.Player;

import java.util.List;
import java.util.Optional;

public interface PlyerDAO {

    void save(Player player);

    Optional<Player> findByName(String name);

//    List<Player> getAll();
}
