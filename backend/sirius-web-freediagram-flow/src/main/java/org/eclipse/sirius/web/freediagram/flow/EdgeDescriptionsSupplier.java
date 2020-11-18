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

import fr.obeo.dsl.designer.sample.flow.DataFlow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.freediagram.flow.behaviors.DataFlowBehavior;
import org.eclipse.sirius.web.freediagram.flow.styles.edges.DataSourceToProcessorStyleProvider;
import org.eclipse.sirius.web.freediagram.services.EdgeDescriptionService;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Free diagram edge descriptions contribution.
 *
 * @author hmarchadour
 */
public class EdgeDescriptionsSupplier implements Supplier<List<EdgeDescription>> {

    public static final UUID DATA_FLOW_DATA_SOURCE_2_PROCESSOR_DESCRIPTION_ID = UUID.nameUUIDFromBytes("DataFlowDataSource2Processor".getBytes()); //$NON-NLS-1$

    public static final UUID DATA_FLOW_PROCESSOR_2_DATA_SOURCE_DESCRIPTION_ID = UUID.nameUUIDFromBytes("DataFlowDataProcessor2Source".getBytes()); //$NON-NLS-1$

    public static final UUID DATA_FLOW_PROCESSOR_2_PROCESSOR_DESCRIPTION_ID = UUID.nameUUIDFromBytes("DataFlowDataProcessor2Processor".getBytes()); //$NON-NLS-1$

    private static final String CENTER_LABEL_SUFFIX = "_centerlabel"; //$NON-NLS-1$

    private final EdgeDescriptionService edgeDescriptionService;

    private final NodeDescription processorNodeDescription;

    private final NodeDescription dataSourceNodeDescription;

    private final DataFlowBehavior dataFlowBehavior;

    private final DataSourceToProcessorStyleProvider edgeStyleProvider;

    public EdgeDescriptionsSupplier(EdgeDescriptionService edgeDescriptionService, NodeDescription processorNodeDescription, NodeDescription dataSourceNodeDescription) {
        this.edgeDescriptionService = Objects.requireNonNull(edgeDescriptionService);
        this.processorNodeDescription = Objects.requireNonNull(processorNodeDescription);
        this.dataSourceNodeDescription = Objects.requireNonNull(dataSourceNodeDescription);
        this.dataFlowBehavior = new DataFlowBehavior();
        this.edgeStyleProvider = new DataSourceToProcessorStyleProvider();
    }

    @Override
    public List<EdgeDescription> get() {
        List<EdgeDescription> edgeDescriptions = new ArrayList<>();
        edgeDescriptions.add(this.createDataSourceToProcessorDescription());
        edgeDescriptions.add(this.createProcessorToDataSourceDescription());
        edgeDescriptions.add(this.createProcessorToProcessorDescription());
        return edgeDescriptions;
    }

    private EdgeDescription createDataSourceToProcessorDescription() {
        EdgeDescription result = this.edgeDescriptionService.create(DATA_FLOW_DATA_SOURCE_2_PROCESSOR_DESCRIPTION_ID, this.dataFlowBehavior, this.edgeStyleProvider,
                List.of(this.dataSourceNodeDescription), List.of(this.processorNodeDescription));
        result = EdgeDescription.newEdgeDescription(result).centerLabelProvider(this.createLabelProvider()).build();
        return result;
    }

    private EdgeDescription createProcessorToDataSourceDescription() {
        EdgeDescription result = this.edgeDescriptionService.create(DATA_FLOW_DATA_SOURCE_2_PROCESSOR_DESCRIPTION_ID, this.dataFlowBehavior, this.edgeStyleProvider,
                List.of(this.processorNodeDescription), List.of(this.dataSourceNodeDescription));
        result = EdgeDescription.newEdgeDescription(result).centerLabelProvider(this.createLabelProvider()).build();
        return result;
    }

    private EdgeDescription createProcessorToProcessorDescription() {
        EdgeDescription result = this.edgeDescriptionService.create(DATA_FLOW_PROCESSOR_2_PROCESSOR_DESCRIPTION_ID, this.dataFlowBehavior, this.edgeStyleProvider,
                List.of(this.processorNodeDescription), List.of(this.processorNodeDescription));
        result = EdgeDescription.newEdgeDescription(result).centerLabelProvider(this.createLabelProvider()).build();
        return result;
    }

    private Function<VariableManager, Optional<Label>> createLabelProvider() {
        return variableManager -> {
            String ownerId = variableManager.get(LabelDescription.OWNER_ID, String.class).orElse(""); //$NON-NLS-1$

            Optional<String> optionalLabel = variableManager.get(VariableManager.SELF, DataFlow.class).map(dataFlow -> dataFlow.getLoad() + "/" + dataFlow.getCapacity()); //$NON-NLS-1$

            String labelId = ownerId + CENTER_LABEL_SUFFIX;
            LabelStyle style = this.edgeStyleProvider.getLabelStyle(variableManager);
            // @formatter:off
            return optionalLabel.map(
                label -> Label.newLabel(labelId)
                .type("label:inside-center") //$NON-NLS-1$
                .text(label)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .style(style)
                .build()
            );
            // @formatter:on
        };
    }

}
