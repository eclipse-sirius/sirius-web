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
package org.eclipse.sirius.components.flow.starter.configuration;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.flow.starter.helper.ColorProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyUnsynchronizedViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyWithAutoLayoutViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.springframework.stereotype.Service;

/**
 * Register the Flow diagram in the application.
 *
 * @author frouene
 */
@Service
public class FlowRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private static final String FLOW_VIEW_DIAGRAM_ID = "FlowDiagram";

    private final IViewConverter viewConverter;

    private final EPackage.Registry ePackagesRegistry;

    private final IInMemoryViewRegistry inMemoryViewRegistry;

    public FlowRepresentationDescriptionProvider(IViewConverter viewConverter, EPackage.Registry ePackagesRegistry, IInMemoryViewRegistry inMemoryViewRegistry) {
        this.viewConverter = Objects.requireNonNull(viewConverter);
        this.ePackagesRegistry = Objects.requireNonNull(ePackagesRegistry);
        this.inMemoryViewRegistry = Objects.requireNonNull(inMemoryViewRegistry);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        ViewBuilder viewBuilder = new ViewBuilder();
        View view = viewBuilder.build();
        IColorProvider colorProvider = new ColorProvider(view);

        view.getColorPalettes().add(this.createColorPalette());

        view.getDescriptions().add(new FlowTopographyViewDiagramDescriptionProvider().create(colorProvider));
        view.getDescriptions().add(new FlowTopographyWithAutoLayoutViewDiagramDescriptionProvider().create(colorProvider));
        view.getDescriptions().add(new FlowTopographyUnsynchronizedViewDiagramDescriptionProvider().create(colorProvider));

        // Add an ID to all view elements
        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        // All programmatic Views need to be stored in a Resource and registered in IInMemoryViewRegistry
        String resourcePath = UUID.nameUUIDFromBytes(FLOW_VIEW_DIAGRAM_ID.getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter(FLOW_VIEW_DIAGRAM_ID));
        resource.getContents().add(view);
        this.inMemoryViewRegistry.register(view);

        // Convert org.eclipse.sirius.components.view.RepresentationDescription to org.eclipse.sirius.components.representations.IRepresentationDescription
        List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast).toList();
        return this.viewConverter.convert(List.of(view), staticEPackages);
    }

    private ColorPalette createColorPalette() {
        var colorPalette = ViewFactory.eINSTANCE.createColorPalette();

        colorPalette.getColors().add(this.createFixedColor("Flow_White", "#FFFFFF"));
        colorPalette.getColors().add(this.createFixedColor("Flow_Gray", "#B1BCBE"));
        colorPalette.getColors().add(this.createFixedColor("Flow_Orange", "#FBA600"));
        colorPalette.getColors().add(this.createFixedColor("Flow_Red", "#DE1000"));
        colorPalette.getColors().add(this.createFixedColor("Flow_Black", "#002B3C"));
        colorPalette.getColors().add(this.createFixedColor("Flow_LightGray", "#F0F0F0"));

        return colorPalette;
    }

    private FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);

        return fixedColor;
    }
}
