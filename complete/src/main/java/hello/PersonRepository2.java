package hello;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "persons", path = "people")
public interface PersonRepository2 extends PagingAndSortingRepository<Person2, Long> {

}
