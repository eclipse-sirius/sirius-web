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
package org.eclipse.sirius.components.papaya.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.papaya.AnnotableElement;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.AnnotationField;
import org.eclipse.sirius.components.papaya.ApplicationConcern;
import org.eclipse.sirius.components.papaya.Attribute;
import org.eclipse.sirius.components.papaya.Channel;
import org.eclipse.sirius.components.papaya.Classifier;
import org.eclipse.sirius.components.papaya.Command;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.Container;
import org.eclipse.sirius.components.papaya.ContainingLink;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Controller;
import org.eclipse.sirius.components.papaya.DataType;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.FolderElement;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.InterfaceImplementation;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.Link;
import org.eclipse.sirius.components.papaya.Message;
import org.eclipse.sirius.components.papaya.MessageEmitter;
import org.eclipse.sirius.components.papaya.MessageListener;
import org.eclipse.sirius.components.papaya.ModelElement;
import org.eclipse.sirius.components.papaya.NamedElement;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.OperationalActivity;
import org.eclipse.sirius.components.papaya.OperationalActor;
import org.eclipse.sirius.components.papaya.OperationalCapability;
import org.eclipse.sirius.components.papaya.OperationalEntity;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.OperationalProcess;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.components.papaya.ProvidedService;
import org.eclipse.sirius.components.papaya.Publication;
import org.eclipse.sirius.components.papaya.Query;
import org.eclipse.sirius.components.papaya.RecordComponent;
import org.eclipse.sirius.components.papaya.ReferencingLink;
import org.eclipse.sirius.components.papaya.Repository;
import org.eclipse.sirius.components.papaya.RequiredService;
import org.eclipse.sirius.components.papaya.Service;
import org.eclipse.sirius.components.papaya.Subscription;
import org.eclipse.sirius.components.papaya.Tag;
import org.eclipse.sirius.components.papaya.Task;
import org.eclipse.sirius.components.papaya.Type;
import org.eclipse.sirius.components.papaya.TypeParameter;
import org.eclipse.sirius.components.papaya.TypedElement;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter <code>createXXX</code>
 * method for each class of the model. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage
 * @generated
 */
public class PapayaAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static PapayaPackage modelPackage;

    /**
     * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public PapayaAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = PapayaPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
     * implementation returns <code>true</code> if the object is either the model's package or is an instance object of
     * the model. <!-- end-user-doc -->
     *
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject) object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected PapayaSwitch<Adapter> modelSwitch = new PapayaSwitch<>() {
        @Override
        public Adapter caseModelElement(ModelElement object) {
            return PapayaAdapterFactory.this.createModelElementAdapter();
        }

        @Override
        public Adapter caseTag(Tag object) {
            return PapayaAdapterFactory.this.createTagAdapter();
        }

        @Override
        public Adapter caseLink(Link object) {
            return PapayaAdapterFactory.this.createLinkAdapter();
        }

        @Override
        public Adapter caseReferencingLink(ReferencingLink object) {
            return PapayaAdapterFactory.this.createReferencingLinkAdapter();
        }

        @Override
        public Adapter caseContainingLink(ContainingLink object) {
            return PapayaAdapterFactory.this.createContainingLinkAdapter();
        }

        @Override
        public Adapter caseNamedElement(NamedElement object) {
            return PapayaAdapterFactory.this.createNamedElementAdapter();
        }

        @Override
        public Adapter caseContainer(Container object) {
            return PapayaAdapterFactory.this.createContainerAdapter();
        }

        @Override
        public Adapter caseFolder(Folder object) {
            return PapayaAdapterFactory.this.createFolderAdapter();
        }

        @Override
        public Adapter caseFolderElement(FolderElement object) {
            return PapayaAdapterFactory.this.createFolderElementAdapter();
        }

        @Override
        public Adapter caseProject(Project object) {
            return PapayaAdapterFactory.this.createProjectAdapter();
        }

        @Override
        public Adapter caseOperationalCapability(OperationalCapability object) {
            return PapayaAdapterFactory.this.createOperationalCapabilityAdapter();
        }

        @Override
        public Adapter caseOperationalEntity(OperationalEntity object) {
            return PapayaAdapterFactory.this.createOperationalEntityAdapter();
        }

        @Override
        public Adapter caseOperationalActor(OperationalActor object) {
            return PapayaAdapterFactory.this.createOperationalActorAdapter();
        }

        @Override
        public Adapter caseOperationalProcess(OperationalProcess object) {
            return PapayaAdapterFactory.this.createOperationalProcessAdapter();
        }

        @Override
        public Adapter caseOperationalActivity(OperationalActivity object) {
            return PapayaAdapterFactory.this.createOperationalActivityAdapter();
        }

        @Override
        public Adapter caseOperationalInteraction(OperationalInteraction object) {
            return PapayaAdapterFactory.this.createOperationalInteractionAdapter();
        }

        @Override
        public Adapter caseIteration(Iteration object) {
            return PapayaAdapterFactory.this.createIterationAdapter();
        }

        @Override
        public Adapter caseTask(Task object) {
            return PapayaAdapterFactory.this.createTaskAdapter();
        }

        @Override
        public Adapter caseContribution(Contribution object) {
            return PapayaAdapterFactory.this.createContributionAdapter();
        }

        @Override
        public Adapter caseComponent(Component object) {
            return PapayaAdapterFactory.this.createComponentAdapter();
        }

        @Override
        public Adapter caseComponentPort(ComponentPort object) {
            return PapayaAdapterFactory.this.createComponentPortAdapter();
        }

        @Override
        public Adapter caseComponentExchange(ComponentExchange object) {
            return PapayaAdapterFactory.this.createComponentExchangeAdapter();
        }

        @Override
        public Adapter caseProvidedService(ProvidedService object) {
            return PapayaAdapterFactory.this.createProvidedServiceAdapter();
        }

        @Override
        public Adapter caseRequiredService(RequiredService object) {
            return PapayaAdapterFactory.this.createRequiredServiceAdapter();
        }

        @Override
        public Adapter caseAnnotableElement(AnnotableElement object) {
            return PapayaAdapterFactory.this.createAnnotableElementAdapter();
        }

        @Override
        public Adapter casePackage(org.eclipse.sirius.components.papaya.Package object) {
            return PapayaAdapterFactory.this.createPackageAdapter();
        }

        @Override
        public Adapter caseType(Type object) {
            return PapayaAdapterFactory.this.createTypeAdapter();
        }

        @Override
        public Adapter caseTypedElement(TypedElement object) {
            return PapayaAdapterFactory.this.createTypedElementAdapter();
        }

        @Override
        public Adapter caseGenericType(GenericType object) {
            return PapayaAdapterFactory.this.createGenericTypeAdapter();
        }

        @Override
        public Adapter caseAnnotation(Annotation object) {
            return PapayaAdapterFactory.this.createAnnotationAdapter();
        }

        @Override
        public Adapter caseAnnotationField(AnnotationField object) {
            return PapayaAdapterFactory.this.createAnnotationFieldAdapter();
        }

        @Override
        public Adapter caseClassifier(Classifier object) {
            return PapayaAdapterFactory.this.createClassifierAdapter();
        }

        @Override
        public Adapter caseTypeParameter(TypeParameter object) {
            return PapayaAdapterFactory.this.createTypeParameterAdapter();
        }

        @Override
        public Adapter caseInterface(Interface object) {
            return PapayaAdapterFactory.this.createInterfaceAdapter();
        }

        @Override
        public Adapter caseInterfaceImplementation(InterfaceImplementation object) {
            return PapayaAdapterFactory.this.createInterfaceImplementationAdapter();
        }

        @Override
        public Adapter caseClass(org.eclipse.sirius.components.papaya.Class object) {
            return PapayaAdapterFactory.this.createClassAdapter();
        }

        @Override
        public Adapter caseConstructor(Constructor object) {
            return PapayaAdapterFactory.this.createConstructorAdapter();
        }

        @Override
        public Adapter caseAttribute(Attribute object) {
            return PapayaAdapterFactory.this.createAttributeAdapter();
        }

        @Override
        public Adapter caseOperation(Operation object) {
            return PapayaAdapterFactory.this.createOperationAdapter();
        }

        @Override
        public Adapter caseParameter(Parameter object) {
            return PapayaAdapterFactory.this.createParameterAdapter();
        }

        @Override
        public Adapter caseRecord(org.eclipse.sirius.components.papaya.Record object) {
            return PapayaAdapterFactory.this.createRecordAdapter();
        }

        @Override
        public Adapter caseRecordComponent(RecordComponent object) {
            return PapayaAdapterFactory.this.createRecordComponentAdapter();
        }

        @Override
        public Adapter caseDataType(DataType object) {
            return PapayaAdapterFactory.this.createDataTypeAdapter();
        }

        @Override
        public Adapter caseEnum(org.eclipse.sirius.components.papaya.Enum object) {
            return PapayaAdapterFactory.this.createEnumAdapter();
        }

        @Override
        public Adapter caseEnumLiteral(EnumLiteral object) {
            return PapayaAdapterFactory.this.createEnumLiteralAdapter();
        }

        @Override
        public Adapter caseApplicationConcern(ApplicationConcern object) {
            return PapayaAdapterFactory.this.createApplicationConcernAdapter();
        }

        @Override
        public Adapter caseController(Controller object) {
            return PapayaAdapterFactory.this.createControllerAdapter();
        }

        @Override
        public Adapter caseDomain(Domain object) {
            return PapayaAdapterFactory.this.createDomainAdapter();
        }

        @Override
        public Adapter caseService(Service object) {
            return PapayaAdapterFactory.this.createServiceAdapter();
        }

        @Override
        public Adapter caseMessageListener(MessageListener object) {
            return PapayaAdapterFactory.this.createMessageListenerAdapter();
        }

        @Override
        public Adapter caseMessageEmitter(MessageEmitter object) {
            return PapayaAdapterFactory.this.createMessageEmitterAdapter();
        }

        @Override
        public Adapter caseMessage(Message object) {
            return PapayaAdapterFactory.this.createMessageAdapter();
        }

        @Override
        public Adapter caseEvent(Event object) {
            return PapayaAdapterFactory.this.createEventAdapter();
        }

        @Override
        public Adapter caseCommand(Command object) {
            return PapayaAdapterFactory.this.createCommandAdapter();
        }

        @Override
        public Adapter caseQuery(Query object) {
            return PapayaAdapterFactory.this.createQueryAdapter();
        }

        @Override
        public Adapter caseRepository(Repository object) {
            return PapayaAdapterFactory.this.createRepositoryAdapter();
        }

        @Override
        public Adapter caseChannel(Channel object) {
            return PapayaAdapterFactory.this.createChannelAdapter();
        }

        @Override
        public Adapter caseSubscription(Subscription object) {
            return PapayaAdapterFactory.this.createSubscriptionAdapter();
        }

        @Override
        public Adapter casePublication(Publication object) {
            return PapayaAdapterFactory.this.createPublicationAdapter();
        }

        @Override
        public Adapter defaultCase(EObject object) {
            return PapayaAdapterFactory.this.createEObjectAdapter();
        }
    };

    /**
     * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param target
     *            the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return this.modelSwitch.doSwitch((EObject) target);
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ModelElement <em>Model
     * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ModelElement
     * @generated
     */
    public Adapter createModelElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Tag <em>Tag</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Tag
     * @generated
     */
    public Adapter createTagAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Link <em>Link</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Link
     * @generated
     */
    public Adapter createLinkAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ReferencingLink
     * <em>Referencing Link</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ReferencingLink
     * @generated
     */
    public Adapter createReferencingLinkAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ContainingLink
     * <em>Containing Link</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ContainingLink
     * @generated
     */
    public Adapter createContainingLinkAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.NamedElement <em>Named
     * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.NamedElement
     * @generated
     */
    public Adapter createNamedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Container
     * <em>Container</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Container
     * @generated
     */
    public Adapter createContainerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Folder
     * <em>Folder</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Folder
     * @generated
     */
    public Adapter createFolderAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.FolderElement
     * <em>Folder Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.FolderElement
     * @generated
     */
    public Adapter createFolderElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Project
     * <em>Project</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Project
     * @generated
     */
    public Adapter createProjectAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.OperationalCapability
     * <em>Operational Capability</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.OperationalCapability
     * @generated
     */
    public Adapter createOperationalCapabilityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.OperationalEntity
     * <em>Operational Entity</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.OperationalEntity
     * @generated
     */
    public Adapter createOperationalEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.OperationalActor
     * <em>Operational Actor</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.OperationalActor
     * @generated
     */
    public Adapter createOperationalActorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.OperationalProcess
     * <em>Operational Process</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.OperationalProcess
     * @generated
     */
    public Adapter createOperationalProcessAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.OperationalActivity
     * <em>Operational Activity</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.OperationalActivity
     * @generated
     */
    public Adapter createOperationalActivityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.OperationalInteraction
     * <em>Operational Interaction</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.OperationalInteraction
     * @generated
     */
    public Adapter createOperationalInteractionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Iteration
     * <em>Iteration</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Iteration
     * @generated
     */
    public Adapter createIterationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Task <em>Task</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Task
     * @generated
     */
    public Adapter createTaskAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Contribution
     * <em>Contribution</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Contribution
     * @generated
     */
    public Adapter createContributionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Component
     * <em>Component</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Component
     * @generated
     */
    public Adapter createComponentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ComponentPort
     * <em>Component Port</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ComponentPort
     * @generated
     */
    public Adapter createComponentPortAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ComponentExchange
     * <em>Component Exchange</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ComponentExchange
     * @generated
     */
    public Adapter createComponentExchangeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ProvidedService
     * <em>Provided Service</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ProvidedService
     * @generated
     */
    public Adapter createProvidedServiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.RequiredService
     * <em>Required Service</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.RequiredService
     * @generated
     */
    public Adapter createRequiredServiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.AnnotableElement
     * <em>Annotable Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.AnnotableElement
     * @generated
     */
    public Adapter createAnnotableElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Package
     * <em>Package</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Package
     * @generated
     */
    public Adapter createPackageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Type <em>Type</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Type
     * @generated
     */
    public Adapter createTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.TypedElement <em>Typed
     * Element</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.TypedElement
     * @generated
     */
    public Adapter createTypedElementAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.GenericType <em>Generic
     * Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.GenericType
     * @generated
     */
    public Adapter createGenericTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Annotation
     * <em>Annotation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Annotation
     * @generated
     */
    public Adapter createAnnotationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.AnnotationField
     * <em>Annotation Field</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.AnnotationField
     * @generated
     */
    public Adapter createAnnotationFieldAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Classifier
     * <em>Classifier</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Classifier
     * @generated
     */
    public Adapter createClassifierAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.TypeParameter <em>Type
     * Parameter</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.TypeParameter
     * @generated
     */
    public Adapter createTypeParameterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Interface
     * <em>Interface</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Interface
     * @generated
     */
    public Adapter createInterfaceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.InterfaceImplementation
     * <em>Interface Implementation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we
     * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.InterfaceImplementation
     * @generated
     */
    public Adapter createInterfaceImplementationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Class <em>Class</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Class
     * @generated
     */
    public Adapter createClassAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Constructor
     * <em>Constructor</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Constructor
     * @generated
     */
    public Adapter createConstructorAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Attribute
     * <em>Attribute</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Attribute
     * @generated
     */
    public Adapter createAttributeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Operation
     * <em>Operation</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Operation
     * @generated
     */
    public Adapter createOperationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Parameter
     * <em>Parameter</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Parameter
     * @generated
     */
    public Adapter createParameterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Record
     * <em>Record</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Record
     * @generated
     */
    public Adapter createRecordAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.RecordComponent
     * <em>Record Component</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.RecordComponent
     * @generated
     */
    public Adapter createRecordComponentAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.DataType <em>Data
     * Type</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.DataType
     * @generated
     */
    public Adapter createDataTypeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Enum <em>Enum</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Enum
     * @generated
     */
    public Adapter createEnumAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.EnumLiteral <em>Enum
     * Literal</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.EnumLiteral
     * @generated
     */
    public Adapter createEnumLiteralAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.ApplicationConcern
     * <em>Application Concern</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.ApplicationConcern
     * @generated
     */
    public Adapter createApplicationConcernAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Controller
     * <em>Controller</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Controller
     * @generated
     */
    public Adapter createControllerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Domain
     * <em>Domain</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Domain
     * @generated
     */
    public Adapter createDomainAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Service
     * <em>Service</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Service
     * @generated
     */
    public Adapter createServiceAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.MessageListener
     * <em>Message Listener</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.MessageListener
     * @generated
     */
    public Adapter createMessageListenerAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.MessageEmitter
     * <em>Message Emitter</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can
     * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!--
     * end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.MessageEmitter
     * @generated
     */
    public Adapter createMessageEmitterAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Message
     * <em>Message</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Message
     * @generated
     */
    public Adapter createMessageAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Event <em>Event</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Event
     * @generated
     */
    public Adapter createEventAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Command
     * <em>Command</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Command
     * @generated
     */
    public Adapter createCommandAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Query <em>Query</em>}'.
     * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's useful
     * to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Query
     * @generated
     */
    public Adapter createQueryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Repository
     * <em>Repository</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Repository
     * @generated
     */
    public Adapter createRepositoryAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Channel
     * <em>Channel</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore
     * cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Channel
     * @generated
     */
    public Adapter createChannelAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Subscription
     * <em>Subscription</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Subscription
     * @generated
     */
    public Adapter createSubscriptionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.eclipse.sirius.components.papaya.Publication
     * <em>Publication</em>}'. <!-- begin-user-doc --> This default implementation returns null so that we can easily
     * ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc
     * -->
     *
     * @return the new adapter.
     * @see org.eclipse.sirius.components.papaya.Publication
     * @generated
     */
    public Adapter createPublicationAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns null.
     * <!-- end-user-doc -->
     *
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} // PapayaAdapterFactory
