package Pet.Store.Dao;

import Pet.Store.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeDao extends JpaRepository<Employee, Integer> {
}
