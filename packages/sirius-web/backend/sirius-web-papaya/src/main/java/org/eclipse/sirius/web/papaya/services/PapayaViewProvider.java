/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.services;

import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.DefaultColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.papaya.representations.classdiagram.ClassDiagramDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.ComponentDiagramDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.api.IPapayaViewProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create the view model used by Papaya.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaViewProvider implements IPapayaViewProvider {

    @Override
    public View create() {
        var colorPalette = new PapayaColorPaletteProvider().getColorPalette();
        var view = new ViewBuilders().newView()
                .colorPalettes(
                        colorPalette
                )
                .build();

        IColorProvider colorProvider = new DefaultColorProvider(view);
        var componentDiagramDescription = new ComponentDiagramDescriptionProvider().create(colorProvider);
        var classDiagramDescription = new ClassDiagramDescriptionProvider().create(colorProvider);

        view.getDescriptions().add(componentDiagramDescription);
        view.getDescriptions().add(classDiagramDescription);

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("PapayaView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("PapayaView"));
        resource.getContents().add(view);

        return view;
    }
}
