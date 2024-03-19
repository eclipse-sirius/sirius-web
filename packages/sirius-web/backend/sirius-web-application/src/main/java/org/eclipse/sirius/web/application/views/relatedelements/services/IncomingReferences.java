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
package org.eclipse.sirius.web.application.views.relatedelements.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Helper to represent the set of source objects which point to the current element through the same EReference.
 *
 * @author pcdavid
 */
public record IncomingReferences(EReference eReference, List<EObject> sources) {
    public IncomingReferences {
        Objects.requireNonNull(eReference);
        Objects.requireNonNull(sources);
    }
}
