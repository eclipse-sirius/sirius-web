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
package org.eclipse.sirius.web.application.views.relatedelements.services.api;

import org.eclipse.sirius.components.forms.description.TreeDescription;

/**
 * Provides the description of the tree widget for the outgoing panel.
 *
 * @author sbegaudeau
 */
public interface IOutgoingTreeDescriptionProvider {
    TreeDescription getTreeDescription();
}
