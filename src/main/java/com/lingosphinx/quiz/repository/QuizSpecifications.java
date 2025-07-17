package com.lingosphinx.quiz.repository;

import com.lingosphinx.quiz.domain.Quiz;
import com.lingosphinx.quiz.domain.QuizTag;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class QuizSpecifications {

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
}
