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

import org.eclipse.sirius.components.forms.description.ButtonDescription;

/**
 * Provides the description of a button for the diagram filter's split button.
 *
 * @author gdaniel
 */
public interface IDiagramFilterActionContributionProvider {

    ButtonDescription getButtonDescription();

}
