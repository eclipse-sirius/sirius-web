/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.studio.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeProvider;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to return the list of stereotype to create documents.
 *
 * @author sbegaudeau
 */
@Service
public class StudioStereotypeProvider implements IStereotypeProvider {

    public static final String VIEW_STEREOTYPE = "view";

    public static final String DOMAIN_STEREOTYPE = "domain";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public StudioStereotypeProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public List<Stereotype> getStereotypes(IEditingContext editingContext) {
        if (this.studioCapableEditingContextPredicate.test(editingContext)) {
            return List.of(
                    new Stereotype(DOMAIN_STEREOTYPE, "Domain"),
                    new Stereotype(VIEW_STEREOTYPE, "View")
            );
        }
        return List.of();
    }
}
