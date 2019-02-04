package uk.co.punishell.insideview.model.ResourceData;

import java.io.File;
import java.io.IOException;

public interface FileValidator {

    boolean isValidFile(File file) throws IOException;

}
