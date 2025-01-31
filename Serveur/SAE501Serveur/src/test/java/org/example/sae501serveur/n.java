package org.example.sae501serveur;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class PostgresTestcontainersTest {

    // Configuration du conteneur PostgreSQL
    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15.4")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @Autowired
    private DataSource dataSource;

    @Test
    void testDatabaseConnection() throws Exception {
        // Vérifie si le conteneur fonctionne
        assertThat(postgres.isRunning()).isTrue();

        // Vérifie si une connexion valide peut être établie
        assertThat(dataSource.getConnection().isValid(1)).isTrue();
    }
}
