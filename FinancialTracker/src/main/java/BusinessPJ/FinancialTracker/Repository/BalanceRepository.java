package BusinessPJ.FinancialTracker.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import BusinessPJ.FinancialTracker.Model.Balance;
import java.util.Optional;


@Repository
public interface BalanceRepository extends JpaRepository<Balance,Integer>
{
    @Query(value="""
            SELECT * FROM Balance WHERE User_id=:userId
            """, nativeQuery = true)
    Optional<Balance> findByUserId(@Param("userId") Integer userId);
}
