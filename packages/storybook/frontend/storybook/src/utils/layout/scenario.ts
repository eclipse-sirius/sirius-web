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
import { GQLDiagram } from '@eclipse-sirius/sirius-components-diagrams';
import { assembleDiagram } from './diagramConstructionUtils';

const allJsonFiles = (import.meta as any).glob('../../ressources/layout/**/*.json', { eager: true });

interface ScenarioData {
  description?: any;
  representations: Record<string, any>;
}

const groupedScenarios: Record<string, ScenarioData> = {};

Object.entries(allJsonFiles).forEach(([path, data]) => {
  const parts = path.split('/');
  const layoutIndex = parts.indexOf('layout');

  if (layoutIndex !== -1 && parts.length >= layoutIndex + 4) {
    const folderType = parts[layoutIndex + 1];
    const diagramType = parts[layoutIndex + 2];
    const fileName = parts[layoutIndex + 3];
    const scenarioName = fileName.replace('.json', '');

    if (!groupedScenarios[diagramType]) {
      groupedScenarios[diagramType] = {
        representations: {},
      };
    }

    if (folderType === 'descriptions') {
      groupedScenarios[diagramType].description = data;
    } else if (folderType === 'representations') {
      groupedScenarios[diagramType].representations[scenarioName] = data;
    }
  }
});

export const getScenariosMap = (): Record<string, () => GQLDiagram> => {
  const scenarioGenerators: Record<string, () => GQLDiagram> = {};

  for (const [diagramType, data] of Object.entries(groupedScenarios)) {
    if (data.description && data.representations) {
      for (const [scenarioName, representationData] of Object.entries(data.representations)) {
        const uniqueKey = `${scenarioName}`;
        scenarioGenerators[uniqueKey] = () => {
          const diagram = assembleDiagram(representationData, data.description);
          diagram.metadata.label = `${scenarioName}`;
          return diagram;
        };
      }
    }
  }

  return scenarioGenerators;
};
