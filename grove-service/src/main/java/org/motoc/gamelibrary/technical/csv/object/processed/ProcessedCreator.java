package org.motoc.gamelibrary.technical.csv.object.processed;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.motoc.gamelibrary.domain.enumeration.CreatorRole;

import java.util.Objects;

@Getter
@Setter
public class ProcessedCreator {
    private Long id;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null or blank")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lastName;

    @NotNull(message = "Role cannot be null")
    private CreatorRole role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessedCreator that = (ProcessedCreator) o;

        if (!Objects.equals(firstName, that.firstName)) return false;
        return lastName.equals(that.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + lastName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CreatorValues{" +
               "firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               ", role=" + role +
               '}';
    }
}
