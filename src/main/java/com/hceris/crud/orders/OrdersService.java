package com.hceris.crud.orders;

import com.hceris.crud.orders.form.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
class OrdersService {
    private OrdersRepository ordersRepository;

    OrdersService(@Autowired OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    Iterable<Order> findAllByEmail(@NonNull String email) {
        return ordersRepository.findAllByEmail(email);
    }

    Order save(Form form) {
        return ordersRepository.save(form.asOrder());
    }
}
