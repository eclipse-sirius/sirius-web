/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.impactanalysis.services;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;

/**
 * Represents the addition of a new value in an object's feature.
 *
 * @author gdaniel
 */
public record FeatureAddition(EObject source, String feature, Object newValue) {

    public FeatureAddition {
        Objects.requireNonNull(source);
        Objects.requireNonNull(feature);
        Objects.requireNonNull(newValue);
    }

}
