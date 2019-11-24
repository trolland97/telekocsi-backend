package com.telekocsi.controller;

import com.telekocsi.exception.ResourceNotFoundException;
import com.telekocsi.model.Route;
import com.telekocsi.model.User;
import com.telekocsi.repository.RouteRepository;
import com.telekocsi.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/routes")
@CrossOrigin(origins = "http://localhost:4200")
public class RouteController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    ResponseEntity<List<Route>> listRoutes() {
        return ResponseEntity.ok(routeRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Optional<Route>> getRoute(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(routeRepository.findById(id));
    }

    @GetMapping("/driver/{id}")
    ResponseEntity<List<Route>> getRoutesByDriverId(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(routeRepository.findByDriver_Id(id));
    }

    @GetMapping("/passenger/{id}")
    ResponseEntity<List<Route>> getRoutesByPassengerId(@PathVariable("id") Long id) throws Exception {
        return ResponseEntity.ok(routeRepository.findByPassengers_Id(id));
    }

    @PostMapping("/{driverId}")
    ResponseEntity<Route> saveRoute(@PathVariable("driverId") Long id, @RequestBody Route route) throws Exception {
        Optional<User> driver = userRepository.findById(id);
        if(!driver.isPresent()) throw new ResourceNotFoundException("peki");
        Route newRoute = new Route();
        newRoute.setFrom(route.getFrom());
        newRoute.setMaxPassengers(route.getMaxPassengers());
        newRoute.setWhen(route.getWhen());
        newRoute.setWhere(route.getWhere());
        newRoute.setPrice(route.getPrice());
        newRoute.setDriver(driver.get());
        routeRepository.save(newRoute);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRoute);
    }

    @PutMapping("/{id}")
    ResponseEntity<Route> updateRoute(@PathVariable("id") Long id, @RequestBody Route route) throws Exception {
        return routeRepository.findById(id).map(r -> {
            r.setFrom(route.getFrom());
            r.setMaxPassengers(route.getMaxPassengers());
            r.setWhen(route.getWhen());
            r.setWhere(route.getWhere());
            r.setPassengers(route.getPassengers());
            r.setDriver(route.getDriver());
            r.setPrice(route.getPrice());
            routeRepository.save(r);
            return ResponseEntity.ok(r);
        }).orElseThrow(() -> new ResourceNotFoundException("pekmen szidd ki"));
    }

    @PutMapping("/{routeId}/passenger/{passengerId}")
    ResponseEntity<Route> addPassenger(@PathVariable("routeId") Long routeId, @PathVariable("passengerId") Long passengerId) throws Exception {
        Optional<Route> route = routeRepository.findById(routeId);
        Optional<User> passenger = userRepository.findById(passengerId);
        if(!route.isPresent() || !passenger.isPresent()) throw new ResourceNotFoundException("pek");
        Route r = route.get();
        List<User> passengers = r.getPassengers();
        if(passengers.contains(passenger.get())) {
            throw new Exception("mar jelentkezett");
        }
        if(passengers.size() < r.getMaxPassengers()) {
            passengers.add(passenger.get());
            r.setPassengers(passengers);
            routeRepository.save(r);
        }
        else {
            throw new Exception("tele a fuvar");
        }

        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{routeId}/passenger/{passengerId}")
    ResponseEntity<Route> removePassenger(@PathVariable("routeId") Long routeId, @PathVariable("passengerId") Long passengerId) throws Exception {
        Optional<Route> route = routeRepository.findById(routeId);
        Optional<User> passenger = userRepository.findById(passengerId);
        if(!route.isPresent() || !passenger.isPresent()) throw new ResourceNotFoundException("pek");
        Route r = route.get();
        List<User> passengers = r.getPassengers();
        if(!passengers.contains(passenger.get())) {
            throw new Exception("meg nem jelentkezett");
        }
        passengers.remove(passenger.get());
        routeRepository.save(r);

        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteRoute(@PathVariable("id") Long id) {
        routeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
