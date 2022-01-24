/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.ILayoutData;

/**
 * The result of the diagram transformation with the raw layout data and a cache of the values computed during the
 * transformation.
 *
 * @author wpiers
 */
public class IncrementalLayoutConvertedDiagram {

    private final DiagramLayoutData diagramLayoutData;

    private final Map<String, ILayoutData> id2LayoutData;

    public IncrementalLayoutConvertedDiagram(DiagramLayoutData diagramLayoutData, Map<String, ILayoutData> id2LayoutData) {
        this.diagramLayoutData = Objects.requireNonNull(diagramLayoutData);
        this.id2LayoutData = Objects.requireNonNull(id2LayoutData);
    }

    public DiagramLayoutData getDiagramLayoutData() {
        return this.diagramLayoutData;
    }

    public Map<String, ILayoutData> getId2LayoutData() {
        return this.id2LayoutData;
    }
}
