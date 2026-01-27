/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.views.explorer.services;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.URLParser;
import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultExplorerTooltipService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.RepresentationMetadata;
import org.springframework.stereotype.Service;

/**
 * Used to compute the default tooltip of a tree item.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultExplorerTooltipService implements IDefaultExplorerTooltipService {

    @Override
    public String getTooltip(Object self) {
        String result = "";
        if (self instanceof RepresentationMetadata representationMetadata) {
            String kind = representationMetadata.getKind();
            result = new URLParser().getParameterValues(kind).get("type").get(0);
        } else if (self instanceof EObject eObject) {
            EClass eClass = eObject.eClass();
            EPackage ePackage = eClass.getEPackage();
            result = ePackage.getName() + "::" + eClass.getName();
        }
        return result;
    }

}
