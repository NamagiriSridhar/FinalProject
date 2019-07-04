package todos.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import todos.domains.Todo;

@Repository
public interface TodoRepository extends CrudRepository<Todo,Long>
{

}
