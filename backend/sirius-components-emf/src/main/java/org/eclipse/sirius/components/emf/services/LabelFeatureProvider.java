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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;

/**
 * The default implementation for {@link ILabelFeatureProvider}.
 *
 * @author arichard
 *
 */
public class LabelFeatureProvider implements ILabelFeatureProvider {

    private final String ePackageNsUri;

    private final Switch<EAttribute> labelSwitch;

    private final Switch<Boolean> editableSwitch;

    public LabelFeatureProvider(String ePackageNsUri, Switch<EAttribute> labelSwitch, Switch<Boolean> editableSwitch) {
        this.ePackageNsUri = Objects.requireNonNull(ePackageNsUri);
        this.labelSwitch = Objects.requireNonNull(labelSwitch);
        this.editableSwitch = Objects.requireNonNull(editableSwitch);
    }

    @Override
    public String getEPackageNsUri() {
        return this.ePackageNsUri;
    }

    @Override
    public Optional<EAttribute> getLabelEAttribute(EObject eObject) {
        return Optional.ofNullable(this.labelSwitch.doSwitch(eObject));
    }

    @Override
    public boolean isLabelEditable(EObject eObject) {
        return this.editableSwitch.doSwitch(eObject);
    }

}
