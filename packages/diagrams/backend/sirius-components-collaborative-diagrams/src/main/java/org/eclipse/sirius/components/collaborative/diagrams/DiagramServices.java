/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramServices;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.events.FadeDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.ResetViewModifiersEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
import org.springframework.stereotype.Service;

/**
 * Service used to perform operations on a diagram.
 *
 * @author gdaniel
 */
@Service
public class DiagramServices implements IDiagramServices {

    @Override
    public Object collapse(IDiagramService diagramService, List<Node> nodes) {
        nodes.stream().map(Node::getId).forEach(nodeId -> {
            diagramService.getDiagramContext().getDiagramEvents().add(new UpdateCollapsingStateEvent(nodeId, CollapsingState.COLLAPSED));
        });
        return nodes;
    }

    @Override
    public Object expand(IDiagramService diagramService, List<Node> nodes) {
        nodes.stream().map(Node::getId).forEach(nodeId -> {
            diagramService.getDiagramContext().getDiagramEvents().add(new UpdateCollapsingStateEvent(nodeId, CollapsingState.EXPANDED));
        });
        return nodes;
    }

    @Override
    public Object hide(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements) {
        Set<String> diagramElementIds = diagramElements.stream().map(IDiagramElement::getId).collect(Collectors.toSet());
        diagramService.getDiagramContext().getDiagramEvents().add(new HideDiagramElementEvent(diagramElementIds, true));
        return diagramElements;
    }

    @Override
    public Object reveal(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements) {
        Set<String> diagramElementIds = diagramElements.stream().map(IDiagramElement::getId).collect(Collectors.toSet());
        diagramService.getDiagramContext().getDiagramEvents().add(new HideDiagramElementEvent(diagramElementIds, false));
        return diagramElements;
    }

    @Override
    public Object fade(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements) {
        Set<String> diagramElementIds = diagramElements.stream().map(IDiagramElement::getId).collect(Collectors.toSet());
        diagramService.getDiagramContext().getDiagramEvents().add(new FadeDiagramElementEvent(diagramElementIds, true));
        return diagramElements;
    }

    @Override
    public Object unfade(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements) {
        Set<String> diagramElementIds = diagramElements.stream().map(IDiagramElement::getId).collect(Collectors.toSet());
        diagramService.getDiagramContext().getDiagramEvents().add(new FadeDiagramElementEvent(diagramElementIds, false));
        return diagramElements;
    }

    @Override
    public Object resetViewModifiers(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements) {
        Set<String> diagramElementIds = diagramElements.stream().map(IDiagramElement::getId).collect(Collectors.toSet());
        diagramService.getDiagramContext().getDiagramEvents().add(new ResetViewModifiersEvent(diagramElementIds));
        return diagramElementIds;
    }

    @Override
    public boolean isHidden(IDiagramElement diagramElement) {
        boolean isHidden = false;
        if (diagramElement instanceof Node node) {
            isHidden = node.getModifiers().contains(ViewModifier.Hidden);
        } else if (diagramElement instanceof Edge edge) {
            isHidden = edge.getModifiers().contains(ViewModifier.Hidden);
        }
        return isHidden;
    }

    @Override
    public boolean isFaded(IDiagramElement diagramElement) {
        boolean isFaded = false;
        if (diagramElement instanceof Node node) {
            isFaded = node.getModifiers().contains(ViewModifier.Faded);
        } else if (diagramElement instanceof Edge edge) {
            isFaded = edge.getModifiers().contains(ViewModifier.Faded);
        }
        return isFaded;
    }
}
