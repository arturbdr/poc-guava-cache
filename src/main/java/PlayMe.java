import usecase.SomeUseCaseToGetPersonUsingInMemoryCache;

import java.util.concurrent.TimeUnit;

public class PlayMe {

    public static final String JOHN_DOE = "John Doe";
    public static final String MARY_JANE = "Mary Jane";
    private static final int SLEEP_TIME_IN_SECONDS = 5;

    public static void main(String[] args) {
        final SomeUseCaseToGetPersonUsingInMemoryCache useCase = new SomeUseCaseToGetPersonUsingInMemoryCache();
        final PlayMe playMe = new PlayMe();
        try {
            playMe.getAndValidateJohn(useCase);
            playMe.getAndValidateMary(useCase);
            System.out.println("I'm about to sleep for " + SLEEP_TIME_IN_SECONDS + " seconds");
            TimeUnit.SECONDS.sleep(SLEEP_TIME_IN_SECONDS); // For the the cache to expire
            System.out.println("I'm awake!");
            playMe.getAndValidateJohn(useCase);
            playMe.getAndValidateMary(useCase);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getAndValidateJohn(final SomeUseCaseToGetPersonUsingInMemoryCache useCase) {
        for (int i = 0; i < 100; i++) {
            useCase.getPersonByName(JOHN_DOE);
            // If the cache is enabled, only 1 John Doe should be printed in the console
        }
    }

    public void getAndValidateMary(final SomeUseCaseToGetPersonUsingInMemoryCache useCase) {
        for (int i = 0; i < 100; i++) {
            useCase.getPersonByName(MARY_JANE);
            // If the cache is enabled, only 1 Mary Jane should be printed in the console
        }
    }
}
