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
package org.eclipse.sirius.web.papaya.views.details;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.emf.tables.CursorBasedNavigationServices;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.view.emf.ViewConverterResult;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;
import org.eclipse.sirius.web.papaya.views.details.api.IFormDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * Used to convert the view form description.
 *
 * @author sbegaudeau
 */
@Service
public class FormDescriptionConverter implements IFormDescriptionConverter {

    private final ViewFormDescriptionConverter converter;

    public FormDescriptionConverter(ViewFormDescriptionConverter converter) {
        this.converter = Objects.requireNonNull(converter);
    }

    @Override
    public List<ViewConverterResult> convert(org.eclipse.sirius.components.view.View view) {
        // The FormDescription must be part of View inside a proper EMF Resource to be correctly handled
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///papaya_details");
        Resource resource = new XMIResourceImpl(uri);
        resource.getContents().add(view);

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        // Convert the View-based FormDescription and register the result into the system
        AQLInterpreter interpreter = new AQLInterpreter(List.of(CursorBasedNavigationServices.class), List.of(), List.of(PapayaPackage.eINSTANCE));

        return view.getDescriptions().stream()
                .filter(org.eclipse.sirius.components.view.form.FormDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.form.FormDescription.class::cast)
                .map(viewFormDescription -> this.converter.convert(viewFormDescription, List.of(), interpreter))
                .filter(viewConverterResult -> viewConverterResult.representationDescription() instanceof FormDescription)
                .toList();
    }
}
