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

package org.eclipse.sirius.web.application.views.viewsexplorer.services.api;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.RepresentationKind;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;

/**
 * Used to customize the retrieval of the content of the views explorer.
 *
 * @author tgiraudet
 */
public interface IViewsExplorerContentServiceDelegate {

    boolean canHandle(IEditingContext editingContext);

    List<RepresentationKind> getContents(IEditingContext editingContext, List<RepresentationMetadata> representationMetadata, Map<String, IRepresentationDescription> representationDescriptions);

}
