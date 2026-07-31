/*******************************************************************************
 * Copyright (c) 2026 CEA LIST.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormCreationService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.springframework.stereotype.Service;

/**
 * Service used to create forms.
 *
 * @author pdeville
 */
@Service
public class FormCreationService implements IFormCreationService {

    private final IIdentityService identityService;

    public FormCreationService(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public Form create(Object targetObject, FormDescription formDescription) {
        return Form.newForm(UUID.randomUUID().toString())
                .targetObjectId(this.identityService.getId(targetObject))
                .descriptionId(formDescription.getId())
                .pages(List.of())
                .build();
    }

}
