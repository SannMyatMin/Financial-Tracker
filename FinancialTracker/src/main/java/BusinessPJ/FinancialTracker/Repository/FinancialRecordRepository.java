package BusinessPJ.FinancialTracker.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BusinessPJ.FinancialTracker.Model.Balance;
import BusinessPJ.FinancialTracker.Model.FinancialRecord;
import BusinessPJ.FinancialTracker.Model.User;


@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Integer>
{
    @Query(value="""
            SELECT * FROM User
            """, nativeQuery = true)
    List<User> getAllUsers();

    @Query(value="""
                 SELECT * FROM Financial_record WHERE User_id=:id ORDER BY Date DESC LIMIT 1 OFFSET 1
            """, nativeQuery = true)
    Optional<FinancialRecord> findByUser_id(@Param("id") Integer user_id);
    
    @Query(value="""
            SELECT Balance FROM Balance WHERE User_id=:id
            """, nativeQuery = true)
    Float getLastMonthbalance(@Param("id") Integer id);


    @Query(value = """
            SELECT COALESCE(SUM(Amount), 0)
            FROM Transaction
            WHERE User_id = :id
            AND Type = 'income'
            AND YEAR(date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)
            AND MONTH(date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)
            """, nativeQuery = true)
    Float getLastMonthTotalIncome(@Param("id") Integer id);

    @Query(value = """
            SELECT COALESCE(SUM(Amount), 0)
            FROM Transaction
            WHERE User_id = :id
            AND Type = 'expenses'
            AND YEAR(date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)
            AND MONTH(date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)
            """, nativeQuery = true)
    Float getLastMonthTotalExpenses(@Param("id") Integer id);

}
