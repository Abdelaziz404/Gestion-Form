package Service.Configuration;

import java.util.Optional;

public interface ConfigurationService {

    // Get a configuration value by key
    Optional<String> getValue(String key);

    // Set or update a configuration value
    void setValue(String key, String value);

    // Delete a configuration
    void deleteByKey(String key);
}
