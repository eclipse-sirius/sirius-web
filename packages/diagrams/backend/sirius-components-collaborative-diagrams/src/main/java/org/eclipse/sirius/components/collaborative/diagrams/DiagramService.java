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

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;

/**
 * Information used to perform diagram service operations.
 *
 * @author gdaniel
 */
public class DiagramService implements IDiagramService {

    private IDiagramContext diagramContext;

    public DiagramService(IDiagramContext diagramContext) {
        this.diagramContext = diagramContext;
    }

    @Override
    public IDiagramContext getDiagramContext() {
        return this.diagramContext;
    }

}
