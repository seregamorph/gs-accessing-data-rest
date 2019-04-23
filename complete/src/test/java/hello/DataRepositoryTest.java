package hello;

import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.core.Path;
import org.springframework.data.rest.core.mapping.RepositoryResourceMappings;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DataRepositoryTest {

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Autowired
    private RepositoryResourceMappings repositoryResourceMappings;

    @Test
    public void repositoryRestResourcesShouldNotIntersectPaths() {
        Map<Path, List<Class<?>>> mappings = new LinkedHashMap<>();
        repositoryResourceMappings.forEach(resourceMetadata -> {
            mappings.computeIfAbsent(resourceMetadata.getPath(), path -> new ArrayList<>())
                    .add(resourceMetadata.getDomainType());
        });
        mappings.forEach((path, classes) -> collector.checkThat("Repository path [" + path + "] is bound more "
                + "than once for entity classes " + classes + ". You should avoid it as it has unexpected side "
                + "effects and is unreliable. Think twice before changing this test.", classes, hasSize(1)));
    }
}
