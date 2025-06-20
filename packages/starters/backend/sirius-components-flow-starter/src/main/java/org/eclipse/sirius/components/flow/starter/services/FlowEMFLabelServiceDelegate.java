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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;

import fr.obeo.dsl.designer.sample.flow.DataFlow;
import fr.obeo.dsl.designer.sample.flow.Fan;
import fr.obeo.dsl.designer.sample.flow.FlowPackage;
import fr.obeo.dsl.designer.sample.flow.Named;
import fr.obeo.dsl.designer.sample.flow.PowerOutput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.api.IDefaultEMFLabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFLabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to select how the label of flow objects should be computed.
 *
 * @author sbegaudeau
 */
@Service
public class FlowEMFLabelServiceDelegate implements IEMFLabelServiceDelegate {

    private final IDefaultEMFLabelService defaultEMFLabelService;

    public FlowEMFLabelServiceDelegate(IDefaultEMFLabelService defaultEMFLabelService) {
        this.defaultEMFLabelService = Objects.requireNonNull(defaultEMFLabelService);
    }

    @Override
    public boolean canHandle(EObject self) {
        return self.eClass().getEPackage().getNsURI().equals(FlowPackage.eNS_URI);
    }

    @Override
    public StyledString getStyledLabel(EObject self) {
        var label = "";

        if (self instanceof Named named) {
            label = named.getName();
        } else if (self instanceof Fan fan) {
            label = fan.getStatus().toString();
        } else if (self instanceof DataFlow dataFlow) {
            label = dataFlow.getUsage().toString();
        } else if (self instanceof PowerOutput powerOutput) {
            label = String.valueOf(powerOutput.getPower());
        }

        if (label == null || label.isBlank()) {
            label = self.eClass().getName();
        }
        return StyledString.of(label);
    }

    @Override
    public List<String> getImagePaths(EObject self) {
        return this.defaultEMFLabelService.getImagePaths(self);
    }
}
