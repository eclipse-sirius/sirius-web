/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.services.api.objects;

import java.util.List;
import java.util.Optional;

/**
 * Interface of the service interacting with domain objects.
 *
 * @author sbegaudeau
 */
public interface IObjectService {
    String getId(Object object);

    String getLabel(Object object);

    String getKind(Object object);

    String getFullLabel(Object object);

    String getImagePath(Object object);

    Optional<Object> getObject(IEditingContext editingContext, String objectId);

    List<Object> getContents(IEditingContext editingContext, String objectId);

    Optional<String> getLabelField(Object object);

    boolean isLabelEditable(Object object);
}
