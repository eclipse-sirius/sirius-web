/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import java.util.ArrayList;
import java.util.Arrays;
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
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.DefaultViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.KeyBindingBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
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

    private static final String DROP_ATTRIBUTES_TOOL_NAME = "Create new entity from attributes";

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
        if (editingContext instanceof EditingContext siriusWebEditingContext && this.studioCapableEditingContextPredicate.test(editingContext.getId())) {
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

        domainDiagramDescription.getNodeDescriptions().stream()
            .filter(nodeDescription -> nodeDescription.getName().equals(EntityNodeDescriptionProvider.ENTITY_NODE_DESCRIPTION_NAME))
            .flatMap(entityNodeScription -> entityNodeScription.getChildrenDescriptions().stream())
            .filter(nodeDescription -> nodeDescription.getName().equals(EntityNodeDescriptionProvider.ATTRIBUTE_NODE_DESCRIPTION_NAME))
            .findFirst()
            .ifPresent(attributeNodeDescription -> domainDiagramDescription.getPalette().getDropNodeTool().getAcceptedNodeTypes().add(attributeNodeDescription));

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
        var newEntity = "newEntity";
        var newEntityExpr =  "aql:newEntity";
        var initialChangeContext = new ViewBuilders().newChangeContext()
                .expression("aql:self")
                .children(this.withNewEntity("NewEntity", newEntity))
                .build();

        var newEntityNodeTool = new DiagramBuilders().newNodeTool()
                .name("New entity")
                .body(initialChangeContext)
                .keyBindings(
                        new KeyBindingBuilder()
                                .ctrl(true)
                                .key("e")
                                .build(),
                        new KeyBindingBuilder()
                                .key("e")
                                .meta(true)
                                .build()
                )
                .build();

        var dropAttributesTool = new DiagramBuilders().newDropNodeTool()
                .name(DROP_ATTRIBUTES_TOOL_NAME)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:targetElement")
                                .children(
                                        this.withNewEntity(
                                                "AbstractEntity",
                                                newEntity,
                                                this.setValue(newEntityExpr, "abstract", "aql:true"),
                                                this.forEach("droppedAttribute", "aql:droppedElements",
                                                        this.setValue("aql:droppedAttribute.eContainer()", "superTypes", newEntityExpr),
                                                        this.setValue(newEntityExpr, "attributes", "aql:droppedAttribute")
                                                )
                                        )
                                )
                                .build()
                )
                .build();

        var palette = new DiagramBuilders().newDiagramPalette()
                .nodeTools(newEntityNodeTool)
                .dropNodeTool(dropAttributesTool)
                .build();

        var toolbar = new DiagramBuilders().newDiagramToolbar()
            .expandedByDefault(true)
            .build();

        var diagramDescription = new DiagramBuilders()
                .newDiagramDescription()
                .name("Domain")
                .domainType("domain::Domain")
                .titleExpression("aql:'Domain'")
                .palette(palette)
                .toolbar(toolbar)
                .arrangeLayoutDirection(ArrangeLayoutDirection.DOWN)
                .build();

        return diagramDescription;
    }

    /**
     * Create a new Entity with the specified name, and execute the given body in the context if the new instance.
     */
    private Operation withNewEntity(String entityName, String variableName, Operation... body) {
        var fullBody = new ArrayList<Operation>();
        fullBody.add(new ViewBuilders().newSetValue().featureName("name").valueExpression(entityName).build());
        fullBody.addAll(Arrays.asList(body));

        return new ViewBuilders().newCreateInstance()
             .referenceName("types")
             .typeName("domain::Entity")
             .variableName(variableName)
             .children(new ViewBuilders().newChangeContext()
                     .expression("aql:" + variableName)
                     .children(fullBody.toArray(new Operation[0]))
                     .build())
             .build();
    }


    private Operation forEach(String iteratorName, String iterationExpression, Operation... body) {
        return new ViewBuilders().newFor()
                .iteratorName(iteratorName)
                .expression(iterationExpression)
                .children(body)
                .build();
    }

    private Operation setValue(String targetExpression, String featureName, String valueExpression) {
        return new ViewBuilders().newChangeContext()
                .expression(targetExpression)
                .children(new ViewBuilders().newSetValue()
                        .featureName(featureName)
                        .valueExpression(valueExpression)
                        .build()
                )
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
