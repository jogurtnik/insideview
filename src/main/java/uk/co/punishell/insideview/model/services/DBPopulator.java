package uk.co.punishell.insideview.model.services;

import uk.co.punishell.insideview.model.database.entities.Race;

import java.util.List;

public interface DBPopulator {

    void populate(List<Race> races);
}
