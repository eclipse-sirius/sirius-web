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
package org.eclipse.sirius.components.emf.services;

import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.sirius.components.core.api.IDefaultLabelService;

import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;

/**
 * Default implementation of {@link IDefaultLabelService}.
 *
 * @author mcharfadi
 */
@Service
public class DefaultLabelService implements IDefaultLabelService {

    public static final String DEFAULT_ICON_PATH = "/icons/svg/Default.svg";
    private static final String DEFAULT_LABEL_FEATURE = "name";
    private final LabelFeatureProviderRegistry labelFeatureProviderRegistry;
    private final ComposedAdapterFactory composedAdapterFactory;
    private final Logger logger = LoggerFactory.getLogger(LabelFeatureProviderRegistry.class);

    public DefaultLabelService(LabelFeatureProviderRegistry labelFeatureProviderRegistry, ComposedAdapterFactory composedAdapterFactory) {
        this.labelFeatureProviderRegistry = Objects.requireNonNull(labelFeatureProviderRegistry);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
    }
    @Override
    public String getLabel(Object object) {
        return Optional.of(object).filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .flatMap(eObject -> this.getLabelEAttribute(eObject).map(eObject::eGet))
                .map(Object::toString)
                .orElse("");
    }

    @Override
    public String getFullLabel(Object object) {
        String fullLabel;
        if (object instanceof EObject eObject) {
            fullLabel = eObject.eClass().getName();
            String label = this.getLabel(eObject);
            if (label != null && !label.isEmpty()) {
                fullLabel += " " + label;
            }
        } else {
            fullLabel = this.getLabel(object);
        }
        return fullLabel;
    }

    private Optional<EAttribute> getLabelEAttribute(EObject eObject) {
        Optional<EAttribute> optionalLabelEAttribute = Optional.empty();

        String nsUri = eObject.eClass().getEPackage().getNsURI();
        optionalLabelEAttribute = this.labelFeatureProviderRegistry.getLabelFeatureProvider(nsUri).flatMap(provider -> provider.getLabelEAttribute(eObject));
        if (optionalLabelEAttribute.isEmpty()) {
            optionalLabelEAttribute = this.getDefaultLabelEAttribute(eObject);
        }

        return optionalLabelEAttribute;
    }

    private Optional<EAttribute> getDefaultLabelEAttribute(EObject eObject) {
        Optional<EAttribute> optionalLabelEAttribute = Optional.empty();
        EList<EAttribute> allAttributes = eObject.eClass().getEAllAttributes();
        for (EAttribute eAttribute : allAttributes) {
            if (!eAttribute.isMany() && eAttribute.getEAttributeType().getInstanceClass() == String.class) {
                if (DEFAULT_LABEL_FEATURE.equals(eAttribute.getName())) {
                    optionalLabelEAttribute = Optional.of(eAttribute);
                    break;
                } else if (optionalLabelEAttribute.isEmpty()) {
                    optionalLabelEAttribute = Optional.of(eAttribute);
                }
            }
        }
        return optionalLabelEAttribute;
    }

    @Override
    public List<String> getImagePath(Object object) {
        if (object instanceof EObject eObject) {

            Adapter adapter = this.composedAdapterFactory.adapt(eObject, IItemLabelProvider.class);
            if (adapter instanceof IItemLabelProvider labelProvider && !(adapter instanceof ReflectiveItemProvider)) {
                try {
                    Object image = labelProvider.getImage(eObject);
                    List<String> imageFullPath = this.findImagePath(image);
                    if (imageFullPath != null) {
                        return imageFullPath.stream().map(this::getImageRelativePath).toList();
                    }
                } catch (MissingResourceException exception) {
                    this.logger.warn("Missing icon for {}", eObject);
                }
            }
        }
        return List.of(DEFAULT_ICON_PATH);
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

    @Override
    public Optional<String> getLabelField(Object object) {
        return Optional.of(object)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .flatMap(this::getLabelEAttribute)
                .map(EAttribute::getName);
    }

    @Override
    public boolean isLabelEditable(Object object) {
        boolean isEditable = false;
        if (object instanceof EObject eObject) {
            String nsUri = eObject.eClass().getEPackage().getNsURI();

            Optional<ILabelFeatureProvider> labelFeatureProvider = this.labelFeatureProviderRegistry.getLabelFeatureProvider(nsUri);
            if (labelFeatureProvider.isPresent()) {
                isEditable = labelFeatureProvider.get().isLabelEditable(eObject);
            } else {
                Optional<EAttribute> labelAttribute = this.getDefaultLabelEAttribute(eObject);
                if (labelAttribute.isPresent()) {
                    isEditable = true;
                }
            }
        }
        return isEditable;
    }

}
