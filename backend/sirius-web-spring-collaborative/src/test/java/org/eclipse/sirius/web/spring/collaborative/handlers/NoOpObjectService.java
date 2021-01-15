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
package org.eclipse.sirius.web.spring.collaborative.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;

/**
 * Implementation of the object service which does nothing.
 *
 * @author sbegaudeau
 */
public class NoOpObjectService implements IObjectService {

    @Override
    public String getId(Object object) {
        return null;
    }

    @Override
    public String getLabel(Object object) {
        return null;
    }

    @Override
    public String getKind(Object object) {
        return null;
    }

    @Override
    public String getFullLabel(Object object) {
        return null;
    }

    @Override
    public String getImagePath(Object object) {
        return null;
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return Optional.empty();
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        return new ArrayList<>();
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return Optional.empty();
    }

    @Override
    public boolean isLabelEditable(Object object) {
        return true;
    }
}
