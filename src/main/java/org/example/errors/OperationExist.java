package org.example.errors;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OperationExist extends OperationDoesntExist {

    public OperationExist(String message, UUID operationId) {
        super(message, operationId);
    }
}
