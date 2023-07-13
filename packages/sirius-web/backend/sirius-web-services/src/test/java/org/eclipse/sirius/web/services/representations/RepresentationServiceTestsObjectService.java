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
package org.eclipse.sirius.web.services.representations;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * IObjectService implementation for RepresentationService tests.
 *
 * @author arichard
 */
public class RepresentationServiceTestsObjectService implements IObjectService {

    private List<String> objectIds;

    public RepresentationServiceTestsObjectService() {
        this.objectIds = new ArrayList<>();
    }

    public boolean addObject(String objectId) {
        return this.objectIds.add(objectId);
    }

    public boolean removeObject(String objectId) {
        return this.objectIds.remove(objectId);
    }

    @Override
    public String getId(Object object) {
        return "";
    }

    @Override
    public String getLabel(Object object) {
        return "";
    }

    @Override
    public String getKind(Object object) {
        return "";
    }

    @Override
    public String getFullLabel(Object object) {
        return "";
    }

    @Override
    public String getImagePath(Object object) {
        return "";
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return Optional.ofNullable(this.objectIds.stream().filter(o -> Objects.equals(o, objectId)).findFirst().orElse(null));
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        return List.of();
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return Optional.empty();
    }

    @Override
    public boolean isLabelEditable(Object object) {
        return false;
    }
}
