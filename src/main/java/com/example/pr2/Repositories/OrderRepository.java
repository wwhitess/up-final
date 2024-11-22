package com.example.pr2.Repositories;

import com.example.pr2.Model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends GenericRepository<Order, Long> {
}
