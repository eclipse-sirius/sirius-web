/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { useComponent } from '../extension/useComponent';
import { useData } from '../extension/useData';
import { useRepresentationMetadata } from '../representationmetadata/useRepresentationMetadata';
import {
  GQLRepresentationMetadata,
  GQLRepresentationMetadataQueryData,
} from '../representationmetadata/useRepresentationMetadata.types';
import { useSelection } from '../selection/useSelection';
import { Panels } from './Panels';
import { RepresentationNavigation } from './RepresentationNavigation';
import { useEditingContextEventSubscription } from './useEditingContextEventSubscription';
import {
  GQLEditingContextEventPayload,
  GQLRepresentationRenamedEventPayload,
} from './useEditingContextEventSubscription.types';
import {
  RepresentationComponentProps,
  RepresentationMetadata,
  WorkbenchProps,
  WorkbenchViewContribution,
} from './Workbench.types';
import {
  representationFactoryExtensionPoint,
  workbenchMainAreaExtensionPoint,
  workbenchViewContributionExtensionPoint,
} from './WorkbenchExtensionPoints';

const useWorkbenchStyles = makeStyles()(() => ({
  main: {
    display: 'grid',
    gridTemplateRows: 'minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
  },
  representationArea: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    overflow: 'auto',
  },
}));

type WorkbenchState = {
  id: string;
  displayedRepresentation: RepresentationMetadata | null;
  representations: RepresentationMetadata[];
};

const isRepresentationRenamedEventPayload = (
  payload: GQLEditingContextEventPayload | null
): payload is GQLRepresentationRenamedEventPayload =>
  !!payload && payload.__typename === 'RepresentationRenamedEventPayload';

const getRepresentationsMetaData = (data: GQLRepresentationMetadataQueryData | null): GQLRepresentationMetadata[] =>
  data?.viewer.editingContext.representations.edges.map((edge) => edge.node) ?? [];

export const Workbench = ({
  editingContextId,
  initialRepresentationSelected,
  onRepresentationSelected,
  readOnly,
}: WorkbenchProps) => {
  const { classes } = useWorkbenchStyles();
  const [state, setState] = useState<WorkbenchState>({
    id: crypto.randomUUID(),
    displayedRepresentation: initialRepresentationSelected,
    representations: initialRepresentationSelected ? [initialRepresentationSelected] : [],
  });

  const { selection, setSelection } = useSelection();
  const { data: representationMetadataQueryData } = useRepresentationMetadata(editingContextId, selection);
  const { payload: editingContextEventPayload } = useEditingContextEventSubscription(editingContextId);

  const { data: representationFactories } = useData(representationFactoryExtensionPoint);
  useEffect(() => {
    if (isRepresentationRenamedEventPayload(editingContextEventPayload)) {
      const { representationId, newLabel } = editingContextEventPayload;
      const representations = [...state.representations];

      for (var i = 0; i < representations.length; i++) {
        const representation = representations[i];
        if (!!representation && representation.id === representationId) {
          representation.label = newLabel;
        }
      }

      setState((prevState) => ({
        ...prevState,
        representations: representations,
      }));
    }
  }, [editingContextEventPayload]);

  // When opening a representation
  useEffect(() => {
    const selectedElementRepresentationMetaData = getRepresentationsMetaData(representationMetadataQueryData);
    if (
      selectedElementRepresentationMetaData.length > 0 &&
      selectedElementRepresentationMetaData[0]?.id !== state.displayedRepresentation?.id
    ) {
      const displayedRepresentation = selectedElementRepresentationMetaData[0];

      const representations = [...state.representations];
      const newRepresentations = selectedElementRepresentationMetaData.filter(
        (selectedRepresentation) =>
          !representations.find((representation) => selectedRepresentation.id === representation.id)
      );

      const newSelectedRepresentations = [...representations, ...newRepresentations];

      setState((prevState) => ({
        ...prevState,
        displayedRepresentation: displayedRepresentation ? displayedRepresentation : null,
        representations: newSelectedRepresentations,
      }));
    }
  }, [representationMetadataQueryData, selection]);

  const onClose = (representationToHide: RepresentationMetadata) => {
    const previousIndex = state.representations.findIndex(
      (representation) => state.displayedRepresentation && representation.id === state.displayedRepresentation.id
    );
    const newRepresentations = state.representations.filter(
      (representation) => representation.id !== representationToHide.id
    );

    if (newRepresentations.length === 0) {
      // There are no representations anymore
      setState((prevState) => ({
        ...prevState,
        displayedRepresentation: null,
        representations: [],
      }));
    } else {
      const newIndex = newRepresentations.findIndex(
        (representation) => state.displayedRepresentation && representation.id === state.displayedRepresentation.id
      );

      if (newIndex !== -1) {
        // The previously displayed representation has not been closed
        setState((prevState) => ({
          ...prevState,
          representations: newRepresentations,
        }));
      } else if (newRepresentations.length === previousIndex) {
        // The previous representation has been closed and it was the last one
        const displayedRepresentation = newRepresentations[previousIndex - 1];
        setState((prevState) => ({
          ...prevState,
          displayedRepresentation: displayedRepresentation ? displayedRepresentation : null,
          representations: newRepresentations,
        }));
      } else {
        const displayedRepresentation = newRepresentations[previousIndex];
        setState((prevState) => ({
          ...prevState,
          displayedRepresentation: displayedRepresentation ? displayedRepresentation : null,
          representations: newRepresentations,
        }));
      }
    }
  };

  useEffect(() => {
    if (state.displayedRepresentation && state.displayedRepresentation.id !== initialRepresentationSelected?.id) {
      onRepresentationSelected(state.displayedRepresentation);
    } else if (state.displayedRepresentation === null && initialRepresentationSelected) {
      onRepresentationSelected(null);
    }
  }, [onRepresentationSelected, initialRepresentationSelected, state.displayedRepresentation]);

  const workbenchViewLeftSideContributions: WorkbenchViewContribution[] = [];
  const workbenchViewRightSideContributions: WorkbenchViewContribution[] = [];

  const { data: workbenchViewContributions } = useData(workbenchViewContributionExtensionPoint);
  for (const workbenchViewContribution of workbenchViewContributions) {
    if (workbenchViewContribution.side === 'left') {
      workbenchViewLeftSideContributions.push(workbenchViewContribution);
    } else if (workbenchViewContribution.side === 'right') {
      workbenchViewRightSideContributions.push(workbenchViewContribution);
    }
  }

  const { Component: MainComponent } = useComponent(workbenchMainAreaExtensionPoint);
  let main = <MainComponent editingContextId={editingContextId} readOnly={readOnly} />;

  const onRepresentationClick = (representation: RepresentationMetadata) => {
    setSelection({ entries: [{ id: representation.id }] });
  };

  if (state.displayedRepresentation) {
    const metaData = state.displayedRepresentation;
    const RepresentationComponent = representationFactories
      .map((representationFactory) => representationFactory(metaData))
      .find((component) => component != null);
    const props: RepresentationComponentProps = {
      editingContextId,
      readOnly,
      representationId: state.displayedRepresentation.id,
    };
    if (RepresentationComponent) {
      main = (
        <div className={classes.representationArea} data-testid="representation-area">
          <RepresentationNavigation
            representations={state.representations}
            displayedRepresentation={metaData}
            onRepresentationClick={onRepresentationClick}
            onClose={onClose}
          />
          <RepresentationComponent key={`${editingContextId}#${metaData.id}`} {...props} />
        </div>
      );
    }
  }

  return (
    <>
      <Panels
        editingContextId={editingContextId}
        readOnly={readOnly}
        leftContributions={workbenchViewLeftSideContributions}
        leftPanelInitialSize={25}
        rightContributions={workbenchViewRightSideContributions}
        rightPanelInitialSize={25}
        mainArea={main}
      />
    </>
  );
};
