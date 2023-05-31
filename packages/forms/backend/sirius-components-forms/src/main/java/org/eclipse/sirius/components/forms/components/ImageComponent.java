/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.ImageDescription;
import org.eclipse.sirius.components.forms.elements.ImageElementProps;
import org.eclipse.sirius.components.forms.elements.ImageElementProps.Builder;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the Image.
 *
 * @author pcdavid
 */
public class ImageComponent implements IComponent {

    private final ImageComponentProps props;

    public ImageComponent(ImageComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ImageDescription imageDescription = this.props.getImageDescription();

        String id = imageDescription.getIdProvider().apply(variableManager);
        String label = imageDescription.getLabelProvider().apply(variableManager);
        String iconURL = imageDescription.getIconURLProvider().apply(variableManager);
        String url = imageDescription.getUrlProvider().apply(variableManager);
        String maxWidth = imageDescription.getMaxWidthProvider().apply(variableManager);
        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(imageDescription, variableManager)));

        // @formatter:off
        Builder imageElementPropsBuilder = ImageElementProps.newImageElementProps(id)
                .label(label)
                .url(url)
                .children(children);

        if (iconURL != null) {
            imageElementPropsBuilder.iconURL(iconURL);
        }
        if (maxWidth != null) {
            imageElementPropsBuilder.maxWidth(maxWidth);
        }
        if (imageDescription.getHelpTextProvider() != null) {
            imageElementPropsBuilder.helpTextProvider(() -> imageDescription.getHelpTextProvider().apply(variableManager));
        }
        return new Element(ImageElementProps.TYPE, imageElementPropsBuilder.build());
        // @formatter:on
    }
}
