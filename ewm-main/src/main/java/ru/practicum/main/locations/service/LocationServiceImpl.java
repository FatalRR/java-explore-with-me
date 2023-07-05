package ru.practicum.main.locations.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.locations.model.Location;
import ru.practicum.main.locations.repository.LocationRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository repository;

    @Override
    public Location save(Location location) {
        return repository.save(location);
    }
}