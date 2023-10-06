package mbp.booking.repo;

import mbp.booking.entity.SeatAllocationStatus;
import mbp.booking.entity.SeatAllocationStatusId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatAllocationStatusRepository extends JpaRepository<SeatAllocationStatus, SeatAllocationStatusId> {
}
