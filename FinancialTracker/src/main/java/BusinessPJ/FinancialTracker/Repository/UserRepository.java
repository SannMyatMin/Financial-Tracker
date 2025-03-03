package BusinessPJ.FinancialTracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BusinessPJ.FinancialTracker.DTOs.UserData;
import BusinessPJ.FinancialTracker.Model.User;


@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    boolean existsByMail(String mail);

    boolean existsByName(String name);

    Optional<User> findByName(String name);

    @Query(value="""
                 SELECT u.Name as name,u.Mail as mail,u.Photo as photo,
                        b.Balance as balance,b.Monthly_income as income,b.Monthly_expenses as expenses
                 From User u JOIN Balance b ON u.Id = b.User_id WHERE u.Name=:name
            """, nativeQuery = true)
    Optional<UserData> getUserDataByName(@Param("name") String name);


}   
