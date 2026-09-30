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
package org.eclipse.sirius.components.diagrams.renderer.api;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to customize {@link INodeStyle}.
 *
 * @author gcoutable
 */
public interface INodeStyleCustomizer {

    INodeStyle customize(VariableManager variableManager, DiagramDescription diagramDescription, NodeDescription nodeDescription, INodeStyle nodeStyle);

}
