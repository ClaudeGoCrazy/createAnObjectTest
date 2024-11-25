package delft;

import nl.tudelft.cse1110.andy.config.MetaTest;
import nl.tudelft.cse1110.andy.config.RunConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration extends RunConfiguration {

    @Override
    public Map<String, Float> weights() {
        return new HashMap<>() {{
            put("coverage", 0.50f); // was 1.0
            put("mutation", 0.5f); // was 0.0
            put("meta", 0.0f); // was 0.0
            put("codechecks", 0.0f);
        }};
    }

    @Override
    public int numberOfMutationsToConsider() {
        return 7;
    }

    @Override
    public List<String> classesUnderTest() {
        return List.of("delft.BudgetManager");
    }

    @Override
    public List<MetaTest> metaTests() {
        return List.of();
    }

}