package com.telekocsi.repository;

import com.telekocsi.model.Route;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

  List<Route> findByDriver_Id(Long id);
  List<Route> findByPassengers_Id(Long id);
}
