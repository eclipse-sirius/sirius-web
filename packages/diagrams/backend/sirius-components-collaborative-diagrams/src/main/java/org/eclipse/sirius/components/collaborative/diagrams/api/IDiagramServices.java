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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.List;

import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Service used to perform operations on a diagram.
 *
 * @author gdaniel
 */
public interface IDiagramServices {

    Object collapse(IDiagramService diagramService, List<Node> nodes);

    Object expand(IDiagramService diagramService, List<Node> nodes);

    Object hide(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements);

    Object reveal(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements);

    Object fade(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements);

    Object unfade(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements);

    Object resetViewModifiers(IDiagramService diagramService, List<? extends IDiagramElement> diagramElements);

    boolean isHidden(IDiagramElement diagramElement);

    boolean isFaded(IDiagramElement diagramElement);
}
