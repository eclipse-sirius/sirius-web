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

package org.eclipse.sirius.components.collaborative.diagrams.changes;

import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;

/**
 * Layout diagram event.
 *
 * @author mcharfadi
 */
public record LayoutDiagramRepresentationChange(String representationId, DiagramLayoutData previousLayout, DiagramLayoutData newLayout, DiagramLayoutDataChanges changes) implements IRepresentationChangeEvent {
}
