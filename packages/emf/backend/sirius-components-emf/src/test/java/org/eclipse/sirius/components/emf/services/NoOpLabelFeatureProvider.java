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
package org.eclipse.sirius.components.emf.services;

import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * Implementation of the label feature provider which does nothing.
 *
 * @author arichard
 *
 */
public class NoOpLabelFeatureProvider implements ILabelFeatureProvider {

    @Override
    public String getEPackageNsUri() {
        return null;
    }

    @Override
    public Optional<EAttribute> getLabelEAttribute(EObject eObject) {
        return Optional.empty();
    }

    @Override
    public boolean isLabelEditable(EObject eObject) {
        return true;
    }

}
