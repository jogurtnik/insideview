package uk.co.punishell.insideview.model.services.poi;

import org.apache.poi.ss.usermodel.Row;
import uk.co.punishell.insideview.model.database.entities.Race;

import java.util.List;

public interface RowProcessor {

    Race processRow(Row row);
}
