/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.undo.services.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeProvider;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeUndoRecorder;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo for the label appearance changes.
 *
 * @author mcharfadi
 */
@Service
public class EdgeLabelAppearanceChangeUndoRecorder implements ILabelAppearanceChangeUndoRecorder {

    private final ILabelAppearanceChangeProvider labelAppearanceChangeProvider;

    public EdgeLabelAppearanceChangeUndoRecorder(ILabelAppearanceChangeProvider labelAppearanceChangeProvider) {
        this.labelAppearanceChangeProvider = Objects.requireNonNull(labelAppearanceChangeProvider);
    }

    @Override
    public boolean canHandle(IDiagramElement diagramElement) {
        return diagramElement instanceof Edge;
    }

    @Override
    public List<IAppearanceChange> computeUndoLabelAppearanceChanges(IDiagramElement diagramElement, String labelId, Optional<ILabelAppearanceChange> optionalChange) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        if (diagramElement instanceof Edge previousEdge) {
            if (optionalChange.isPresent()) {
                var change = optionalChange.get();
                this.getEdgeLabel(previousEdge, labelId)
                        .ifPresent(previousLabel -> appearanceChanges.addAll(this.labelAppearanceChangeProvider.getAppearanceChanges(change, previousLabel.customizedStyleProperties(), previousLabel.style())));
            } else {
                this.getEdgeLabel(previousEdge, labelId)
                        .ifPresent(previousLabel -> appearanceChanges.addAll(this.labelAppearanceChangeProvider.getResetLabelAppearanceChange(labelId, previousLabel.customizedStyleProperties(), previousLabel.style())));
            }
        }
        return appearanceChanges;
    }

    private Optional<Label> getEdgeLabel(Edge edge, String labelId) {
        Optional<Label> optionalLabel = Optional.empty();
        if (edge.getBeginLabel() != null && edge.getBeginLabel().id().equals(labelId)) {
            optionalLabel = Optional.of(edge.getBeginLabel());
        }
        if (edge.getCenterLabel() != null && edge.getCenterLabel().id().equals(labelId)) {
            optionalLabel = Optional.of(edge.getCenterLabel());
        }
        if (edge.getEndLabel() != null && edge.getEndLabel().id().equals(labelId)) {
            optionalLabel = Optional.of(edge.getEndLabel());
        }
        return optionalLabel;
    }

}
