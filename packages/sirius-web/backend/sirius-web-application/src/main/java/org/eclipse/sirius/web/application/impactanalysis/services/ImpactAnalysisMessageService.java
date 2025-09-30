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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IImpactAnalysisMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides impact analysis messages.
 *
 * @author gdaniel
 */
@Service
public class ImpactAnalysisMessageService implements IImpactAnalysisMessageService {

    private final ILabelService labelService;

    public ImpactAnalysisMessageService(ILabelService labelService) {
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public List<String> getUnresolvedProxyMessages(Map<EObject, Collection<Setting>> unresolvedProxies) {
        List<String> report = new ArrayList<>();
        unresolvedProxies.entrySet().stream()
            .forEach(entry -> {
                EObject proxyObject = entry.getKey();
                Collection<Setting> settings = entry.getValue();
                for (Setting setting : settings) {
                    StringBuilder brokenProxiesBuilder = new StringBuilder();
                    brokenProxiesBuilder
                        .append("[BROKEN] ")
                        .append(this.getResourceAndEObjectLabel(setting.getEObject()))
                        .append(".")
                        .append(setting.getEStructuralFeature().getName())
                        .append(" (previously set to ")
                        .append(this.labelService.getStyledLabel(proxyObject))
                        .append(")");
                    report.add(brokenProxiesBuilder.toString());
                }
            });
        return report;
    }

    private String getResourceAndEObjectLabel(EObject eObject) {
        return this.labelService.getStyledLabel(eObject.eResource()).toString()
                + " - "
                + this.labelService.getStyledLabel(eObject).toString();
    }

}
