package com.sartech.customer;

import com.sartech.clients.fraud.FraudCheckResponse;
import com.sartech.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        //todo: check if email is valid
        //todo: check if email not taken
        //todo: check if fraudster
        customerRepository.saveAndFlush(customer);
         /*
        Rest template setup
         */
//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
//                "http://FRAUD:8081/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        /*
        Now we can use Open feign way
         */
        FraudCheckResponse fraudCheckResponse =
                fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudulent()) {
            throw new IllegalStateException("fraudulent");
        }
        //todo: store customer in db
//        customerRepository.save(customer);
        //todo: to send notification

    }
}

