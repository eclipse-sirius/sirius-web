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
package org.eclipse.sirius.web.freediagram.flow.styles.nodes;

import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.Fan;
import fr.obeo.dsl.designer.sample.flow.FlowElement;
import fr.obeo.dsl.designer.sample.flow.Processor;
import fr.obeo.dsl.designer.sample.flow.design.FlowServices;

import java.util.Optional;

import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.freediagram.styles.DefaultStyles;
import org.eclipse.sirius.web.freediagram.styles.INodeStyleProvider;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Customize the flow.FlowElement Node/Label styles.
 *
 * @author hmarchadour
 */
public class FlowElementStyleProvider implements INodeStyleProvider {

    public static final String WIFI = "Wifi"; //$NON-NLS-1$

    public static final String RADAR = "Radar"; //$NON-NLS-1$

    public static final String CAMERA = "Camera"; //$NON-NLS-1$

    public static final String CAMERA_IMAGE_PATH = "/images/camera.svg"; //$NON-NLS-1$

    public static final String RADAR_IMAGE_PATH = "/images/radar.svg"; //$NON-NLS-1$

    public static final String ANTENNA_IMAGE_PATH = "/images/antenna.svg"; //$NON-NLS-1$

    private static final String FAN_IMAGE_PATH = "/images/fan.svg"; //$NON-NLS-1$

    private static final String PROCESSOR_IMAGE_PATH = "/images/cpu_standard.svg"; //$NON-NLS-1$

    private static final String SENSOR_IMAGE_PATH = "/images/sensor.svg"; //$NON-NLS-1$

    private final FlowServices flowServices;

    public FlowElementStyleProvider() {
        this.flowServices = new FlowServices();
    }

    @Override
    public String getNodeType(VariableManager variableManager) {
        return NodeType.NODE_IMAGE;
    }

    @Override
    public INodeStyle getNodeStyle(VariableManager variableManager) {
        // @formatter:off
        INodeStyle result = RectangularNodeStyle.newRectangularNodeStyle()
            .color(DefaultStyles.NODE_BACKGROUND_COLOR)
            .borderColor(DefaultStyles.NODE_BORDER_COLOR)
            .borderSize(DefaultStyles.NODE_BORDER_SIZE)
            .borderStyle(DefaultStyles.NODE_BORDER_STYLE)
            .build();
        // @formatter:on
        Optional<FlowElement> dataSource = variableManager.get(VariableManager.SELF, FlowElement.class);
        if (dataSource.isPresent()) {
            FlowElement flowElement = dataSource.get();
            if (flowElement instanceof DataSource) {
                String name = ((DataSource) flowElement).getName();
                String imageURL;
                if (name.contains(CAMERA)) {
                    imageURL = CAMERA_IMAGE_PATH;
                } else if (name.contains(RADAR)) {
                    imageURL = RADAR_IMAGE_PATH;
                } else if (name.contains(WIFI)) {
                    imageURL = ANTENNA_IMAGE_PATH;
                } else {
                    imageURL = SENSOR_IMAGE_PATH;
                }
                int scalingFactor = ((DataSource) flowElement).getVolume();
                // @formatter:off
                result = ImageNodeStyle.newImageNodeStyle()
                   .imageURL(imageURL)
                   .scalingFactor(scalingFactor)
                   .build();
                // @formatter:on
            } else if (flowElement instanceof Processor) {
                int scalingFactor = this.flowServices.sizeFromCapacity((Processor) flowElement);
                // @formatter:off
                result = ImageNodeStyle.newImageNodeStyle()
                   .imageURL(PROCESSOR_IMAGE_PATH)
                   .scalingFactor(scalingFactor)
                   .build();
               // @formatter:on
            } else if (flowElement instanceof Fan) {
                int scalingFactor = ((Fan) flowElement).getSpeed() / 30;
                // @formatter:off
                result = ImageNodeStyle.newImageNodeStyle()
                   .imageURL(FAN_IMAGE_PATH)
                   .scalingFactor(scalingFactor)
                   .build();
               // @formatter:on
            }
        }
        return result;
    }

    @Override
    public LabelStyleDescription getLabelStyleDescription() {
    // @formatter:off
       return LabelStyleDescription.newLabelStyleDescription()
               .colorProvider(vManager -> DefaultStyles.LABEL_FONT_COLOR)
               .fontSizeProvider(vManager -> 8)
               .boldProvider(vManager -> DefaultStyles.LABEL_BOLD)
               .italicProvider(vManager -> DefaultStyles.LABEL_ITALIC)
               .underlineProvider(vManager -> DefaultStyles.LABEL_UNDERLINE)
               .strikeThroughProvider(vManager -> false)
               .iconURLProvider(vManager -> "") //$NON-NLS-1$
               .build();
       // @formatter:on
    }

}
