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
package org.eclipse.sirius.web.spring.collaborative.forms.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.api.configuration.ILinksDescriptionRegistry;
import org.eclipse.sirius.web.forms.description.FormDescription;

/**
 * Registry containing all the links descriptions. It actually contains form descriptions, these forms should contain
 * only Links, possibly grouped within groups if needed.
 *
 * @author ldelaigue
 */
public class LinksDescriptionRegistry implements ILinksDescriptionRegistry {

    private final Map<UUID, FormDescription> id2FormDescriptions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void add(FormDescription formDescription) {
        this.id2FormDescriptions.put(formDescription.getId(), formDescription);
    }

    /**
     * Lookup a links FormDescription among the registered descriptions, by ID.
     *
     * @param id
     *            The UUID of the desired provider
     * @return An optional provider, containing the provider with the given ID, empty if such a provider is not
     *         registered.
     */
    public Optional<FormDescription> getLinksDescriptionProvider(UUID id) {
        return Optional.ofNullable(this.id2FormDescriptions.get(id));
    }

    /**
     * Provides a list of the registered links FormDescriptions, that is independent of the registry (i.e. Changes to
     * the returned list don't affect the registry).
     *
     * @return A list of the registered descriptions, never <code>null</code> but possibly empty.
     */
    public List<FormDescription> getLinksDescriptions() {
        return this.id2FormDescriptions.values().stream().collect(Collectors.toList());
    }

}
