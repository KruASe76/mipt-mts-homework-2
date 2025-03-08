package me.kruase.mipt.db.course;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.db.course.exceptions.CourseNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import java.util.Objects;

@Repository
@Slf4j
public class CourseRepositoryImpl implements CourseRepository {
    private @NotNull Course dummyCourse = new Course(0L, "Advanced Java", 777, 0L);

    @Override
    public @NotNull Course getById(@NotNull Long id) {
        log.info("Get course by id = {}", id);
        if (!id.equals(dummyCourse.id())) {
            throw new CourseNotFoundException(id);
        }
        return dummyCourse;
    }

    @Override
    public @NotNull Course create(@NotNull Course course) {
        log.info("Create course = {}", course);
        dummyCourse = course.withId(dummyCourse.id());
        return dummyCourse;
    }

    @Override
    public void update(@NotNull Course course) {
        log.info("Update course = {}", course);
        if (Objects.equals(course.id(), dummyCourse.id())) {
            dummyCourse = course;
        } else {
            throw new CourseNotFoundException(course.id());
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
            throw new CourseNotFoundException(id);
        }
    }
}

