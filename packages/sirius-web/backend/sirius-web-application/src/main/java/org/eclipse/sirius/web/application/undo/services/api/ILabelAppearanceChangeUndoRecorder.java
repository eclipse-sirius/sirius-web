/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.undo.services.api;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;

import java.util.List;
import java.util.Optional;

/**
 * Use to compute undo label appearance change.
 *
 * @author mcharfadi
 */
public interface ILabelAppearanceChangeUndoRecorder {

    List<IAppearanceChange> computeUndoNodeLabelAppearanceChanges(Node previousNode, String labelId, Optional<ILabelAppearanceChange> change);

    List<IAppearanceChange> computeUndoEdgeLabelAppearanceChanges(Edge previousEdge, String labelId, Optional<ILabelAppearanceChange> change);
}
