/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.api.IDefaultEMFLabelService;
import org.eclipse.sirius.components.emf.services.api.IDefaultLabelFeatureProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The default implementation used to manipulate the label of EMF objects.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultEMFLabelService implements IDefaultEMFLabelService {

    private final ComposedAdapterFactory composedAdapterFactory;

    private final IDefaultLabelFeatureProvider defaultLabelFeatureProvider;

    private final Logger logger = LoggerFactory.getLogger(DefaultEMFLabelService.class);

    public DefaultEMFLabelService(ComposedAdapterFactory composedAdapterFactory, IDefaultLabelFeatureProvider defaultLabelFeatureProvider) {
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.defaultLabelFeatureProvider = Objects.requireNonNull(defaultLabelFeatureProvider);
    }

    @Override
    public StyledString getStyledLabel(EObject self) {
        var label = this.defaultLabelFeatureProvider.getDefaultLabelEAttribute(self)
                .map(self::eGet)
                .map(Object::toString)
                .orElse("");
        if (label.isBlank()) {
            label = self.eClass().getName();
        }
        return StyledString.of(label);
    }

    @Override
    public List<String> getImagePaths(EObject self) {
        List<String> result = List.of("/icons/svg/Default.svg");

        Adapter adapter = this.composedAdapterFactory.adapt(self, IItemLabelProvider.class);
        if (adapter instanceof IItemLabelProvider labelProvider && !(adapter instanceof ReflectiveItemProvider)) {
            try {
                Object image = labelProvider.getImage(self);
                List<String> imageFullPath = this.findImagePath(image);
                if (imageFullPath != null) {
                    result = imageFullPath.stream().map(this::getImageRelativePath).toList();
                }
            } catch (MissingResourceException exception) {
                this.logger.warn("Missing icon for {}", self);
            }
        }
        return result;
    }

    private List<String> findImagePath(Object image) {
        List<String> imagePath = null;
        if (image instanceof URI uri) {
            imagePath = List.of(uri.toString());
        } else if (image instanceof URL url) {
            imagePath = List.of(url.toString());
        } else if (image instanceof ComposedImage composite) {
            imagePath = composite.getImages().stream()
                    .map(this::findImagePath)
                    .flatMap(Collection::stream)
                    .filter(Objects::nonNull)
                    .toList();
        }
        return imagePath;
    }

    private String getImageRelativePath(String imageFullPath) {
        String imageRelativePath = null;
        String[] uriSplit = imageFullPath.split("!");
        if (uriSplit.length > 1) {
            imageRelativePath = uriSplit[uriSplit.length - 1];
        } else {
            // in development mode, when the image is not contained in a jar
            uriSplit = imageFullPath.split("target/classes");
            if (uriSplit.length > 1) {
                imageRelativePath = uriSplit[uriSplit.length - 1];
            }
        }
        return imageRelativePath;
    }
}
