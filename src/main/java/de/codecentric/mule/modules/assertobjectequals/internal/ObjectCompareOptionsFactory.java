package de.codecentric.mule.modules.assertobjectequals.internal;

import java.util.EnumSet;

public interface ObjectCompareOptionsFactory {

    /**
     * @param inherited
     *            Options of parent path
     * @param path
     *            A path
     * @return Options controlling the comparison for a path.
     */
    public EnumSet<PathOption> createOptions(EnumSet<PathOption> inherited, Path path);
}
