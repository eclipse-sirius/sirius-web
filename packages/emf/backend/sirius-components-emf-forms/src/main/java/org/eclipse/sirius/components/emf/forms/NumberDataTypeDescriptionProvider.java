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
package org.eclipse.sirius.components.emf.forms;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.forms.api.IEMFFormIfDescriptionProvider;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.emf.forms.api.IWidgetReadOnlyProvider;
import org.eclipse.sirius.components.emf.services.messages.IEMFMessageService;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to provide the description to edit all number types.
 *
 * @author sbegaudeau
 */
@Service
public class NumberDataTypeDescriptionProvider implements IEMFFormIfDescriptionProvider {

    private final IIdentityService identityService;

    private final IPropertiesValidationProvider propertiesValidationProvider;

    private final IEMFMessageService emfMessageService;

    private final IWidgetReadOnlyProvider widgetReadOnlyProvider;

    public NumberDataTypeDescriptionProvider(IIdentityService identityService, IPropertiesValidationProvider propertiesValidationProvider, IEMFMessageService emfMessageService, IWidgetReadOnlyProvider widgetReadOnlyProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.propertiesValidationProvider = Objects.requireNonNull(propertiesValidationProvider);
        this.emfMessageService = Objects.requireNonNull(emfMessageService);
        this.widgetReadOnlyProvider = Objects.requireNonNull(widgetReadOnlyProvider);
    }

    @Override
    public List<IfDescription> getIfDescriptions() {
        Function<VariableManager, String> semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        var dataTypes = List.of(
                EcorePackage.Literals.EINT,
                EcorePackage.Literals.EINTEGER_OBJECT,
                EcorePackage.Literals.EDOUBLE,
                EcorePackage.Literals.EDOUBLE_OBJECT,
                EcorePackage.Literals.EFLOAT,
                EcorePackage.Literals.EFLOAT_OBJECT,
                EcorePackage.Literals.ELONG,
                EcorePackage.Literals.ELONG_OBJECT,
                EcorePackage.Literals.ESHORT,
                EcorePackage.Literals.ESHORT_OBJECT,
                EcorePackage.Literals.EBIG_INTEGER,
                EcorePackage.Literals.EBIG_DECIMAL
        );
        return dataTypes.stream()
                .map(dataType -> new NumberIfDescriptionProvider(dataType, this.propertiesValidationProvider, this.emfMessageService, semanticTargetIdProvider, this.widgetReadOnlyProvider).getIfDescription())
                .toList();
    }
}
