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
package org.eclipse.sirius.components.view.tree.provider;

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
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeFactory;
import org.eclipse.sirius.components.view.tree.TreePackage;
import org.eclipse.sirius.components.view.tree.util.TreeAdapterFactory;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class TreeItemProviderAdapterFactory extends TreeAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {

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
    protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(TreeEditPlugin.INSTANCE, TreePackage.eNS_URI);

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<>();

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.tree.TreeDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeDescriptionItemProvider treeDescriptionItemProvider;

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected TreeItemLabelDescriptionItemProvider treeItemLabelDescriptionItemProvider;

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected TreeItemLabelFragmentDescriptionItemProvider treeItemLabelFragmentDescriptionItemProvider;

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SingleClickTreeItemContextMenuEntryItemProvider singleClickTreeItemContextMenuEntryItemProvider;

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected FetchTreeItemContextMenuEntryItemProvider fetchTreeItemContextMenuEntryItemProvider;

    /**
     * This constructs an instance. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TreeItemProviderAdapterFactory() {
        this.supportedTypes.add(IEditingDomainItemProvider.class);
        this.supportedTypes.add(IStructuredItemContentProvider.class);
        this.supportedTypes.add(ITreeItemContentProvider.class);
        this.supportedTypes.add(IItemLabelProvider.class);
        this.supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.tree.TreeDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createTreeDescriptionAdapter() {
        if (this.treeDescriptionItemProvider == null) {
            this.treeDescriptionItemProvider = new TreeDescriptionItemProvider(this);
        }

        return this.treeDescriptionItemProvider;
    }

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.tree.TreeItemLabelDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createTreeItemLabelDescriptionAdapter() {
        if (this.treeItemLabelDescriptionItemProvider == null) {
            this.treeItemLabelDescriptionItemProvider = new TreeItemLabelDescriptionItemProvider(this);
        }

        return this.treeItemLabelDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected IfTreeItemLabelElementDescriptionItemProvider ifTreeItemLabelElementDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createIfTreeItemLabelElementDescriptionAdapter() {
        if (this.ifTreeItemLabelElementDescriptionItemProvider == null) {
            this.ifTreeItemLabelElementDescriptionItemProvider = new IfTreeItemLabelElementDescriptionItemProvider(this);
        }

        return this.ifTreeItemLabelElementDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ForTreeItemLabelElementDescriptionItemProvider forTreeItemLabelElementDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createForTreeItemLabelElementDescriptionAdapter() {
        if (this.forTreeItemLabelElementDescriptionItemProvider == null) {
            this.forTreeItemLabelElementDescriptionItemProvider = new ForTreeItemLabelElementDescriptionItemProvider(this);
        }

        return this.forTreeItemLabelElementDescriptionItemProvider;
    }

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createTreeItemLabelFragmentDescriptionAdapter() {
        if (this.treeItemLabelFragmentDescriptionItemProvider == null) {
            this.treeItemLabelFragmentDescriptionItemProvider = new TreeItemLabelFragmentDescriptionItemProvider(this);
        }

        return this.treeItemLabelFragmentDescriptionItemProvider;
    }

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry}. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createSingleClickTreeItemContextMenuEntryAdapter() {
        if (this.singleClickTreeItemContextMenuEntryItemProvider == null) {
            this.singleClickTreeItemContextMenuEntryItemProvider = new SingleClickTreeItemContextMenuEntryItemProvider(this);
        }

        return this.singleClickTreeItemContextMenuEntryItemProvider;
    }

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createFetchTreeItemContextMenuEntryAdapter() {
        if (this.fetchTreeItemContextMenuEntryItemProvider == null) {
            this.fetchTreeItemContextMenuEntryItemProvider = new FetchTreeItemContextMenuEntryItemProvider(this);
        }

        return this.fetchTreeItemContextMenuEntryItemProvider;
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
        if (this.treeDescriptionItemProvider != null)
            this.treeDescriptionItemProvider.dispose();
        if (this.treeItemLabelDescriptionItemProvider != null)
            this.treeItemLabelDescriptionItemProvider.dispose();
        if (this.ifTreeItemLabelElementDescriptionItemProvider != null)
            this.ifTreeItemLabelElementDescriptionItemProvider.dispose();
        if (this.forTreeItemLabelElementDescriptionItemProvider != null)
            this.forTreeItemLabelElementDescriptionItemProvider.dispose();
        if (this.treeItemLabelFragmentDescriptionItemProvider != null)
            this.treeItemLabelFragmentDescriptionItemProvider.dispose();
        if (this.singleClickTreeItemContextMenuEntryItemProvider != null)
            this.singleClickTreeItemContextMenuEntryItemProvider.dispose();
        if (this.fetchTreeItemContextMenuEntryItemProvider != null)
            this.fetchTreeItemContextMenuEntryItemProvider.dispose();
    }

    /**
     * A child creation extender for the {@link ViewPackage}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public static class ViewChildCreationExtender implements IChildCreationExtender {

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
            return TreeEditPlugin.INSTANCE;
        }

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
                TreeDescription treeDescription = TreeFactory.eINSTANCE.createTreeDescription();
                treeDescription.setName("New Tree Description");
                treeDescription.setTitleExpression("aql:'New Tree Representation'");
                this.newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.VIEW__DESCRIPTIONS, treeDescription));

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
    }

}
