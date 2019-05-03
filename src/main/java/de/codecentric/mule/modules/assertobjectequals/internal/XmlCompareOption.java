package de.codecentric.mule.modules.assertobjectequals.internal;

import org.mule.runtime.api.value.Value;
import org.mule.runtime.extension.api.values.ValueBuilder;
import org.mule.runtime.extension.api.values.ValueProvider;
import org.mule.runtime.extension.api.values.ValueResolvingException;

import java.util.Set;


public class XmlCompareOption implements ValueProvider {

    /**
     * Resolves and provides a {@link Set} of {@link Value values} which represents a set of possible and valid values for
     * a parameter.
     *
     * @return a {@link Set} of {@link Value values}.
     *
     * @throws ValueResolvingException if an error occurs during the resolving
     */
    @Override
    public Set<Value> resolve() throws ValueResolvingException {
        return ValueBuilder.getValuesFor("IGNORE_COMMENTS", "IGNORE_WHITESPACE", "NORMALIZE_WHITESPACE");
    }
}
