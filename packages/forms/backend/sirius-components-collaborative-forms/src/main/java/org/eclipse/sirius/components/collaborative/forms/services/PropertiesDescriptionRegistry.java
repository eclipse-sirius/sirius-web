/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.forms.description.FormDescription;

/**
 * Registry containing all the properties descriptions.
 *
 * @author hmarchadour
 */
public class PropertiesDescriptionRegistry implements IPropertiesDescriptionRegistry {

    private final Map<String, FormDescription> id2propertiesDescriptions = new HashMap<>();

    @Override
    public void add(FormDescription formDescription) {
        this.id2propertiesDescriptions.put(formDescription.getId(), formDescription);
    }

    public Optional<FormDescription> getPropertiesDescription(String id) {
        return Optional.ofNullable(this.id2propertiesDescriptions.get(id));
    }

    public List<FormDescription> getPropertiesDescriptions() {
        return this.id2propertiesDescriptions.values().stream().collect(Collectors.toList());
    }

}
