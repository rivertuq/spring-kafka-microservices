package ru.ostritsov.payment.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ostritsov.payment.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
