package usecase;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import domain.Person;
import gateway.mysql.SomeGatewayImpl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SomeUseCaseToGetPersonUsingInMemoryCache {

    private static final boolean IS_CACHE_ENABLED = true;
    private final LoadingCache<String, Person> loadingCache;
    private final SomeGatewayImpl someGateway;

    // Class constructor
    public SomeUseCaseToGetPersonUsingInMemoryCache() {
        this.someGateway = new SomeGatewayImpl();

        final CacheLoader<String, Person> cacheLoader = getCachedLoader();

        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build(cacheLoader);
    }

    private CacheLoader<String, Person> getCachedLoader() {
        return new CacheLoader<String, Person>() {
            @Override
            public Person load(final String cacheKey) {
                return getValueToBeCached(cacheKey);
            }
        };
    }

    private Person getValueToBeCached(final String personName) {
        return someGateway.getPerson(personName);
    }

    /**
     * This is the exposed service to the world. It will receive a person name and, internally, will use cache
     *
     * @return - a person Object
     */
    public Person getPersonByName(final String personName) {
        try {
            if (IS_CACHE_ENABLED) {
                return this.loadingCache.get(personName);
            } else {
                return this.someGateway.getPerson(personName);
            }
        } catch (final ExecutionException e) {
            throw new RuntimeException("failed to retrieve person name", e);
        }
    }
}
