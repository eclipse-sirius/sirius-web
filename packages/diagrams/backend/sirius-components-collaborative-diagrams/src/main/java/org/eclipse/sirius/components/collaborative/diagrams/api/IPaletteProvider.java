/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;


/**
 * Provide the tool palette for a given diagram element.
 *
 * @author arichard
 */
public interface IPaletteProvider {

    boolean canHandle(DiagramDescription diagramDescription);

    Palette handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription);
}
