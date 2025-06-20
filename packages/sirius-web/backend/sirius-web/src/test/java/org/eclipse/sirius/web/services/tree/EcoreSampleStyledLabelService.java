/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.services.tree;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.ILabelServiceDelegate;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.springframework.stereotype.Service;

/**
 * Implementation of ILabelServiceDelegate to test styled tree items.
 *
 * @author mcharfadi
 */
@Service
public class EcoreSampleStyledLabelService implements ILabelServiceDelegate {

    @Override
    public boolean canHandle(Object object) {
        if (object instanceof EObject eObject) {
            var eStructuralFeature = eObject.eClass().getEStructuralFeature("name");
            if (eStructuralFeature != null) {
                return "Sample".equals(eObject.eGet(eStructuralFeature));
            }
        }
        return false;
    }

    @Override
    public StyledString getStyledLabel(Object object) {
        if (object instanceof EObject eObject) {
            String label = eObject.eGet(eObject.eClass().getEStructuralFeature("name")).toString();

            var style = StyledStringFragmentStyle.newDefaultStyledStringFragmentStyle()
                    .backgroundColor("red")
                    .struckOut(true)
                    .build();
            var fragment = new StyledStringFragment(label, style);
            return new StyledString(List.of(fragment));
        }
        return StyledString.of("");
    }

    @Override
    public List<String> getImagePaths(Object object) {
        return List.of();
    }
}
