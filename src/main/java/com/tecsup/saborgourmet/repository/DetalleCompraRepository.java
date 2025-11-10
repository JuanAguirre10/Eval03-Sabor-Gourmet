package com.tecsup.saborgourmet.repository;

import com.tecsup.saborgourmet.model.Compra;
import com.tecsup.saborgourmet.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {
    List<DetalleCompra> findByCompra(Compra compra);
}