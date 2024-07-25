package com.example.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
        import com.google.cloud.firestore.Firestore;
        import com.google.cloud.firestore.FirestoreOptions;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.beans.factory.annotation.Value;

        import java.io.FileInputStream;
        import java.io.IOException;

@Configuration
public class FirestoreConfig {

    @Value("${spring.cloud.gcp.firestore.credentials.location}")
    private String credentialsPath;

    @Value("${spring.cloud.gcp.firestore.project-id}")
    private String projectId;

    @Bean
    public Firestore firestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(credentialsPath);

        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        return firestoreOptions.getService();
    }
}
