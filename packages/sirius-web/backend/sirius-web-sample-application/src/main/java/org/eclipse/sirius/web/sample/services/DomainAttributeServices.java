/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.sample.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.domain.Attribute;
import org.eclipse.sirius.components.domain.DataType;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.view.form.FormDescription;

/**
 * Java services needed to execute the AQL expressions used in the {@link FormDescription} created.
 *
 * @author pcdavid
 */
public final class DomainAttributeServices {

    private final IFeedbackMessageService feedbackMessageService;

    public DomainAttributeServices(IFeedbackMessageService feedbackMessageService) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public List<String> getAvailableDataTypes(EObject self) {
        return DataType.VALUES.stream().map(DataType::getName).toList();
    }

    public String getDataType(Attribute attr) {
        return Optional.ofNullable(attr.getType()).map(DataType::getName).orElse(null);
    }

    public EObject setDataType(Attribute attr, String typeName) {
        var type = DataType.getByName(typeName);
        attr.setType(type);
        if (type == null) {
            this.feedbackMessageService.addFeedbackMessage(new Message("Reverting type of %s to the default type %s".formatted(attr.getName(),
                    this.capitalize(DataType.STRING.getLiteral())), MessageLevel.WARNING));
        }
        return attr;
    }

    public EObject setValue(EObject eObject, String attributeName, Object value) {
        eObject.eSet(eObject.eClass().getEStructuralFeature(attributeName), value);
        return eObject;
    }

    public String capitalize(String str) {
        return str.substring(0, 1).toUpperCase().concat(str.substring(1).toLowerCase());
    }
}
