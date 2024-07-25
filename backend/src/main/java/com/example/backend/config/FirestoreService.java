package com.example.backend.config;

import com.example.backend.user.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class FirestoreService {
    @Autowired
    private Firestore firestore;

    public void addUser() throws ExecutionException, InterruptedException {
        String uniqueId = UUID.randomUUID().toString();
        User user = new User(uniqueId, "joshua4@test.com", "joshuaTestUser", "JC", "Campbell", "This is a test password");
        CollectionReference usersCollection = firestore.collection("users");
        DocumentReference docRef = usersCollection.document(uniqueId);
        ApiFuture<WriteResult> future = docRef.set(user);
        WriteResult result = future.get();
        System.out.println("Write time: " + result.getUpdateTime());
    }

    public User getUserByEmail(String email) throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("users");


        ApiFuture<QuerySnapshot> query = usersCollection.whereEqualTo("email", email).get();
        QuerySnapshot querySnapshot = query.get();

        if (!querySnapshot.getDocuments().isEmpty()) {
            // Assuming that email is unique and you expect only one result
            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
            return document.toObject(User.class);
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersCollection = firestore.collection("users");
        ApiFuture<QuerySnapshot> future = usersCollection.get();
        QuerySnapshot querySnapshot = future.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        // Convert documents to User objects
        return documents.stream()
                .map(document -> document.toObject(User.class))
                .collect(Collectors.toList());
    }
}
