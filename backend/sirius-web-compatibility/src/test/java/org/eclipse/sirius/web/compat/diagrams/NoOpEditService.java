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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.objects.ChildCreationDescription;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.Namespace;

/**
 * The implementation of the {@link IEditService} repository which does nothing.
 *
 * @author SMonnier
 */
public class NoOpEditService implements IEditService {

    @Override
    public Optional<Object> findClass(String classId) {
        return Optional.empty();
    }

    @Override
    public List<ChildCreationDescription> getChildCreationDescriptions(String classId) {
        return new ArrayList<>();
    }

    @Override
    public Optional<Object> createChild(IEditingContext editingContext, Object object, String childCreationDescriptionId) {
        return Optional.empty();
    }

    @Override
    public List<Namespace> getNamespaces() {
        return new ArrayList<>();
    }

    @Override
    public List<ChildCreationDescription> getRootCreationDescriptions(String namespaceId, boolean suggested) {
        return new ArrayList<>();
    }

    @Override
    public Optional<Object> createRootObject(IEditingContext editingContext, UUID documentId, String namespaceId, String rootObjectCreationDescriptionId) {
        return Optional.empty();
    }

    @Override
    public void delete(Object object) {
    }

    @Override
    public void editLabel(Object object, String labelField, String newValue) {
    }

}
