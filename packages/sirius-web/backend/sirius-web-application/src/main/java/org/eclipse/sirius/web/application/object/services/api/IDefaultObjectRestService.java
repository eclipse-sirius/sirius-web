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
package org.eclipse.sirius.web.application.object.services.api;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.object.dto.Direction;

/**
 * Interface of the default service used by objects-related REST APIs.
 *
 * @author arichard
 */
public interface IDefaultObjectRestService {

    List<Object> getElements(IEditingContext editingContext);

    Optional<Object> getElementById(IEditingContext editingContext, String elementId);

    List<Object> getRelationshipsByRelatedElement(IEditingContext editingContext, String elementId, Direction direction);

    List<Object> getRootElements(IEditingContext editingContext);
}
