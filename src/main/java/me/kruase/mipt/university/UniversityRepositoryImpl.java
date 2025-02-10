package me.kruase.mipt.university;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.university.exceptions.UniversityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
@Slf4j
public class UniversityRepositoryImpl implements UniversityRepository {
    private @NotNull University dummyUniversity = new University(0L, "MIPT", "Dolgoprudny");

    @Override
    public University getById(@NotNull Long id) {
        log.info("Get university by id = {}", id);
        if (!id.equals(dummyUniversity.id())) {
            throw new UniversityNotFoundException(id);
        }
        return dummyUniversity;
    }

    @Override
    public University create(@NotNull University university) {
        log.info("Create university = {}", university);
        dummyUniversity = university.withId(dummyUniversity.id());
        return dummyUniversity;
    }

    @Override
    public void update(@NotNull University university) {
        log.info("Update university = {}", university);
        if (Objects.equals(university.id(), dummyUniversity.id())) {
            dummyUniversity = university;
        } else {
            throw new UniversityNotFoundException(university.id());
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        log.info("Delete university by id = {}", id);
        if (id.equals(dummyUniversity.id())) {
            dummyUniversity = dummyUniversity
                    .withName("")
                    .withLocation("");
        } else {
            throw new UniversityNotFoundException(id);
        }
    }
}
