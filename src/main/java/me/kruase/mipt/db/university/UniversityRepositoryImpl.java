package me.kruase.mipt.db.university;

import lombok.extern.slf4j.Slf4j;
import me.kruase.mipt.db.university.exceptions.UniversityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Repository
@Slf4j
public class UniversityRepositoryImpl implements UniversityRepository {
    private @NotNull University dummyUniversity = new University(0L, "MIPT", "Dolgoprudny");

    private static final Pattern titlePattern = Pattern.compile("(?<=<title>)(.*?)(?=</title>)");

    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient = WebClient.create();

    @Override
    public @NotNull University getById(@NotNull Long id) {
        log.info("Getting random url (RestTemplate)");
        String page = restTemplate.getForObject("https://kruase.vercel.app", String.class);
        if (page != null) {
            Matcher matcher = titlePattern.matcher(page);
            if (matcher.find()) {
                log.info("RestTemplate got page with title '{}'", matcher.group());
            }
        }

        log.info("Getting university by id = {}", id);
        if (!id.equals(dummyUniversity.id())) {
            throw new UniversityNotFoundException(id);
        }
        return dummyUniversity;
    }

    @Override
    public @NotNull University create(@NotNull University university) {
        log.info("Getting random url (WebClient)");
        String page = webClient
                .get()
                .uri("https://kruase.vercel.app")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        if (page != null) {
            Matcher matcher = titlePattern.matcher(page);
            if (matcher.find()) {
                log.info("WebClient got page with title '{}'", matcher.group());
            }
        }

        log.info("Creating university = {}", university);
        dummyUniversity = university.withId(dummyUniversity.id());
        return dummyUniversity;
    }

    @Override
    public void update(@NotNull University university) {
        log.info("Updating university = {}", university);
        if (Objects.equals(university.id(), dummyUniversity.id())) {
            dummyUniversity = university;
        } else {
            throw new UniversityNotFoundException(university.id());
        }
    }

    @Override
    public void delete(@NotNull Long id) {
        log.info("Deleting university by id = {}", id);
        if (id.equals(dummyUniversity.id())) {
            dummyUniversity = dummyUniversity
                    .withName("")
                    .withLocation("");
        } else {
            throw new UniversityNotFoundException(id);
        }
    }
}

