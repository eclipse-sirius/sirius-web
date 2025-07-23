package org.eclipse.sirius.components.diagrams.components;

import org.eclipse.sirius.components.diagrams.EdgeStyle;

import java.util.Set;

/**
 * Wrapper for an edge style and the list of its customized style properties.
 *
 * @author mcharfadi
 */
public record EdgeAppearance (EdgeStyle style, Set<String> customizedStyleProperties) {

}
