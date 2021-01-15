/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.properties;

import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;

/**
 * An implementation of the object service used for some tests.
 *
 * @author sbegaudeau
 */
public class NoOpObjectService implements IObjectService {

    @Override
    public String getId(Object object) {
        if (object instanceof EObject) {
            return EcoreUtil.getURI((EObject) object).toString();
        }
        return ""; //$NON-NLS-1$
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
        return null;
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
