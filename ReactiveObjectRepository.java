import org.reactivestreams.Publisher;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interface for generic repository operations on a repository with a specific type.
 * This repository follows reactive paradigms and uses Project Reactor types which are built on top of Reactive Streams.
 *
 * @author Mark Mikus
 * @author kyussfia
 * @see Mono
 * @see Flux
 */
@NoRepositoryBean
public interface ReactiveObjectRepository<T, ID> extends org.springframework.data.repository.Repository<T, ID> {

    /**
     * Find entities by the given value on the given property.
     *
     * @param property must not be {@literal null}.
     * @param value which must be in the found entity on the given property.
     * @return {@link Flux} emitting the found entities.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    <S extends T> Flux<S> findBy(String property, Object value);

    /**
     * Find entities by the given map.
     * The map keys must be the fields of the target entity.
     * The map objects must be the values for the determined property.
     * The elements of the condition-chain created from the map are in conjunctive relation with each other (AND).
     *
     * @param value which represents the condition list what can be used for to identify the result entities. This param can not be {@literal null}.
     * @return {@link Flux} emitting the found entities.
     * @throws IllegalArgumentException in case the given {@code value} is {@literal null}.
     */
    <S extends T> Flux<S> findBy(Map<String, Object> value);

    /**
     * Find entities by the given maps.
     * The keys of the maps must be the fields of the target entity.
     * The objects of the maps must be the values for the determined property.
     * The conditions created from the maps are in disjunctive relation with each other (OR), but in one map the conditions created for a conjunctive condition-chain.
     *
     * @param value which represents the list of condition lists what can be used for to identify the result entities. This param can not be {@literal null}.
     * @return {@link Flux} emitting the found entities.
     * @throws IllegalArgumentException in case the given {@code value} is {@literal null}.
     */
    <S extends T> Flux<S> findBy(Iterable<Map<String, Object>> value);

    /**
     * Find entities by the given map conditions to identify, supplied by a {@link Publisher}.
     *
     * @see hu.gds.dashboard.dal.repository.ReactiveObjectRepository#findBy(Iterable) This is
     *
     * @param conditionStream which represents the list of the condition lists what can be used for to identify the result entities. This param can not be {@literal null}.
     * @return {@link Flux} emitting the found entities.
     * @throws IllegalArgumentException in case the given {@code value} is {@literal null}.
     */
    <S extends T> Flux<S> findBy(Publisher<Map<String, Object>> conditionStream);
}