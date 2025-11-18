package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class QuizSpecifications {

    public static Specification<Quiz> hasLanguage(LanguageCode language) {
        return (root, query, cb) -> language == null ? null : cb.equal(root.get("language"), language);
    }

    public static Specification<Quiz> hasSubject(Subject subject) {
        return (root, query, cb) -> subject == null ? null : cb.equal(root.get("subject"), subject);
    }

    public static Specification<Quiz> hasTopic(Topic topic) {
        return (root, query, cb) -> topic == null ? null : cb.equal(root.get("topic"), topic);
    }

    public static Specification<Quiz> randomOrder() {
        final var seed = ThreadLocalRandom.current().nextDouble();
        return (root, query, cb) -> {
            query.orderBy(
                    cb.asc(cb.abs(cb.diff(root.get("randomSeed"), seed)))
            );
            return cb.conjunction();
        };
    }

    
    public static Specification<Quiz> hasAllTagIds(Set<Long> tagIds) {
        return (root, query, builder) -> {
            var subquery = query.subquery(Long.class);
            var qt = subquery.from(QuizTag.class);
            subquery.select(qt.get("quiz").get("id"))
                    .where(qt.get("tag").get("id").in(tagIds))
                    .groupBy(qt.get("quiz").get("id"))
                    .having(builder.equal(builder.countDistinct(qt.get("tag").get("id")), tagIds.size()));

            return root.get("id").in(subquery);
        };
    }

    public static Specification<Quiz> isVisible(Student student) {
        var canViewPremium = student != null && AccessLevel.PREMIUM.equals(student.getAccessLevel());
        return (root, query, cb) -> {
            var free = cb.equal(root.get("visibility"), Visibility.FREE);
            var isOwner = cb.equal(root.get("owner"), student);

            if(!canViewPremium) {
                cb.or(isOwner, free);
            }

            var premium = cb.equal(root.get("visibility"), Visibility.PREMIUM);
            return cb.or(isOwner, free, premium);
        };
    }
}
