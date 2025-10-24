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
package org.eclipse.sirius.components.papaya.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
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
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.provider.spec.AnnotationFieldItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.AnnotationItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ApplicationConcernItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.AttributeItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ChannelItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ClassItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.CommandItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ComponentExchangeItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ComponentItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ComponentPortItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ConstructorItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ContainingLinkItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ContributionItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ControllerItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.DataTypeItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.DomainItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.EnumItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.EnumLiteralItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.EventItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.FolderItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.GenericTypeItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.InterfaceItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.IterationItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationalActivityItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationalActorItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationalCapabilityItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationalEntityItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationalInteractionItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.OperationalProcessItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.PackageItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ParameterItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ProjectItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ProvidedServiceItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.PublicationItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.QueryItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.RecordComponentItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.RecordItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ReferencingLinkItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.RepositoryItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.RequiredServiceItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.ServiceItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.SubscriptionItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.TagItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.TaskItemProviderSpec;
import org.eclipse.sirius.components.papaya.provider.spec.TypeParameterItemProviderSpec;
import org.eclipse.sirius.components.papaya.util.PapayaAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class PapayaItemProviderAdapterFactory extends PapayaAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
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
    protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(PapayaEditPlugin.INSTANCE, PapayaPackage.eNS_URI);

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
    public PapayaItemProviderAdapterFactory() {
        this.supportedTypes.add(IEditingDomainItemProvider.class);
        this.supportedTypes.add(IStructuredItemContentProvider.class);
        this.supportedTypes.add(ITreeItemContentProvider.class);
        this.supportedTypes.add(IItemLabelProvider.class);
        this.supportedTypes.add(IItemPropertySource.class);
        this.supportedTypes.add(IItemStyledLabelProvider.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Tag} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TagItemProvider tagItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Tag}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTagAdapter() {
        if (this.tagItemProvider == null) {
            this.tagItemProvider = new TagItemProviderSpec(this);
        }

        return this.tagItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.ReferencingLink}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ReferencingLinkItemProvider referencingLinkItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.ReferencingLink}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createReferencingLinkAdapter() {
        if (this.referencingLinkItemProvider == null) {
            this.referencingLinkItemProvider = new ReferencingLinkItemProviderSpec(this);
        }

        return this.referencingLinkItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.ContainingLink}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContainingLinkItemProvider containingLinkItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.ContainingLink}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createContainingLinkAdapter() {
        if (this.containingLinkItemProvider == null) {
            this.containingLinkItemProvider = new ContainingLinkItemProviderSpec(this);
        }

        return this.containingLinkItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Folder} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected FolderItemProvider folderItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Folder}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createFolderAdapter() {
        if (this.folderItemProvider == null) {
            this.folderItemProvider = new FolderItemProviderSpec(this);
        }

        return this.folderItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Project} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProjectItemProvider projectItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Project}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createProjectAdapter() {
        if (this.projectItemProvider == null) {
            this.projectItemProvider = new ProjectItemProviderSpec(this);
        }

        return this.projectItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.papaya.OperationalCapability} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected OperationalCapabilityItemProvider operationalCapabilityItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.OperationalCapability}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOperationalCapabilityAdapter() {
        if (this.operationalCapabilityItemProvider == null) {
            this.operationalCapabilityItemProvider = new OperationalCapabilityItemProviderSpec(this);
        }

        return this.operationalCapabilityItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.OperationalEntity}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationalEntityItemProvider operationalEntityItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.OperationalEntity}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOperationalEntityAdapter() {
        if (this.operationalEntityItemProvider == null) {
            this.operationalEntityItemProvider = new OperationalEntityItemProviderSpec(this);
        }

        return this.operationalEntityItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.OperationalActor}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationalActorItemProvider operationalActorItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.OperationalActor}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOperationalActorAdapter() {
        if (this.operationalActorItemProvider == null) {
            this.operationalActorItemProvider = new OperationalActorItemProviderSpec(this);
        }

        return this.operationalActorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.OperationalProcess}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationalProcessItemProvider operationalProcessItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.OperationalProcess}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOperationalProcessAdapter() {
        if (this.operationalProcessItemProvider == null) {
            this.operationalProcessItemProvider = new OperationalProcessItemProviderSpec(this);
        }

        return this.operationalProcessItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.OperationalActivity}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationalActivityItemProvider operationalActivityItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.OperationalActivity}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOperationalActivityAdapter() {
        if (this.operationalActivityItemProvider == null) {
            this.operationalActivityItemProvider = new OperationalActivityItemProviderSpec(this);
        }

        return this.operationalActivityItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.papaya.OperationalInteraction} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected OperationalInteractionItemProvider operationalInteractionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.OperationalInteraction}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createOperationalInteractionAdapter() {
        if (this.operationalInteractionItemProvider == null) {
            this.operationalInteractionItemProvider = new OperationalInteractionItemProviderSpec(this);
        }

        return this.operationalInteractionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Iteration}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected IterationItemProvider iterationItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Iteration}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createIterationAdapter() {
        if (this.iterationItemProvider == null) {
            this.iterationItemProvider = new IterationItemProviderSpec(this);
        }

        return this.iterationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Task} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TaskItemProvider taskItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Task}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTaskAdapter() {
        if (this.taskItemProvider == null) {
            this.taskItemProvider = new TaskItemProviderSpec(this);
        }

        return this.taskItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Contribution}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ContributionItemProvider contributionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Contribution}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createContributionAdapter() {
        if (this.contributionItemProvider == null) {
            this.contributionItemProvider = new ContributionItemProviderSpec(this);
        }

        return this.contributionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Component}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ComponentItemProvider componentItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Component}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createComponentAdapter() {
        if (this.componentItemProvider == null) {
            this.componentItemProvider = new ComponentItemProviderSpec(this);
        }

        return this.componentItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.ComponentPort}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ComponentPortItemProvider componentPortItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.ComponentPort}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createComponentPortAdapter() {
        if (this.componentPortItemProvider == null) {
            this.componentPortItemProvider = new ComponentPortItemProviderSpec(this);
        }

        return this.componentPortItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.ComponentExchange}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ComponentExchangeItemProvider componentExchangeItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.ComponentExchange}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createComponentExchangeAdapter() {
        if (this.componentExchangeItemProvider == null) {
            this.componentExchangeItemProvider = new ComponentExchangeItemProviderSpec(this);
        }

        return this.componentExchangeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.ProvidedService}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ProvidedServiceItemProvider providedServiceItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.ProvidedService}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createProvidedServiceAdapter() {
        if (this.providedServiceItemProvider == null) {
            this.providedServiceItemProvider = new ProvidedServiceItemProviderSpec(this);
        }

        return this.providedServiceItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.RequiredService}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RequiredServiceItemProvider requiredServiceItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.RequiredService}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRequiredServiceAdapter() {
        if (this.requiredServiceItemProvider == null) {
            this.requiredServiceItemProvider = new RequiredServiceItemProviderSpec(this);
        }

        return this.requiredServiceItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Package} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PackageItemProvider packageItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Package}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createPackageAdapter() {
        if (this.packageItemProvider == null) {
            this.packageItemProvider = new PackageItemProviderSpec(this);
        }

        return this.packageItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.GenericType}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected GenericTypeItemProvider genericTypeItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.GenericType}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createGenericTypeAdapter() {
        if (this.genericTypeItemProvider == null) {
            this.genericTypeItemProvider = new GenericTypeItemProviderSpec(this);
        }

        return this.genericTypeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Annotation}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected AnnotationItemProvider annotationItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Annotation}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createAnnotationAdapter() {
        if (this.annotationItemProvider == null) {
            this.annotationItemProvider = new AnnotationItemProviderSpec(this);
        }

        return this.annotationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.AnnotationField}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected AnnotationFieldItemProvider annotationFieldItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.AnnotationField}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createAnnotationFieldAdapter() {
        if (this.annotationFieldItemProvider == null) {
            this.annotationFieldItemProvider = new AnnotationFieldItemProviderSpec(this);
        }

        return this.annotationFieldItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.TypeParameter}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TypeParameterItemProvider typeParameterItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.TypeParameter}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createTypeParameterAdapter() {
        if (this.typeParameterItemProvider == null) {
            this.typeParameterItemProvider = new TypeParameterItemProviderSpec(this);
        }

        return this.typeParameterItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Interface}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected InterfaceItemProvider interfaceItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Interface}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createInterfaceAdapter() {
        if (this.interfaceItemProvider == null) {
            this.interfaceItemProvider = new InterfaceItemProviderSpec(this);
        }

        return this.interfaceItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Class} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ClassItemProvider classItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Class}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createClassAdapter() {
        if (this.classItemProvider == null) {
            this.classItemProvider = new ClassItemProviderSpec(this);
        }

        return this.classItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Constructor}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConstructorItemProvider constructorItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Constructor}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createConstructorAdapter() {
        if (this.constructorItemProvider == null) {
            this.constructorItemProvider = new ConstructorItemProviderSpec(this);
        }

        return this.constructorItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Attribute}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected AttributeItemProvider attributeItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Attribute}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createAttributeAdapter() {
        if (this.attributeItemProvider == null) {
            this.attributeItemProvider = new AttributeItemProviderSpec(this);
        }

        return this.attributeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Operation}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected OperationItemProvider operationItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Operation}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createOperationAdapter() {
        if (this.operationItemProvider == null) {
            this.operationItemProvider = new OperationItemProviderSpec(this);
        }

        return this.operationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Parameter}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ParameterItemProvider parameterItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Parameter}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createParameterAdapter() {
        if (this.parameterItemProvider == null) {
            this.parameterItemProvider = new ParameterItemProviderSpec(this);
        }

        return this.parameterItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Record} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RecordItemProvider recordItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Record}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRecordAdapter() {
        if (this.recordItemProvider == null) {
            this.recordItemProvider = new RecordItemProviderSpec(this);
        }

        return this.recordItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.RecordComponent}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RecordComponentItemProvider recordComponentItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.RecordComponent}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRecordComponentAdapter() {
        if (this.recordComponentItemProvider == null) {
            this.recordComponentItemProvider = new RecordComponentItemProviderSpec(this);
        }

        return this.recordComponentItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.DataType} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DataTypeItemProvider dataTypeItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.DataType}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createDataTypeAdapter() {
        if (this.dataTypeItemProvider == null) {
            this.dataTypeItemProvider = new DataTypeItemProviderSpec(this);
        }

        return this.dataTypeItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Enum} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EnumItemProvider enumItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Enum}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createEnumAdapter() {
        if (this.enumItemProvider == null) {
            this.enumItemProvider = new EnumItemProviderSpec(this);
        }

        return this.enumItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.EnumLiteral}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EnumLiteralItemProvider enumLiteralItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.EnumLiteral}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createEnumLiteralAdapter() {
        if (this.enumLiteralItemProvider == null) {
            this.enumLiteralItemProvider = new EnumLiteralItemProviderSpec(this);
        }

        return this.enumLiteralItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.ApplicationConcern}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ApplicationConcernItemProvider applicationConcernItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.ApplicationConcern}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createApplicationConcernAdapter() {
        if (this.applicationConcernItemProvider == null) {
            this.applicationConcernItemProvider = new ApplicationConcernItemProviderSpec(this);
        }

        return this.applicationConcernItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Controller}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ControllerItemProvider controllerItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Controller}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createControllerAdapter() {
        if (this.controllerItemProvider == null) {
            this.controllerItemProvider = new ControllerItemProviderSpec(this);
        }

        return this.controllerItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Domain} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DomainItemProvider domainItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Domain}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createDomainAdapter() {
        if (this.domainItemProvider == null) {
            this.domainItemProvider = new DomainItemProviderSpec(this);
        }

        return this.domainItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Service} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ServiceItemProvider serviceItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Service}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createServiceAdapter() {
        if (this.serviceItemProvider == null) {
            this.serviceItemProvider = new ServiceItemProviderSpec(this);
        }

        return this.serviceItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Event} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EventItemProvider eventItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Event}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createEventAdapter() {
        if (this.eventItemProvider == null) {
            this.eventItemProvider = new EventItemProviderSpec(this);
        }

        return this.eventItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Command} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CommandItemProvider commandItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Command}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createCommandAdapter() {
        if (this.commandItemProvider == null) {
            this.commandItemProvider = new CommandItemProviderSpec(this);
        }

        return this.commandItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Query} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected QueryItemProvider queryItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Query}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createQueryAdapter() {
        if (this.queryItemProvider == null) {
            this.queryItemProvider = new QueryItemProviderSpec(this);
        }

        return this.queryItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Repository}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected RepositoryItemProvider repositoryItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Repository}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createRepositoryAdapter() {
        if (this.repositoryItemProvider == null) {
            this.repositoryItemProvider = new RepositoryItemProviderSpec(this);
        }

        return this.repositoryItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Channel} instances.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ChannelItemProvider channelItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Channel}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createChannelAdapter() {
        if (this.channelItemProvider == null) {
            this.channelItemProvider = new ChannelItemProviderSpec(this);
        }

        return this.channelItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Subscription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SubscriptionItemProvider subscriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Subscription}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createSubscriptionAdapter() {
        if (this.subscriptionItemProvider == null) {
            this.subscriptionItemProvider = new SubscriptionItemProviderSpec(this);
        }

        return this.subscriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.papaya.Publication}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PublicationItemProvider publicationItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.papaya.Publication}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Adapter createPublicationAdapter() {
        if (this.publicationItemProvider == null) {
            this.publicationItemProvider = new PublicationItemProviderSpec(this);
        }

        return this.publicationItemProvider;
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
        if (this.tagItemProvider != null)
            this.tagItemProvider.dispose();
        if (this.referencingLinkItemProvider != null)
            this.referencingLinkItemProvider.dispose();
        if (this.containingLinkItemProvider != null)
            this.containingLinkItemProvider.dispose();
        if (this.folderItemProvider != null)
            this.folderItemProvider.dispose();
        if (this.projectItemProvider != null)
            this.projectItemProvider.dispose();
        if (this.operationalCapabilityItemProvider != null)
            this.operationalCapabilityItemProvider.dispose();
        if (this.operationalEntityItemProvider != null)
            this.operationalEntityItemProvider.dispose();
        if (this.operationalActorItemProvider != null)
            this.operationalActorItemProvider.dispose();
        if (this.operationalProcessItemProvider != null)
            this.operationalProcessItemProvider.dispose();
        if (this.operationalActivityItemProvider != null)
            this.operationalActivityItemProvider.dispose();
        if (this.operationalInteractionItemProvider != null)
            this.operationalInteractionItemProvider.dispose();
        if (this.iterationItemProvider != null)
            this.iterationItemProvider.dispose();
        if (this.taskItemProvider != null)
            this.taskItemProvider.dispose();
        if (this.contributionItemProvider != null)
            this.contributionItemProvider.dispose();
        if (this.componentItemProvider != null)
            this.componentItemProvider.dispose();
        if (this.componentPortItemProvider != null)
            this.componentPortItemProvider.dispose();
        if (this.componentExchangeItemProvider != null)
            this.componentExchangeItemProvider.dispose();
        if (this.providedServiceItemProvider != null)
            this.providedServiceItemProvider.dispose();
        if (this.requiredServiceItemProvider != null)
            this.requiredServiceItemProvider.dispose();
        if (this.packageItemProvider != null)
            this.packageItemProvider.dispose();
        if (this.genericTypeItemProvider != null)
            this.genericTypeItemProvider.dispose();
        if (this.annotationItemProvider != null)
            this.annotationItemProvider.dispose();
        if (this.annotationFieldItemProvider != null)
            this.annotationFieldItemProvider.dispose();
        if (this.typeParameterItemProvider != null)
            this.typeParameterItemProvider.dispose();
        if (this.interfaceItemProvider != null)
            this.interfaceItemProvider.dispose();
        if (this.classItemProvider != null)
            this.classItemProvider.dispose();
        if (this.constructorItemProvider != null)
            this.constructorItemProvider.dispose();
        if (this.attributeItemProvider != null)
            this.attributeItemProvider.dispose();
        if (this.operationItemProvider != null)
            this.operationItemProvider.dispose();
        if (this.parameterItemProvider != null)
            this.parameterItemProvider.dispose();
        if (this.recordItemProvider != null)
            this.recordItemProvider.dispose();
        if (this.recordComponentItemProvider != null)
            this.recordComponentItemProvider.dispose();
        if (this.dataTypeItemProvider != null)
            this.dataTypeItemProvider.dispose();
        if (this.enumItemProvider != null)
            this.enumItemProvider.dispose();
        if (this.enumLiteralItemProvider != null)
            this.enumLiteralItemProvider.dispose();
        if (this.applicationConcernItemProvider != null)
            this.applicationConcernItemProvider.dispose();
        if (this.controllerItemProvider != null)
            this.controllerItemProvider.dispose();
        if (this.domainItemProvider != null)
            this.domainItemProvider.dispose();
        if (this.serviceItemProvider != null)
            this.serviceItemProvider.dispose();
        if (this.eventItemProvider != null)
            this.eventItemProvider.dispose();
        if (this.commandItemProvider != null)
            this.commandItemProvider.dispose();
        if (this.queryItemProvider != null)
            this.queryItemProvider.dispose();
        if (this.repositoryItemProvider != null)
            this.repositoryItemProvider.dispose();
        if (this.channelItemProvider != null)
            this.channelItemProvider.dispose();
        if (this.subscriptionItemProvider != null)
            this.subscriptionItemProvider.dispose();
        if (this.publicationItemProvider != null)
            this.publicationItemProvider.dispose();
    }

}
