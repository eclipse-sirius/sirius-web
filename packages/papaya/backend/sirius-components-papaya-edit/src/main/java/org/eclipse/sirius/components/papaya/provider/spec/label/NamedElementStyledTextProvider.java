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
package org.eclipse.sirius.components.papaya.provider.spec.label;

import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.sirius.components.papaya.NamedElement;

/**
 * Used to compute the styled text of a regular named element.
 *
 * @author sbegaudeau
 */
public class NamedElementStyledTextProvider {

    public StyledString getStyledText(NamedElement namedElement, String fallback) {
        StyledString styledLabel = new StyledString();
        if (namedElement.getName() == null || namedElement.getName().isEmpty()) {
            styledLabel.append(fallback);
        } else {
            styledLabel.append(namedElement.getName());
        }
        return styledLabel;
    }
}
