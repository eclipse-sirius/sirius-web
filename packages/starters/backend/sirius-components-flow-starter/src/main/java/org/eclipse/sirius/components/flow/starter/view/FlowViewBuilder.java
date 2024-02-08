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

    public static final String ANTENNA_SVG_ID = "594ba1cf-6e77-311b-a2ab-660c5eabf015";
    public static final String CAMERA_SVG_ID = "ae1a67e3-d455-3198-9f35-372ef2a3006d";
    public static final String RADAR_SVG_ID = "a6e0845d-971f-33c7-ae25-6ac4f47056dc";
    public static final String SENSOR_SVG_ID = "6c07834a-5ff6-3819-8326-0614d3dcd674";
    public static final String FAN_SVG_ID = "9019f843-5511-3318-8f89-0e50994c98e8";
    public static final String POWER_OUTPUT_SVG_ID = "bca5ab3c-b13c-38b4-910d-3ec4b8e81321";
    public static final String POWER_INPUT_SVG_ID = "f9219f29-df22-31a6-9473-5955f5962287";
    public static final String ENGINE_HIGH_SVG_ID = "0449ce13-3eed-3426-a9f7-d1f68c8a2b8f";
    public static final String ENGINE_LOW_SVG_ID = "b0617610-43e0-3636-b7eb-c77aec046f5f";
    public static final String ENGINE_OVER_SVG_ID = "a960705f-dab1-3dbb-b1e4-ce8d71b51317";
    public static final String ENGINE_STANDARD_SVG_ID = "716d8b5c-015b-3591-984c-d5ed5ee80353";
    public static final String ENGINE_UNUSED_SVG_ID = "91feeafe-b539-3d85-a6f3-27e8aa4049de";
    public static final String DSP_HIGH_SVG_ID = "aed8c38e-1af1-3158-a100-dfb866c8ee41";
    public static final String DSP_LOW_SVG_ID = "c225260a-3b75-3ae6-b88c-037d675553e6";
    public static final String DSP_OVER_SVG_ID = "7b8fe180-0203-345f-a32a-ae6c86c1d1f8";
    public static final String DSP_STANDARD_SVG_ID = "6072ed0b-5df2-3348-ad88-90c9d0fbb3ca";
    public static final String DSP_UNUSED_SVG_ID = "6e63d73e-4005-3bcb-aab5-ad85dcd01881";
    public static final String CPU_HIGH_SVG_ID = "20494133-0e68-3bd6-b2fe-c83cb4241085";
    public static final String CPU_LOW_SVG_ID = "5a705cd6-82cc-30f5-b5c8-26fbc9fae8e8";
    public static final String CPU_OVER_SVG_ID = "499082ab-cf8e-37c8-9c06-52ce8cce6746";
    public static final String CPU_STANDARD_SVG_ID = "7f7de740-1ad1-3060-ba8a-3d7f31e885f7";
    public static final String CPU_UNUSED_SVG_ID = "fe439901-ad7a-3baa-b3b7-44c6cd53afcb";

    private final ViewBuilders viewBuilderHelper = new ViewBuilders();
    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    public ImageNodeStyleDescription createImageNodeStyleDescription(String shapeId, IColorProvider colorProvider) {
        return this.diagramBuilderHelper.newImageNodeStyleDescription()
                .shape(shapeId)
                .color(colorProvider.getColor("transparent"))
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
                        .build())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();
    }

    public OutsideLabelDescription getOutsideLabelDescription(IColorProvider colorProvider, String labelExpression) {
        return this.diagramBuilderHelper.newOutsideLabelDescription()
                .labelExpression(labelExpression)
                .style(this.diagramBuilderHelper.newOutsideLabelStyle()
                        .labelColor(colorProvider.getColor("Flow_Black"))
                        .build())
                .position(OutsideLabelPosition.BOTTOM_CENTER)
                .build();
    }

}
