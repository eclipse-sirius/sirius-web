/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.components.flow.starter.services;

import fr.obeo.dsl.designer.sample.flow.FlowPackage;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.flow.starter.helper.ColorProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyUnsynchronizedViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.flow.starter.view.FlowTopographyWithAutoLayoutViewDiagramDescriptionProvider;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to initialize the editing context of a flow project.
 *
 * @author sbegaudeau
 */
@Service
public class FlowEditingContextInitializer implements IEditingContextProcessor {

    private final IProjectSearchService projectSearchService;

    public FlowEditingContextInitializer(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        var isFlowProject = new UUIDParser().parse(editingContext.getId())
                .flatMap(this.projectSearchService::findById)
                .filter(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(FlowProjectTemplatesProvider.FLOW_NATURE::equals))
                .isPresent();

        if (isFlowProject && editingContext instanceof EditingContext emfEditingContext) {
            var packageRegistry = emfEditingContext.getDomain().getResourceSet().getPackageRegistry();
            packageRegistry.put(FlowPackage.eNS_URI, FlowPackage.eINSTANCE);

            emfEditingContext.getViews().add(this.getFlowView());
        }
    }

    private View getFlowView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View view = viewBuilder.build();
        IColorProvider colorProvider = new ColorProvider(view);

        view.getColorPalettes().add(this.createColorPalette());

        view.getDescriptions().add(new FlowTopographyViewDiagramDescriptionProvider().create(colorProvider));
        view.getDescriptions().add(new FlowTopographyWithAutoLayoutViewDiagramDescriptionProvider().create(colorProvider));
        view.getDescriptions().add(new FlowTopographyUnsynchronizedViewDiagramDescriptionProvider().create(colorProvider));

        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("FlowView".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("FlowView"));
        resource.getContents().add(view);

        return view;
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
