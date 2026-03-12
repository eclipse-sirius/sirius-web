/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.util.List;
import java.util.Objects;

/**
 * Object to represent a representation kind for the Views Explorer view.
 * @param id the URI of the representation kind, for instance, "siriusComponents://representation?type=Diagram".
 * @param name the name of the representation kind, for instance, "diagram".
 * @param representationDescriptionTypes the representation descriptions having for kind the current kind.
 *
 * @author tgiraudet
 */
public record RepresentationKind(String id, String name, List<RepresentationDescriptionType> representationDescriptionTypes) {

    public RepresentationKind {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        Objects.requireNonNull(representationDescriptionTypes);
    }
}
