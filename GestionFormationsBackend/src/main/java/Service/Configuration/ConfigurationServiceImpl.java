package Service.Configuration;

import Entity.Configuration;
import Repository.ConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository) {
        this.configurationRepository = configurationRepository;
    }

    @Override
    public Optional<String> getValue(String key) {
        return configurationRepository.findByKey(key)
                .map(Configuration::getValue);
    }

    @Override
    public void setValue(String key, String value) {
        Configuration config = configurationRepository.findByKey(key)
                .orElse(new Configuration());
        config.setKey(key);
        config.setValue(value);
        configurationRepository.save(config);
    }

    @Override
    public void deleteByKey(String key) {
        configurationRepository.findByKey(key)
                .ifPresent(configurationRepository::delete);
    }
}