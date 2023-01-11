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
package org.eclipse.sirius.components.view.emf.diagram;

import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IEdgeEditLabelHandler;

/**
 * Subtype of the generic edge label edit handler function type with additional metadata.
 *
 * @author pcdavid
 */
public interface IViewEdgeLabelEditHandler extends IEdgeEditLabelHandler {
    boolean hasLabelEditTool(EdgeLabelKind labelKind);
}
