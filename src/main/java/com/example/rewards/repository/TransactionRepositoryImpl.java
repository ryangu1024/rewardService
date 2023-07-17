package com.example.rewards.repository;

import com.example.rewards.entity.TransactionDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepositoryImpl implements CustomTransactionRepository{

    EntityManager em;

    public TransactionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Integer sumRewardByUserIDAndTimestamp(String userID, LocalDate startDate, LocalDate endDate) {
        Session session = em.unwrap(Session.class);
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root<TransactionDetails> root = query.from(TransactionDetails.class);
        Predicate startDatePredicate = builder.greaterThanOrEqualTo(root.get("timestamp"), startDate);
        Predicate endDatePredicate = builder.lessThanOrEqualTo(root.get("timestamp"), endDate);
        Predicate userIDPredicate = builder.equal(root.get("userID"), userID);
        Predicate andPredicate = builder.and(startDatePredicate, endDatePredicate, userIDPredicate);
        query.select(builder.sum(root.get("reward"))).where(andPredicate);
        Integer result = session.createQuery(query).getSingleResult();
        if(result == null){
            result = 0;
        }
        return result;
    }
    @Override
    public Map<String, Integer> sumRewardByUserIDAndTimestampByMonth(String userID, LocalDate startDate, LocalDate endDate) {
        Session session = em.unwrap(Session.class);
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createQuery(Tuple.class);
        Root<TransactionDetails> root = query.from(TransactionDetails.class);
        Predicate startDatePredicate = builder.greaterThanOrEqualTo(root.get("timestamp"), startDate);
        Predicate endDatePredicate = builder.lessThanOrEqualTo(root.get("timestamp"), endDate);
        Predicate userIDPredicate = builder.equal(root.get("userID"), userID);
        Predicate andPredicate = builder.and(startDatePredicate, endDatePredicate, userIDPredicate);
        Expression yearMonthExpression = builder.function("FORMATDATETIME", String.class, root.get("timestamp"), builder.literal("yyyy-MM"));
        query.multiselect(yearMonthExpression, builder.sum(root.get("reward"))).where(andPredicate).groupBy(yearMonthExpression);
        List<Tuple> result = session.createQuery(query).getResultList();
        HashMap<String, Integer> resultMap = new HashMap<>();
        for(Tuple tuple : result){
            resultMap.put((String) tuple.get(0), (Integer) tuple.get(1));
        }
        return resultMap;
    }


}
