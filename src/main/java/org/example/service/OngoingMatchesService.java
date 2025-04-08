package org.example.service;


import org.example.model.entity.Match;
import org.example.model.entity.Player;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class OngoingMatchesService {

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private final Map<UUID, Match> ongoingMatches;

    private OngoingMatchesService() {
        ongoingMatches = new ConcurrentHashMap<>();
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public void createNewMatch(UUID uuid, Match match) {
        ongoingMatches.put(uuid, match);
    }

    public Match getCurrentMatch(UUID uuid) {
        return ongoingMatches.get(uuid);
    }

    public void deleteMatch(UUID uuid) {
        ongoingMatches.remove(uuid);
    }

}
