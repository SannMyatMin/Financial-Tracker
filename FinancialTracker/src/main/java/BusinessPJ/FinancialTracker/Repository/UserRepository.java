package BusinessPJ.FinancialTracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import BusinessPJ.FinancialTracker.DTOs.Udata;
import BusinessPJ.FinancialTracker.DTOs.UserData;
import BusinessPJ.FinancialTracker.Model.User;
import java.util.List;



@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    boolean existsByMail(String mail);

    boolean existsByName(String name);

    Optional<User> findByName(String name);

    Optional<User> findByMail(String mail);

    @Query(value="""
                 SELECT u.Name as name,u.Mail as mail,u.Photo as photo,
                        b.Balance as balance,b.Monthly_income as income,b.Monthly_expenses as expenses
                 From User u JOIN Balance b ON u.Id = b.User_id WHERE u.Name=:name
            """, nativeQuery = true)
    Optional<UserData> getUserDataByName(@Param("name") String name);

    @Query(value="""
                SELECT Name As name, Mail As mail From User Where Mail=:mail
            """, nativeQuery = true)
    Optional<Udata> getUserNameMail(@Param("mail") String mail);


}   
