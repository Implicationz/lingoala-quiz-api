package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class TrialSpecifications {

    public static Specification<Trial> studyListEnabled(StudyList studyList) {
        return (root, query, cb) -> {
            var subquery = query.subquery(Long.class);
            var sliRoot = subquery.from(StudyListItem.class);
            subquery.select(sliRoot.get("id"))
                    .where(
                            cb.equal(sliRoot.get("quiz").get("id"), root.get("question").get("quiz").get("id")),
                            cb.equal(sliRoot.get("studyList"), studyList),
                            cb.equal(sliRoot.get("dueQuestions"), StudyStatus.ENABLED)
                    );
            return cb.exists(subquery);
        };
    }

    public static Specification<Trial> studentEquals(Student student) {
        return (root, query, cb) -> cb.equal(root.get("student"), student);
    }

    public static Specification<Trial> nextDueDateBeforeOrEqual(Instant date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("nextDueDate"), date);
    }

    public static Specification<Trial> quizIdEquals(Long quizId) {
        return (root, query, cb) -> cb.equal(root.get("question").get("quiz").get("id"), quizId);
    }

    public static Specification<Trial> isDue(Student student, StudyList studyList) {
        var today = Instant.now();
        var spec = studentEquals(student).and(studyListEnabled(studyList))
                .and(nextDueDateBeforeOrEqual(today));
        return spec;
    }

    public static Specification<Trial> isDue(Student student, Long quizId) {
        var today = Instant.now();
        var spec = studentEquals(student)
                .and(nextDueDateBeforeOrEqual(today))
                .and(quizIdEquals(quizId));
        return spec;
    }
    public static Specification<Trial> randomOrder() {
        final var seed = ThreadLocalRandom.current().nextDouble();
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(cb.abs(cb.diff(root.get("question").get("randomSeed"), seed)))
            );
            return cb.conjunction();
        };
    }
}