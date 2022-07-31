package com.sartech.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email
) {
}
