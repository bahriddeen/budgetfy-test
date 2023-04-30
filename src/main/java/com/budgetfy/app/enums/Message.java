package com.budgetfy.app.enums;

public enum Message {

    DATA_CREATED("Data has been created"),
    DATA_UPDATED("Data has been updated"),
    DATA_DELETED("Data has been deleted"),
    DATA_NOT_FOUND("Data not found"),
    NO_CONTENT("Empty content"),
    CONFLICT("The process finished with conflict"),
    DATA_ALREADY_EXISTS("Data already exists with this"),

    ACCOUNT_CREATED("Account has been created"),
    ACCOUNT_UPDATED("Account has been updated"),
    ACCOUNT_DELETED("Account has been deleted"),
    ACCOUNT_NOT_FOUND("Account not found"),
    ACCOUNT_ALREADY_EXISTS("Account already exists with this name"),
    TOO_MANY_ACCOUNTS("Sorry, you have reached the maximum number of accounts allowed per user"),

    CATEGORY_CREATED("Category has been created"),
    CATEGORY_UPDATED("Category has been updated"),
    CATEGORY_DELETED("Category has been deleted"),
    CATEGORY_NOT_FOUND("Category not found"),
    CATEGORY_ALREADY_EXISTS("Category already exists with this name"),
    CATEGORY_CONFLICT("You cannot delete and update parent and default category"),

    TEMPLATE_CREATED("Template has been created"),
    TEMPLATE_UPDATED("Template has been updated"),
    TEMPLATE_DELETED("Template has been deleted"),
    TEMPLATE_NOT_FOUND("Template not found"),
    TEMPLATE_ALREADY_EXISTS("Template already exists with this name"),

    TRANSACTION_CREATED("Transaction has been created"),
    TRANSACTION_UPDATED("Transaction has been updated"),
    TRANSACTION_DELETED("Transaction has been deleted"),
    TRANSACTION_NOT_FOUND("Transaction not found"),
    TRANSACTION_ALREADY_EXISTS("Transaction already exists with this name"),

    USER_CREATED("User has been created"),
    USER_UPDATED("User has been updated"),
    USER_DELETED("User has been deleted"),
    USER_NOT_FOUND("User not found"),
    USER_ALREADY_EXISTS("User already exists: ");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

}
