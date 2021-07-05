package com.groupten.statemachine.jsonimport;

import java.io.IOException;

public interface IJSONImport {

    boolean importJSONData(String path) throws IOException;

    boolean instantiateJSONData();

}
