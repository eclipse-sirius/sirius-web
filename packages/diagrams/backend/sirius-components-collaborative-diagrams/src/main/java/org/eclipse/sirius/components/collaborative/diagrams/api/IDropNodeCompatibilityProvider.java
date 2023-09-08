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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeCompatibilityEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Provides drop node compatibility information for a diagram.
 *
 * @author pcdavid
 */
public interface IDropNodeCompatibilityProvider {
    boolean canHandle(DiagramDescription diagramDescription);
    List<DropNodeCompatibilityEntry> getDropNodeCompatibility(Diagram diagram, IEditingContext editingContext);
}
