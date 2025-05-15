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
package org.eclipse.sirius.components.flow.starter.services;

import fr.obeo.dsl.designer.sample.flow.CompositeProcessor;
import fr.obeo.dsl.designer.sample.flow.DataSource;
import fr.obeo.dsl.designer.sample.flow.FlowElementUsage;
import fr.obeo.dsl.designer.sample.flow.FlowFactory;
import fr.obeo.dsl.designer.sample.flow.Processor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.flow.starter.services.api.IRobotFlowProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the robot flow sample.
 *
 * @author sbegaudeau
 */
@Service
public class RobotFlowProvider implements IRobotFlowProvider {

    private final List<IMigrationParticipant> migrationParticipants;

    public RobotFlowProvider(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
    }

    @Override
    public UUID addRobotFlow(ResourceSet resourceSet, String resourceName) {
        var documentId = UUID.randomUUID();
        var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());

        var resourceMetadataAdapter = new ResourceMetadataAdapter(resourceName);
        var migrationService = new MigrationService(this.migrationParticipants);

        resourceMetadataAdapter.addMigrationData(migrationService.getMostRecentParticipantMigrationData());

        resource.eAdapters().add(resourceMetadataAdapter);
        resourceSet.getResources().add(resource);

        var dspProcessor = this.dspProcessor();
        var motionEngineProcessor = this.motionEngineProcessor();

        var firstFan = FlowFactory.eINSTANCE.createFan();
        firstFan.setConsumption(10);

        var centralUnitCompositeProcessor = this.centralUnitCompositeProcessor();

        var dspToMotionEngineFlow = FlowFactory.eINSTANCE.createDataFlow();
        dspToMotionEngineFlow.setSource(dspProcessor);
        dspToMotionEngineFlow.setTarget(motionEngineProcessor);
        dspProcessor.getOutgoingFlows().add(dspToMotionEngineFlow);

        motionEngineProcessor.getOutgoingFlows().add(dspToMotionEngineFlow);

        centralUnitCompositeProcessor.getElements().addAll(List.of(
                dspProcessor,
                motionEngineProcessor,
                firstFan
        ));

        var radarCaptureProcessor = this.radarCaptureProcessor();
        var backCameraDataSource = this.backCameraDataSource();
        var radarDataSource = this.radarDataSource();
        var engineProcessor = this.engineProcessor();
        var gpuProcessor = this.gpuProcessor();

        var gpuProcessorToMotionEngineProcessor = FlowFactory.eINSTANCE.createDataFlow();
        gpuProcessorToMotionEngineProcessor.setSource(gpuProcessor);
        gpuProcessorToMotionEngineProcessor.setTarget(motionEngineProcessor);
        gpuProcessor.getOutgoingFlows().add(gpuProcessorToMotionEngineProcessor);

        var secondFan = FlowFactory.eINSTANCE.createFan();
        secondFan.setSpeed(20);

        var captureSubsystemCompositeProcessor = FlowFactory.eINSTANCE.createCompositeProcessor();
        captureSubsystemCompositeProcessor.getElements().addAll(List.of(
                radarCaptureProcessor,
                backCameraDataSource,
                radarDataSource,
                engineProcessor,
                gpuProcessor,
                secondFan
        ));

        var wifiDataSource = this.wifiDataSource();

        var wifiToDspProcessorDataFlow = FlowFactory.eINSTANCE.createDataFlow();
        wifiToDspProcessorDataFlow.setSource(wifiDataSource);
        wifiToDspProcessorDataFlow.setTarget(dspProcessor);
        wifiToDspProcessorDataFlow.setUsage(FlowElementUsage.STANDARD);
        wifiToDspProcessorDataFlow.setCapacity(4);
        wifiToDspProcessorDataFlow.setLoad(4);

        wifiDataSource.getOutgoingFlows().add(wifiToDspProcessorDataFlow);

        var system = FlowFactory.eINSTANCE.createSystem();
        system.getElements().addAll(List.of(
                centralUnitCompositeProcessor,
                captureSubsystemCompositeProcessor,
                wifiDataSource
        ));

        resource.getContents().add(system);

        return documentId;
    }

    private Processor dspProcessor() {
        var dspProcessor = FlowFactory.eINSTANCE.createProcessor();
        dspProcessor.setName("DSP");
        dspProcessor.setVolume(4);
        dspProcessor.setConsumption(40);
        dspProcessor.setLoad(4);
        dspProcessor.setCapacity(4);
        dspProcessor.setUsage(FlowElementUsage.STANDARD);
        return dspProcessor;
    }

    private Processor motionEngineProcessor() {
        var motionEngineProcessor = FlowFactory.eINSTANCE.createProcessor();
        motionEngineProcessor.setName("Motion_Engine");
        motionEngineProcessor.setVolume(18);
        motionEngineProcessor.setConsumption(150);
        motionEngineProcessor.setLoad(18);
        motionEngineProcessor.setCapacity(15);
        motionEngineProcessor.setUsage(FlowElementUsage.HIGH);
        return motionEngineProcessor;
    }

    private CompositeProcessor centralUnitCompositeProcessor() {
        var centralUnitCompositeProcessor = FlowFactory.eINSTANCE.createCompositeProcessor();
        centralUnitCompositeProcessor.setName("Central_Unit");
        centralUnitCompositeProcessor.setConsumption(200);
        centralUnitCompositeProcessor.setUsage(FlowElementUsage.STANDARD);
        centralUnitCompositeProcessor.setTemperature(25);
        centralUnitCompositeProcessor.setWeight(23);
        centralUnitCompositeProcessor.setRoutingRules("Case Robot.temperature >= 70 C ! critical !");
        return centralUnitCompositeProcessor;
    }

    private Processor radarCaptureProcessor() {
        var radarCaptureProcessor = FlowFactory.eINSTANCE.createProcessor();
        radarCaptureProcessor.setName("Radar_Capture");
        radarCaptureProcessor.setVolume(8);
        radarCaptureProcessor.setLoad(8);
        radarCaptureProcessor.setUsage(FlowElementUsage.STANDARD);
        return radarCaptureProcessor;
    }

    private DataSource backCameraDataSource() {
        var backCameraDataSource = FlowFactory.eINSTANCE.createDataSource();
        backCameraDataSource.setName("Back_Camera");
        backCameraDataSource.setVolume(6);
        backCameraDataSource.setUsage(FlowElementUsage.STANDARD);
        return backCameraDataSource;
    }

    private DataSource radarDataSource() {
        var radarDataSource = FlowFactory.eINSTANCE.createDataSource();
        radarDataSource.setName("Radar");
        radarDataSource.setVolume(8);
        return radarDataSource;
    }

    private Processor engineProcessor() {
        var engineProcessor = FlowFactory.eINSTANCE.createProcessor();
        engineProcessor.setName("Engine");
        engineProcessor.setVolume(8);
        engineProcessor.setConsumption(100);
        engineProcessor.setLoad(8);
        return engineProcessor;
    }

    private Processor gpuProcessor() {
        var gpuProcessor = FlowFactory.eINSTANCE.createProcessor();
        gpuProcessor.setName("GPU");
        gpuProcessor.setVolume(6);
        gpuProcessor.setLoad(6);
        gpuProcessor.setUsage(FlowElementUsage.STANDARD);
        return gpuProcessor;
    }

    private DataSource wifiDataSource() {
        var wifiDataSource = FlowFactory.eINSTANCE.createDataSource();
        wifiDataSource.setName("Wifi");
        wifiDataSource.setVolume(4);
        return wifiDataSource;
    }
}
