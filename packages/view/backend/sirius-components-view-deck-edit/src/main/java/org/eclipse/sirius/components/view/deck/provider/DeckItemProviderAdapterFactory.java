/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.deck.provider;

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
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckFactory;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.util.DeckAdapterFactory;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 *
 * @generated
 */
public class DeckItemProviderAdapterFactory extends DeckAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable, IChildCreationExtender {
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
    protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(DeckEditPlugin.INSTANCE, DeckPackage.eNS_URI);

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
    public DeckItemProviderAdapterFactory() {
        this.supportedTypes.add(IEditingDomainItemProvider.class);
        this.supportedTypes.add(IStructuredItemContentProvider.class);
        this.supportedTypes.add(ITreeItemContentProvider.class);
        this.supportedTypes.add(IItemLabelProvider.class);
        this.supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.DeckDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DeckDescriptionItemProvider deckDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.DeckDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDeckDescriptionAdapter() {
        if (this.deckDescriptionItemProvider == null) {
            this.deckDescriptionItemProvider = new DeckDescriptionItemProvider(this);
        }

        return this.deckDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.LaneDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LaneDescriptionItemProvider laneDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.LaneDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createLaneDescriptionAdapter() {
        if (this.laneDescriptionItemProvider == null) {
            this.laneDescriptionItemProvider = new LaneDescriptionItemProvider(this);
        }

        return this.laneDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.CardDescription}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CardDescriptionItemProvider cardDescriptionItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.CardDescription}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createCardDescriptionAdapter() {
        if (this.cardDescriptionItemProvider == null) {
            this.cardDescriptionItemProvider = new CardDescriptionItemProvider(this);
        }

        return this.cardDescriptionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.CreateCardTool}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CreateCardToolItemProvider createCardToolItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.CreateCardTool}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createCreateCardToolAdapter() {
        if (this.createCardToolItemProvider == null) {
            this.createCardToolItemProvider = new CreateCardToolItemProvider(this);
        }

        return this.createCardToolItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.EditCardTool}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EditCardToolItemProvider editCardToolItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.EditCardTool}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createEditCardToolAdapter() {
        if (this.editCardToolItemProvider == null) {
            this.editCardToolItemProvider = new EditCardToolItemProvider(this);
        }

        return this.editCardToolItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.DeleteCardTool}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DeleteCardToolItemProvider deleteCardToolItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.DeleteCardTool}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDeleteCardToolAdapter() {
        if (this.deleteCardToolItemProvider == null) {
            this.deleteCardToolItemProvider = new DeleteCardToolItemProvider(this);
        }

        return this.deleteCardToolItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.EditLaneTool}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected EditLaneToolItemProvider editLaneToolItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.EditLaneTool}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createEditLaneToolAdapter() {
        if (this.editLaneToolItemProvider == null) {
            this.editLaneToolItemProvider = new EditLaneToolItemProvider(this);
        }

        return this.editLaneToolItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.CardDropTool}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected CardDropToolItemProvider cardDropToolItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.CardDropTool}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createCardDropToolAdapter() {
        if (this.cardDropToolItemProvider == null) {
            this.cardDropToolItemProvider = new CardDropToolItemProvider(this);
        }

        return this.cardDropToolItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.eclipse.sirius.components.view.deck.LaneDropTool}
     * instances. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected LaneDropToolItemProvider laneDropToolItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.LaneDropTool}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createLaneDropToolAdapter() {
        if (this.laneDropToolItemProvider == null) {
            this.laneDropToolItemProvider = new LaneDropToolItemProvider(this);
        }

        return this.laneDropToolItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.deck.DeckDescriptionStyle} instances. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @generated
     */
    protected DeckDescriptionStyleItemProvider deckDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.DeckDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDeckDescriptionStyleAdapter() {
        if (this.deckDescriptionStyleItemProvider == null) {
            this.deckDescriptionStyleItemProvider = new DeckDescriptionStyleItemProvider(this);
        }

        return this.deckDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle} instances. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalDeckDescriptionStyleItemProvider conditionalDeckDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle}.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createConditionalDeckDescriptionStyleAdapter() {
        if (this.conditionalDeckDescriptionStyleItemProvider == null) {
            this.conditionalDeckDescriptionStyleItemProvider = new ConditionalDeckDescriptionStyleItemProvider(this);
        }

        return this.conditionalDeckDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle} instances. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    protected DeckElementDescriptionStyleItemProvider deckElementDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a {@link org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle}. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createDeckElementDescriptionStyleAdapter() {
        if (this.deckElementDescriptionStyleItemProvider == null) {
            this.deckElementDescriptionStyleItemProvider = new DeckElementDescriptionStyleItemProvider(this);
        }

        return this.deckElementDescriptionStyleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all
     * {@link org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle} instances. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected ConditionalDeckElementDescriptionStyleItemProvider conditionalDeckElementDescriptionStyleItemProvider;

    /**
     * This creates an adapter for a
     * {@link org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle}. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Adapter createConditionalDeckElementDescriptionStyleAdapter() {
        if (this.conditionalDeckElementDescriptionStyleItemProvider == null) {
            this.conditionalDeckElementDescriptionStyleItemProvider = new ConditionalDeckElementDescriptionStyleItemProvider(this);
        }

        return this.conditionalDeckElementDescriptionStyleItemProvider;
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
        if (this.deckDescriptionItemProvider != null)
            this.deckDescriptionItemProvider.dispose();
        if (this.laneDescriptionItemProvider != null)
            this.laneDescriptionItemProvider.dispose();
        if (this.cardDescriptionItemProvider != null)
            this.cardDescriptionItemProvider.dispose();
        if (this.createCardToolItemProvider != null)
            this.createCardToolItemProvider.dispose();
        if (this.editCardToolItemProvider != null)
            this.editCardToolItemProvider.dispose();
        if (this.deleteCardToolItemProvider != null)
            this.deleteCardToolItemProvider.dispose();
        if (this.editLaneToolItemProvider != null)
            this.editLaneToolItemProvider.dispose();
        if (this.cardDropToolItemProvider != null)
            this.cardDropToolItemProvider.dispose();
        if (this.laneDropToolItemProvider != null)
            this.laneDropToolItemProvider.dispose();
        if (this.deckDescriptionStyleItemProvider != null)
            this.deckDescriptionStyleItemProvider.dispose();
        if (this.conditionalDeckDescriptionStyleItemProvider != null)
            this.conditionalDeckDescriptionStyleItemProvider.dispose();
        if (this.deckElementDescriptionStyleItemProvider != null)
            this.deckElementDescriptionStyleItemProvider.dispose();
        if (this.conditionalDeckElementDescriptionStyleItemProvider != null)
            this.conditionalDeckElementDescriptionStyleItemProvider.dispose();
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
                DeckDescription deckDescription = DeckFactory.eINSTANCE.createDeckDescription();
                deckDescription.setName("New Deck Description");
                deckDescription.setTitleExpression("aql:'New Deck Representation'");
                this.newChildDescriptors.add(this.createChildParameter(ViewPackage.Literals.VIEW__DESCRIPTIONS, deckDescription));

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
            return DeckEditPlugin.INSTANCE;
        }
    }

}
