package org.eclipse.sirius.components.collaborative.diagrams.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

import java.util.Optional;

public interface IDiagramPostProcessor {
    /**
     * Determines whether this post-processor is applicable for the given diagram/representation.
     * This method is called before {@code postProcess} to check whether the processor
     * should be applied to the diagram in the current context.
     *
     * @param editingContext Project-specific context containing information about the environment
     * @param diagramDescription Optional diagram description (if already available)
     * @return {@code true} if this processor should handle the diagram, {@code false} otherwise
     */
    public boolean canHandle(IEditingContext editingContext,
                             Optional<DiagramDescription> diagramDescription);

    /**
     * Applies custom logic to a diagram after creation or update.
     *
     * @param diagram The diagram to process
     * @param editingContext Project-specific context containing information about the environment
     * @param diagramDescription Optional diagram description (if already available)
     * @param diagramContext Optional context containing triggering information
     * @return The modified diagram
     */
    Diagram postProcess(Diagram diagram, IEditingContext editingContext,
                        Optional<DiagramDescription> diagramDescription,
                        Optional<IDiagramContext> diagramContext);
}
