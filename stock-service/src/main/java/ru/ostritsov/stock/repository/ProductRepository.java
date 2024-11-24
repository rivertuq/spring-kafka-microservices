package ru.ostritsov.stock.repository;

import org.springframework.data.repository.CrudRepository;
import ru.ostritsov.stock.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
