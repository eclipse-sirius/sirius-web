/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.papaya.impl;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.sirius.components.papaya.Annotation;
import org.eclipse.sirius.components.papaya.AnnotationField;
import org.eclipse.sirius.components.papaya.ApplicationConcern;
import org.eclipse.sirius.components.papaya.Attribute;
import org.eclipse.sirius.components.papaya.Channel;
import org.eclipse.sirius.components.papaya.Command;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.ComponentExchange;
import org.eclipse.sirius.components.papaya.ComponentPort;
import org.eclipse.sirius.components.papaya.Constructor;
import org.eclipse.sirius.components.papaya.ContainingLink;
import org.eclipse.sirius.components.papaya.Contribution;
import org.eclipse.sirius.components.papaya.Controller;
import org.eclipse.sirius.components.papaya.DataType;
import org.eclipse.sirius.components.papaya.Domain;
import org.eclipse.sirius.components.papaya.EnumLiteral;
import org.eclipse.sirius.components.papaya.Event;
import org.eclipse.sirius.components.papaya.Folder;
import org.eclipse.sirius.components.papaya.GenericType;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.Iteration;
import org.eclipse.sirius.components.papaya.Operation;
import org.eclipse.sirius.components.papaya.OperationalActivity;
import org.eclipse.sirius.components.papaya.OperationalActor;
import org.eclipse.sirius.components.papaya.OperationalCapability;
import org.eclipse.sirius.components.papaya.OperationalEntity;
import org.eclipse.sirius.components.papaya.OperationalInteraction;
import org.eclipse.sirius.components.papaya.OperationalProcess;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.PapayaPackage;
import org.eclipse.sirius.components.papaya.Parameter;
import org.eclipse.sirius.components.papaya.Priority;
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
import org.eclipse.sirius.components.papaya.TypeParameter;
import org.eclipse.sirius.components.papaya.Visibility;
import org.eclipse.sirius.components.papaya.spec.AnnotationSpec;
import org.eclipse.sirius.components.papaya.spec.ClassSpec;
import org.eclipse.sirius.components.papaya.spec.ComponentSpec;
import org.eclipse.sirius.components.papaya.spec.DataTypeSpec;
import org.eclipse.sirius.components.papaya.spec.EnumSpec;
import org.eclipse.sirius.components.papaya.spec.InterfaceSpec;
import org.eclipse.sirius.components.papaya.spec.PackageSpec;
import org.eclipse.sirius.components.papaya.spec.RecordSpec;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class PapayaFactoryImpl extends EFactoryImpl implements PapayaFactory {
    /**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public static PapayaFactory init() {
		try
		{
			PapayaFactory thePapayaFactory = (PapayaFactory)EPackage.Registry.INSTANCE.getEFactory(PapayaPackage.eNS_URI);
			if (thePapayaFactory != null)
			{
				return thePapayaFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PapayaFactoryImpl();
	}

    /**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public PapayaFactoryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
			case PapayaPackage.TAG: return createTag();
			case PapayaPackage.REFERENCING_LINK: return createReferencingLink();
			case PapayaPackage.CONTAINING_LINK: return createContainingLink();
			case PapayaPackage.FOLDER: return createFolder();
			case PapayaPackage.PROJECT: return createProject();
			case PapayaPackage.OPERATIONAL_CAPABILITY: return createOperationalCapability();
			case PapayaPackage.OPERATIONAL_ENTITY: return createOperationalEntity();
			case PapayaPackage.OPERATIONAL_ACTOR: return createOperationalActor();
			case PapayaPackage.OPERATIONAL_PROCESS: return createOperationalProcess();
			case PapayaPackage.OPERATIONAL_ACTIVITY: return createOperationalActivity();
			case PapayaPackage.OPERATIONAL_INTERACTION: return createOperationalInteraction();
			case PapayaPackage.ITERATION: return createIteration();
			case PapayaPackage.TASK: return createTask();
			case PapayaPackage.CONTRIBUTION: return createContribution();
			case PapayaPackage.COMPONENT: return createComponent();
			case PapayaPackage.COMPONENT_PORT: return createComponentPort();
			case PapayaPackage.COMPONENT_EXCHANGE: return createComponentExchange();
			case PapayaPackage.PROVIDED_SERVICE: return createProvidedService();
			case PapayaPackage.REQUIRED_SERVICE: return createRequiredService();
			case PapayaPackage.PACKAGE: return createPackage();
			case PapayaPackage.GENERIC_TYPE: return createGenericType();
			case PapayaPackage.ANNOTATION: return createAnnotation();
			case PapayaPackage.ANNOTATION_FIELD: return createAnnotationField();
			case PapayaPackage.TYPE_PARAMETER: return createTypeParameter();
			case PapayaPackage.INTERFACE: return createInterface();
			case PapayaPackage.CLASS: return createClass();
			case PapayaPackage.CONSTRUCTOR: return createConstructor();
			case PapayaPackage.ATTRIBUTE: return createAttribute();
			case PapayaPackage.OPERATION: return createOperation();
			case PapayaPackage.PARAMETER: return createParameter();
			case PapayaPackage.RECORD: return createRecord();
			case PapayaPackage.RECORD_COMPONENT: return createRecordComponent();
			case PapayaPackage.DATA_TYPE: return createDataType();
			case PapayaPackage.ENUM: return createEnum();
			case PapayaPackage.ENUM_LITERAL: return createEnumLiteral();
			case PapayaPackage.APPLICATION_CONCERN: return createApplicationConcern();
			case PapayaPackage.CONTROLLER: return createController();
			case PapayaPackage.DOMAIN: return createDomain();
			case PapayaPackage.SERVICE: return createService();
			case PapayaPackage.EVENT: return createEvent();
			case PapayaPackage.COMMAND: return createCommand();
			case PapayaPackage.QUERY: return createQuery();
			case PapayaPackage.REPOSITORY: return createRepository();
			case PapayaPackage.CHANNEL: return createChannel();
			case PapayaPackage.SUBSCRIPTION: return createSubscription();
			case PapayaPackage.PUBLICATION: return createPublication();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID())
		{
			case PapayaPackage.PRIORITY:
				return createPriorityFromString(eDataType, initialValue);
			case PapayaPackage.VISIBILITY:
				return createVisibilityFromString(eDataType, initialValue);
			case PapayaPackage.INSTANT:
				return createInstantFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID())
		{
			case PapayaPackage.PRIORITY:
				return convertPriorityToString(eDataType, instanceValue);
			case PapayaPackage.VISIBILITY:
				return convertVisibilityToString(eDataType, instanceValue);
			case PapayaPackage.INSTANT:
				return convertInstantToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Tag createTag() {
		TagImpl tag = new TagImpl();
		return tag;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ReferencingLink createReferencingLink() {
		ReferencingLinkImpl referencingLink = new ReferencingLinkImpl();
		return referencingLink;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ContainingLink createContainingLink() {
		ContainingLinkImpl containingLink = new ContainingLinkImpl();
		return containingLink;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Folder createFolder() {
		FolderImpl folder = new FolderImpl();
		return folder;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Project createProject() {
		ProjectImpl project = new ProjectImpl();
		return project;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OperationalCapability createOperationalCapability() {
		OperationalCapabilityImpl operationalCapability = new OperationalCapabilityImpl();
		return operationalCapability;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OperationalEntity createOperationalEntity() {
		OperationalEntityImpl operationalEntity = new OperationalEntityImpl();
		return operationalEntity;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OperationalActor createOperationalActor() {
		OperationalActorImpl operationalActor = new OperationalActorImpl();
		return operationalActor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OperationalProcess createOperationalProcess() {
		OperationalProcessImpl operationalProcess = new OperationalProcessImpl();
		return operationalProcess;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OperationalActivity createOperationalActivity() {
		OperationalActivityImpl operationalActivity = new OperationalActivityImpl();
		return operationalActivity;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public OperationalInteraction createOperationalInteraction() {
		OperationalInteractionImpl operationalInteraction = new OperationalInteractionImpl();
		return operationalInteraction;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Iteration createIteration() {
		IterationImpl iteration = new IterationImpl();
		return iteration;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Task createTask() {
		TaskImpl task = new TaskImpl();
		return task;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Contribution createContribution() {
		ContributionImpl contribution = new ContributionImpl();
		return contribution;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Component createComponent() {
        ComponentImpl component = new ComponentSpec();
        return component;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ComponentPort createComponentPort() {
		ComponentPortImpl componentPort = new ComponentPortImpl();
		return componentPort;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ComponentExchange createComponentExchange() {
		ComponentExchangeImpl componentExchange = new ComponentExchangeImpl();
		return componentExchange;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ProvidedService createProvidedService() {
		ProvidedServiceImpl providedService = new ProvidedServiceImpl();
		return providedService;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public RequiredService createRequiredService() {
		RequiredServiceImpl requiredService = new RequiredServiceImpl();
		return requiredService;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public org.eclipse.sirius.components.papaya.Package createPackage() {
        PackageImpl package_ = new PackageSpec();
        return package_;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public GenericType createGenericType() {
		GenericTypeImpl genericType = new GenericTypeImpl();
		return genericType;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Annotation createAnnotation() {
        AnnotationImpl annotation = new AnnotationSpec();
        return annotation;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public AnnotationField createAnnotationField() {
		AnnotationFieldImpl annotationField = new AnnotationFieldImpl();
		return annotationField;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public TypeParameter createTypeParameter() {
		TypeParameterImpl typeParameter = new TypeParameterImpl();
		return typeParameter;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public Interface createInterface() {
        InterfaceImpl interface_ = new InterfaceSpec();
        return interface_;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public org.eclipse.sirius.components.papaya.Class createClass() {
        ClassImpl class_ = new ClassSpec();
        return class_;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Constructor createConstructor() {
		ConstructorImpl constructor = new ConstructorImpl();
		return constructor;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Attribute createAttribute() {
		AttributeImpl attribute = new AttributeImpl();
		return attribute;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Operation createOperation() {
		OperationImpl operation = new OperationImpl();
		return operation;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public org.eclipse.sirius.components.papaya.Record createRecord() {
        RecordImpl record = new RecordSpec();
        return record;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public RecordComponent createRecordComponent() {
		RecordComponentImpl recordComponent = new RecordComponentImpl();
		return recordComponent;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public DataType createDataType() {
        DataTypeImpl dataType = new DataTypeSpec();
        return dataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public org.eclipse.sirius.components.papaya.Enum createEnum() {
        EnumImpl enum_ = new EnumSpec();
        return enum_;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EnumLiteral createEnumLiteral() {
		EnumLiteralImpl enumLiteral = new EnumLiteralImpl();
		return enumLiteral;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public ApplicationConcern createApplicationConcern() {
		ApplicationConcernImpl applicationConcern = new ApplicationConcernImpl();
		return applicationConcern;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Controller createController() {
		ControllerImpl controller = new ControllerImpl();
		return controller;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Domain createDomain() {
		DomainImpl domain = new DomainImpl();
		return domain;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Service createService() {
		ServiceImpl service = new ServiceImpl();
		return service;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Event createEvent() {
		EventImpl event = new EventImpl();
		return event;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Command createCommand() {
		CommandImpl command = new CommandImpl();
		return command;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Query createQuery() {
		QueryImpl query = new QueryImpl();
		return query;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Repository createRepository() {
		RepositoryImpl repository = new RepositoryImpl();
		return repository;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Channel createChannel() {
		ChannelImpl channel = new ChannelImpl();
		return channel;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Subscription createSubscription() {
		SubscriptionImpl subscription = new SubscriptionImpl();
		return subscription;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Publication createPublication() {
		PublicationImpl publication = new PublicationImpl();
		return publication;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public Priority createPriorityFromString(EDataType eDataType, String initialValue) {
		Priority result = Priority.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public String convertPriorityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public Visibility createVisibilityFromString(EDataType eDataType, String initialValue) {
		Visibility result = Visibility.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public String convertVisibilityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    public Instant createInstantFromString(EDataType eDataType, String initialValue) {
        return Instant.parse(initialValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    public String convertInstantToString(EDataType eDataType, Object instanceValue) {
        if (instanceValue instanceof Instant instant) {
            return DateTimeFormatter.ISO_INSTANT.format(instant);
        }
        return super.convertToString(eDataType, instanceValue);
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public PapayaPackage getPapayaPackage() {
		return (PapayaPackage)getEPackage();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
    @Deprecated
    public static PapayaPackage getPackage() {
		return PapayaPackage.eINSTANCE;
	}

} // PapayaFactoryImpl
