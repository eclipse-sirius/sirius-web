/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.freediagram.behaviors;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Describe the expected eObject behavior during a create instance (owner, containmentFeature, createEObject) according
 * to the {@link VariableManager} context and a {@link EClass}.
 *
 * @author hmarchadour
 */
public interface ICreateInstanceBehavior {

    /**
     * Describe the expected owner according to the given {@link VariableManager}.
     */
    Optional<EObject> getOwner(VariableManager variableManager);

    /**
     * Describe the expected containment feature according to the given {@link VariableManager}.
     */
    Optional<EReference> getContainmentFeature(VariableManager variableManager);

    /**
     * Describe the expected create eObject according to the given {@link VariableManager}.
     */
    Optional<EObject> createEObject(VariableManager variableManager);
}
