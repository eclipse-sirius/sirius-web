/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.services;

import java.util.List;

import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.services.ClassDiagramServices;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.services.ComponentDiagramServices;
import org.springframework.stereotype.Service;

/**
 * Used to register the Java services available for the papaya representations.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaJavaServiceProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {
        var isComponentDiagram = view.getDescriptions().stream()
                .anyMatch(representationDescription -> representationDescription.getDomainType().startsWith(PapayaPackage.eNS_PREFIX + ":"));
        if (isComponentDiagram) {
            return List.of(PapayaRepresentationServices.class, ComponentDiagramServices.class, ClassDiagramServices.class);
        }
        return List.of();
    }
}
