package BusinessPJ.FinancialTracker.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BusinessPJ.FinancialTracker.DTOs.UserTransaction;
import BusinessPJ.FinancialTracker.Model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer>
{
    @Query(value="""
                SELECT
                Type AS type, Category As category, Description As description, Amount As amount, Date as date
                FROM Transaction
                WHERE User_id=:user_id
                ORDER BY Transaction_id DESC
            """, nativeQuery = true)
    List<UserTransaction> getUserTransaction(@Param("user_id") Integer user_id);
}
