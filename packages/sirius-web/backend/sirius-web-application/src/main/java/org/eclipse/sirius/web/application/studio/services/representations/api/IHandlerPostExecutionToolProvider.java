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
package org.eclipse.sirius.web.application.studio.services.representations.api;

import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverterContext;

/**
 * Used to inject some data in result of a tool handler.
 *
 * @author mcharfadi
 */
public interface IHandlerPostExecutionToolProvider {

    boolean canHandle(IStatus result, ViewDiagramDescriptionConverterContext converterContext, VariableManager variableManager, Tool tool);

    IStatus handle(IStatus result, ViewDiagramDescriptionConverterContext converterContext, VariableManager variableManager, Tool tool);

}
