/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.OutsideLabel;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeProvider;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeUndoRecorder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Use to record data needed to perform the undo for the label appearance changes.
 *
 * @author mcharfadi
 */
@Service
public class NodeLabelAppearanceChangeUndoRecorder implements ILabelAppearanceChangeUndoRecorder {

    private final ILabelAppearanceChangeProvider labelAppearanceChangeProvider;

    public NodeLabelAppearanceChangeUndoRecorder(ILabelAppearanceChangeProvider labelAppearanceChangeProvider) {
        this.labelAppearanceChangeProvider = Objects.requireNonNull(labelAppearanceChangeProvider);
    }

    @Override
    public boolean canHandle(IDiagramElement diagramElement) {
        return diagramElement instanceof Node;
    }

    @Override
    public List<IAppearanceChange> computeUndoLabelAppearanceChanges(IDiagramElement diagramElement, String labelId, Optional<ILabelAppearanceChange> optionalChange) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        if (diagramElement instanceof Node previousNode) {
            if (optionalChange.isPresent()) {
                var change = optionalChange.get();
                getNodeOutsideLabel(previousNode, labelId)
                        .ifPresent(previousLabel -> appearanceChanges.addAll(this.labelAppearanceChangeProvider.getAppearanceChanges(change, previousLabel.customizedStyleProperties(), previousLabel.style())));
                getNodeInsideLabel(previousNode, labelId)
                        .ifPresent(previousLabel -> appearanceChanges.addAll(this.labelAppearanceChangeProvider.getAppearanceChanges(change, previousLabel.getCustomizedStyleProperties(), previousLabel.getStyle())));
            } else {
                getNodeOutsideLabel(previousNode, labelId)
                        .ifPresent(previousLabel -> appearanceChanges.addAll(this.labelAppearanceChangeProvider.getResetLabelAppearanceChange(labelId, previousLabel.customizedStyleProperties(), previousLabel.style())));
                getNodeInsideLabel(previousNode, labelId)
                        .ifPresent(previousLabel -> appearanceChanges.addAll(this.labelAppearanceChangeProvider.getResetLabelAppearanceChange(labelId, previousLabel.getCustomizedStyleProperties(), previousLabel.getStyle())));
            }
        }
        return appearanceChanges;
    }

    private Optional<InsideLabel> getNodeInsideLabel(Node node, String labelId) {
        Optional<InsideLabel> optionalLabel = Optional.empty();
        if (node.getInsideLabel() != null && node.getInsideLabel().getId().equals(labelId)) {
            optionalLabel = Optional.of(node.getInsideLabel());
        }
        return optionalLabel;
    }

    private Optional<OutsideLabel> getNodeOutsideLabel(Node node, String labelId) {
        return  node.getOutsideLabels().stream().filter(label -> label.id().equals(labelId)).findFirst();
    }

}
