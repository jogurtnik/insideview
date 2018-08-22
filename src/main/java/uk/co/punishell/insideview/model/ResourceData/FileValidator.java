package uk.co.punishell.insideview.model.ResourceData;

import java.io.File;

public interface FileValidator {

    boolean isValidFile(File file, DataFormat dataFormat);

}
