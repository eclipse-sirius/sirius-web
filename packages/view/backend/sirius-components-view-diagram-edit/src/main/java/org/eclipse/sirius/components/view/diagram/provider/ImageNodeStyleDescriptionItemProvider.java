/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.view.diagram.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription} object.
 * <!-- begin-user-doc --> <!-- end-user-doc -->
 * @generated
 */
public class ImageNodeStyleDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {

    /**
	 * This constructs an instance from a factory and a notifier.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public ImageNodeStyleDescriptionItemProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

    /**
	 * This returns the property descriptors for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
		if (itemPropertyDescriptors == null)
		{
			super.getPropertyDescriptors(object);

			addBorderColorPropertyDescriptor(object);
			addBorderRadiusPropertyDescriptor(object);
			addBorderSizePropertyDescriptor(object);
			addBorderLineStylePropertyDescriptor(object);
			addShapePropertyDescriptor(object);
			addPositionDependentRotationPropertyDescriptor(object);
		}
		return itemPropertyDescriptors;
	}

    /**
	 * This adds a property descriptor for the Border Color feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addBorderColorPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BorderStyle_borderColor_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderColor_feature", "_UI_BorderStyle_type"),
				 DiagramPackage.Literals.BORDER_STYLE__BORDER_COLOR,
				 true,
				 false,
				 false,
				 null,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Border Radius feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addBorderRadiusPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BorderStyle_borderRadius_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderRadius_feature", "_UI_BorderStyle_type"),
				 DiagramPackage.Literals.BORDER_STYLE__BORDER_RADIUS,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Border Size feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addBorderSizePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BorderStyle_borderSize_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderSize_feature", "_UI_BorderStyle_type"),
				 DiagramPackage.Literals.BORDER_STYLE__BORDER_SIZE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.INTEGRAL_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Border Line Style feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addBorderLineStylePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_BorderStyle_borderLineStyle_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_BorderStyle_borderLineStyle_feature", "_UI_BorderStyle_type"),
				 DiagramPackage.Literals.BORDER_STYLE__BORDER_LINE_STYLE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Shape feature.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected void addShapePropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ImageNodeStyleDescription_shape_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ImageNodeStyleDescription_shape_feature", "_UI_ImageNodeStyleDescription_type"),
				 DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This adds a property descriptor for the Position Dependent Rotation feature.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    protected void addPositionDependentRotationPropertyDescriptor(Object object) {
		itemPropertyDescriptors.add
			(createItemPropertyDescriptor
				(((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
				 getResourceLocator(),
				 getString("_UI_ImageNodeStyleDescription_positionDependentRotation_feature"),
				 getString("_UI_PropertyDescriptor_description", "_UI_ImageNodeStyleDescription_positionDependentRotation_feature", "_UI_ImageNodeStyleDescription_type"),
				 DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION,
				 true,
				 false,
				 false,
				 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
				 null,
				 null));
	}

    /**
	 * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
	 * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
	 * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @generated
	 */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
		if (childrenFeatures == null)
		{
			super.getChildrenFeatures(object);
			childrenFeatures.add(DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY);
		}
		return childrenFeatures;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
		// Check the type of the specified child object and return the proper feature to use for
		// adding (see {@link AddCommand}) it as a child.

		return super.getChildFeature(object, child);
	}

    /**
     * This returns ImageNodeStyleDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/ImageNodeStyle.svg"));
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected boolean shouldComposeCreationImage() {
		return true;
	}

    /**
	 * This returns the label text for the adapted class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getText(Object object) {
		ImageNodeStyleDescription imageNodeStyleDescription = (ImageNodeStyleDescription)object;
		return getString("_UI_ImageNodeStyleDescription_type") + " " + imageNodeStyleDescription.getBorderRadius();
	}

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached children and by creating
     * a viewer notification, which it passes to {@link #fireNotifyChanged}. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    @Override
    public void notifyChanged(Notification notification) {
		updateChildren(notification);

		switch (notification.getFeatureID(ImageNodeStyleDescription.class))
		{
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_COLOR:
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_RADIUS:
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_SIZE:
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__BORDER_LINE_STYLE:
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__SHAPE:
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__POSITION_DEPENDENT_ROTATION:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
				return;
			case DiagramPackage.IMAGE_NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY:
				fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
				return;
		}
		super.notifyChanged(notification);
	}

    /**
	 * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
	 * that can be created under this object.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
		super.collectNewChildDescriptors(newChildDescriptors, object);

		newChildDescriptors.add
			(createChildParameter
				(DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY,
				 DiagramFactory.eINSTANCE.createListLayoutStrategyDescription()));

		newChildDescriptors.add
			(createChildParameter
				(DiagramPackage.Literals.NODE_STYLE_DESCRIPTION__CHILDREN_LAYOUT_STRATEGY,
				 DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()));
	}

    /**
	 * Return the resource locator for this item provider's resources.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ResourceLocator getResourceLocator() {
		return ((IChildCreationExtender)adapterFactory).getResourceLocator();
	}

}
