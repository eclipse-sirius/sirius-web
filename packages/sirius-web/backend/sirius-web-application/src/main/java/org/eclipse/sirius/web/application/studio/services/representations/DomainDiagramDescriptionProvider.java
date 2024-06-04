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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.DefaultViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.DefaultColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.ArrangeLayoutDirection;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.application.studio.services.representations.api.IDomainDiagramDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the diagram description for domains.
 *
 * @author sbegaudeau
 */
@Service
public class DomainDiagramDescriptionProvider implements IEditingContextProcessor, IDomainDiagramDescriptionProvider {

    public static final String MAIN_COLOR = "Main";

    public static final String LIGHT_GREY_COLOR = "Light Grey";

    public static final String GREY_COLOR = "Grey";

    public static final String BACKGROUND_COLOR = "Background";

    public static final String BLACK_COLOR = "Black";

    private static final String DOMAIN_VIEW_DIAGRAM_ID = "DomainDiagram";

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    public DomainDiagramDescriptionProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate, IDiagramIdProvider diagramIdProvider) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.view = this.createView();
    }

    @Override
    public String getDescriptionId() {
        return this.view.getDescriptions().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .findFirst()
                .map(this.diagramIdProvider::getId)
                .orElse("");
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext && this.studioCapableEditingContextPredicate.test(editingContext)) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    private View createView() {
        var domainDiagramDescription = this.domainDiagramDescription();

        var domainView = new ViewBuilders()
                .newView()
                .colorPalettes(this.colorPalette())
                .descriptions(domainDiagramDescription)
                .build();

        IColorProvider colorProvider = new DefaultColorProvider(domainView);
        var cache = new DefaultViewDiagramElementFinder();

        var diagramElementDescriptionProviders = List.of(
                new EntityNodeDescriptionProvider(colorProvider),
                new InheritanceEdgeDescriptionProvider(colorProvider),
                new RelationEdgeDescriptionProvider(colorProvider)
        );
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(domainDiagramDescription, cache));

        domainView.eAllContents().forEachRemaining(eObject -> {
            var id = UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
            eObject.eAdapters().add(new IDAdapter(id));
        });

        String resourcePath = UUID.nameUUIDFromBytes(DOMAIN_VIEW_DIAGRAM_ID.getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter(DOMAIN_VIEW_DIAGRAM_ID));
        resource.getContents().add(domainView);

        return domainView;
    }

    private DiagramDescription domainDiagramDescription() {
        var newEntitySetValue = new ViewBuilders().newSetValue()
                .featureName("name")
                .valueExpression("NewEntity")
                .build();

        var newEntityChangeContext = new ViewBuilders().newChangeContext()
                .expression("aql:newEntity")
                .children(newEntitySetValue)
                .build();

        var createEntityInstance = new ViewBuilders().newCreateInstance()
                .referenceName("types")
                .typeName("domain::Entity")
                .variableName("newEntity")
                .children(newEntityChangeContext)
                .build();

        var initialChangeContext = new ViewBuilders().newChangeContext()
                .expression("aql:self")
                .children(createEntityInstance)
                .build();

        var newEntityNodeTool = new DiagramBuilders().newNodeTool()
                .name("New entity")
                .body(initialChangeContext)
                .build();

        var palette = new DiagramBuilders().newDiagramPalette()
                .nodeTools(newEntityNodeTool)
                .build();

        return new DiagramBuilders()
                .newDiagramDescription()
                .name("Domain")
                .domainType("domain::Domain")
                .titleExpression("aql:'Domain'")
                .palette(palette)
                .arrangeLayoutDirection(ArrangeLayoutDirection.DOWN)
                .build();
    }

    private ColorPalette colorPalette() {
        var main = new ViewBuilders()
                .newFixedColor()
                .name(MAIN_COLOR)
                .value("rgb(251, 184, 0)")
                .build();

        var lightGrey = new ViewBuilders()
                .newFixedColor()
                .name(LIGHT_GREY_COLOR)
                .value("rgb(117, 117, 117)")
                .build();

        var grey = new ViewBuilders()
                .newFixedColor()
                .name(GREY_COLOR)
                .value("rgb(66, 66, 66)")
                .build();

        var background = new ViewBuilders()
                .newFixedColor()
                .name(BACKGROUND_COLOR)
                .value("rgb(250, 250, 250)")
                .build();

        var black = new ViewBuilders()
                .newFixedColor()
                .name(BLACK_COLOR)
                .value("rgb(0, 0, 0)")
                .build();

        return new ViewBuilders()
                .newColorPalette()
                .colors(main, lightGrey, grey, background, black)
                .build();
    }

}
