/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.spring.collaborative.forms.api.ILinksDescriptionService;

/**
 * Service used to query the links descriptions available.
 *
 * @author ldelaigue
 */
public class LinksDescriptionService implements ILinksDescriptionService {

    private final LinksDescriptionRegistry registry;

    public LinksDescriptionService(LinksDescriptionRegistry registry) {
        this.registry = Objects.requireNonNull(registry);
    }

    @Override
    public List<FormDescription> getLinksDescriptions() {
        return this.registry.getLinksDescriptions();
    }

}
