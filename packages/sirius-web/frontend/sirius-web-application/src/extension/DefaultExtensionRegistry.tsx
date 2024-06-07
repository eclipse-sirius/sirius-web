/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import {
  ExtensionRegistry,
  RepresentationComponentFactory,
  RepresentationMetadata,
  WorkbenchViewContribution,
  representationFactoryExtensionPoint,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from '@eclipse-sirius/sirius-components-core';
import { DeckRepresentation } from '@eclipse-sirius/sirius-components-deck';
import { DiagramRepresentation } from '@eclipse-sirius/sirius-components-diagrams';
import { FormDescriptionEditorRepresentation } from '@eclipse-sirius/sirius-components-formdescriptioneditors';
import {
  DetailsView,
  FormRepresentation,
  RelatedElementsView,
  RepresentationsView,
} from '@eclipse-sirius/sirius-components-forms';
import { GanttRepresentation } from '@eclipse-sirius/sirius-components-gantt';
import { PortalRepresentation } from '@eclipse-sirius/sirius-components-portals';
import { ExplorerView } from '@eclipse-sirius/sirius-components-trees';
import { ValidationView } from '@eclipse-sirius/sirius-components-validation';
import AccountTreeIcon from '@material-ui/icons/AccountTree';
import Filter from '@material-ui/icons/Filter';
import LinkIcon from '@material-ui/icons/Link';
import MenuIcon from '@material-ui/icons/Menu';
import WarningIcon from '@material-ui/icons/Warning';
import { OnboardArea } from '../onboarding/OnboardArea';

const getType = (representation: RepresentationMetadata): string | null => {
  const query = representation.kind.substring(representation.kind.indexOf('?') + 1, representation.kind.length);
  const params = new URLSearchParams(query);
  const type = params.get('type');
  return type;
};

const defaultExtensionRegistry = new ExtensionRegistry();

/*******************************************************************************
 *
 * Workbench main area
 *
 * Used to register the component used by default when no representation is opened
 *
 *******************************************************************************/
defaultExtensionRegistry.addComponent(workbenchMainAreaExtensionPoint, {
  identifier: `siriusweb_${workbenchMainAreaExtensionPoint.identifier}`,
  Component: OnboardArea,
});

/*******************************************************************************
 *
 * Workbench views
 *
 * Used to register all the views available in the left and right of the workbench
 *
 *******************************************************************************/
const workbenchViewContributions: WorkbenchViewContribution[] = [
  {
    side: 'left',
    title: 'Explorer',
    icon: <AccountTreeIcon />,
    component: ExplorerView,
  },
  {
    side: 'left',
    title: 'Validation',
    icon: <WarningIcon />,
    component: ValidationView,
  },
  {
    side: 'right',
    title: 'Details',
    icon: <MenuIcon />,
    component: DetailsView,
  },
  {
    side: 'right',
    title: 'Representations',
    icon: <Filter />,
    component: RepresentationsView,
  },
  {
    side: 'right',
    title: 'Related Elements',
    icon: <LinkIcon />,
    component: RelatedElementsView,
  },
];
defaultExtensionRegistry.putData(workbenchViewContributionExtensionPoint, {
  identifier: `siriusweb_${workbenchViewContributionExtensionPoint.identifier}`,
  data: workbenchViewContributions,
});

/*******************************************************************************
 *
 * Workbench representation factories
 *
 * Used to register all the type of representations that are supported in Sirius Web
 *
 *******************************************************************************/

const representationFactories: RepresentationComponentFactory[] = [
  (representationMetadata) => (getType(representationMetadata) === 'Diagram' ? DiagramRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Form' ? FormRepresentation : null),
  (representationMetadata) =>
    getType(representationMetadata) === 'FormDescriptionEditor' ? FormDescriptionEditorRepresentation : null,
  (representationMetadata) => (getType(representationMetadata) === 'Gantt' ? GanttRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Deck' ? DeckRepresentation : null),
  (representationMetadata) => (getType(representationMetadata) === 'Portal' ? PortalRepresentation : null),
];

defaultExtensionRegistry.putData(representationFactoryExtensionPoint, {
  identifier: `siriusweb_${representationFactoryExtensionPoint.identifier}`,
  data: representationFactories,
});

export { defaultExtensionRegistry };
