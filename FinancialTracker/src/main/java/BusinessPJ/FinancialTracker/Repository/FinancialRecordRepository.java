package BusinessPJ.FinancialTracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import BusinessPJ.FinancialTracker.Model.FinancialRecord;


@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord,Integer>
{
    @Query(value="""
                 SELECT * FROM Financial_record WHERE User_id=:id ORDER BY Date DESC LIMIT 1 OFFSET 1
            """, nativeQuery = true)
    Optional<FinancialRecord> findByUser_id(@Param("id") Integer user_id);
}
