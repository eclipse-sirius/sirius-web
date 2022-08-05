/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IDiagramNavigationService}.
 *
 * @author pcdavid
 */
@Service
public class DiagramNavigationService implements IDiagramNavigationService {
    private final IEMFNavigationService emfNavigationService;

    public DiagramNavigationService(IEMFNavigationService emfNavigationService) {
        this.emfNavigationService = Objects.requireNonNull(emfNavigationService);
    }

    @Override
    public Optional<DiagramDescription> getDiagramDescription(Map<NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> capturedNodeDescriptions) {
        // @formatter:off
        return capturedNodeDescriptions.keySet().stream()
                .map(nd -> this.emfNavigationService.getAncestor(DiagramDescription.class, nd))
                .filter(Objects::nonNull)
                .findFirst();
        // @formatter:on
    }

}
