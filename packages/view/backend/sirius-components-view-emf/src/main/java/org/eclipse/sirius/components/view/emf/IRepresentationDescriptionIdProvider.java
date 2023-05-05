/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import org.eclipse.sirius.components.view.RepresentationDescription;

/**
 * Used to provide the id of a representation description.
 *
 * @author sbegaudeau
 *
 * @param <T> The type of representation description supported.
 */
public interface IRepresentationDescriptionIdProvider<T extends RepresentationDescription> {

    String PREFIX = "siriusComponents://representationDescription";

    String KIND = "kind";

    String SOURCE_KIND = "sourceKind";

    String VIEW_SOURCE_KIND = "view";

    String SOURCE_ID = "sourceId";

    String SOURCE_ELEMENT_ID = "sourceElementId";

    String getId(T representationDescription);

}
