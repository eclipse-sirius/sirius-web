/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.papaya.provider.spec;

import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.sirius.components.papaya.Record;
import org.eclipse.sirius.components.papaya.provider.RecordItemProvider;
import org.eclipse.sirius.components.papaya.provider.spec.images.VisibilityOverlayImageProvider;

/**
 * Customization of the item provider implementation generated by EMF.
 *
 * @author sbegaudeau
 */
public class RecordItemProviderSpec extends RecordItemProvider {
    public RecordItemProviderSpec(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public Object getImage(Object object) {
        if (object instanceof Record record) {
            var visibilityImage = new VisibilityOverlayImageProvider().overlayImage(this.getResourceLocator(), record.getVisibility());

            return new ComposedImage(List.of(
                    this.getResourceLocator().getImage("papaya/full/obj16/Record.svg"),
                    visibilityImage
            ));
        }
        return this.overlayImage(object, this.getResourceLocator().getImage("papaya/full/obj16/Record.svg"));
    }

    @Override
    public Object getStyledText(Object object) {
        if (object instanceof Record record && record.getName() != null && !record.getName().isBlank()) {
            StyledString styledLabel = new StyledString();
            styledLabel.append(record.getName());

            if (!record.getTypeParameters().isEmpty()) {
                styledLabel.append("<", PapayaStyledStringStyles.DECORATOR_STYLE);

                for (var i = 0; i < record.getTypeParameters().size(); i++) {
                    var typeParameter = record.getTypeParameters().get(i);
                    styledLabel.append(typeParameter.getName(), PapayaStyledStringStyles.GENERIC_TYPE_STYLE);
                    if (i < record.getTypeParameters().size() - 1) {
                        styledLabel.append(", ", PapayaStyledStringStyles.DECORATOR_STYLE);
                    }
                }

                styledLabel.append(">", PapayaStyledStringStyles.DECORATOR_STYLE);
            }
        }
        return super.getStyledText(object);
    }
}
