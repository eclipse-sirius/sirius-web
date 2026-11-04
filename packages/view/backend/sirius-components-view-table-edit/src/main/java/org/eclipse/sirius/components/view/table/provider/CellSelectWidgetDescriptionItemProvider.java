/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.table.provider;

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
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.table.CellSelectWidgetDescription;
import org.eclipse.sirius.components.view.table.TablePackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.table.CellSelectWidgetDescription}
 * object. <!-- begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class CellSelectWidgetDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CellSelectWidgetDescriptionItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (this.itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            this.addCandidatesExpressionPropertyDescriptor(object);
            this.addCandidateLabelExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Candidates Expression feature. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @generated
     */
    protected void addCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_CellSelectWidgetDescription_candidatesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_CellSelectWidgetDescription_candidatesExpression_feature", "_UI_CellSelectWidgetDescription_type"),
                TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Candidate Label Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addCandidateLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_CellSelectWidgetDescription_candidateLabelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_CellSelectWidgetDescription_candidateLabelExpression_feature", "_UI_CellSelectWidgetDescription_type"),
                TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (this.childrenFeatures == null) {
            super.getChildrenFeatures(object);
            this.childrenFeatures.add(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY);
        }
        return this.childrenFeatures;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns CellSelectWidgetDescription.svg. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/CellSelectWidgetDescription.svg"));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected boolean shouldComposeCreationImage() {
        return true;
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getText(Object object) {
        String label = ((CellSelectWidgetDescription) object).getCandidatesExpression();
        return label == null || label.length() == 0 ? this.getString("_UI_CellSelectWidgetDescription_type") : this.getString("_UI_CellSelectWidgetDescription_type") + " " + label;
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
        this.updateChildren(notification);

        switch (notification.getFeatureID(CellSelectWidgetDescription.class)) {
            case TablePackage.CELL_SELECT_WIDGET_DESCRIPTION__CANDIDATES_EXPRESSION:
            case TablePackage.CELL_SELECT_WIDGET_DESCRIPTION__CANDIDATE_LABEL_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case TablePackage.CELL_SELECT_WIDGET_DESCRIPTION__BODY:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createChangeContext()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createCreateInstance()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createSetValue()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createUnsetValue()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createDeleteElement()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createLet()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createIf()));

        newChildDescriptors.add(this.createChildParameter(TablePackage.Literals.CELL_SELECT_WIDGET_DESCRIPTION__BODY, ViewFactory.eINSTANCE.createFor()));
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return ((IChildCreationExtender) this.adapterFactory).getResourceLocator();
    }

}
