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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.Objects;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.renderer.api.IDiagramStyleCustomizationProvider;
import org.eclipse.sirius.components.diagrams.renderer.api.INodeStyleCustomizer;
import org.springframework.stereotype.Service;

/**
 * Provides the style customizers available for diagrams.
 *
 * @author gcoutable
 */
@Service
public class DiagramStyleCustomizationProvider implements IDiagramStyleCustomizationProvider {

    private final Set<INodeStyleCustomizer> nodeStyleCustomizers;

    public DiagramStyleCustomizationProvider(Set<INodeStyleCustomizer> nodeStyleCustomizers) {
        this.nodeStyleCustomizers = Objects.requireNonNull(nodeStyleCustomizers);
    }

    @Override
    public Set<INodeStyleCustomizer> getNodeStyleCustomizers() {
        return this.nodeStyleCustomizers;
    }
}
