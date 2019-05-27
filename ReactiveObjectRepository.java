package hu.gds.dashboard.dal.repository;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Interface for generic repository operations on a repository with a specific type.
 * This repository follows reactive paradigms and uses Project Reactor types which are built on top of Reactive Streams.
 *
 * The String-Object version of the functions are for to determine single conditions.
 * That means we look for object where the given property equals with the given value.
 * Or if the given value is a collection then the given property is IN the given options (value).
 *
 * The Map versions are for to determine multiple condition with conjunctive relation with each other.
 * That means every key in the map represents a property and every value in the map represents options for the property to be equals with.
 * So every entry in the map will produce a condition and these conditions connect with each other with an AND.
 *
 * The Iterable versions are for to determine multiple Map conditions, which are in disjunctive relation with each other.
 * That means every map is an AND-chain and these are connect with OR with each other.
 *
 * The Publisher versions are the same as the Iterable versions except they are in a stream. So they from a Publisher object.
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
    Flux<T> findBy(String property, Object value);

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
    Flux<T> findBy(Map<String, Object> value);

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
    Flux<T> findBy(Iterable<Map<String, Object>> value);

    /**
     * Find entities by the given map conditions to identify, supplied by a {@link Publisher}.
     *
     * @see hu.gds.dashboard.dal.repository.ReactiveObjectRepository#findBy(Iterable) This is
     *
     * @param conditionStream which represents the list of the condition lists what can be used for to identify the result entities. This param can not be {@literal null}.
     * @return {@link Flux} emitting the found entities.
     * @throws IllegalArgumentException in case the given {@code value} is {@literal null}.
     */
    Flux<T> findBy(Publisher<Map<String, Object>> conditionStream);

    /**
     * Find one entity by the given value on the given property.
     * Find the first of the matches if there are more than one.
     *
     * @param property must not be {@literal null}.
     * @param value which must be in the found entity on the given property.
     * @return {@link Mono} emitting the found entity.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<T> findOneBy(String property, Object value);

    /**
     * Find one entity by the given map.
     * Find the first of the matches if there are more than one.
     *
     * @param value which represents the condition list what can be used for to identify the result entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting the found entity.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<T> findOneBy(Map<String, Object> value);

    /**
     * Find one entity by the given maps.
     * Find the first of the matches if there are more than one.
     *
     * @param value which represents the condition list what can be used for to identify the result entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting the found entity.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<T> findOneBy(Iterable<Map<String, Object>> value);

    /**
     * Find one entity by the given maps supplied, supplied by a {@link Publisher}.
     * Find the first of the matches if there are more than one.
     *
     * @param conditionStream which represents the condition list what can be used for to identify the result entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting the found entity.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<T> findOneBy(Publisher<Map<String, Object>> conditionStream);

    /**
     * Delete entities by the given value on the given property.
     *
     * @param property must not be {@literal null}.
     * @param value which must be in the deleted entity on the given property.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Void> deleteBy(String property, Object value);

    /**
     * Delete entities by the given map.
     *
     * @param value which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Void> deleteBy(Map<String, Object> value);

    /**
     * Delete entities by the given maps.
     *
     * @param value which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Void> deleteBy(Iterable<Map<String, Object>> value);

    /**
     * Delete entities by the given maps, supplied by a {@link Publisher}.
     *
     * @param conditionStream which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} signaling when operation has completed.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Void> deleteBy(Publisher<Map<String, Object>> conditionStream);

    /**
     * Count entities by the given value on the given property.
     *
     * @param property must not be {@literal null}.
     * @param value which must be in the matching entity on the given property.
     * @return {@link Mono} emitting the number of entities.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Long> countBy(String property, Object value);

    /**
     * Count entities by the given map.
     *
     * @param value which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting the number of entities.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Long> countBy(Map<String, Object> value);

    /**
     * Count entities by the given maps.
     *
     * @param value which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting the number of entities.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Long> countBy(Iterable<Map<String, Object>> value);

    /**
     * Count entities by the given maps, supplied by a {@link Publisher}.
     *
     * @param conditionStream which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting the number of entities.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Long> countBy(Publisher<Map<String, Object>> conditionStream);

    /**
     * Returns whether an entity with the given value on the given property exists.
     *
     * @param property must not be {@literal null}.
     * @param value which must be in the matching entity on the given property.
     * @return {@link Mono} emitting {@literal true} if an entity with the given value on the given property exists, {@literal false} otherwise.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Boolean> existsBy(String property, Object value);

    /**
     * Returns whether an entity with the given condition in the map exists.
     *
     * @param value which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting {@literal true} if an entity with the given value on the given property exists, {@literal false} otherwise.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Boolean> existsBy(Map<String, Object> value);

    /**
     * Returns whether an entity with the given condition in the maps exists.
     *
     * @param value which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting {@literal true} if an entity with the given value on the given property exists, {@literal false} otherwise.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Boolean> existsBy(Iterable<Map<String, Object>> value);

    /**
     * Returns whether an entity with the given condition in the maps, supplied by a {@link Publisher} exists.
     *
     * @param conditionStream which represents the condition list what can be used for to identify the deleted entity. This param can not be {@literal null}.
     * @return {@link Mono} emitting {@literal true} if an entity with the given value on the given property exists, {@literal false} otherwise.
     * @throws IllegalArgumentException in case the given {@code property} is {@literal null}.
     */
    Mono<Boolean> existsBy(Publisher<Map<String, Object>> conditionStream);
}