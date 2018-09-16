package uk.co.punishell.insideview.model.services;

import uk.co.punishell.insideview.model.database.entities.Race;

import java.util.Set;

public interface DBPopulator {

    void populate(Set<Race> races);
}
