package tn.esprit.gnbapp.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, int resourceId) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, resourceId));
    }

}

