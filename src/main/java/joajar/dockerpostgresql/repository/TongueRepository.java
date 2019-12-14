package joajar.dockerpostgresql.repository;

import joajar.dockerpostgresql.domain.Tongue;
import joajar.dockerpostgresql.domain.TongueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TongueRepository extends JpaRepository<Tongue, TongueId> {
    Optional<Tongue> findFirstByIdCodeOrderByPercentageDesc(String code);
}
