package uk.co.punishell.insideview.model.database.services;


import uk.co.punishell.insideview.view.commands.guiCommands.Criteria;
import uk.co.punishell.insideview.view.commands.guiCommands.SearchResult;

public interface SearchEngine<T extends Criteria, E extends SearchResult> {

    E search(T criteria);
}
