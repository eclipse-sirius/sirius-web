/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.flow;

import fr.obeo.dsl.designer.sample.flow.FlowPackage;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.freediagram.flow.styles.nodes.FlowElementStyleProvider;
import org.eclipse.sirius.web.freediagram.flow.styles.nodes.SystemStyleProvider;
import org.eclipse.sirius.web.freediagram.services.EdgeDescriptionService;
import org.eclipse.sirius.web.freediagram.services.FreeDiagramService;
import org.eclipse.sirius.web.freediagram.services.NodeDescriptionService;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * Free diagram contribution.
 *
 * @author hmarchadour
 */
public class DiagramDescriptionSupplier implements Supplier<DiagramDescription> {

    public static final String FREE_DIAGRAM_LABEL = "Free Topography"; //$NON-NLS-1$

    public static final UUID DIAGRAM_DESCRIPTION_ID = UUID.nameUUIDFromBytes(FREE_DIAGRAM_LABEL.getBytes());

    public static final UUID SYSTEM_DESCRIPTION_ID = UUID.nameUUIDFromBytes("System".getBytes()); //$NON-NLS-1$

    public static final UUID PROCESSOR_DESCRIPTION_ID = UUID.nameUUIDFromBytes("Processor".getBytes()); //$NON-NLS-1$

    public static final UUID DATA_SOURCE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("DataSource".getBytes()); //$NON-NLS-1$

    public static final UUID FAN_DESCRIPTION_ID = UUID.nameUUIDFromBytes("Fan".getBytes()); //$NON-NLS-1$

    private final NodeDescriptionService nodeDescriptionService;

    private final EdgeDescriptionService edgeDescriptionService;

    private final IObjectService objectService;

    private final FreeDiagramService freeDiagramService;

    public DiagramDescriptionSupplier(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.freeDiagramService = new FreeDiagramService(objectService);
        this.nodeDescriptionService = new NodeDescriptionService(objectService, editService, this.freeDiagramService);
        this.edgeDescriptionService = new EdgeDescriptionService(objectService, editService);
    }

    @Override
    public DiagramDescription get() {

        NodeDescription systemNodeDescription = this.nodeDescriptionService.create(SYSTEM_DESCRIPTION_ID, false, new SystemStyleProvider(this.freeDiagramService));
        FlowElementStyleProvider flowElementStyleProvider = new FlowElementStyleProvider();
        NodeDescription processorNodeDescription = this.nodeDescriptionService.create(PROCESSOR_DESCRIPTION_ID, false, flowElementStyleProvider);
        NodeDescription fanNodeDescription = this.nodeDescriptionService.create(FAN_DESCRIPTION_ID, false, flowElementStyleProvider);
        NodeDescription dataSourceNodeDescription = this.nodeDescriptionService.create(DATA_SOURCE_DESCRIPTION_ID, false, flowElementStyleProvider);

        List<NodeDescription> nodeDescriptions = List.of(systemNodeDescription, processorNodeDescription, fanNodeDescription, dataSourceNodeDescription);
        systemNodeDescription.setChildNodeDescriptions(nodeDescriptions);

        // @formatter:off
        List<EdgeDescription> edgeDescriptions = new EdgeDescriptionsSupplier(this.edgeDescriptionService, processorNodeDescription, dataSourceNodeDescription)
                .get();

        List<ToolSection> toolSections = new ToolSectionsSupplier(this.freeDiagramService, this.objectService, this.edgeDescriptionService, systemNodeDescription, processorNodeDescription,
                fanNodeDescription, dataSourceNodeDescription)
                .get();

        Function<VariableManager, String> targetObjectIdProvider = vM -> vM.get(VariableManager.SELF, EObject.class)
                .map(this.objectService::getId)
                .orElse(null);

        Predicate<VariableManager> canCreatePredicate = vM -> vM.get(IRepresentationDescription.CLASS, EClass.class)
                .map(FlowPackage.Literals.FLOW_ELEMENT::isSuperTypeOf)
                .orElse(false);

        Function<VariableManager, String> getLabel = vM -> vM.get(DiagramDescription.LABEL, String.class).orElse(""); //$NON-NLS-1$
        return DiagramDescription.newDiagramDescription(DIAGRAM_DESCRIPTION_ID)
            .label(FREE_DIAGRAM_LABEL)
            .idProvider(new GetOrCreateRandomIdProvider())
            .targetObjectIdProvider(targetObjectIdProvider)
            .canCreatePredicate(canCreatePredicate)
            .labelProvider(getLabel)
            .nodeDescriptions(nodeDescriptions)
            .edgeDescriptions(edgeDescriptions)
            .toolSections(toolSections)
            .build();
        // @formatter:on
    }

}
