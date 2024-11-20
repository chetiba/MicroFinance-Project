package tn.esprit.gnbapp.controllers;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) { super (message);
    }
}

