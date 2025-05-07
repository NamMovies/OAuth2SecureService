package antonBurshteyn.authentication.repository;
import antonBurshteyn.authentication.entity.User;
import antonBurshteyn.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndProvider(String email, Provider provider);
    Optional<User> findByEmail(String email);
}