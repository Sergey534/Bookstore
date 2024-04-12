package org.bookstore.integration;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.MountableFile;

public class MyPostgreSQLContainer extends PostgreSQLContainer {

    private static final String INIT_SCRIPT_PATH = "/docker-entrypoint-initdb.d/init.sql";

    public MyPostgreSQLContainer() {
        super();
        this.withCopyFileToContainer(MountableFile.forClasspathResource("init.sql"), INIT_SCRIPT_PATH);
        this.withUsername("test");
        this.withPassword("test");
    }
}