/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProvider;
import org.eclipse.sirius.components.core.api.IDefaultObjectService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Default implementation of the IDefaultObjectService.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class DefaultObjectService implements IDefaultObjectService {

    public static final String DEFAULT_ICON_PATH = "/icons/svg/Default.svg";

    private static final String DEFAULT_LABEL_FEATURE = "name";

    private static final String ID_SEPARATOR = "#";

    private final IEMFKindService emfKindService;

    private final ComposedAdapterFactory composedAdapterFactory;

    private final LabelFeatureProviderRegistry labelFeatureProviderRegistry;

    private final Logger logger = LoggerFactory.getLogger(DefaultObjectService.class);

    public DefaultObjectService(IEMFKindService emfKindService, ComposedAdapterFactory composedAdapterFactory, LabelFeatureProviderRegistry labelFeatureProviderRegistry) {
        this.emfKindService = Objects.requireNonNull(emfKindService);
        this.composedAdapterFactory = Objects.requireNonNull(composedAdapterFactory);
        this.labelFeatureProviderRegistry = Objects.requireNonNull(labelFeatureProviderRegistry);
    }

    @Override
    public String getId(Object object) {
        String id = null;
        if (object instanceof EObject eObject) {

            id = this.getIdFromIDAdapter(eObject);
            if (id == null) {
                id = this.getIdFromURIFragment(eObject);
            }
            if (id == null && eObject.eIsProxy()) {
                id = ((InternalEObject) eObject).eProxyURI().toString();
            }
        }
        return id;

    }

    @Override
    public String getKind(Object object) {
        if (object instanceof EObject eObject) {
            return this.emfKindService.getKind(eObject.eClass());
        }
        return "";
    }

    private String getIdFromIDAdapter(EObject eObject) {
        return eObject.eAdapters().stream()
                .filter(IDAdapter.class::isInstance)
                .map(IDAdapter.class::cast)
                .findFirst()
                .map(IDAdapter::getId)
                .map(Object::toString)
                .orElse(null);
    }

    private String getIdFromURIFragment(EObject eObject) {
        Resource resource = eObject.eResource();
        String id = null;
        if (resource != null && resource.getURI() != null) {
            ResourceSet resourceSet = resource.getResourceSet();
            if (resourceSet != null && resourceSet.getResources().contains(resource)) {
                id = resource.getURI().lastSegment() + ID_SEPARATOR + resource.getURIFragment(eObject);
            } else {
                // In order to getObject method can retrieve the object from the id, we need to return the full URI for
                // resources that are in the PackageRegistry
                id = resource.getURI() + ID_SEPARATOR + resource.getURIFragment(eObject);
            }
        }
        return id;
    }

    @Override
    public String getLabel(Object object) {
        return this.getStyledLabel(object).toString();
    }

    public StyledString getStyledLabel(Object object) {
        var label = Optional.of(object).filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .flatMap(eObject -> this.getLabelEAttribute(eObject).map(eObject::eGet))
                .map(Object::toString)
                .orElse("");
        return StyledString.of(label);
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

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .flatMap(resourceSet -> {
                    Optional<EObject> optionalEObject = Optional.empty();

                    int index = objectId.indexOf(ID_SEPARATOR);
                    if (index != -1) {
                        String resourceLastSegment = objectId.substring(0, index);
                        String eObjectURIFragment = objectId.substring(index + ID_SEPARATOR.length());
                        optionalEObject = resourceSet.getResources().stream()
                                .filter(resource -> resourceLastSegment.equals(resource.getURI().lastSegment())).findFirst()
                                .map(resource -> resource.getEObject(eObjectURIFragment));
                    } else {
                        optionalEObject = resourceSet.getResources().stream()
                                .flatMap(resource -> Optional.ofNullable(resource.getEObject(objectId)).stream())
                                .findFirst();
                    }

                    // If not found in the resources of the ResourceSet, we search in the PackageRegistry resources
                    if (optionalEObject.isEmpty()) {
                        URI uri = URI.createURI(objectId);
                        if (uri.hasFragment()) {
                            EObject eObject = resourceSet.getEObject(uri, false);
                            optionalEObject = Optional.ofNullable(eObject);
                        }
                    }
                    return optionalEObject;
                });
    }

    @Override
    public List<Object> getContents(IEditingContext editingContext, String objectId) {
        List<Object> contents = new ArrayList<>();
        Optional<EObject> optionalEObject = this.getObject(editingContext, objectId)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);

        if (optionalEObject.isPresent()) {
            EObject eObject = optionalEObject.get();
            Adapter adapter = this.composedAdapterFactory.adapt(eObject, IEditingDomainItemProvider.class);
            if (adapter instanceof IEditingDomainItemProvider contentProvider) {
                contents.addAll(contentProvider.getChildren(eObject));
            } else {
                contents.addAll(eObject.eContents());
            }
        }
        return contents;
    }

    @Override
    public Optional<String> getLabelField(Object object) {
        return Optional.of(object)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .flatMap(this::getLabelEAttribute)
                .map(EAttribute::getName);
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
