package me.kruase.mipt.universitycourse;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.universitycourse.exceptions.UniversityCourseNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
@Slf4j
public class UniversityCourseRepositoryImpl implements UniversityCourseRepository {
    private @NotNull UniversityCourse dummyCourse = new UniversityCourse(0L, "Advanced Java", 4, 0L);

    @Override
    public UniversityCourse getById(@NotNull Long id) {
        log.info("Get course by id = {}", id);
        if (!id.equals(dummyCourse.id())) {
            throw new UniversityCourseNotFoundException(id);
        }
        return dummyCourse;
    }

    @Override
    public void create(@NotNull UniversityCourse course) {
        log.info("Create course = {}", course);
        dummyCourse = course.withId(dummyCourse.id());
    }

    @Override
    public void update(@NotNull UniversityCourse course) {
        log.info("Update course = {}", course);
        if (Objects.equals(course.id(), dummyCourse.id())) {
            dummyCourse = course;
        } else {
            throw new UniversityCourseNotFoundException(course.id());
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        log.info("Delete course by id = {}", id);
        if (id.equals(dummyCourse.id())) {
            dummyCourse = dummyCourse
                    .withName("")
                    .withCredits(0)
                    .withUniversityId(0L);
        } else {
            throw new UniversityCourseNotFoundException(id);
        }
    }
}
