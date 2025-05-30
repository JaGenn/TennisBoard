package org.example.dao;

import org.example.model.entity.Player;

import java.util.Optional;

public interface PlayerDAO {

    Player save(Player player);

    Optional<Player> findByName(String name);

}
