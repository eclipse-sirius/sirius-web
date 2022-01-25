/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.compatibility.services.diagrams.api;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Used to convert a Sirius diagram description to an Sirius Web one.
 *
 * @author sbegaudeau
 */
public interface IDiagramDescriptionConverter {
    DiagramDescription convert(org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription);
}
