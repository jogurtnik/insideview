package uk.co.punishell.insideview.model.services;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;

public interface DBPopulator {

    void populate(File file);
}
