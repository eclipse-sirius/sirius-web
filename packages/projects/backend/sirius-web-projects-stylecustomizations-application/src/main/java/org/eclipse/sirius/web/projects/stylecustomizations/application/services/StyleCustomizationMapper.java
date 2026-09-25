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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services;

import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationMapper;
import org.springframework.stereotype.Component;

/**
 * Used to convert a description of style customization to a DTO.
 *
 * @author gcoutable
 */
@Component
public class StyleCustomizationMapper implements IStyleCustomizationMapper {
    @Override
    public StyleCustomizationDTO toDTO(StyleCustomizationDescription styleCustomizationDescription) {
        return new StyleCustomizationDTO(styleCustomizationDescription.id(), styleCustomizationDescription.label(), styleCustomizationDescription.description(), true);
    }
}
