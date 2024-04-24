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
package org.eclipse.sirius.web.application.diagram.services.filter.api;

import java.util.Set;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provides utility methods for the "Diagram Filter" view.
 *
 * @author gdaniel
 */
public interface IDiagramFilterHelper {

    Set<String> getSelectedElementIds(VariableManager variableManager);

    IStatus sendDiagramEvent(VariableManager variableManager, IDiagramInput diagramInput);
}
