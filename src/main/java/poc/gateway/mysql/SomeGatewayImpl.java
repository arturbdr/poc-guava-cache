package poc.gateway.mysql;

import poc.domain.Person;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SomeGatewayImpl {

    public Person getPerson(final String personName) {
        // This lines will, in the real world, access the database
        // perform some query and return the information
        System.out.println("Accessing Database to retrieve person = " + personName);
        return Person.builder()
                .name(personName.toUpperCase())
                .build();
    }
}
