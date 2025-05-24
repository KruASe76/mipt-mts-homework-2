package me.kruase.mipt.services;

import lombok.RequiredArgsConstructor;
import me.kruase.mipt.api.models.request.UniversityCreateRequest;
import me.kruase.mipt.api.models.request.UniversityPatchRequest;
import me.kruase.mipt.api.models.request.UniversityUpdateRequest;
import me.kruase.mipt.db.course.Course;
import me.kruase.mipt.db.university.University;
import me.kruase.mipt.db.university.UniversityRepository;
import me.kruase.mipt.db.university.exceptions.UniversityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final @NotNull UniversityRepository repository;

    private final Set<Long> deletedIds = ConcurrentHashMap.newKeySet();

    @Transactional(readOnly = true)
    public @NotNull University getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UniversityNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public @NotNull University getRichById(Long id) {
        University university = getById(id);
        List<Course> ignored = university.getCourses();

        return university;
    }

    @Transactional
    public @NotNull University create(UniversityCreateRequest request) {
        University university = new University(request.name(), request.location());
        repository.save(university);

        return getRichById(university.getId());
    }

    @Transactional
    public void update(UniversityUpdateRequest request) {
        University university = getById(request.id());

        university.setName(request.name());
        university.setLocation(request.location());

        repository.save(university);
    }

    @Transactional
    public void partialUpdate(UniversityPatchRequest request) {
        University university = getById(request.id());

        university.setName(request.nameOptional().orElse(university.getName()));
        university.setLocation(request.locationOptional().orElse(university.getLocation()));

        repository.save(university);
    }

    // let's say that university ids are globally unique to avoid confusion,
    // so each id can be deleted only once
    @Transactional
    public void delete(Long id) {
        if (!deletedIds.add(id)) {
            return;
        }

        if (!repository.existsById(id)) {
            throw new UniversityNotFoundException(id);
        }

        repository.deleteById(id);
    }
}
