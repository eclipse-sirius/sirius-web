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
package org.eclipse.sirius.components.view.form.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.form.FormDescription;
import org.eclipse.sirius.components.view.form.FormFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.provider.spec.BarChartDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.BarChartDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ButtonDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ButtonDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.CheckboxDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.CheckboxDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalBarChartDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalButtonDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalCheckboxDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalContainerBorderStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalDateTimeDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalLabelDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalLinkDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalListDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalMultiSelectDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalPieChartDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalRadioDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalSelectDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalTextareaDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ConditionalTextfieldDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ContainerBorderStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.DateTimeDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.DateTimeDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.FlexboxContainerDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.FormDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.FormElementForItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.FormElementIfItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.FormVariableItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.GroupDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ImageDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.LabelDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.LabelDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.LinkDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.LinkDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ListDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.ListDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.MultiSelectDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.MultiSelectDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.PageDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.PieChartDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.PieChartDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.RadioDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.RadioDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.RichTextDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.SelectDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.SelectDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.SliderDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.SplitButtonDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.TextAreaDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.TextareaDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.TextfieldDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.TextfieldDescriptionStyleItemProviderSpec;
import org.eclipse.sirius.components.view.form.provider.spec.TreeDescriptionItemProviderSpec;
import org.eclipse.sirius.components.view.form.util.FormAdapterFactory;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class FormItemProviderAdapterFactory extends FormAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This helps manage the child creation extenders. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(FormEditPlugin.INSTANCE, FormPackage.eNS_URI);

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<>();

    /**
     * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public FormItemProviderAdapterFactory() {
        this.supportedTypes.add(IEditingDomainItemProvider.class);
        this.supportedTypes.add(IStructuredItemContentProvider.class);
        this.supportedTypes.add(ITreeItemContentProvider.class);
        this.supportedTypes.add(IItemLabelProvider.class);
        this.supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.FormDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormDescriptionItemProvider formDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.FormDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createFormDescriptionAdapter() {
        if (this.formDescriptionItemProvider == null) {
            this.formDescriptionItemProvider = new FormDescriptionItemProviderSpec(this);
        }

        return this.formDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.FormVariable}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormVariableItemProvider formVariableItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.FormVariable}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createFormVariableAdapter() {
        if (this.formVariableItemProvider == null) {
            this.formVariableItemProvider = new FormVariableItemProviderSpec(this);
        }

        return this.formVariableItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.PageDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PageDescriptionItemProvider pageDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.PageDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createPageDescriptionAdapter() {
        if (this.pageDescriptionItemProvider == null) {
            this.pageDescriptionItemProvider = new PageDescriptionItemProviderSpec(this);
        }

        return this.pageDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.GroupDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected GroupDescriptionItemProvider groupDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.GroupDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createGroupDescriptionAdapter() {
        if (this.groupDescriptionItemProvider == null) {
            this.groupDescriptionItemProvider = new GroupDescriptionItemProviderSpec(this);
        }

        return this.groupDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.BarChartDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected BarChartDescriptionItemProvider barChartDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.BarChartDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createBarChartDescriptionAdapter() {
        if (this.barChartDescriptionItemProvider == null) {
            this.barChartDescriptionItemProvider = new BarChartDescriptionItemProviderSpec(this);
        }

        return this.barChartDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.SplitButtonDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected SplitButtonDescriptionItemProvider splitButtonDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.SplitButtonDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createSplitButtonDescriptionAdapter() {
        if (this.splitButtonDescriptionItemProvider == null) {
            this.splitButtonDescriptionItemProvider = new SplitButtonDescriptionItemProviderSpec(this);
        }

        return this.splitButtonDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ButtonDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ButtonDescriptionItemProvider buttonDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ButtonDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createButtonDescriptionAdapter() {
        if (this.buttonDescriptionItemProvider == null) {
            this.buttonDescriptionItemProvider = new ButtonDescriptionItemProviderSpec(this);
        }

        return this.buttonDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.CheckboxDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected CheckboxDescriptionItemProvider checkboxDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.CheckboxDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createCheckboxDescriptionAdapter() {
        if (this.checkboxDescriptionItemProvider == null) {
            this.checkboxDescriptionItemProvider = new CheckboxDescriptionItemProviderSpec(this);
        }

        return this.checkboxDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected FlexboxContainerDescriptionItemProvider flexboxContainerDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.FlexboxContainerDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createFlexboxContainerDescriptionAdapter() {
        if (this.flexboxContainerDescriptionItemProvider == null) {
            this.flexboxContainerDescriptionItemProvider = new FlexboxContainerDescriptionItemProviderSpec(this);
        }

        return this.flexboxContainerDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.ImageDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ImageDescriptionItemProvider imageDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ImageDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createImageDescriptionAdapter() {
        if (this.imageDescriptionItemProvider == null) {
            this.imageDescriptionItemProvider = new ImageDescriptionItemProviderSpec(this);
        }

        return this.imageDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.LabelDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LabelDescriptionItemProvider labelDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.LabelDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createLabelDescriptionAdapter() {
        if (this.labelDescriptionItemProvider == null) {
            this.labelDescriptionItemProvider = new LabelDescriptionItemProviderSpec(this);
        }

        return this.labelDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.LinkDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LinkDescriptionItemProvider linkDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.LinkDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createLinkDescriptionAdapter() {
        if (this.linkDescriptionItemProvider == null) {
            this.linkDescriptionItemProvider = new LinkDescriptionItemProviderSpec(this);
        }

        return this.linkDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.ListDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ListDescriptionItemProvider listDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ListDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createListDescriptionAdapter() {
        if (this.listDescriptionItemProvider == null) {
            this.listDescriptionItemProvider = new ListDescriptionItemProviderSpec(this);
        }

        return this.listDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.MultiSelectDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected MultiSelectDescriptionItemProvider multiSelectDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.MultiSelectDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createMultiSelectDescriptionAdapter() {
        if (this.multiSelectDescriptionItemProvider == null) {
            this.multiSelectDescriptionItemProvider = new MultiSelectDescriptionItemProviderSpec(this);
        }

        return this.multiSelectDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.TreeDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeDescriptionItemProvider treeDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.TreeDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTreeDescriptionAdapter() {
        if (this.treeDescriptionItemProvider == null) {
            this.treeDescriptionItemProvider = new TreeDescriptionItemProviderSpec(this);
        }

        return this.treeDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.DateTimeDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected DateTimeDescriptionItemProvider dateTimeDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.DateTimeDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createDateTimeDescriptionAdapter() {
        if (this.dateTimeDescriptionItemProvider == null) {
            this.dateTimeDescriptionItemProvider = new DateTimeDescriptionItemProviderSpec(this);
        }

        return this.dateTimeDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.SliderDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected SliderDescriptionItemProvider sliderDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.SliderDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createSliderDescriptionAdapter() {
        if (this.sliderDescriptionItemProvider == null) {
            this.sliderDescriptionItemProvider = new SliderDescriptionItemProviderSpec(this);
        }

        return this.sliderDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.PieChartDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected PieChartDescriptionItemProvider pieChartDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.PieChartDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createPieChartDescriptionAdapter() {
        if (this.pieChartDescriptionItemProvider == null) {
            this.pieChartDescriptionItemProvider = new PieChartDescriptionItemProviderSpec(this);
        }

        return this.pieChartDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.RadioDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RadioDescriptionItemProvider radioDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.RadioDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRadioDescriptionAdapter() {
        if (this.radioDescriptionItemProvider == null) {
            this.radioDescriptionItemProvider = new RadioDescriptionItemProviderSpec(this);
        }

        return this.radioDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.RichTextDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected RichTextDescriptionItemProvider richTextDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.RichTextDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRichTextDescriptionAdapter() {
        if (this.richTextDescriptionItemProvider == null) {
            this.richTextDescriptionItemProvider = new RichTextDescriptionItemProviderSpec(this);
        }

        return this.richTextDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.SelectDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected SelectDescriptionItemProvider selectDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.SelectDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createSelectDescriptionAdapter() {
        if (this.selectDescriptionItemProvider == null) {
            this.selectDescriptionItemProvider = new SelectDescriptionItemProviderSpec(this);
        }

        return this.selectDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.TextAreaDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected TextAreaDescriptionItemProvider textAreaDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.TextAreaDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTextAreaDescriptionAdapter() {
        if (this.textAreaDescriptionItemProvider == null) {
            this.textAreaDescriptionItemProvider = new TextAreaDescriptionItemProviderSpec(this);
        }

        return this.textAreaDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.TextfieldDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected TextfieldDescriptionItemProvider textfieldDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.TextfieldDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTextfieldDescriptionAdapter() {
        if (this.textfieldDescriptionItemProvider == null) {
            this.textfieldDescriptionItemProvider = new TextfieldDescriptionItemProviderSpec(this);
        }

        return this.textfieldDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected BarChartDescriptionStyleItemProvider barChartDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.BarChartDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createBarChartDescriptionStyleAdapter() {
        if (this.barChartDescriptionStyleItemProvider == null) {
            this.barChartDescriptionStyleItemProvider = new BarChartDescriptionStyleItemProviderSpec(this);
        }

        return this.barChartDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalBarChartDescriptionStyleItemProvider conditionalBarChartDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalBarChartDescriptionStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalBarChartDescriptionStyleAdapter() {
        if (this.conditionalBarChartDescriptionStyleItemProvider == null) {
            this.conditionalBarChartDescriptionStyleItemProvider = new ConditionalBarChartDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalBarChartDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ButtonDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ButtonDescriptionStyleItemProvider buttonDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ButtonDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createButtonDescriptionStyleAdapter() {
        if (this.buttonDescriptionStyleItemProvider == null) {
            this.buttonDescriptionStyleItemProvider = new ButtonDescriptionStyleItemProviderSpec(this);
        }

        return this.buttonDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalButtonDescriptionStyleItemProvider conditionalButtonDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalButtonDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalButtonDescriptionStyleAdapter() {
        if (this.conditionalButtonDescriptionStyleItemProvider == null) {
            this.conditionalButtonDescriptionStyleItemProvider = new ConditionalButtonDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalButtonDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected CheckboxDescriptionStyleItemProvider checkboxDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createCheckboxDescriptionStyleAdapter() {
        if (this.checkboxDescriptionStyleItemProvider == null) {
            this.checkboxDescriptionStyleItemProvider = new CheckboxDescriptionStyleItemProviderSpec(this);
        }

        return this.checkboxDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalCheckboxDescriptionStyleItemProvider conditionalCheckboxDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalCheckboxDescriptionStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalCheckboxDescriptionStyleAdapter() {
        if (this.conditionalCheckboxDescriptionStyleItemProvider == null) {
            this.conditionalCheckboxDescriptionStyleItemProvider = new ConditionalCheckboxDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalCheckboxDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.LabelDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected LabelDescriptionStyleItemProvider labelDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.LabelDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createLabelDescriptionStyleAdapter() {
        if (this.labelDescriptionStyleItemProvider == null) {
            this.labelDescriptionStyleItemProvider = new LabelDescriptionStyleItemProviderSpec(this);
        }

        return this.labelDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalLabelDescriptionStyleItemProvider conditionalLabelDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalLabelDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalLabelDescriptionStyleAdapter() {
        if (this.conditionalLabelDescriptionStyleItemProvider == null) {
            this.conditionalLabelDescriptionStyleItemProvider = new ConditionalLabelDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalLabelDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.LinkDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected LinkDescriptionStyleItemProvider linkDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.LinkDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createLinkDescriptionStyleAdapter() {
        if (this.linkDescriptionStyleItemProvider == null) {
            this.linkDescriptionStyleItemProvider = new LinkDescriptionStyleItemProviderSpec(this);
        }

        return this.linkDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalLinkDescriptionStyleItemProvider conditionalLinkDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalLinkDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalLinkDescriptionStyleAdapter() {
        if (this.conditionalLinkDescriptionStyleItemProvider == null) {
            this.conditionalLinkDescriptionStyleItemProvider = new ConditionalLinkDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalLinkDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ListDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ListDescriptionStyleItemProvider listDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ListDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createListDescriptionStyleAdapter() {
        if (this.listDescriptionStyleItemProvider == null) {
            this.listDescriptionStyleItemProvider = new ListDescriptionStyleItemProviderSpec(this);
        }

        return this.listDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalListDescriptionStyleItemProvider conditionalListDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalListDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalListDescriptionStyleAdapter() {
        if (this.conditionalListDescriptionStyleItemProvider == null) {
            this.conditionalListDescriptionStyleItemProvider = new ConditionalListDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalListDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected MultiSelectDescriptionStyleItemProvider multiSelectDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createMultiSelectDescriptionStyleAdapter() {
        if (this.multiSelectDescriptionStyleItemProvider == null) {
            this.multiSelectDescriptionStyleItemProvider = new MultiSelectDescriptionStyleItemProviderSpec(this);
        }

        return this.multiSelectDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalMultiSelectDescriptionStyleItemProvider conditionalMultiSelectDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalMultiSelectDescriptionStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalMultiSelectDescriptionStyleAdapter() {
        if (this.conditionalMultiSelectDescriptionStyleItemProvider == null) {
            this.conditionalMultiSelectDescriptionStyleItemProvider = new ConditionalMultiSelectDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalMultiSelectDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected PieChartDescriptionStyleItemProvider pieChartDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.PieChartDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createPieChartDescriptionStyleAdapter() {
        if (this.pieChartDescriptionStyleItemProvider == null) {
            this.pieChartDescriptionStyleItemProvider = new PieChartDescriptionStyleItemProviderSpec(this);
        }

        return this.pieChartDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalPieChartDescriptionStyleItemProvider conditionalPieChartDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalPieChartDescriptionStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalPieChartDescriptionStyleAdapter() {
        if (this.conditionalPieChartDescriptionStyleItemProvider == null) {
            this.conditionalPieChartDescriptionStyleItemProvider = new ConditionalPieChartDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalPieChartDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.RadioDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected RadioDescriptionStyleItemProvider radioDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.RadioDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRadioDescriptionStyleAdapter() {
        if (this.radioDescriptionStyleItemProvider == null) {
            this.radioDescriptionStyleItemProvider = new RadioDescriptionStyleItemProviderSpec(this);
        }

        return this.radioDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalRadioDescriptionStyleItemProvider conditionalRadioDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalRadioDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalRadioDescriptionStyleAdapter() {
        if (this.conditionalRadioDescriptionStyleItemProvider == null) {
            this.conditionalRadioDescriptionStyleItemProvider = new ConditionalRadioDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalRadioDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected SelectDescriptionStyleItemProvider selectDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.SelectDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createSelectDescriptionStyleAdapter() {
        if (this.selectDescriptionStyleItemProvider == null) {
            this.selectDescriptionStyleItemProvider = new SelectDescriptionStyleItemProviderSpec(this);
        }

        return this.selectDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalSelectDescriptionStyleItemProvider conditionalSelectDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalSelectDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalSelectDescriptionStyleAdapter() {
        if (this.conditionalSelectDescriptionStyleItemProvider == null) {
            this.conditionalSelectDescriptionStyleItemProvider = new ConditionalSelectDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalSelectDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected TextareaDescriptionStyleItemProvider textareaDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.TextareaDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTextareaDescriptionStyleAdapter() {
        if (this.textareaDescriptionStyleItemProvider == null) {
            this.textareaDescriptionStyleItemProvider = new TextareaDescriptionStyleItemProviderSpec(this);
        }

        return this.textareaDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalTextareaDescriptionStyleItemProvider conditionalTextareaDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalTextareaDescriptionStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalTextareaDescriptionStyleAdapter() {
        if (this.conditionalTextareaDescriptionStyleItemProvider == null) {
            this.conditionalTextareaDescriptionStyleItemProvider = new ConditionalTextareaDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalTextareaDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected TextfieldDescriptionStyleItemProvider textfieldDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTextfieldDescriptionStyleAdapter() {
        if (this.textfieldDescriptionStyleItemProvider == null) {
            this.textfieldDescriptionStyleItemProvider = new TextfieldDescriptionStyleItemProviderSpec(this);
        }

        return this.textfieldDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalTextfieldDescriptionStyleItemProvider conditionalTextfieldDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalTextfieldDescriptionStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalTextfieldDescriptionStyleAdapter() {
        if (this.conditionalTextfieldDescriptionStyleItemProvider == null) {
            this.conditionalTextfieldDescriptionStyleItemProvider = new ConditionalTextfieldDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalTextfieldDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected DateTimeDescriptionStyleItemProvider dateTimeDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.DateTimeDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createDateTimeDescriptionStyleAdapter() {
        if (this.dateTimeDescriptionStyleItemProvider == null) {
            this.dateTimeDescriptionStyleItemProvider = new DateTimeDescriptionStyleItemProviderSpec(this);
        }

        return this.dateTimeDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalDateTimeDescriptionStyleItemProvider conditionalDateTimeDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.form.ConditionalDateTimeDescriptionStyle}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalDateTimeDescriptionStyleAdapter() {
        if (this.conditionalDateTimeDescriptionStyleItemProvider == null) {
            this.conditionalDateTimeDescriptionStyleItemProvider = new ConditionalDateTimeDescriptionStyleItemProviderSpec(this);
        }

        return this.conditionalDateTimeDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ContainerBorderStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected ContainerBorderStyleItemProvider containerBorderStyleItemProvider;

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalContainerBorderStyleItemProvider conditionalContainerBorderStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ContainerBorderStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createContainerBorderStyleAdapter() {
        if (this.containerBorderStyleItemProvider == null) {
            this.containerBorderStyleItemProvider = new ContainerBorderStyleItemProviderSpec(this);
        }

        return this.containerBorderStyleItemProvider;
    }

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.ConditionalContainerBorderStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConditionalContainerBorderStyleAdapter() {
        if (this.conditionalContainerBorderStyleItemProvider == null) {
            this.conditionalContainerBorderStyleItemProvider = new ConditionalContainerBorderStyleItemProviderSpec(this);
        }

        return this.conditionalContainerBorderStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.FormElementFor}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormElementForItemProvider formElementForItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.FormElementFor}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createFormElementForAdapter() {
        if (this.formElementForItemProvider == null) {
            this.formElementForItemProvider = new FormElementForItemProviderSpec(this);
        }

        return this.formElementForItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.form.FormElementIf}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FormElementIfItemProvider formElementIfItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.form.FormElementIf}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createFormElementIfAdapter() {
        if (this.formElementIfItemProvider == null) {
            this.formElementIfItemProvider = new FormElementIfItemProviderSpec(this);
        }

        return this.formElementIfItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return this.parentAdapterFactory == null ? this : this.parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return this.supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (this.isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public List<IChildCreationExtender> getChildCreationExtenders() {
        return this.childCreationExtenderManager.getChildCreationExtenders();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
        return this.childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public ResourceLocator getResourceLocator() {
        return this.childCreationExtenderManager;
    }

    /**
     * This adds a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void addListener(INotifyChangedListener notifyChangedListener) {
        this.changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        this.changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public void fireNotifyChanged(Notification notification) {
        this.changeNotifier.fireNotifyChanged(notification);

        if (this.parentAdapterFactory != null) {
            this.parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void dispose() {
        if (this.formDescriptionItemProvider != null)
            this.formDescriptionItemProvider.dispose();
        if (this.formVariableItemProvider != null)
            this.formVariableItemProvider.dispose();
        if (this.pageDescriptionItemProvider != null)
            this.pageDescriptionItemProvider.dispose();
        if (this.groupDescriptionItemProvider != null)
            this.groupDescriptionItemProvider.dispose();
        if (this.barChartDescriptionItemProvider != null)
            this.barChartDescriptionItemProvider.dispose();
        if (this.buttonDescriptionItemProvider != null)
            this.buttonDescriptionItemProvider.dispose();
        if (this.checkboxDescriptionItemProvider != null)
            this.checkboxDescriptionItemProvider.dispose();
        if (this.dateTimeDescriptionItemProvider != null)
            this.dateTimeDescriptionItemProvider.dispose();
        if (this.flexboxContainerDescriptionItemProvider != null)
            this.flexboxContainerDescriptionItemProvider.dispose();
        if (this.imageDescriptionItemProvider != null)
            this.imageDescriptionItemProvider.dispose();
        if (this.labelDescriptionItemProvider != null)
            this.labelDescriptionItemProvider.dispose();
        if (this.linkDescriptionItemProvider != null)
            this.linkDescriptionItemProvider.dispose();
        if (this.listDescriptionItemProvider != null)
            this.listDescriptionItemProvider.dispose();
        if (this.multiSelectDescriptionItemProvider != null)
            this.multiSelectDescriptionItemProvider.dispose();
        if (this.pieChartDescriptionItemProvider != null)
            this.pieChartDescriptionItemProvider.dispose();
        if (this.radioDescriptionItemProvider != null)
            this.radioDescriptionItemProvider.dispose();
        if (this.richTextDescriptionItemProvider != null)
            this.richTextDescriptionItemProvider.dispose();
        if (this.selectDescriptionItemProvider != null)
            this.selectDescriptionItemProvider.dispose();
        if (this.splitButtonDescriptionItemProvider != null)
            this.splitButtonDescriptionItemProvider.dispose();
        if (this.textAreaDescriptionItemProvider != null)
            this.textAreaDescriptionItemProvider.dispose();
        if (this.textfieldDescriptionItemProvider != null)
            this.textfieldDescriptionItemProvider.dispose();
        if (this.treeDescriptionItemProvider != null)
            this.treeDescriptionItemProvider.dispose();
        if (this.sliderDescriptionItemProvider != null)
            this.sliderDescriptionItemProvider.dispose();
        if (this.barChartDescriptionStyleItemProvider != null)
            this.barChartDescriptionStyleItemProvider.dispose();
        if (this.conditionalBarChartDescriptionStyleItemProvider != null)
            this.conditionalBarChartDescriptionStyleItemProvider.dispose();
        if (this.buttonDescriptionStyleItemProvider != null)
            this.buttonDescriptionStyleItemProvider.dispose();
        if (this.conditionalButtonDescriptionStyleItemProvider != null)
            this.conditionalButtonDescriptionStyleItemProvider.dispose();
        if (this.checkboxDescriptionStyleItemProvider != null)
            this.checkboxDescriptionStyleItemProvider.dispose();
        if (this.conditionalCheckboxDescriptionStyleItemProvider != null)
            this.conditionalCheckboxDescriptionStyleItemProvider.dispose();
        if (this.dateTimeDescriptionStyleItemProvider != null)
            this.dateTimeDescriptionStyleItemProvider.dispose();
        if (this.conditionalDateTimeDescriptionStyleItemProvider != null)
            this.conditionalDateTimeDescriptionStyleItemProvider.dispose();
        if (this.labelDescriptionStyleItemProvider != null)
            this.labelDescriptionStyleItemProvider.dispose();
        if (this.conditionalLabelDescriptionStyleItemProvider != null)
            this.conditionalLabelDescriptionStyleItemProvider.dispose();
        if (this.linkDescriptionStyleItemProvider != null)
            this.linkDescriptionStyleItemProvider.dispose();
        if (this.conditionalLinkDescriptionStyleItemProvider != null)
            this.conditionalLinkDescriptionStyleItemProvider.dispose();
        if (this.listDescriptionStyleItemProvider != null)
            this.listDescriptionStyleItemProvider.dispose();
        if (this.conditionalListDescriptionStyleItemProvider != null)
            this.conditionalListDescriptionStyleItemProvider.dispose();
        if (this.multiSelectDescriptionStyleItemProvider != null)
            this.multiSelectDescriptionStyleItemProvider.dispose();
        if (this.conditionalMultiSelectDescriptionStyleItemProvider != null)
            this.conditionalMultiSelectDescriptionStyleItemProvider.dispose();
        if (this.pieChartDescriptionStyleItemProvider != null)
            this.pieChartDescriptionStyleItemProvider.dispose();
        if (this.conditionalPieChartDescriptionStyleItemProvider != null)
            this.conditionalPieChartDescriptionStyleItemProvider.dispose();
        if (this.radioDescriptionStyleItemProvider != null)
            this.radioDescriptionStyleItemProvider.dispose();
        if (this.conditionalRadioDescriptionStyleItemProvider != null)
            this.conditionalRadioDescriptionStyleItemProvider.dispose();
        if (this.selectDescriptionStyleItemProvider != null)
            this.selectDescriptionStyleItemProvider.dispose();
        if (this.conditionalSelectDescriptionStyleItemProvider != null)
            this.conditionalSelectDescriptionStyleItemProvider.dispose();
        if (this.textareaDescriptionStyleItemProvider != null)
            this.textareaDescriptionStyleItemProvider.dispose();
        if (this.conditionalTextareaDescriptionStyleItemProvider != null)
            this.conditionalTextareaDescriptionStyleItemProvider.dispose();
        if (this.textfieldDescriptionStyleItemProvider != null)
            this.textfieldDescriptionStyleItemProvider.dispose();
        if (this.conditionalTextfieldDescriptionStyleItemProvider != null)
            this.conditionalTextfieldDescriptionStyleItemProvider.dispose();
        if (this.containerBorderStyleItemProvider != null)
            this.containerBorderStyleItemProvider.dispose();
        if (this.conditionalContainerBorderStyleItemProvider != null)
            this.conditionalContainerBorderStyleItemProvider.dispose();
        if (this.formElementForItemProvider != null)
            this.formElementForItemProvider.dispose();
        if (this.formElementIfItemProvider != null)
            this.formElementIfItemProvider.dispose();
    }

    /**
     * A child creation extender for the {@link ViewPackage}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static class ViewChildCreationExtender implements IChildCreationExtender {
        /**
         * The switch for creating child descriptors specific to each extended class. <!-- begin-user-doc --> <!--
         * end-user-doc -->
         *
         * @generated
         */
        protected static class CreationSwitch extends ViewSwitch<Object> {
            /**
             * The child descriptors being populated. <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            protected List<Object> newChildDescriptors;

            /**
             * The domain in which to create the children. <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            protected EditingDomain editingDomain;

            /**
             * Creates the a switch for populating child descriptors in the given domain. <!-- begin-user-doc --> <!--
             * end-user-doc -->
             *
             * @generated
             */
            CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain) {
                this.newChildDescriptors = newChildDescriptors;
                this.editingDomain = editingDomain;
            }

            /**
             * <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated NOT
             */
            @Override
            public Object caseView(View object) {
                FormDescription newFormDescription = FormFactory.eINSTANCE.createFormDescription();
                newFormDescription.setName("New Form Description");
                PageDescription pageDescription = FormFactory.eINSTANCE.createPageDescription();
                newFormDescription.getPages().add(pageDescription);
                GroupDescription groupDescription = FormFactory.eINSTANCE.createGroupDescription();
                pageDescription.getGroups().add(groupDescription);
                this.newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.VIEW__DESCRIPTIONS, newFormDescription));

                return null;
            }

            /**
             * <!-- begin-user-doc --> <!-- end-user-doc -->
             *
             * @generated
             */
            protected CommandParameter createChildParameter(Object feature, Object child) {
                return new CommandParameter(null, feature, child);
            }

        }

        /**
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        @Override
        public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain) {
            ArrayList<Object> result = new ArrayList<>();
            new CreationSwitch(result, editingDomain).doSwitch((EObject) object);
            return result;
        }

        /**
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         *
         * @generated
         */
        @Override
        public ResourceLocator getResourceLocator() {
            return FormEditPlugin.INSTANCE;
        }
    }

}
