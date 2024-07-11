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
package org.eclipse.sirius.components.flow.starter.view;

import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.DeleteTool;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LabelEditTool;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelPosition;

/**
 * Used to help creating the Flow view.
 *
 * @author frouene
 */
public class FlowViewBuilder {

    public static final String ANTENNA_SVG_ID = "/flow-images/antenna.svg";
    public static final String CAMERA_SVG_ID = "/flow-images/camera.svg";
    public static final String RADAR_SVG_ID = "/flow-images/radar.svg";
    public static final String SENSOR_SVG_ID = "/flow-images/sensor.svg";
    public static final String FAN_SVG_ID = "/flow-images/fan.svg";
    public static final String POWER_OUTPUT_SVG_ID = "/flow-images/power-output.svg";
    public static final String POWER_INPUT_SVG_ID = "/flow-images/power-input.svg";
    public static final String ENGINE_HIGH_SVG_ID = "/flow-images/chipset2_high.svg";
    public static final String ENGINE_LOW_SVG_ID = "/flow-images/chipset2_low.svg";
    public static final String ENGINE_OVER_SVG_ID = "/flow-images/chipset2_over.svg";
    public static final String ENGINE_STANDARD_SVG_ID = "/flow-images/chipset2_standard.svg";
    public static final String ENGINE_UNUSED_SVG_ID = "/flow-images/chipset2_unused.svg";
    public static final String DSP_HIGH_SVG_ID = "/flow-images/chipset_high.svg";
    public static final String DSP_LOW_SVG_ID = "/flow-images/chipset_low.svg";
    public static final String DSP_OVER_SVG_ID = "/flow-images/chipset_over.svg";
    public static final String DSP_STANDARD_SVG_ID = "/flow-images/chipset_standard.svg";
    public static final String DSP_UNUSED_SVG_ID = "/flow-images/chipset_unused.svg";
    public static final String CPU_HIGH_SVG_ID = "/flow-images/cpu_high.svg";
    public static final String CPU_LOW_SVG_ID = "/flow-images/cpu_low.svg";
    public static final String CPU_OVER_SVG_ID = "/flow-images/cpu_over.svg";
    public static final String CPU_STANDARD_SVG_ID = "/flow-images/cpu_standard.svg";
    public static final String CPU_UNUSED_SVG_ID = "/flow-images/cpu_unused.svg";

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();
    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    public ImageNodeStyleDescription createImageNodeStyleDescription(String shapeId, IColorProvider colorProvider) {
        return this.diagramBuilderHelper.newImageNodeStyleDescription()
                .shape(shapeId)
                .borderColor(colorProvider.getColor("transparent"))
                .borderSize(0)
                .build();
    }

    public DeleteTool createDeleteTool() {
        var changeContext = this.viewBuilderHelper.newChangeContext()
                .expression("aql:self.defaultDelete()");

        return this.diagramBuilderHelper.newDeleteTool()
                .name("Delete from Model")
                .body(changeContext.build())
                .build();
    }

    public LabelEditTool createLabelEditTool() {
        var callEditService = this.viewBuilderHelper.newChangeContext()
                .expression("aql:self.defaultEditLabel(newLabel)");

        return this.diagramBuilderHelper.newLabelEditTool()
                .name("Edit")
                .body(callEditService.build())
                .build();
    }

    public EdgeTool createEdgeToProcessorTool(NodeDescription processorNodeDescription) {

        var setValueCapacity = this.viewBuilderHelper.newSetValue()
                .featureName("capacity")
                .valueExpression("6");
        var setValueLoad = this.viewBuilderHelper.newSetValue()
                .featureName("load")
                .valueExpression("6");

        var setValueSource = this.viewBuilderHelper.newSetValue()
                .featureName("source")
                .valueExpression("var:semanticEdgeSource");

        var setValueTarget = this.viewBuilderHelper.newSetValue()
                .featureName("target")
                .valueExpression("var:semanticEdgeTarget");

        var changeContextNewInstance = this.viewBuilderHelper.newChangeContext()
                .expression("aql:newInstance")
                .children(setValueTarget.build(), setValueSource.build(), setValueCapacity.build(), setValueLoad.build());

        var createInstance = this.viewBuilderHelper.newCreateInstance()
                .typeName("flow.DataFlow")
                .referenceName("outgoingFlows")
                .variableName("newInstance")
                .children(changeContextNewInstance.build());

        var changeContext = this.viewBuilderHelper.newChangeContext()
                .expression("var:semanticEdgeSource")
                .children(createInstance.build());

        return this.diagramBuilderHelper.newEdgeTool()
                .name("Indirect Dependencies")
                .targetElementDescriptions(processorNodeDescription)
                .body(changeContext.build())
                .build();
    }


    public InsideLabelDescription getInsideLabelDescription(IColorProvider colorProvider, String labelExpression) {
        return this.getInsideLabelDescription(colorProvider, labelExpression, false, false, false);
    }


    public InsideLabelDescription getInsideLabelDescription(IColorProvider colorProvider, String labelExpression, boolean bold, boolean withHeader, boolean displayHeaderSeparator) {
        return this.diagramBuilderHelper.newInsideLabelDescription()
                .labelExpression(labelExpression)
                .style(this.diagramBuilderHelper.newInsideLabelStyle()
                        .labelColor(colorProvider.getColor("Flow_Black"))
                        .bold(bold)
                        .withHeader(withHeader)
                        .displayHeaderSeparator(displayHeaderSeparator)
                        .borderSize(0)
                        .build())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();
    }

    public OutsideLabelDescription getOutsideLabelDescription(IColorProvider colorProvider, String labelExpression) {
        return this.diagramBuilderHelper.newOutsideLabelDescription()
                .labelExpression(labelExpression)
                .style(this.diagramBuilderHelper.newOutsideLabelStyle()
                        .labelColor(colorProvider.getColor("Flow_Black"))
                        .borderSize(0)
                        .build())
                .position(OutsideLabelPosition.BOTTOM_CENTER)
                .build();
    }

}
