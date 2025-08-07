    package com.pettrek.backend.passport.repos;

    import com.pettrek.backend.auth.models.User;
    import com.pettrek.backend.passport.models.Pet;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.Optional;

    public interface PetRepo extends JpaRepository<Pet,Long> {
        Optional<Integer> findMaxPassportNumberByUser(User user);
    }
