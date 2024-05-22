/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.forms.description.PageDescription;

/**
 * Registry containing all the properties descriptions.
 *
 * @author hmarchadour
 */
public class PropertiesDescriptionRegistry implements IPropertiesDescriptionRegistry {

    private final Map<String, PageDescription> id2propertiesDescriptions = new LinkedHashMap<>();

    @Override
    public void add(PageDescription pageDescription) {
        this.id2propertiesDescriptions.put(pageDescription.getId(), pageDescription);
    }

    public List<PageDescription> getPropertiesDescriptions() {
        return this.id2propertiesDescriptions.values().stream().toList();
    }

}
