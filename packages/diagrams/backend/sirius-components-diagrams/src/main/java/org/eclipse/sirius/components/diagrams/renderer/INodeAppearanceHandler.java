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
package org.eclipse.sirius.components.diagrams.renderer;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.components.NodeAppearance;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;

/**
 * Service used to handle the customization of a node's appearance.
 *
 * @author nvannier
 */
public interface INodeAppearanceHandler {

    boolean canHandle(INodeStyle nodeStyle);

    NodeAppearance handle(INodeStyle providedStyle, List<INodeAppearanceChange> changes, Optional<NodeAppearance> previousAppearance);
}
