/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;

/**
 * Information used to perform diagram service operations.
 *
 * @author gdaniel
 */
public interface IDiagramService {

    /**
     * The name of the variable used to store and retrieve the diagram services context from a variable manager.
     */
    String DIAGRAM_SERVICES = "diagramServices";

    DiagramContext getDiagramContext();

}
