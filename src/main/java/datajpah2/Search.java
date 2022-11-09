package datajpah2;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;

import javax.validation.constraints.NotBlank;

@Introspected
public class Search {

    @Nullable
    private final String code;

    @Nullable
    private final String name;

    @Nullable
    private final String description;

    public Search(@Nullable String code,
                  @Nullable String name,
                  @Nullable String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @Nullable
    public String getCode() {
        return code;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }
}
