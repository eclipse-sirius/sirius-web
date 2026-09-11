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
package org.eclipse.sirius.components.view.emf.diagram.tools.api;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to provide the variable manager used to evaluate the precondition of the tools of the palette on a single click on a diagram element.
 *
 * @author mcharfadi
 */
public interface IDiagramElementPaletteVariableManagerProvider {
    Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, Object diagramElement, Object semanticElement);
}
