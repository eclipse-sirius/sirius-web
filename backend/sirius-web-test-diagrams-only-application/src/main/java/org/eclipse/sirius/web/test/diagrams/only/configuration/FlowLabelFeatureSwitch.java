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
package org.eclipse.sirius.web.test.diagrams.only.configuration;

import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.Fan;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.Named;
import fr.obeo.dsl.designer.sample.flow.PowerOutput;
import fr.obeo.dsl.designer.sample.flow.util.FlowSwitch;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * Specific {@link FlowSwitch} returning the {@link EAttribute} to use as label for an {@link EObject}
 *
 * @author arichard
 *
 */
class FlowLabelFeatureSwitch extends FlowSwitch<EAttribute> {
    @Override
    public EAttribute caseNamed(Named object) {
        return FlowPackage.eINSTANCE.getNamed_Name();
    }

    @Override
    public EAttribute caseFan(Fan object) {
        return FlowPackage.eINSTANCE.getFlowElement_Status();
    }

    @Override
    public EAttribute caseDataFlow(DataFlow object) {
        return FlowPackage.eINSTANCE.getFlowElement_Usage();
    }

    @Override
    public EAttribute casePowerOutput(PowerOutput object) {
        return FlowPackage.eINSTANCE.getPowerOutput_Power();
    }
}
