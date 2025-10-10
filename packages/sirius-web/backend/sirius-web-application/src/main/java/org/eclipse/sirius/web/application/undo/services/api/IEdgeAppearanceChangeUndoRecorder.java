/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.IEdgeAppearanceChange;

/**
 * Used to compute undo edge appearance change.
 *
 * @author mcharfadi
 */
public interface IEdgeAppearanceChangeUndoRecorder {

    List<IAppearanceChange> computeEdgeAppearanceChanges(Edge previousEdge, Optional<IEdgeAppearanceChange> change);

}
