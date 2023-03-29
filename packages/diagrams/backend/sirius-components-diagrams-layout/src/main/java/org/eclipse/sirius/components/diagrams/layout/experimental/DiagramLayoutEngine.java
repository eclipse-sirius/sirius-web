/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.api.IDiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.springframework.stereotype.Service;

/**
 * The default {@link IDiagramLayoutEngine}. Empty/no-op for now.
 *
 * @author pcdavid
 */
@Service
public class DiagramLayoutEngine implements IDiagramLayoutEngine {
    @Override
    public DiagramLayoutData layout(IEditingContext editingContext, Diagram diagram, DiagramLayoutData previousLayoutData, Optional<IDiagramEvent> optionalDiagramEvent) {
        return new DiagramLayoutData(Map.of(), Map.of(), Map.of());
    }
}
