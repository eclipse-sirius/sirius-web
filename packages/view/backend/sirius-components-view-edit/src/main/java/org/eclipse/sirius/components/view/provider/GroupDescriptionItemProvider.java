/**
 * Copyright (c) 2021, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view.provider;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.sirius.components.view.BarChartDescription;
import org.eclipse.sirius.components.view.ButtonDescription;
import org.eclipse.sirius.components.view.CheckboxDescription;
import org.eclipse.sirius.components.view.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.GroupDescription;
import org.eclipse.sirius.components.view.ImageDescription;
import org.eclipse.sirius.components.view.LabelDescription;
import org.eclipse.sirius.components.view.LinkDescription;
import org.eclipse.sirius.components.view.ListDescription;
import org.eclipse.sirius.components.view.MultiSelectDescription;
import org.eclipse.sirius.components.view.PieChartDescription;
import org.eclipse.sirius.components.view.RadioDescription;
import org.eclipse.sirius.components.view.RichTextDescription;
import org.eclipse.sirius.components.view.SelectDescription;
import org.eclipse.sirius.components.view.TextAreaDescription;
import org.eclipse.sirius.components.view.TextfieldDescription;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.sirius.components.view.GroupDescription} object. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class GroupDescriptionItemProvider extends ItemProviderAdapter
        implements IEditingDomainItemProvider, IStructuredItemContentProvider, ITreeItemContentProvider, IItemLabelProvider, IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public GroupDescriptionItemProvider(AdapterFactory adapterFactory) {
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

            this.addNamePropertyDescriptor(object);
            this.addLabelExpressionPropertyDescriptor(object);
            this.addDisplayModePropertyDescriptor(object);
            this.addSemanticCandidatesExpressionPropertyDescriptor(object);
        }
        return this.itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Name feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_GroupDescription_name_feature"), this.getString("_UI_PropertyDescriptor_description", "_UI_GroupDescription_name_feature", "_UI_GroupDescription_type"),
                ViewPackage.Literals.GROUP_DESCRIPTION__NAME, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Label Expression feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addLabelExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_GroupDescription_labelExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_GroupDescription_labelExpression_feature", "_UI_GroupDescription_type"),
                ViewPackage.Literals.GROUP_DESCRIPTION__LABEL_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Display Mode feature. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected void addDisplayModePropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_GroupDescription_displayMode_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_GroupDescription_displayMode_feature", "_UI_GroupDescription_type"), ViewPackage.Literals.GROUP_DESCRIPTION__DISPLAY_MODE,
                true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
    }

    /**
     * This adds a property descriptor for the Semantic Candidates Expression feature. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected void addSemanticCandidatesExpressionPropertyDescriptor(Object object) {
        this.itemPropertyDescriptors.add(this.createItemPropertyDescriptor(((ComposeableAdapterFactory) this.adapterFactory).getRootAdapterFactory(), this.getResourceLocator(),
                this.getString("_UI_GroupDescription_semanticCandidatesExpression_feature"),
                this.getString("_UI_PropertyDescriptor_description", "_UI_GroupDescription_semanticCandidatesExpression_feature", "_UI_GroupDescription_type"),
                ViewPackage.Literals.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION, true, false, false, ItemPropertyDescriptor.GENERIC_VALUE_IMAGE, null, null));
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
            this.childrenFeatures.add(ViewPackage.Literals.GROUP_DESCRIPTION__TOOLBAR_ACTIONS);
            this.childrenFeatures.add(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS);
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
     * This returns GroupDescription.gif. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Object getImage(Object object) {
        return this.overlayImage(object, this.getResourceLocator().getImage("full/obj16/GroupDescription.svg"));
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
        String label = ((GroupDescription) object).getName();
        return label == null || label.length() == 0 ? this.getString("_UI_GroupDescription_type") : this.getString("_UI_GroupDescription_type") + " " + label;
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

        switch (notification.getFeatureID(GroupDescription.class)) {
            case ViewPackage.GROUP_DESCRIPTION__NAME:
            case ViewPackage.GROUP_DESCRIPTION__LABEL_EXPRESSION:
            case ViewPackage.GROUP_DESCRIPTION__DISPLAY_MODE:
            case ViewPackage.GROUP_DESCRIPTION__SEMANTIC_CANDIDATES_EXPRESSION:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case ViewPackage.GROUP_DESCRIPTION__TOOLBAR_ACTIONS:
            case ViewPackage.GROUP_DESCRIPTION__WIDGETS:
                this.fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children that can be created
     * under this object. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        BarChartDescription barChartDescription = ViewFactory.eINSTANCE.createBarChartDescription();
        barChartDescription.setStyle(ViewFactory.eINSTANCE.createBarChartDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, barChartDescription));

        ButtonDescription buttonDescription = ViewFactory.eINSTANCE.createButtonDescription();
        buttonDescription.setStyle(ViewFactory.eINSTANCE.createButtonDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, buttonDescription));

        CheckboxDescription checkboxDescription = ViewFactory.eINSTANCE.createCheckboxDescription();
        checkboxDescription.setStyle(ViewFactory.eINSTANCE.createCheckboxDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, checkboxDescription));

        FlexboxContainerDescription flexboxContainerDescription = ViewFactory.eINSTANCE.createFlexboxContainerDescription();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, flexboxContainerDescription));

        ImageDescription imageDescription = ViewFactory.eINSTANCE.createImageDescription();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, imageDescription));

        LabelDescription labelDescription = ViewFactory.eINSTANCE.createLabelDescription();
        labelDescription.setStyle(ViewFactory.eINSTANCE.createLabelDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, labelDescription));

        LinkDescription linkDescription = ViewFactory.eINSTANCE.createLinkDescription();
        linkDescription.setStyle(ViewFactory.eINSTANCE.createLinkDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, linkDescription));

        ListDescription listDescription = ViewFactory.eINSTANCE.createListDescription();
        listDescription.setStyle(ViewFactory.eINSTANCE.createListDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, listDescription));

        MultiSelectDescription multiSelectDescription = ViewFactory.eINSTANCE.createMultiSelectDescription();
        multiSelectDescription.setStyle(ViewFactory.eINSTANCE.createMultiSelectDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, multiSelectDescription));

        PieChartDescription pieChartDescription = ViewFactory.eINSTANCE.createPieChartDescription();
        pieChartDescription.setStyle(ViewFactory.eINSTANCE.createPieChartDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, pieChartDescription));

        RadioDescription radioDescription = ViewFactory.eINSTANCE.createRadioDescription();
        radioDescription.setStyle(ViewFactory.eINSTANCE.createRadioDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, radioDescription));

        RichTextDescription richTextDescription = ViewFactory.eINSTANCE.createRichTextDescription();
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, richTextDescription));

        SelectDescription selectDescription = ViewFactory.eINSTANCE.createSelectDescription();
        selectDescription.setStyle(ViewFactory.eINSTANCE.createSelectDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, selectDescription));

        TextAreaDescription textareaDescription = ViewFactory.eINSTANCE.createTextAreaDescription();
        textareaDescription.setStyle(ViewFactory.eINSTANCE.createTextareaDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, textareaDescription));

        TextfieldDescription textfieldDescription = ViewFactory.eINSTANCE.createTextfieldDescription();
        textfieldDescription.setStyle(ViewFactory.eINSTANCE.createTextfieldDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS, textfieldDescription));

        ButtonDescription toolbarActionDescription = ViewFactory.eINSTANCE.createButtonDescription();
        toolbarActionDescription.setStyle(ViewFactory.eINSTANCE.createButtonDescriptionStyle());
        toolbarActionDescription.setName("ToolbarAction");
        toolbarActionDescription.setStyle(ViewFactory.eINSTANCE.createButtonDescriptionStyle());
        newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.GROUP_DESCRIPTION__TOOLBAR_ACTIONS, toolbarActionDescription));

    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        boolean qualify = childFeature == ViewPackage.Literals.GROUP_DESCRIPTION__TOOLBAR_ACTIONS || childFeature == ViewPackage.Literals.GROUP_DESCRIPTION__WIDGETS;

        if (qualify) {
            return this.getString("_UI_CreateChild_text2", new Object[] { this.getTypeText(childObject), this.getFeatureText(childFeature), this.getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
    }

    /**
     * Return the resource locator for this item provider's resources. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return ViewEditPlugin.INSTANCE;
    }

}
