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
package org.eclipse.sirius.web.application.views.explorer.services.api;

import java.util.List;

import org.eclipse.sirius.web.application.views.explorer.dto.ContainmentFeature;

/**
 * Used to provide the default implementation in charge of computing the list of features available to add a child in a container.
 *
 * @author Arthur daussy
 */
public interface IDefaultContainmentFeatureProvider {

    List<ContainmentFeature> getContainmentFeatures(Object container, Object child);
}
