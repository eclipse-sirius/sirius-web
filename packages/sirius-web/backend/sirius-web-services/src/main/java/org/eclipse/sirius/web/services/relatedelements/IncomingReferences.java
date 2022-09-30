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
package org.eclipse.sirius.web.services.relatedelements;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * Helper to represent the set of source objects which point to the current element through the same EReference.
 *
 * @author pcdavid
 */
public class IncomingReferences {
    private final EReference reference;

    private final List<EObject> sources;

    public IncomingReferences(EReference reference, List<EObject> sources) {
        this.reference = Objects.requireNonNull(reference);
        this.sources = Objects.requireNonNull(sources);
    }

    public EReference getReference() {
        return this.reference;
    }

    public List<EObject> getSources() {
        return this.sources;
    }
}
