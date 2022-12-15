/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService.NoOp;
import org.eclipse.sirius.components.diagrams.tests.builder.Element;
import org.eclipse.sirius.components.diagrams.tests.builder.JsonBasedEditingContext;

/**
 * The object service used by layout tests.
 *
 * @author gcoutable
 */
public class TestLayoutObjectService extends NoOp {

    @Override
    public String getId(Object object) {
        if (object instanceof Element) {
            Element element = (Element) object;
            String[] composedName = element.getName().split(":");
            if (composedName.length >= 2) {
                return composedName[1];
            }
        }
        return "";
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        // @formatter:off
        return Optional.of(editingContext)
                .filter(JsonBasedEditingContext.class::isInstance)
                .map(JsonBasedEditingContext.class::cast)
                .map(JsonBasedEditingContext::getContent)
                .map(element -> this.doGetObject(element, objectId));
        // @formatter:on
    }

    private Object doGetObject(Element element, String objectId) {
        if (this.isObject(element, objectId)) {
            return element;
        }
        // @formatter:off
        return element.getChildren().stream()
                .map(child -> this.doGetObject(child, objectId))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        // @formatter:on
    }

    private boolean isObject(Element element, String objectId) {
        String[] composedName = element.getName().split(":");
        if (composedName.length < 2) {
            return false;
        }

        return objectId.equals(composedName[1]);
    }

}
