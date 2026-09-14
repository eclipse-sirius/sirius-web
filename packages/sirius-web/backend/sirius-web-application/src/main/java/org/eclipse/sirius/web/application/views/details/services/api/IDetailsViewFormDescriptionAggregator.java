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
package org.eclipse.sirius.web.application.views.details.services.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;

/**
 * Aggregates the page descriptions eligible for a details view selection.
 *
 * @author sbegaudeau
 * @since v2026.11.0
 */
public interface IDetailsViewFormDescriptionAggregator {

    Optional<FormDescription> aggregate(List<PageDescription> pageDescriptions, List<Object> objects);
}
