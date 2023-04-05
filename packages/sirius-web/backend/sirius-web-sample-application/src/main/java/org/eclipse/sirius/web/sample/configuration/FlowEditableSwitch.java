/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.web.sample.configuration;

import fr.obeo.dsl.designer.sample.flow.Named;
import fr.obeo.dsl.designer.sample.flow.util.FlowSwitch;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Specific {@link FlowSwitch} returning the {@link EStructuralFeature} to use as label for an {@link EObject}.
 *
 * @author arichard
 */
public class FlowEditableSwitch extends FlowSwitch<Boolean> {
    @Override
    public Boolean caseNamed(Named object) {
        return true;
    }

    @Override
    public Boolean defaultCase(EObject object) {
        return false;
    }
}
